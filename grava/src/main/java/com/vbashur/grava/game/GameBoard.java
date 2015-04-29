package com.vbashur.grava.game;

import java.util.HashMap;
import java.util.Map;

import com.vbashur.grava.Const;

public class GameBoard {

	protected Map<Integer, Integer> boardPitMap;

	protected GravaArbiter arbiter;
	
	public GameBoard(GravaArbiter gravaArbiter) {
		arbiter = gravaArbiter;
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

	public void grabStones(Integer grabFromIndex) {
		arbiter.grabOppositeStones(this, grabFromIndex);
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
				// TODO observer must say about another turn
			} else {
				Integer stonesRemain = boardPitMap.get(lastIndex);
				if (stonesRemain == 0) {
					addGravaStones(1);
					grabStones(lastIndex);
				}
			}
			checkGrava();
		}
	}

	public Integer getGravaStones() {
		return boardPitMap.get(Const.GRAVA_INDEX);
	}
	
	public void addGravaStones(Integer count) {
		Integer currStonesCount = getGravaStones();
		currStonesCount += count;
		boardPitMap.put(Const.GRAVA_INDEX, currStonesCount);
	}

	private void checkGrava() {
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
		if (isCompleted) {
			// TODO observer for checking who has more stones
		}
	}

}
