package com.vbashur.grava.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.vbashur.grava.Player;
import com.vbashur.grava.Utils;
import com.vbashur.grava.ui.Broadcaster;

@Service
public class ResponseHandler implements ApplicationListener<GravaEvent> {

	private PlayerInfo playerA;

	private PlayerInfo playerB;
	
	@Autowired
	private PlayerInfoHolder playerInfoHolder;
		
	public void registerPlayers(PlayerInfo player1, PlayerInfo player2) {
		if (player1 == null || player2 == null || player1 == player2) {
			throw new UnsupportedOperationException("Unable to register players");
		}
		this.playerA = player1;
		this.playerB = player2;	
	}	
	
	@Override
	public void onApplicationEvent(GravaEvent event) {
		Player player = event.getPlayer();
		if (event.getClass().equals(GravaEvent.OnMakingTurn.class)) {
			Integer pitIndex = ((GravaEvent.OnMakingTurn) event).getPit();
			if (player == playerA.getPlayer()) {
				playerA.makeTurn(pitIndex);
			} else {
				playerB.makeTurn(pitIndex);
			}			

		} else if (event.getClass().equals(GravaEvent.OnFinishTurn.class)) {				
			refresh(player);
			playerInfoHolder.changeTurn(player, player.getOpposite());			
			Broadcaster.broadcast(new BroadcastEvent.OnFinishTurn(event.getPlayer()));

		} else if (event.getClass().equals(GravaEvent.OnCapturingStone.class)) {
			Integer targetPit = ((GravaEvent.OnCapturingStone) event).getIndex();
			Integer pitIndex = Utils.getOpponentPitIndex(targetPit); 
			Integer stonesToGrab = 0;
			if (player == playerA.getPlayer()) {
				stonesToGrab = playerB.giveStones(pitIndex);
				playerA.grabStones(stonesToGrab);
			} else {
				stonesToGrab = playerA.giveStones(pitIndex);
				playerB.grabStones(stonesToGrab);
			}
			playerInfoHolder.updatePlayerComponent(playerA.getPlayer(), playerA.refreshComponentValue());
			playerInfoHolder.updatePlayerComponent(playerB.getPlayer(), playerB.refreshComponentValue());	
			Broadcaster.broadcast(new BroadcastEvent.OnCapturing(player, stonesToGrab));
			
		} else if (event.getClass().equals(GravaEvent.OnMakingOneMoreTurn.class)) {
			refresh(player);
			Broadcaster.broadcast(new BroadcastEvent.OnOneMoreTurn(player));
			
		} else if (event.getClass().equals(GravaEvent.OnFinishingGame.class)) {
			
			if (playerA.getStones() > playerB.getStones()) {
				Broadcaster.broadcast(new BroadcastEvent.OnFinishing(playerA.getPlayer(), playerA.getStones()));
			} else if (playerB.getStones() > playerA.getStones()) {
				Broadcaster.broadcast(new BroadcastEvent.OnFinishing(playerB.getPlayer(), playerB.getStones()));
			} else if (playerB.getStones() == playerA.getStones()) {
				Broadcaster.broadcast(new BroadcastEvent.OnFinishing(player, playerB.getStones())); // wins the first who put all the stones to grava
			}
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
