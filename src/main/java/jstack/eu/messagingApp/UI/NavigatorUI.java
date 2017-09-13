package jstack.eu.messagingApp.UI;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import jstack.eu.messagingApp.models.Conversation;
import jstack.eu.messagingApp.models.User;
import jstack.eu.messagingApp.repositories.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.annotation.WebServlet;
import java.util.List;

@Theme("mytheme")
@SpringUI(path = "index")
public class NavigatorUI extends UI {
    private User user;

    private ConversationRepository conversationRepository;
    private HomeView homeView;
    private ConversationView conversationView;
    private LoginView loginView;

    @Autowired
    public NavigatorUI(ConversationRepository conversationRepository, HomeView homeView, ConversationView conversationView, LoginView loginView) {
        this.conversationRepository = conversationRepository;
        this.homeView = homeView;
        this.conversationView = conversationView;
        this.loginView = loginView;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        List<Conversation> conversations = conversationRepository.findAll();

        Navigator navigator = new Navigator(this, this);
        navigator.addView("", loginView);
        navigator.addView("home", homeView);
        // Create and register the views

        conversations.forEach(conversation -> navigator.addView("/conversation", conversationView));
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = NavigatorUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}

