package com.ludumdare.game.helper;

import com.ludumdare.Dash_component;
import com.ludumdare.game.Game;

/**
 * Created by Emil on 2014-07-19.
 */
public class GameScreen {
	Game game;

	public float x, y,
		width, height;

	public GameScreen(Game game) {
		this.game = game;
		x = 0;
		y = 0;
		width = Dash_component.GAME_W;
		height = Dash_component.GAME_H;
	}

	public int get_x() { return (int)x; }
	public int get_y() { return (int)y; }

	public void logic() {
		x = game.player.get_center_x() - width / 2;
		y = game.player.get_center_y() - height / 2;
	}
}
