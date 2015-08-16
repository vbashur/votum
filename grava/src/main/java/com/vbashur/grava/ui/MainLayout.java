package com.vbashur.grava.ui;

import org.springframework.beans.factory.annotation.Autowired;

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
import com.vaadin.ui.Window;
import com.vbashur.grava.Const;
import com.vbashur.grava.game.BroadcastEvent;
import com.vbashur.grava.game.PlayerInfo;
import com.vbashur.grava.game.PlayerInfoHolder;
import com.vbashur.grava.game.ResponseHandler;

@SuppressWarnings("serial")
@SpringUI
@PreserveOnRefresh
@Push
public class MainLayout extends UI implements Broadcaster.BroadcastListener {
	
	@Autowired
	protected ResponseHandler respHandler;

	@Autowired
	protected PlayerInfoHolder playerInfoHolder;
		
	protected Label statusLabel;
	
	protected RestartClickListener restartlistener;
	
	protected static final String WELCOME_MESSAGE = "%s, please start the game";

	@Override
	protected void init(VaadinRequest request) {

		VerticalLayout vl = new VerticalLayout();
		vl.setMargin(true);
		vl.setWidth(100, Unit.PERCENTAGE);
		vl.setHeight(100, Unit.PERCENTAGE);

		Label caption = new Label("<b>Grava Hal - Bikini Bottom Edition</b>", ContentMode.HTML);
		vl.addComponent(caption);

		PlayerInfo playerA = playerInfoHolder.getPlayerA();
		PlayerInfo playerB = playerInfoHolder.getPlayerB();

		PlayerComponent pc1 = new PlayerComponent(playerA.getPlayer(), playerInfoHolder.getGravaArbiter());
		PlayerComponent pc2 = new PlayerComponent(playerB.getPlayer(), playerInfoHolder.getGravaArbiter());
		
		playerInfoHolder.registerPlayerComponent(playerA.getPlayer(), pc1);
		playerInfoHolder.registerPlayerComponent(playerB.getPlayer(), pc2);
		
		respHandler.registerPlayers(playerA, playerB);
		
		Broadcaster.register(this);
		
		statusLabel = new Label();

		restartlistener = new RestartClickListener(playerA, playerB, pc1, pc2);
		Button resetBtn = new Button("reset");
		resetBtn.setWidth(Const.DEFAULT_WIDTH, Unit.PIXELS);		
		resetBtn.addClickListener(restartlistener);

		vl.addComponent(pc1);
		vl.addComponent(pc2);
		vl.addComponent(statusLabel);
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
		String welcomeMessage = String.format(WELCOME_MESSAGE, playerA.getPlayer().getName());
		Notification.show(welcomeMessage, Type.HUMANIZED_MESSAGE);
		statusLabel.setCaption(welcomeMessage);
	}

	@Override
	public void receiveBroadcast(final BroadcastEvent event) {
		// Must lock the session to execute logic safely
        access(new Runnable() {
            @Override
            public void run() {
            	if (event instanceof BroadcastEvent.OnFinishTurn) {
            		statusLabel.setCaption(event.getPlayer().getOppositeName() + " it's your turn");
            	} else if (event instanceof BroadcastEvent.OnOneMoreTurn) {
            		Notification.show(event.getPlayer().getName() + " has one more turn", Type.TRAY_NOTIFICATION);
            	} else if (event instanceof BroadcastEvent.OnCapturing) {
            		int stonesToGrab = ((BroadcastEvent.OnCapturing)event).getCaptured();
            		Notification.show(event.getPlayer().getName() + " captures " + stonesToGrab, Type.TRAY_NOTIFICATION);
                } else if (event instanceof BroadcastEvent.OnFinishing) {                	
                	
                	Window window = new Window(event.getPlayer().getName() + " won the game");
    				window.setModal(true);
    				window.setClosable(true);
    				window.setResizable(false);  
    				Button restartBtn = new Button("Restart", restartlistener);
    				VerticalLayout vl = new VerticalLayout();
    				vl.addComponent(new Label("Result: " + ((BroadcastEvent.OnFinishing)event).getGrava()));
    				vl.addComponent(event.getPlayer().getImage());
    				vl.addComponent(restartBtn);
    				vl.setExpandRatio(restartBtn, 1.0f);
    				window.setContent(vl);				
    				UI.getCurrent().addWindow(window);
                	                
                }
            }
        });
		
	}

	@Override
    public void detach() {
        Broadcaster.unregister(this);
        super.detach();
    }
		
	protected class RestartClickListener implements ClickListener {
		
		private PlayerInfo playerA;
		private PlayerInfo playerB;
		private PlayerComponent pc1;
		private PlayerComponent pc2;
		
		public RestartClickListener(PlayerInfo playerA, PlayerInfo playerB, PlayerComponent pc1, PlayerComponent pc2) {
			super();
			this.playerA = playerA;
			this.playerB = playerB;
			this.pc1 = pc1;
			this.pc2 = pc2;
		}		
		
		@Override
		public void buttonClick(ClickEvent event) {
			playerA.reset();
			playerB.reset();
			refreshGame(playerA, playerB, pc1, pc2);			
		}
		
	}
}
