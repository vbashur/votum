package com.vbashur.grava.game;

import com.vbashur.grava.Player;

public class BroadcastEvent {

	private Player player;
	
	public BroadcastEvent(Player p) {
		this.player = p;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public static class OnFinishTurn extends BroadcastEvent {
		
		public OnFinishTurn(Player p) {
			super(p);
		}	
	}
	
	public static class OnOneMoreTurn extends BroadcastEvent {
		
		public OnOneMoreTurn(Player p) {
			super(p);
		}
		
	}
	
	public static class OnCapturing extends BroadcastEvent {
		
		private int amount = 0;
		
		public OnCapturing(Player p, int captured) {
			super(p);
			amount = captured;
		}
		
		public int getCaptured() {
			return this.amount;
		}
	}
	
	public static class OnFinishing extends  BroadcastEvent {
		
		private int amount;
		
		public OnFinishing(Player p, int grava) {
			super(p);
			amount = grava;
		}
		
		public int getGrava() {
			return this.amount;
		}
	}
}
