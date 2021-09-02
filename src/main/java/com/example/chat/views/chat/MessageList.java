package com.example.chat.views.chat;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import org.vaadin.artur.Avataaar;

public class MessageList extends Div {

    public MessageList() {
        setClassName(getClass().getSimpleName());
    }

    public void addMessage(String sender, Avataaar avatar, String text, boolean isCurrentUser) {
        // Container of the name displayed under avatar
        Span fromContainer = new Span(new Text(sender));
        // Naming its class to modify its style easily
        fromContainer.addClassName(getClass().getSimpleName() + "-name");

        // Container of the message sent or received
        Div textContainer = new Div(new Text(text));
        textContainer.addClassName(getClass().getSimpleName() + "-bubble");

        // Container of the avatar
        Div avatarContainer = new Div(avatar, fromContainer);
        avatarContainer.addClassName(getClass().getSimpleName() + "-avatar");

        // Container of the whole
        Div line = new Div(avatarContainer, textContainer);
        line.addClassName(getClass().getSimpleName() + "-row");
        add(line);

        //Modify display colors (css) of current user
        if (isCurrentUser) {
            line.addClassName(getClass().getSimpleName() + "-row-currentUser");
            textContainer.addClassName(getClass().getSimpleName() + "-bubble-currentUser");
        }

        line.getElement().callJsFunction("scrollIntoView");
    }

}
