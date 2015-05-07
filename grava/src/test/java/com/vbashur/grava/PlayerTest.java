package com.vbashur.grava;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PlayerTest {

	@Test
	public void getOppositeTest() {
		Player playerA = Player.PATRICK_STAR;
		Player opposite = playerA.getOpposite();
		assertTrue(opposite == Player.SPOUNGE_BOB);

		String oppositeName = playerA.getOppositeName();
		assertTrue(oppositeName.equals(Player.SPOUNGE_BOB.getName()));
	}
}
