package com.vbashur.grava.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vbashur.grava.Player;
import com.vbashur.grava.game.GravaArbiter;
import com.vbashur.grava.game.PlayerInfo;
import com.vbashur.grava.game.ResponseHandler;

@SpringUI
@PreserveOnRefresh
public class MainLayout extends UI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private GravaArbiter gravaArbiter;

	@Autowired
	private ResponseHandler respHandler;

	@Override
	protected void init(VaadinRequest request) {

		VerticalLayout vl = new VerticalLayout();
		vl.setMargin(true);
		vl.setWidth(100, Unit.PERCENTAGE);
		vl.setHeight(100, Unit.PERCENTAGE);

		Label caption = new Label("<b>Grava Hal - Bikini Bottom Edition</b>", ContentMode.HTML);
		vl.addComponent(caption);

		PlayerInfo playerA = new PlayerInfo(Player.SPOUNGE_BOB, gravaArbiter);
		PlayerInfo playerB = new PlayerInfo(Player.PATRICK_STAR, gravaArbiter);
		
		respHandler.registerPlayers(playerA, playerB);

		vl.addComponent(playerA.getPlayerComponent());
		vl.addComponent(playerB.getPlayerComponent());
		vl.setExpandRatio(playerB.getPlayerComponent(), 1.0f);
		playerA.refreshComponent();
		playerB.refreshComponent();
		playerB.getPlayerComponent().setEnabled(false);
		Notification.show(playerA.getPlayer().getName() + ", please start the game", Type.HUMANIZED_MESSAGE);

		setContent(vl);
	}

}
