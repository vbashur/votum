package com.vbashur.grava.ui;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vbashur.grava.Const;
import com.vbashur.grava.Player;
import com.vbashur.grava.game.GravaArbiter;

public class PlayerComponent extends CustomComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private GravaArbiter arbiter;

	private GridLayout grid;

	private Player player;

	public PlayerComponent(Player p, GravaArbiter arb) {
		player = p;
		arbiter = arb;
	}

	private List<Entry<Button, Integer>> buttonIndexPair;

	@Override
	public void attach() {
		buildLayout();
		buildPits();
		setCompositionRoot(grid);
	}

	private void buildLayout() {
		grid = new GridLayout(Const.DEFAULT_PIT_NUM + 2, 2);
		grid.setSpacing(true);
		grid.setMargin(true);
	}

	@SuppressWarnings("serial")
	private void buildPits() {
		buttonIndexPair = new LinkedList<Entry<Button, Integer>>();
		Label nameLabel = new Label(player.getName());
		if (player == Player.SPOUNGE_BOB) {
			grid.addComponent(nameLabel, 1, 0, Const.DEFAULT_PIT_NUM, 0);
			for (int iter = 0; iter <= Const.DEFAULT_PIT_NUM; ++iter) {
				final int buttonIndex = iter;
				Button b = new Button();
				if (iter == 0) {
					b.setEnabled(false);
				} else {
					b.addClickListener(new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							arbiter.makeTurn(player, buttonIndex);							
						}
					});					
				}
				grid.addComponent(b, iter, 1);
				buttonIndexPair.add(new java.util.AbstractMap.SimpleEntry<>(b, iter));
			}
			grid.addComponent(new Label("^^"), Const.DEFAULT_PIT_NUM + 1, 1); // placeholder

		} else {
			grid.addComponent(new Label(":)"), 0, 0); // placeholder
			for (int iter = 1; iter <= Const.DEFAULT_PIT_NUM + 1; ++iter) {
				Button b = new Button();
				final int buttonIndex = Const.DEFAULT_PIT_NUM + 1 - iter;
				if (iter == Const.DEFAULT_PIT_NUM + 1) {
					b.setEnabled(false);
				} else {
					b.addClickListener(new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							arbiter.makeTurn(player, buttonIndex);							
						}
					});	
				}
				grid.addComponent(b, iter, 0);
				buttonIndexPair.add(new java.util.AbstractMap.SimpleEntry<>(b, buttonIndex));
			}

			grid.addComponent(nameLabel, 1, 1, Const.DEFAULT_PIT_NUM, 1);

		}
	}

	public void updateState(Map<Integer, Integer> stateMap) {
		for (Entry<Button, Integer> pits : buttonIndexPair) {
			Integer index = pits.getValue();
			Integer actiualValue = stateMap.get(index);
			Button targetButton = pits.getKey();
			targetButton.setCaption(actiualValue.toString());
		}
	}	
}
