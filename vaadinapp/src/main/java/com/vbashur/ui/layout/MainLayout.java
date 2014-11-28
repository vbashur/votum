package com.vbashur.ui.layout;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class MainLayout extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected CssLayout header;
	protected CssLayout main;
	protected CssLayout footer;

	public MainLayout() {
		setSizeFull();
		addStyleName(SampleLayout.STYLE_MAIN_WRAPPER);

		initHeader();
		initMain();
		initFooter();
	}

	public void setMainContent(Component mainContent) {
		main.removeAllComponents();
		main.addComponent(mainContent);
	}

	public void setFooter(Component footerContent) {
		footer.removeAllComponents();
		footer.addComponent(footerContent);
	}


	protected void initHeader() {
		header = new CssLayout();
		header.addStyleName(SampleLayout.STYLE_HEADER);
		header.setWidth(100, Unit.PERCENTAGE);
		addComponent(header);
	}

	protected void initMain() {
		main = new CssLayout();
		main.setSizeFull();
		main.addStyleName(SampleLayout.STYLE_MAIN_CONTENT);
		addComponent(main);
		setExpandRatio(main, 1.0f);
	}

	protected void initFooter() {
		footer = new CssLayout();
		footer.setWidth(100, Unit.PERCENTAGE);
		footer.addStyleName(SampleLayout.STYLE_MAIN_FOOTER);
		addComponent(footer);

		Label footerLabel = new Label();
		footerLabel.setContentMode(ContentMode.HTML);
		footerLabel.setValue("Label nghe-he-he2");
		footerLabel.setWidth(100, Unit.PERCENTAGE);
		footer.addComponent(footerLabel);
	}
}
