package com.vbashur.grava;

public class Utils {
	
	public static Integer getOpponentPitIndex(int targetPit) {
		return Const.DEFAULT_PIT_NUM - targetPit + 1;
	}

}
