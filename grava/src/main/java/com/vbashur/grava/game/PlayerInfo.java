package com.vbashur.grava.game;

import java.util.Map;

import com.vbashur.grava.Player;
import com.vbashur.grava.ui.PlayerComponent;


public class PlayerInfo {
	
	private Player player;
	
	private GameBoard gameBoard;
	
	private PlayerComponent playerComponent;

	
	public PlayerInfo(Player p, GravaArbiter arbiter) {
		this.player = p;
		this.playerComponent = new PlayerComponent(p, arbiter);
		this.gameBoard = new GameBoard(p, arbiter);
	}
	
	public PlayerComponent getPlayerComponent() {
		return this.playerComponent;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public void makeTurn(Integer startIndex) {
		this.gameBoard.makeTurn(startIndex);
	}
	
	public Integer giveStones(Integer pitIndex) {
		return this.gameBoard.giveStones(pitIndex);
	}
	
	public void grabStones(Integer stonesNum) {
		this.gameBoard.addGravaStones(stonesNum);
	}
	
	public void refreshComponent() {
		Map<Integer, Integer> stateMap = gameBoard.getPits();
		playerComponent.updateState(stateMap);
	}		
	
}
