package com.vbashur.grava.ui;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.vaadin.server.ClassResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vbashur.grava.Const;
import com.vbashur.grava.Player;
import com.vbashur.grava.game.GravaArbiter;

@SuppressWarnings("serial")
public class PlayerComponent extends CustomComponent {

	private GravaArbiter arbiter;

	private GridLayout grid;

	private Player player;

	private static int jokeCounter = 0;

	public PlayerComponent(Player p, GravaArbiter arb) {
		player = p;
		arbiter = arb;
		buildLayout();
		buildPits();
		setCompositionRoot(grid);
		this.setImmediate(true);
	}

	private List<Entry<Button, Integer>> buttonIndexPair;

	private void buildLayout() {
		grid = new GridLayout(Const.DEFAULT_PIT_NUM + 2, 2);
		grid.setSpacing(true);
		grid.setMargin(true);
		grid.setImmediate(true);
	}

	private void buildPits() {
		buttonIndexPair = new LinkedList<Entry<Button, Integer>>();
		Label nameLabel = new Label(player.getName());
		if (player == Player.SPOUNGE_BOB) {
			grid.addComponent(nameLabel, 1, 0, Const.DEFAULT_PIT_NUM, 0);
			for (int iter = 0; iter <= Const.DEFAULT_PIT_NUM; ++iter) {
				final int buttonIndex = Const.DEFAULT_PIT_NUM - iter + 1;
				Button b = new Button();
				b.setImmediate(true);
				if (iter == 0) {
					b.addClickListener(new JokeClickListener());
					b.setIcon(new ClassResource("/static/img/ico_valid.png"), "grava");
					b.setWidth(Const.DEFAULT_WIDTH, Unit.PIXELS);
					buttonIndexPair.add(new java.util.AbstractMap.SimpleEntry<>(b, 0));
				} else {
					b.addClickListener(new ClickListener() {

						@Override
						public void buttonClick(ClickEvent event) {
							arbiter.makeTurn(player, buttonIndex);
						}
					});
					buttonIndexPair.add(new java.util.AbstractMap.SimpleEntry<>(b, buttonIndex));
				}
				grid.addComponent(b, iter, 1);

			}
			grid.addComponent(player.getImage(), Const.DEFAULT_PIT_NUM + 1, 0, Const.DEFAULT_PIT_NUM + 1, 1);

		} else {
			grid.addComponent(player.getImage(), 0, 0, 0, 1);
			for (int iter = 1; iter <= Const.DEFAULT_PIT_NUM + 1; ++iter) {
				Button b = new Button();
				b.setImmediate(true);
				final int buttonIndex = iter;
				if (iter == Const.DEFAULT_PIT_NUM + 1) {
					b.addClickListener(new JokeClickListener());
					b.setIcon(new ClassResource("/static/img/ico_valid.png"), "grava");
					b.setWidth(Const.DEFAULT_WIDTH, Unit.PIXELS);
					buttonIndexPair.add(new java.util.AbstractMap.SimpleEntry<>(b, 0));
				} else {
					b.addClickListener(new ClickListener() {

						@Override
						public void buttonClick(ClickEvent event) {
							arbiter.makeTurn(player, buttonIndex);
						}
					});
					buttonIndexPair.add(new java.util.AbstractMap.SimpleEntry<>(b, buttonIndex));
				}
				grid.addComponent(b, iter, 0);

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

	public static class JokeClickListener implements ClickListener {

		@Override
		public void buttonClick(ClickEvent event) {
			if (jokeCounter != 0 && (jokeCounter % 11 == 0 || jokeCounter % 17 == 0)) {
				Window window = new Window("Plankton says: \"Don't touch my grava!\"");
				window.setModal(true);
				window.setResizable(false);
				Image img = new Image();
				img.setSource(new ClassResource("/static/img/plankton.png"));
				window.setContent(img);
				UI.getCurrent().addWindow(window);
			}
			++jokeCounter;
		}

	}
}
