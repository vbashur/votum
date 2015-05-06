package com.vbashur.grava.game;

import java.util.Map;

import com.vbashur.grava.Player;


public class PlayerInfo {
	
	private Player player;
	
	private GameBoard gameBoard;
	
	public PlayerInfo(Player p, GameBoard gb) {
		this.player = p;
		this.gameBoard = gb;
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
	
	public Map<Integer, Integer> refreshComponentValue() {
		return gameBoard.getPits();		
	}
	
	public void reset() {
		gameBoard.initPitMap();
	}	
	
	public Integer getStones() {
		return gameBoard.collectAllStones();
	}
}
