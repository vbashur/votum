package com.vbashur.grava.game;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaadin.server.ClientConnector.AttachEvent;
import com.vaadin.server.ClientConnector.AttachListener;
import com.vaadin.server.ClientConnector.DetachEvent;
import com.vaadin.server.ClientConnector.DetachListener;
import com.vaadin.server.Page;
import com.vbashur.grava.Player;
import com.vbashur.grava.ui.PlayerComponent;

@Service
public class PlayerInfoHolder {

	@Autowired
	private GravaArbiter gravaArbiter;

	private GameBoard gameBoardA;

	private GameBoard gameBoardB;

	private static Map<Player, Set<PlayerComponent>> playerComponents;
	
	private static Set<Page> pageSet;

	public GameBoard getGameBoardA() {
		return gameBoardA;
	}

	public GameBoard getGameBoardB() {
		return gameBoardB;
	}

	public GravaArbiter getGravaArbiter() {
		return gravaArbiter;
	}

	@PostConstruct
	public void init() {
		gameBoardA = new GameBoard(Player.SPOUNGE_BOB, gravaArbiter);
		gameBoardB = new GameBoard(Player.PATRICK_STAR, gravaArbiter);
		playerComponents = new HashMap<Player, Set<PlayerComponent>>();
		pageSet = new HashSet<Page>();
	}

	public PlayerInfo getPlayerA() {
		return new PlayerInfo(Player.SPOUNGE_BOB, gravaArbiter, gameBoardA);
	}

	public PlayerInfo getPlayerB() {
		return new PlayerInfo(Player.PATRICK_STAR, gravaArbiter, gameBoardB);
	}

	public void registerPlayerComponent(Player player, PlayerComponent playerComponent, Page page) {
		ComponentAttachListener attachListener = new ComponentAttachListener(player, playerComponent);
		ComponentDetachListener detachListener = new ComponentDetachListener(player, playerComponent);
		playerComponent.addAttachListener(attachListener);
		playerComponent.addDetachListener(detachListener);
		pageSet.add(page);
	}

	public void updatePlayerComponent(Player player, Map<Integer, Integer> stateMap) {
		Set<PlayerComponent> playerComponentSet = playerComponents.get(player);
		for(PlayerComponent pc : playerComponentSet) {
			pc.updateState(stateMap);
		}		
	}
	
	public void changeTurn(Player playerDisable, Player playerEnable) {		
		
		for(PlayerComponent pcd : playerComponents.get(playerDisable)) {
			pcd.setEnabled(false);
		}
		
		for(PlayerComponent pce : playerComponents.get(playerEnable)) {
			pce.setEnabled(true);
		}		
	}
	
	
	
	@SuppressWarnings("serial")
	public static class ComponentAttachListener implements AttachListener {

		private Player player;
		private PlayerComponent playerComponent;

		ComponentAttachListener(Player p, PlayerComponent component) {
			player = p;
			playerComponent = component;
		}

		@Override
		public void attach(AttachEvent event) {
			if (!playerComponents.containsKey(player)) {
				Set<PlayerComponent> compSet = new HashSet<PlayerComponent>();
				compSet.add(playerComponent);
				playerComponents.put(player, compSet);
			} else {
				playerComponents.get(player).add(playerComponent);
			}

		}

	}

	@SuppressWarnings("serial")
	public static class ComponentDetachListener implements DetachListener {

		private Player player;
		private PlayerComponent playerComponent;

		ComponentDetachListener(Player p, PlayerComponent component) {
			player = p;
			playerComponent = component;
		}

		@Override
		public void detach(DetachEvent event) {
			if (playerComponents.containsKey(player)) {
				playerComponents.get(player).remove(playerComponent);
			}

		}

	}

}
