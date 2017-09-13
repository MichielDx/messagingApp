package jstack.eu.messagingApp.UI;

import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import jstack.eu.messagingApp.StaticUser;
import jstack.eu.messagingApp.models.User;
import jstack.eu.messagingApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;

@UIScope
@SpringView(name = "loginView")
public class LoginView extends VerticalLayout implements View {
    private RegisterModal registerModal;
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public LoginView(RegisterModal registerModal, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.registerModal = registerModal;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostConstruct
    public void init() {
        TextField usernameField = new TextField("Username:");
        PasswordField passwordField = new PasswordField("Password:");

        Button loginButton = new Button("Login");
        loginButton.setStyleName("loginFormButton");
        loginButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        Button registerButton = new Button("Register");
        registerButton.setStyleName("loginFormButton");

        loginButton.addClickListener((Button.ClickListener) clickEvent -> {
            User user = userRepository.findByUsername(usernameField.getValue());
            if (bCryptPasswordEncoder.matches(passwordField.getValue(), user.getPassword())) {
                StaticUser.user = user;
                getUI().getNavigator().navigateTo("home");
            } else {
                getUI().addWindow(registerModalWindow(usernameField, passwordField));
            }
        });

        registerButton.addClickListener((Button.ClickListener) clickEvent -> getUI().addWindow(registerModalWindow(usernameField, passwordField)));

        HorizontalLayout horizontalLayout = new HorizontalLayout(new VerticalLayout(usernameField, passwordField, new HorizontalLayout(loginButton, registerButton)));
        horizontalLayout.setStyleName("loginForm");
        this.addComponent(horizontalLayout);
        this.setSizeFull();
        this.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);
    }

    private Window registerModalWindow(TextField usernameField, PasswordField passwordField) {
        Window registerModalWindow = new Window("Edit message");
        registerModal.init(usernameField.getValue(), passwordField.getValue(), registerModalWindow);
        registerModalWindow.setContent(registerModal);
        registerModalWindow.center();
        return registerModalWindow;
    }
}
