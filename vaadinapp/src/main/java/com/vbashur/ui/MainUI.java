package com.vbashur.ui;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vbashur.ui.layout.MainLayout;



@SuppressWarnings("serial")
@Theme("mytheme") //runo
public class MainUI extends UI  {
    
    protected WebApplicationContext applicationContext;

    @Override
    protected void init(VaadinRequest request) {
    	
        final MainLayout layout = new MainLayout();
        
        layout.setMargin(true);
        setContent(layout);
        
        applicationContext = WebApplicationContextUtils.getWebApplicationContext(VaadinServlet.getCurrent().getServletContext());
        
        Button button = new Button("Click Me");
        button.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                layout.addComponent(new Label("Thanks"));
            }
        });
        layout.addComponent(button);        
    }

}
