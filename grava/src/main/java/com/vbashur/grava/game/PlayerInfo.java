package com.vbashur.grava.game;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.vbashur.grava.game.GravaEvent.OnCapturingStone;

@Service
public class PlayerInfo implements ApplicationListener<GravaEvent.OnCapturingStone> {
	
	@Autowired
	private GravaArbiter gravaArbiter;

	private GameBoard gameBoardA;
	
	private GameBoard gameBoardB;
	
	@PostConstruct
	public void init() {
		gameBoardA = new GameBoard(gravaArbiter);
		gameBoardB = new GameBoard(gravaArbiter);
	}
	
//	
//	public void grabOppositeStones(GameBoard target, int index) {
//		if (target.equals(gameBoardA)) {
//			Integer grabbedStones = gameBoardB.giveStones(index);
//			gameBoardA.addGravaStones(grabbedStones);
//		} else {
//			Integer grabbedStones = gameBoardA.giveStones(index);
//			gameBoardB.addGravaStones(grabbedStones);						
//		}
//	}


	@Override
	public void onApplicationEvent(OnCapturingStone event) {
		GameBoard target = (GameBoard)event.getSource();
		int index = event.getIndex();
		if (target.equals(gameBoardA)) {
			Integer grabbedStones = gameBoardB.giveStones(index);
			gameBoardA.addGravaStones(grabbedStones);
		} else {
			Integer grabbedStones = gameBoardA.giveStones(index);
			gameBoardB.addGravaStones(grabbedStones);						
		}		
	}
	
	
}
