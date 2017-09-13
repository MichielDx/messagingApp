package jstack.eu.messagingApp.UI;

import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.server.ThemeResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import jstack.eu.messagingApp.StaticUser;
import jstack.eu.messagingApp.models.Conversation;
import jstack.eu.messagingApp.models.Message;
import jstack.eu.messagingApp.models.User;
import jstack.eu.messagingApp.repositories.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@UIScope
@SpringView(name = "messageForm")
public class MessageFormView extends VerticalLayout implements View {
    private TextArea message;
    private TextField usernameField;
    private Conversation conversation;
    private Message msg;
    private VerticalLayout messageArea;
    private String currentUsername;
    private String usernameStyle;

    private ConversationRepository conversationRepository;
    private EditModal editModal;

    @Autowired
    public MessageFormView(ConversationRepository conversationRepository, EditModal editModal) {
        this.conversationRepository = conversationRepository;
        this.editModal = editModal;
        this.message = new TextArea("Type your message here:");
        this.usernameField = new TextField("Username:");
        this.currentUsername = "";
        this.usernameStyle = "leftMessage";
    }

    @PostConstruct
    public void init() {
        Button homeButton = new Button("Home");
        homeButton.addClickListener(e -> getUI().getNavigator().navigateTo(""));

        Button sendButton = new Button("Send");
        sendButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        sendButton.addClickListener(e -> {
            msg = new Message(message.getValue(), StaticUser.user);
            addMessage(msg, true);
        });
        this.addComponents(homeButton, usernameField, message, sendButton);
        this.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
    }

    public void init(VerticalLayout messageArea, Conversation conversation) {
        this.messageArea = messageArea;
        this.conversation = conversation;
        usernameField.focus();
    }

    public void addMessage(Message msg, boolean add) {
        VerticalLayout messageLayout = new VerticalLayout();

        TextArea messages = new TextArea(msg.getUser().getUsername() + "'s message:");
        messages.setStyleName("message");
        messages.setEnabled(false);
        messages.setWordWrap(true);
        messages.setValue(msg.getMessage());
        message.clear();

        if (!currentUsername.equals(msg.getUser().getUsername()) && !currentUsername.isEmpty()) {
            usernameStyle = usernameStyle.equals("leftMessage") ? "rightMessage" : "leftMessage";
        }
        messageLayout.setStyleName(usernameStyle);

        if (add)
            conversation.addMessage(msg);
        conversationRepository.save(conversation);



        Button editButton = new Button(new ThemeResource("images/edit.png"));
        editButton.addClickListener((Button.ClickListener) clickEvent -> {
            Window editModalWindow = new Window("Edit message");
            editModal.init(messages, msg, conversation, editModalWindow);
            editModalWindow.setContent(editModal);
            editModalWindow.center();
            getUI().addWindow(editModalWindow);
        });

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Button deleteButton = new Button(new ThemeResource("images/close-small.png"));
        deleteButton.setStyleName("deleteButton");
        deleteButton.addClickListener((Button.ClickListener) clickEvent -> {
            conversation.removeMessage(msg);
            conversationRepository.save(conversation);
            messageLayout.removeComponent(messages);
            messageLayout.removeComponent(horizontalLayout);
        });
        horizontalLayout.addComponents(editButton,deleteButton);

        messageLayout.addComponents(messages, horizontalLayout);
        messageArea.addComponents(messageLayout);

        currentUsername = msg.getUser().getUsername();
    }
}
