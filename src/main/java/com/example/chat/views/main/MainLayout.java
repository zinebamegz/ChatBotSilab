package com.example.chat.views.main;

import com.example.chat.views.Contact.ContactUsView;
import com.example.chat.views.chat.ChatView;
import com.example.chat.views.home.HomeView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.Route;
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
//@Route("")
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

        //Drawer Toggle set up
        DrawerToggle drawerToggleButton = new DrawerToggle();
        Icon icon = new Icon(VaadinIcon.LINES);
        icon.setColor("#ecf3f8");
        drawerToggleButton.setIcon(icon);

        addToNavbar(true, drawerToggleButton);

        //create tabs and menu list
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
     * Method to get all available tabs
     * In this method your tabs need to be explicitly created
     * @return array of tabs
     */
    private static Tab[] getAvailableTabs() {
        final List<Tab> tabs = new ArrayList<>();
        //tabs.add(createTab("Home", HomeView.class, new Icon(VaadinIcon.HOME)));
        tabs.add(createTab("ChatBot", ChatView.class, new Icon(VaadinIcon.COMMENT_ELLIPSIS)));
        //tabs.add(createTab("Contact us", ContactUsView.class, new Icon(VaadinIcon.ENVELOPE)));
        return tabs.toArray(new Tab[tabs.size()]);
    }

    /**
     * Method to create a tab
     * @param title the title of the tab to be displayed
     * @param viewClass the class to be referenced by the tab
     * @param icon the icon to display next to the tab
     * @return tab
     */
    private static Tab createTab(String title, Class<? extends Component> viewClass, Icon icon) {
       return createTab(populateLink(new RouterLink(null, viewClass), title), icon);
    }

    /**
     * Method to create a tab with a component
     * @param content contenu du tab
     * @param icon icon the icon to display next to the tab
     * @return tab
     */
    private static Tab createTab(Component content, Icon icon) {
        final Tab tab = new Tab();
        tab.setClassName("tab");
        HorizontalLayout layout = new HorizontalLayout(icon,content);
        tab.add(layout);
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
