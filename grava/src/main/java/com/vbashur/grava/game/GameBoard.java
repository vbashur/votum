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
	
	public  Map<Integer, Integer> initPitMap() {
		if (boardPitMap == null) {
			boardPitMap = new HashMap<Integer, Integer>();
		} else {
			boardPitMap.clear();
		}
		boardPitMap.put(Const.GRAVA_INDEX, 0);
		for (int iter = 1; iter <= Const.DEFAULT_PIT_NUM; ++iter) {
			boardPitMap.put(iter, Const.DEFAULT_STONE_COUNT);
		}
		return boardPitMap;
	}

	public Integer giveStones(Integer targetIndex) {
		Integer items = getPits().get(targetIndex);
		getPits().put(targetIndex, 0);
		return items;
	}

	public void makeTurn(Integer startIndex) {
		Map<Integer, Integer> pitmap = getPits();
		Integer stones = pitmap.get(startIndex);
		if (stones > 0) {
			pitmap.put(startIndex, 0);

			int tmpIndex = startIndex + 1;

			int upperBound = startIndex + stones - 1;

			while (tmpIndex <= upperBound) {
				int currentIndex = tmpIndex % pitmap.size();
				int currentValue = pitmap.get(currentIndex);
				pitmap.put(currentIndex, ++currentValue);
				++tmpIndex;
			}

			int lastIndex = tmpIndex % pitmap.size();
			if (lastIndex == 0) {
				addGravaStones(1);
				if (isGameCompeted()) {
					arbiter.showWinner(player);					
				} else {					
					arbiter.makeOneMoreTurn(player);
				}
			} else {
				Integer stonesRemain = pitmap.get(lastIndex);
				if (stonesRemain == 0) {
					addGravaStones(1);
					arbiter.grabOppositeStones(this.player, lastIndex);
					if (isGameCompeted()) {
						arbiter.showWinner(player);					
					} else {
						arbiter.finishTurn(player);
					}
				} else {
					int currentValue = pitmap.get(lastIndex);
					pitmap.put(lastIndex, ++currentValue);
					arbiter.finishTurn(player);
				}
				
			}			
		}
	}

	public Integer getGravaStones() {
		return getPits().get(Const.GRAVA_INDEX);
	}
	
	public Integer collectAllStones() {
		Integer allStones = 0;
		for(Integer stones : getPits().values()) {
			allStones += stones;
		}
		return allStones;
	}
	
	public Map<Integer, Integer> getPits() {
		return boardPitMap;
	}
	
	public void addGravaStones(Integer count) {
		Integer currStonesCount = getGravaStones();
		currStonesCount += count;
		getPits().put(Const.GRAVA_INDEX, currStonesCount);
	}

	public boolean isGameCompeted() {
		boolean isCompleted = true;
		Integer iter = 1;
		while (isCompleted && iter <= Const.DEFAULT_PIT_NUM) {
			Integer stoneNum = getPits().get(iter);
			if (stoneNum != 0) {
				isCompleted = false;
			} else {
				iter += 1;
			}
		}
		return isCompleted;			
	}
}
