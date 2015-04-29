package com.vbashur.grava.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

import com.vbashur.grava.Player;

@Service
public class GravaArbiter implements ApplicationEventPublisherAware {

	private ApplicationEventPublisher eventPublisher = null;
	
	@Autowired
	private PlayerInfo playerInfo;
	
	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.eventPublisher = applicationEventPublisher;		
	}
	
	public void showWinner(GameBoard gb) {
		// TODO show the winner on UI  
	}
	
	public void makeOneMoreTurn(GameBoard gb) {
		// TODO show that this gb has to do one more turn  
	}
	
	public void grabOppositeStones(GameBoard gb, int index) {
		eventPublisher.publishEvent(new GravaEvent.OnCapturingStone(gb, index));		
	}
	
	public void makeTurn(Object source, Player player, int index) {
		eventPublisher.publishEvent(new GravaEvent.OnMakingTurn(source, player, index));		
	}
	

}
