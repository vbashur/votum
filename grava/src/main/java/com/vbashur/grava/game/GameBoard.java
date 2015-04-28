package com.vbashur.grava.game;

import java.util.HashMap;
import java.util.Map;

public class GameBoard {

	protected Map<Integer, Integer> boardPitMap;

	protected final static int DEFAULT_STONE_COUNT = 6;

	protected final static int DEFAULT_PIT_NUM = 6;

	protected final static int GRAVA_INDEX = 0;

	public void initPitMap() {
		if (boardPitMap == null) {
			boardPitMap = new HashMap<>();
		} else {
			boardPitMap.clear();
		}
		boardPitMap.put(GRAVA_INDEX, 0);
		for (int iter = 1; iter <= DEFAULT_PIT_NUM; ++iter) {
			boardPitMap.put(iter, DEFAULT_STONE_COUNT);
		}
	}

	public Integer giveStones(Integer targetIndex) {
		Integer items = boardPitMap.get(targetIndex);
		boardPitMap.put(targetIndex, 0);
		return items;
	}

	public void grabStones(Integer grabFromIndex) {

		// TODO observer
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
					int gravaStones = boardPitMap.get(GRAVA_INDEX);
					boardPitMap.put(GRAVA_INDEX, ++gravaStones);
					// TODO observer must say about grabbing stones from
					// opposite player
				}
			}
			checkGrava();
		}
	}

	public Integer getGravaStones() {
		return boardPitMap.get(GRAVA_INDEX);

	}

	private void checkGrava() {
		boolean isCompleted = true;
		Integer iter = 1;
		while (isCompleted && iter <= DEFAULT_PIT_NUM) {
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
