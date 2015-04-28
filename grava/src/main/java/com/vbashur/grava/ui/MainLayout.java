package com.vbashur.grava.ui;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringUI
public class MainLayout extends UI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void init(VaadinRequest request) {
		Button but = new Button("Click it!");
		but.addClickListener(new ClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				System.out.println("hi");
				
			}
		});
		
		VerticalLayout vl = new VerticalLayout();
		vl.setMargin(true);
		vl.setWidth(100, Unit.PERCENTAGE);
		vl.setHeight(100, Unit.PERCENTAGE);
		
		Label caption = new Label("Grava Hal Application");
		vl.addComponent(caption);
		vl.addComponent(but);
		setContent(vl);
	}

}
