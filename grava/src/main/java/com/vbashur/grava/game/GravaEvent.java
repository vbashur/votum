package com.vbashur.grava.game;

import org.springframework.context.ApplicationEvent;

import com.vbashur.grava.Player;

public abstract class GravaEvent extends ApplicationEvent {

	public GravaEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}
	
	
	public static class OnCapturingStone extends GravaEvent {

		private int targetIndex;
		
		public OnCapturingStone(Object source, int index) {
			super(source);
			targetIndex = index;
		}		
		
		public int getIndex() {
			return targetIndex;
		}
	}
	
	public static class OnFinishingGame extends GravaEvent {

		public OnFinishingGame(Object source) {
			super(source);			
		}		
	}
	
	public static class OnMakingTurn extends GravaEvent {

		private Player player;
		
		private Integer pitIndex; 
		
		public OnMakingTurn(Object source, Player pl, Integer index) {
			super(source);			
			this.player = pl;
			this.pitIndex = index;						
		}				
		
		public Player getPlayer() {
			return this.player;
		}
		
		public Integer getPit() {
			return this.pitIndex;
		}
	}

}
