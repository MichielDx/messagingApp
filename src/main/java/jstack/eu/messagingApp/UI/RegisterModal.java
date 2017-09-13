package jstack.eu.messagingApp.UI;

import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import jstack.eu.messagingApp.models.User;
import jstack.eu.messagingApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;

@UIScope
@SpringView(name = "registerModal")
public class RegisterModal extends VerticalLayout implements View {
    private Window registerModalWindow;
    private TextField usernameField;
    private PasswordField passwordField;

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public RegisterModal(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostConstruct
    public void init() {
        usernameField = new TextField("Username:");
        usernameField.focus();
        passwordField = new PasswordField("Password:");

        Button registerButton = new Button("Register");
        registerButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        registerButton.addClickListener((Button.ClickListener) clickEvent -> {
            String username = usernameField.getValue();
            if (userRepository.findByUsername(username) == null) {
                userRepository.save(new User(username, bCryptPasswordEncoder.encode(passwordField.getValue())));
                this.getUI().removeWindow(registerModalWindow);
            } else {
                usernameField.focus();
            }
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setStyleName("editModalButton");
        cancelButton.addClickListener((Button.ClickListener) clickEvent -> this.getUI().removeWindow(registerModalWindow));

        this.addComponents(usernameField, passwordField, new HorizontalLayout(registerButton, cancelButton));
    }


    public void init(String username, String password, Window registerModalWindow) {
        usernameField.setValue(username);
        passwordField.setValue(password);
        this.registerModalWindow = registerModalWindow;
    }
}
