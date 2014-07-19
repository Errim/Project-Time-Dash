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
		float target_x, target_y;

		if (game.player.can_dash()) {
			float shadow_position[] = game.player.player_shadow.get_position();
			target_x = (game.player.get_center_x() + shadow_position[0]) / 2 - width / 2;
			target_y = (game.player.get_center_y() + shadow_position[1]) / 2 - height / 2;
		} else {
			target_x = game.player.get_center_x() - width / 2;
			target_y = game.player.get_center_y() - height / 2;
		}

		x += (target_x - x) * 12.0f * Game.delta_time;
		y += (target_y - y) * 12.0f * Game.delta_time;
	}
}
