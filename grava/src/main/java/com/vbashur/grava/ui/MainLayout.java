package com.vbashur.grava.ui;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vbashur.grava.Player;
import com.vbashur.grava.game.GravaEvent;

@SpringUI
public class MainLayout extends UI implements ApplicationEventPublisherAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ApplicationEventPublisher eventPublisher;

	private PlayerComponent playerA;
	
	private PlayerComponent playerB;
	
static int iterGlobal = 0;
	@Override
	protected void init(VaadinRequest request) {
		Button but = new Button("Click it!");
		playerA = new PlayerComponent(Player.SPOUNGE_BOB);
		playerB = new PlayerComponent(Player.PATRICK_STAR);
		
		Map<Integer, Integer> m = new HashMap<>();
		m.put(0,0);
		m.put(1,1);
		m.put(2,2);
		m.put(3,3);
		m.put(4,4);
		m.put(5,5);
		m.put(6,6);
		
		but.addClickListener(new ClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				
				
				if (iterGlobal % 2 == 0) {
					playerB.setEnabled(false);
					playerA.updateState(m);					
				} else {
					playerB.updateState(m);
					playerA.setEnabled(false);
					
				}
				for (int iter1 = 0; iter1 < m.size(); ++iter1) {
					int val = m.get(iter1);
					val += iterGlobal;
					m.put(iter1, val);
							
				}
				iterGlobal += 1;
//				eventPublisher.publishEvent(new GravaEvent.OnCapturingStone("hui", 1));
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
		vl.addComponent(playerA);
		vl.addComponent(playerB);
		setContent(vl);
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.eventPublisher = applicationEventPublisher;		
	}

}
