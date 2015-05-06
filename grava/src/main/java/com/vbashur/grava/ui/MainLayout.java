package com.vbashur.grava.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Push;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vbashur.grava.Const;
import com.vbashur.grava.game.PlayerInfo;
import com.vbashur.grava.game.PlayerInfoHolder;
import com.vbashur.grava.game.ResponseHandler;

@SpringUI
@PreserveOnRefresh
@Push
public class MainLayout extends UI implements Broadcaster.BroadcastListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ResponseHandler respHandler;

	@Autowired
	private PlayerInfoHolder playerInfoHolder;

	protected WebApplicationContext applicationContext;
	
	VerticalLayout vl;

	@SuppressWarnings("serial")
	@Override
	protected void init(VaadinRequest request) {

		vl = new VerticalLayout();
		vl.setMargin(true);
		vl.setWidth(100, Unit.PERCENTAGE);
		vl.setHeight(100, Unit.PERCENTAGE);

		Label caption = new Label("<b>Grava Hal - Bikini Bottom Edition</b>", ContentMode.HTML);
		vl.addComponent(caption);

		PlayerInfo playerA = playerInfoHolder.getPlayerA();//new PlayerInfo(Player.SPOUNGE_BOB, playerInfoHolder.getGravaArbiter(), playerInfoHolder.getGameBoardA());
		PlayerInfo playerB = playerInfoHolder.getPlayerB(); //new PlayerInfo(Player.PATRICK_STAR, playerInfoHolder.getGravaArbiter(), playerInfoHolder.getGameBoardB());

		PlayerComponent pc1 = new PlayerComponent(playerA.getPlayer(), playerInfoHolder.getGravaArbiter());
		PlayerComponent pc2 = new PlayerComponent(playerB.getPlayer(), playerInfoHolder.getGravaArbiter());
		
		playerInfoHolder.registerPlayerComponent(playerA.getPlayer(), pc1, UI.getCurrent().getPage());
		playerInfoHolder.registerPlayerComponent(playerB.getPlayer(), pc2, UI.getCurrent().getPage());
		
		respHandler.registerPlayers(playerA, playerB, playerInfoHolder);
//		respHandler.registerComponents(pc1, pc2);
		
		 Broadcaster.register(this);

		Button resetBtn = new Button("reset");
		resetBtn.setWidth(Const.DEFAULT_WIDTH, Unit.PIXELS);
		resetBtn.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				playerA.reset();
				playerB.reset();
				refreshGame(playerA, playerB, pc1, pc2);
			}
		});

		vl.addComponent(pc1);
		vl.addComponent(pc2);
		vl.addComponent(resetBtn);
		vl.setExpandRatio(resetBtn, 1.0f);

		refreshGame(playerA, playerB, pc1, pc2);

		setContent(vl);

		Page.getCurrent().setTitle(Const.APP_NAME);

	}

	private void refreshGame(PlayerInfo playerA, PlayerInfo playerB, PlayerComponent pc1 , PlayerComponent pc2) {
		pc1.updateState(playerA.refreshComponentValue());
		pc2.updateState(playerB.refreshComponentValue());
		
		pc1.setEnabled(true);
		pc2.setEnabled(false);
		Notification.show(playerA.getPlayer().getName() + ", please start the game", Type.HUMANIZED_MESSAGE);
	}

	@Override
	public void receiveBroadcast(String message) {
		// Must lock the session to execute logic safely
        access(new Runnable() {
            @Override
            public void run() {
                // Show it somehow
                vl.addComponent(new Label(message));
            }
        });
		
		
	}

	@Override
    public void detach() {
        Broadcaster.unregister(this);
        super.detach();
    }
}
