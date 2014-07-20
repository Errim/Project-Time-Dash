package com.ludumdare.game.helper;

import com.ludumdare.Dash_component;
import com.ludumdare.game.Game;
import gamemath.GameMath;

/**
 * Created by Emil on 2014-07-19.
 */
public class GameScreen {
	Game game;

	public float x, y,
		width, height;

	public GameScreen(Game game) {
		this.game = game;
		width = Dash_component.GAME_W;
		height = Dash_component.GAME_H;
		x = game.player.get_center_x() - width/2;
		y = game.player.get_center_y() - height/2;
	}

	public int get_x() { return (int)x; }
	public int get_y() { return (int)y; }

	public void logic() {
		float target_x, target_y;

		if (game.player.can_dash()) {
			float shadow_position[] = game.player.player_shadow.get_position();

			float p_x = game.player.get_center_x(),
					p_y = game.player.get_center_y(),
					s_x = shadow_position[0] + game.player.width/2,
					s_y = shadow_position[1] + game.player.height/2;

			float dir = (float)GameMath.getDirection(p_x, p_y, s_x, s_y),
					dis = (float)GameMath.getDistance(p_x, p_y, s_x, s_y);

			target_x = p_x + (float)GameMath.lengthDirX(dir, Math.min(dis/2, 50)) + game.player.xspeed*0.4f - width/2;
			target_y = p_y + (float)GameMath.lengthDirY(dir, Math.min(dis/2, 50)) + game.player.yspeed*0.4f - height/2;
		} else {
			target_x = game.player.get_center_x() + game.player.xspeed*0.4f - width / 2;
			target_y = game.player.get_center_y() + game.player.yspeed*0.4f - height / 2;
		}

		x += (target_x - x) * 2.0f * Game.delta_time;
		y += (target_y - y) * 2.0f * Game.delta_time;
	}
}
