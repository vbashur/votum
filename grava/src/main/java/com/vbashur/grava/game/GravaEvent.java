package com.vbashur.grava.game;

import org.springframework.context.ApplicationEvent;

import com.vbashur.grava.Player;

public abstract class GravaEvent extends ApplicationEvent {

	public GravaEvent(Object source) {
		super(source);
	}
	
	public Player getPlayer() {
		return (Player)this.source;
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
				
		private Integer pitIndex; 
		
		public OnMakingTurn(Object source, Integer index) {
			super(source);					
			this.pitIndex = index;						
		}					
		
		public Integer getPit() {
			return this.pitIndex;
		}
	}
	
	public static class OnMakingOneMoreTurn extends GravaEvent {
		
		public OnMakingOneMoreTurn(Object source) {
			super(source);
		}
	}
	
	public static class OnFinishTurn extends GravaEvent {
		
		public OnFinishTurn(Object source) {
			super(source);
		}
	}


}
