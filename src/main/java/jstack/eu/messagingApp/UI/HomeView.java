package jstack.eu.messagingApp.UI;

import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import jstack.eu.messagingApp.models.Conversation;
import jstack.eu.messagingApp.repositories.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

@UIScope
@SpringView(name = "home")
public class HomeView extends VerticalLayout implements View {
    @Autowired
    private ConversationRepository conversationRepository;

    public HomeView() {

    }

    @PostConstruct
    public void init() {

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        this.removeAllComponents();
        this.setStyleName("conversationGrid");
        List<Conversation> conversations = conversationRepository.findAll();
        // Create and register the views

        TextField conversationName = new TextField();
        conversationName.focus();
        conversationName.setPlaceholder("Conversation name");

        Button sendButton = new Button("Create conversation");
        sendButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        sendButton.addClickListener(e -> {
            Conversation conv = conversationRepository.insert(new Conversation(conversationName.getValue()));
            getUI().getNavigator().navigateTo("/conversation/" + conv.getId());
            Page.getCurrent().reload();
        });
        this.addComponents(conversationName, sendButton,
                new Label("<b>List of conversations</b>", ContentMode.HTML));

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
        conversations.forEach(conversation -> {
            Button button = new Button(conversation.getName());
            button.setStyleName("conversationButton");
            button.addClickListener((Button.ClickListener) clickEvent -> getUI().getNavigator().navigateTo("/conversation/" + conversation.getId()));
            horizontalLayout.addComponent(button);
        });

        this.addComponent(horizontalLayout);
    }
}
