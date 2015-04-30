package com.vbashur.grava.game;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vbashur.grava.Player;

@Service
public class ResponseHandler implements ApplicationListener<GravaEvent> {

	private PlayerInfo playerA;

	private PlayerInfo playerB;

	public void registerPlayers(PlayerInfo player1, PlayerInfo player2) {
		if (player1 == null || player2 == null || player1 == player2) {
			throw new UnsupportedOperationException("Unable to register players");
		}
		this.playerA = player1;
		this.playerB = player2;
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
			refresh(event.getPlayer());
			if (event.getPlayer() == playerA.getPlayer()) {
				playerA.getPlayerComponent().setEnabled(false);
				playerB.getPlayerComponent().setEnabled(true);
			} else {
				playerA.getPlayerComponent().setEnabled(true);
				playerB.getPlayerComponent().setEnabled(false);
			}

		} else if (event.getClass().equals(GravaEvent.OnCapturingStone.class)) {
			Integer pitIndex = ((GravaEvent.OnCapturingStone) event).getIndex();
			if (event.getPlayer() == playerA.getPlayer()) {
				Integer stonesToGrab = playerB.giveStones(pitIndex);
				playerA.grabStones(stonesToGrab);
			} else {
				Integer stonesToGrab = playerA.giveStones(pitIndex);
				playerB.grabStones(stonesToGrab);
			}
			playerA.refreshComponent();
			playerB.refreshComponent();

		} else if (event.getClass().equals(GravaEvent.OnMakingOneMoreTurn.class)) {
			refresh(event.getPlayer());
			Notification.show(event.getPlayer().getName() + " has one more turn", Type.TRAY_NOTIFICATION);

		} else if (event.getClass().equals(GravaEvent.OnFinishingGame.class)) {
			Notification.show(event.getPlayer().getName() + " won! Game over. ", Type.TRAY_NOTIFICATION);

		}

	}

	public void refresh(Player player) {
		if (player == playerA.getPlayer()) {
			playerA.refreshComponent();
		} else {
			playerB.refreshComponent();
		}
	}

}
