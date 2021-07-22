package com.example.chat.views.main;

import com.example.chat.views.chat.ChatView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.component.html.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The main view is a top-level placeholder for other views.
 */
@JsModule("./styles/shared-styles.js")
@PWA(name = "Zee", shortName = "Zee")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@CssImport(value = "./styles/app-layout-styles.css", themeFor = "vaadin-app-layout")
@Push
public class MainLayout extends AppLayout {

    private final Tabs menu;

    /**
     * Method to set the main layout of the WebApp
     */
    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        //Image logo = new Image("./images/logo.png", "LogoSilab");
        //logo.setHeight("15px");

        DrawerToggle drawerToggleButton = new DrawerToggle();
        Icon icon = new Icon(VaadinIcon.LINES);
        icon.setColor("#ecf3f8");
        drawerToggleButton.setIcon(icon);

        addToNavbar(true, drawerToggleButton);
        menu = createMenuTabs();

        addToDrawer(createDrawerContent(menu));
    }

    private Component createDrawerContent(Tabs menu) {
        VerticalLayout layout = new VerticalLayout();
        layout.setClassName("sidemenu-menu");
        layout.setSizeFull();
        layout.setPadding(true);
        layout.setSpacing(true);
        layout.getThemeList().set("spacing-s", true);

        // To align the whole menu and its component :
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        HorizontalLayout logoLayout = new HorizontalLayout();
        logoLayout.setId("logo");
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        Image logo = new Image("images/logo.png", "LogoSilab");
        logo.setHeight("100px");
        logoLayout.add(logo);
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        //H1 header = new H1("Zee");
        //header.setHeight("20px");
        // Pour la manipuler dans le CSS :
        //header.setClassName("h11");
        // logoLayout.add(header);
        layout.add(logoLayout, menu);
        return layout;
    }

    /**
     * Method to create a menu and its different tabs and customize them
     * @return tabs
     */
    private static Tabs createMenuTabs() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId("tabs");
        tabs.add(getAvailableTabs());
        return tabs;
    }

    /**
     * Method to return a all available tabs
     * @return array of tabs
     */
    private static Tab[] getAvailableTabs() {
        final List<Tab> tabs = new ArrayList<>();
        tabs.add(createTab("ChatBot", ChatView.class));
        return tabs.toArray(new Tab[tabs.size()]);
    }

    /**
     * Method to create a tab
     * @param title the title of the tab to be displayed
     * @param viewClass the class to be referenced by the tab
     * @return tab
     */
    private static Tab createTab(String title, Class<? extends Component> viewClass) {
        return createTab(populateLink(new RouterLink(null, viewClass), title));
    }

    /**
     * Method to create a tab with a component
     * @param content contenu du tab
     * @return tab
     */
    private static Tab createTab(Component content) {
        final Tab tab = new Tab();
        tab.setClassName("tab");
        tab.add(content);
        return tab;
    }

    private static <T extends HasComponents> T populateLink(T a, String title) {
        a.add(title);
        return a;
    }


    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        selectTab();
    }


    private void selectTab() {
        String target = RouteConfiguration.forSessionScope().getUrl(getContent().getClass());
        Optional<Component> tabToSelect = menu.getChildren().filter(tab -> {
            Component child = tab.getChildren().findFirst().get();
            return child instanceof RouterLink && ((RouterLink) child).getHref().equals(target);
        }).findFirst();
        tabToSelect.ifPresent(tab -> menu.setSelectedTab((Tab) tab));
    }
}
