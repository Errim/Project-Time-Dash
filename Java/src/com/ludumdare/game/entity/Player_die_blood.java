package com.ludumdare.game.entity;

import com.ludumdare.game.Game;
import gamemath.GameMath;

import java.awt.*;

/**
 * Created by Emil on 2014-07-20.
 */
public class Player_die_blood {
	Game game;
	public float x, y, xspeed, yspeed;
	public int size;

	public int get_screen_x() { return (int)(x - game.game_screen.get_x()); }
	public int get_screen_y() { return (int)(y - game.game_screen.get_y()); }

	public Player_die_blood(float x, float y, Game game) {
		this.game = game;
		this.x = x + (float)GameMath.getRndDouble(-10, 10);
		this.y = y + (float)GameMath.getRndDouble(-10, 10);
		xspeed = (float)GameMath.getRndDouble(-100, 100);
		yspeed = (float)GameMath.getRndDouble(-120, -45);
		size = GameMath.getRndInt(1, 5);
	}

	public void draw(Graphics g) {
		x += xspeed * Game.delta_time;
		y += yspeed * Game.delta_time;

		yspeed += Actor.GRAVITY_FACTOR * Game.delta_time;

		g.setColor(new Color(1f, 0f, 0f, 1f));
		g.fillRect(get_screen_x() - size/2, get_screen_y() - size/2, size, size);
	}
}

