package com.vbashur.grava;

import static org.junit.Assert.*;

import org.junit.Test;

public class UtilsTest {

	@Test
	public void getOpponentPitIndexTest() {
		int oppponentPitIndex = Utils.getOpponentPitIndex(1);
		assertTrue(oppponentPitIndex == 6);

		oppponentPitIndex = Utils.getOpponentPitIndex(6);
		assertTrue(oppponentPitIndex == 1);

		oppponentPitIndex = Utils.getOpponentPitIndex(4);
		assertTrue(oppponentPitIndex == 3);
	}
}
