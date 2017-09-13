package jstack.eu.messagingApp.UI;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.VerticalLayout;
import jstack.eu.messagingApp.StaticUser;
import jstack.eu.messagingApp.models.Conversation;
import jstack.eu.messagingApp.models.User;
import jstack.eu.messagingApp.repositories.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@UIScope
@SpringView(name = "conversation")
public class ConversationView extends GridLayout implements View {
    private Conversation conversation = new Conversation();

    private ConversationRepository conversationRepository;
    private MessageFormView messageFormView;

    @Autowired
    public ConversationView(ConversationRepository conversationRepository, MessageFormView messageFormView) {
        this.conversationRepository = conversationRepository;
        this.messageFormView = messageFormView;
    }

    @PostConstruct
    public void init() {

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        this.removeAllComponents();
        if (event.getParameters() != null) {
            // split at "/", add each part as a label
            String[] msgs = event.getParameters().split("/");
            for (String msg : msgs) {
                this.conversation = conversationRepository.findOne(msg);
                this.setRows(2);
                this.setColumns(2);
                //big box for messages
                VerticalLayout messageArea = new VerticalLayout();
                messageArea.setStyleName("messageArea");

                messageFormView.init(messageArea, conversation);
                if (conversation != null)
                    conversation.getMessages().forEach(message -> messageFormView.addMessage(message, false));


                this.addComponent(messageFormView, 0, 0, 0, 0);
                this.addComponent(messageArea, 1, 0, 1, 0);
            }
        }
    }
}
