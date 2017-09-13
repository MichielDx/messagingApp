package jstack.eu.messagingApp.UI;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import jstack.eu.messagingApp.models.Conversation;
import jstack.eu.messagingApp.models.Message;
import jstack.eu.messagingApp.repositories.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@UIScope
@SpringView(name = "editModal")
public class EditModal extends VerticalLayout implements View {
    private TextArea messages;
    private Message msg;
    private Conversation conversation;
    private Window editModalWindow;
    private TextArea editMessageArea;

    private ConversationRepository conversationRepository;

    @Autowired
    public EditModal(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    @PostConstruct
    public void init() {
        editMessageArea = new TextArea();
        Button saveEditButton = new Button("Save");
        Button cancelButton = new Button("Cancel");
        saveEditButton.setStyleName("editModalButton");
        cancelButton.setStyleName("editModalButton");
        cancelButton.addClickListener((Button.ClickListener) clickEvent -> this.getUI().removeWindow(editModalWindow));
        saveEditButton.addClickListener((Button.ClickListener) clickEvent -> {
            String value = editMessageArea.getValue();
            conversation.getMessages().get(conversation.getMessages().indexOf(msg)).setMessage(value);
            messages.setValue(value);
            conversationRepository.save(conversation);
            this.getUI().removeWindow(editModalWindow);
        });
        this.addComponents(editMessageArea, new HorizontalLayout(saveEditButton, cancelButton));
    }

    public void init(TextArea messages, Message msg, Conversation conversation, Window editModalWindow) {
        this.messages = messages;
        this.msg = msg;
        this.conversation = conversation;
        this.editModalWindow = editModalWindow;
        this.editMessageArea.setValue(msg.getMessage());
    }
}
