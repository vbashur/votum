package com.vbashur.grava.game;

import java.util.HashMap;
import java.util.Map;

import com.vbashur.grava.Const;
import com.vbashur.grava.Player;

public class GameBoard {

	protected Map<Integer, Integer> boardPitMap;

	protected GravaArbiter arbiter;
	
	protected Player player;
	
	public GameBoard(Player p, GravaArbiter gravaArbiter) {
		arbiter = gravaArbiter;
		player = p;
		initPitMap();
	}
	
	public void initPitMap() {
		if (boardPitMap == null) {
			boardPitMap = new HashMap<Integer, Integer>();
		} else {
			boardPitMap.clear();
		}
		boardPitMap.put(Const.GRAVA_INDEX, 0);
		for (int iter = 1; iter <= Const.DEFAULT_PIT_NUM; ++iter) {
			boardPitMap.put(iter, Const.DEFAULT_STONE_COUNT);
		}
	}

	public Integer giveStones(Integer targetIndex) {
		Integer items = boardPitMap.get(targetIndex);
		boardPitMap.put(targetIndex, 0);
		return items;
	}

	public void makeTurn(Integer startIndex) {
		Integer stones = boardPitMap.get(startIndex);
		if (stones > 0) {
			boardPitMap.put(startIndex, 0);

			int tmpIndex = startIndex + 1;

			int upperBound = startIndex + stones - 1;

			while (tmpIndex <= upperBound) {
				int currentIndex = tmpIndex % boardPitMap.size();
				int currentValue = boardPitMap.get(currentIndex);
				boardPitMap.put(currentIndex, ++currentValue);
				++tmpIndex;
			}

			int lastIndex = tmpIndex % boardPitMap.size();
			if (lastIndex == 0) {
				if (isGameCompeted()) {
					arbiter.showWinner(player);					
				} else {
					arbiter.makeOneMoreTurn(player);
				}
			} else {
				Integer stonesRemain = boardPitMap.get(lastIndex);
				if (stonesRemain == 0) {
					addGravaStones(1);
					arbiter.grabOppositeStones(this.player, lastIndex);
				}
				if (isGameCompeted()) {
					arbiter.showWinner(player);					
				} else {
					arbiter.finishTurn(player);
				}
			}			
		}
	}

	public Integer getGravaStones() {
		return boardPitMap.get(Const.GRAVA_INDEX);
	}
	
	public Map<Integer, Integer> getPits() {
		return boardPitMap;
	}
	
	public void addGravaStones(Integer count) {
		Integer currStonesCount = getGravaStones();
		currStonesCount += count;
		boardPitMap.put(Const.GRAVA_INDEX, currStonesCount);
	}

	private boolean isGameCompeted() {
		boolean isCompleted = true;
		Integer iter = 1;
		while (isCompleted && iter <= Const.DEFAULT_PIT_NUM) {
			Integer stoneNum = boardPitMap.get(iter);
			if (stoneNum != 0) {
				isCompleted = false;
			} else {
				iter += 1;
			}
		}
		return isCompleted;			
	}
}
