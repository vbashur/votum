package com.vbashur.grava.game;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.vbashur.grava.Const;
import com.vbashur.grava.Player;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = GhalApplication.class)
//@WebAppConfiguration
public class GameBoardTest {

	@Mock
	private GravaArbiter arbiter;

	private GameBoard gb;

	private Player testPlayer = Player.SPOUNGE_BOB;

	@Before
	public void initGameBoard() {
		MockitoAnnotations.initMocks(this);
		gb = new GameBoard(testPlayer, arbiter);
	}

	@Test
	public void onInitTest() {
		Map<Integer, Integer> pits = gb.getPits();
		assertTrue(pits.size() == Const.DEFAULT_PIT_NUM + 1);
		for (int iter = 1; iter <= Const.DEFAULT_PIT_NUM; ++iter) {
			assertTrue(pits.get(iter) == Const.DEFAULT_STONE_COUNT);
		}
		assertTrue(gb.getGravaStones() == 0);
	}

	@Test
	public void addRemoveCollectStonesTest() {
		Integer stonesToAdd = 1;
		gb.addGravaStones(stonesToAdd);
		Integer gravaStones = gb.getGravaStones();
		assertTrue(gravaStones == 1);

		Integer stonesTotal = gb.collectAllStones();
		Integer stonesTotalExpected = stonesToAdd + Const.DEFAULT_PIT_NUM * Const.DEFAULT_STONE_COUNT;
		assertTrue(stonesTotal == stonesTotalExpected);

		int targetIndex = 1;
		Integer capturedStones = gb.giveStones(targetIndex);
		assertTrue(capturedStones == Const.DEFAULT_STONE_COUNT);

		int stonesLeftinPit = gb.getPits().get(targetIndex);
		assertTrue(stonesLeftinPit == 0);

		stonesTotal = gb.collectAllStones();
		stonesTotalExpected -= capturedStones;
		assertTrue(stonesTotal == stonesTotalExpected);
	}

	@Test
	public void isGameCompletedTest() {

		for (int iter = 1; iter <= Const.DEFAULT_PIT_NUM; ++iter) {
			gb.giveStones(iter);
			if (iter != Const.DEFAULT_PIT_NUM) {
				assertFalse(gb.isGameCompeted());
			} else {
				assertTrue(gb.isGameCompeted());

			}
		}
	}

	@Test
	public void makeSimpleTurnTest() {
		Integer targetPit = 3;
		gb.makeTurn(targetPit);
		Mockito.verify(arbiter, Mockito.times(1)).finishTurn(testPlayer);
		Integer gravaStones = gb.getGravaStones();
		assertTrue(gravaStones == 1);

		Integer targetPitStones = gb.getPits().get(targetPit);
		assertTrue(targetPitStones == 0);
		for (int iter = 1; iter <= Const.DEFAULT_PIT_NUM; ++iter) {
			if (iter != targetPit) {
				Integer pitStones = gb.getPits().get(iter);
				assertTrue(pitStones == Const.DEFAULT_STONE_COUNT + 1);
			}
		}
		Integer stonesTotalExpected = Const.DEFAULT_PIT_NUM * Const.DEFAULT_STONE_COUNT;
		Integer stonesTotal = gb.collectAllStones();
		assertTrue(stonesTotal == stonesTotalExpected);
	}

	@Test
	public void makeDoubleTurnAndGrabTest() {
		Integer targetPit = 1;
		gb.makeTurn(targetPit);
		Mockito.verify(arbiter, Mockito.times(1)).makeOneMoreTurn(testPlayer);

		targetPit = 2;
		gb.makeTurn(targetPit);
		Mockito.verify(arbiter, Mockito.times(1)).grabOppositeStones(testPlayer, targetPit);
		Mockito.verify(arbiter, Mockito.times(1)).finishTurn(testPlayer);

		Integer targetPitStones = gb.getPits().get(targetPit);
		assertTrue(targetPitStones == 0);

		Integer gravaStones = gb.getGravaStones();
		assertTrue(gravaStones == 3);

		Integer stonesTotalExpected = Const.DEFAULT_PIT_NUM * Const.DEFAULT_STONE_COUNT;
		Integer stonesTotal = gb.collectAllStones();
		assertTrue(stonesTotal == stonesTotalExpected);
	}

	@Test
	public void finishGameSingleTurnTest() {
		Map<Integer, Integer> pitMap = new HashMap<>();
		Integer gravaStones = 10;
		pitMap.put(0, gravaStones);
		pitMap.put(1, 0);
		pitMap.put(2, 0);
		pitMap.put(3, 0);
		pitMap.put(4, 0);
		pitMap.put(5, 0);
		pitMap.put(6, 1);

		GameBoard gbSpy = Mockito.spy(gb);
		Mockito.stub(gbSpy.getPits()).toReturn(pitMap);

		Integer targetPit = 6;
		gbSpy.makeTurn(targetPit);
		assertTrue(gbSpy.getGravaStones() == (gravaStones += 1));
		Mockito.verify(arbiter, Mockito.times(0)).makeOneMoreTurn(testPlayer);
		Mockito.verify(arbiter, Mockito.times(0)).finishTurn(testPlayer);
		Mockito.verify(arbiter, Mockito.times(1)).showWinner(testPlayer);
	}

	@Test
	public void finishGameTest() {
		Map<Integer, Integer> pitMap = new HashMap<>();
		Integer gravaStones = 10;
		pitMap.put(0, gravaStones);
		pitMap.put(1, 1);
		pitMap.put(2, 0);
		pitMap.put(3, 0);
		pitMap.put(4, 1);
		pitMap.put(5, 0);
		pitMap.put(6, 1);

		GameBoard gbSpy = Mockito.spy(gb);
		Mockito.stub(gbSpy.getPits()).toReturn(pitMap);

		Integer targetPit = 6;
		gbSpy.makeTurn(targetPit);
		assertTrue(gbSpy.getGravaStones() == (gravaStones += 1));
		Mockito.verify(arbiter, Mockito.times(1)).makeOneMoreTurn(testPlayer);
		Mockito.verify(arbiter, Mockito.times(0)).finishTurn(testPlayer);
		Mockito.verify(arbiter, Mockito.times(0)).showWinner(testPlayer);

		targetPit = 4;
		gbSpy.makeTurn(targetPit);
		assertTrue(gbSpy.getGravaStones() == (gravaStones += 1));
		Mockito.verify(arbiter, Mockito.times(1)).grabOppositeStones(testPlayer, targetPit + 1);
		Mockito.verify(arbiter, Mockito.times(1)).finishTurn(testPlayer);
		Mockito.verify(arbiter, Mockito.times(0)).showWinner(testPlayer);

		targetPit = 1;
		gbSpy.makeTurn(targetPit);
		assertTrue(gbSpy.getGravaStones() == (gravaStones += 1));
		Mockito.verify(arbiter, Mockito.times(1)).showWinner(testPlayer);			
	}

}
