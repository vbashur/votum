package com.vbashur.grava;

public enum Player {
	SPOUNGE_BOB("Spounge Bob"), PATRICK_STAR("Patrick Star");
	
	private String playerName;

	Player(String name) {
		this.playerName = name;
	}

	public String getName() {
		return this.playerName;
	}

}
