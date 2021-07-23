package com.example.chat.views.Contact;

import com.example.chat.views.main.MainLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.spring.scopes.VaadinUIScope;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;



@Route(value = "ContactUsView", layout = MainLayout.class)
@PageTitle("ContactUsView")
@CssImport("styles/views/chat/chat-view.css")
@Component
@Scope(VaadinUIScope.VAADIN_UI_SCOPE_NAME)

public class ContactUsView  extends VerticalLayout {

    public ContactUsView() {
    }
}
