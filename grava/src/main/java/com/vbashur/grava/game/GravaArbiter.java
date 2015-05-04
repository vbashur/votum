package com.vbashur.grava.game;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

import com.vbashur.grava.Player;

@Service
public class GravaArbiter implements ApplicationEventPublisherAware {

	private ApplicationEventPublisher eventPublisher = null;
	
	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.eventPublisher = applicationEventPublisher;		
	}
	
	public void showWinner(Player player) {
		eventPublisher.publishEvent(new GravaEvent.OnFinishingGame(player));  
	}
	
	public void makeOneMoreTurn(Player player) {
		eventPublisher.publishEvent(new GravaEvent.OnMakingOneMoreTurn(player));  
	}
	
	public void grabOppositeStones(Player player, Integer index) {
		eventPublisher.publishEvent(new GravaEvent.OnCapturingStone(player, index));		
	}
	
	public void makeTurn(Player player, Integer index) {
		eventPublisher.publishEvent(new GravaEvent.OnMakingTurn(player, index));		
	}
	
	public void finishTurn(Player player) {
		eventPublisher.publishEvent(new GravaEvent.OnFinishTurn(player));
	}

}
