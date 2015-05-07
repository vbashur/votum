package com.vbashur.grava;

import com.vaadin.server.ClassResource;
import com.vaadin.ui.Image;

public enum Player {
	SPOUNGE_BOB("Spounge Bob"), PATRICK_STAR("Patrick Star");
	
	private String playerName;

	Player(String name) {
		this.playerName = name;
	}

	public String getName() {
		return this.playerName;
	}
	
	public String getOppositeName() {
		return this.getOpposite().getName();		
	}
	
	public Player getOpposite() {
		if (this.name().equals(SPOUNGE_BOB.name())) {
			return PATRICK_STAR;
		} else {
			return SPOUNGE_BOB;
		}
	}
	
	public Image getImage() {
		Image image = new Image();
		if (this.name().equals(SPOUNGE_BOB.name())) {
			image.setSource(new ClassResource("/static/img/spoungebob.png"));
		} else {
			image.setSource(new ClassResource("/static/img/patrick.png"));
		}
		return image;
	}

}
