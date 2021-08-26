package com.example.chat.views.chat;

import com.example.chat.views.main.MainLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.spring.scopes.VaadinUIScope;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vaadin.artur.Avataaar;

import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Route(value = "chat", layout = MainLayout.class)
//@RouteAlias(value = "", layout = MainLayout.class)
@PageTitle("ChatBot")
@CssImport("styles/views/chat/chat-view.css")
@Component
@Scope(VaadinUIScope.VAADIN_UI_SCOPE_NAME)
public class ChatView extends VerticalLayout {

    private final UI ui;
    private final MessageList messageList = new MessageList();
    private final TextField message = new TextField();
    private final Chat chatSession;
    private final ScheduledExecutorService executorService;
    private String welcomeMessage;

    public ChatView(Bot alice, ScheduledExecutorService executorService) throws InterruptedException {
        this.executorService = executorService;
        ui = UI.getCurrent();
        chatSession = new Chat(alice);

        //Message affiché avant de taper un message
        message.setPlaceholder("Tapez ici pour poser une question...");
        message.setSizeFull();

        Icon icon = new Icon(VaadinIcon.LOCATION_ARROW);
        icon.setColor("#ecf3f8");

        Button send = new Button(icon, event -> sendMessage());
        send.addClickShortcut(Key.ENTER);

        HorizontalLayout inputLayout = new HorizontalLayout(message, send);
        inputLayout.addClassName("inputLayout");

        add(messageList, inputLayout);
        expand(messageList);
        setSizeFull();

        welcomeMessage= "Bonjour ! Je m'appelle Zee et je suis là pour t'assister dans ton parcours IMS.";
        //To add an emoji using unicode (link codes:https://apps.timwhitlock.info/emoji/tables/unicode)
        welcomeMessage += new String(Character.toChars(0x1F601));
       // welcomeMessage += " What do you want to know ?";
        messageList.addMessage("Bot", new Avataaar("Bot"), welcomeMessage, false );
    }

    /*private void welcomeMessage() throws InterruptedException {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        messageList.addMessage("Bot", new Avataaar("Bot"), "Bienvenu !", false );
    }*/

    private void sendMessage() {
        String text = message.getValue();

        // Avataaar : Generates an human like avatar image from a string. The string can be anything such as name, email etc.
        messageList.addMessage("Vous", new Avataaar("Human"), text, true);
        message.clear();

        //Generating the answer and displaying it
        executorService.schedule(() -> {
            String answer = chatSession.multisentenceRespond(text);
            ui.access(() -> messageList.addMessage("Bot", new Avataaar("Bot"), answer, false));
        }, new Random().ints(300, 500).findFirst().getAsInt(), TimeUnit.MILLISECONDS);
    }

}
