package com.vbashur.grava.game;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vbashur.grava.Player;
import com.vbashur.grava.Utils;
import com.vbashur.grava.ui.Broadcaster;

@Service
//@Scope("prototype")
public class ResponseHandler implements ApplicationListener<GravaEvent> {

	private PlayerInfo playerA;

	private PlayerInfo playerB;
	
//	@Autowired
	private PlayerInfoHolder playerInfoHolder;
		
	public void registerPlayers(PlayerInfo player1, PlayerInfo player2, PlayerInfoHolder holder) {
		if (player1 == null || player2 == null || player1 == player2) {
			throw new UnsupportedOperationException("Unable to register players");
		}
		this.playerA = player1;
		this.playerB = player2;		
		this.playerInfoHolder = holder;
	}	
	
	@Override
	public void onApplicationEvent(GravaEvent event) {
			
		if (event.getClass().equals(GravaEvent.OnMakingTurn.class)) {
			Integer pitIndex = ((GravaEvent.OnMakingTurn) event).getPit();
			if (event.getPlayer() == playerA.getPlayer()) {
				playerA.makeTurn(pitIndex);
			} else {
				playerB.makeTurn(pitIndex);
			}			

		} else if (event.getClass().equals(GravaEvent.OnFinishTurn.class)) {
			Broadcaster.broadcast(event.getPlayer().getName());
			refresh(event.getPlayer());
			if (event.getPlayer() == playerA.getPlayer()) {
				playerInfoHolder.changeTurn(playerA.getPlayer(), playerB.getPlayer());				
			} else {
				playerInfoHolder.changeTurn(playerB.getPlayer(), playerA.getPlayer());
			}

		} else if (event.getClass().equals(GravaEvent.OnCapturingStone.class)) {
			Integer targetPit = ((GravaEvent.OnCapturingStone) event).getIndex();
			Integer pitIndex = Utils.getOpponentPitIndex(targetPit); 
			Integer stonesToGrab = 0;
			if (event.getPlayer() == playerA.getPlayer()) {
				stonesToGrab = playerB.giveStones(pitIndex);
				playerA.grabStones(stonesToGrab);
			} else {
				stonesToGrab = playerA.giveStones(pitIndex);
				playerB.grabStones(stonesToGrab);
			}
			playerInfoHolder.updatePlayerComponent(playerA.getPlayer(), playerA.refreshComponentValue());
			playerInfoHolder.updatePlayerComponent(playerB.getPlayer(), playerB.refreshComponentValue());			
			Notification.show(event.getPlayer().getName() + " captures " + stonesToGrab, Type.TRAY_NOTIFICATION);

		} else if (event.getClass().equals(GravaEvent.OnMakingOneMoreTurn.class)) {
			refresh(event.getPlayer());
			Notification.show(event.getPlayer().getName() + " has one more turn", Type.TRAY_NOTIFICATION);

		} else if (event.getClass().equals(GravaEvent.OnFinishingGame.class)) {
			Notification.show(event.getPlayer().getName() + " won! Game over. ", Type.TRAY_NOTIFICATION);

		}

	}

	public void refresh(Player player) {
		if (player == playerA.getPlayer()) {
			playerInfoHolder.updatePlayerComponent(playerA.getPlayer(), playerA.refreshComponentValue());						
		} else {
			playerInfoHolder.updatePlayerComponent(playerB.getPlayer(), playerB.refreshComponentValue());
			
		}		
	}

	
}
