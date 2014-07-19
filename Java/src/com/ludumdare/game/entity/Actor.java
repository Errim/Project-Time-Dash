package com.ludumdare.game.entity;

import com.ludumdare.game.Environment;
import com.ludumdare.game.Game;

import java.awt.*;

/**
 * Created by J on 19/07/2014.
 */

public class Actor extends Entity {
	public static final float GRAVITY_FACTOR = 600f;

	public enum face {RIGHT, LEFT}
	public face facing = face.RIGHT;
	public boolean flying = false;

	//Physics
	float xspeed=0f, yspeed=0f;

	public Actor(float x, float y, float height, float width, boolean collision, face facing, Game game) {
		super(x, y, height, width, collision, game);
		this.facing = facing;
	}

	public boolean is_in_air() { return y < 100; }
	public boolean is_on_ground() { return y > 100 - 2; }

	public void logic(Environment environment) {
		if (!flying) {
			yspeed += GRAVITY_FACTOR * Game.delta_time;
		}

		//Temp bounce
		if (y + yspeed * Game.delta_time > 100) yspeed *= -0.1f;
		//if (x + xspeed * Game.delta_time < 0) xspeed *= -0.2f;

		/*This collision detection might just work*/
		float x_new = x + xspeed * Game.delta_time;
		float y_new = y + yspeed * Game.delta_time;

		if (environment.tile_clear(x_new, y)) { x = x_new; }
		else {
			x += environment.dist_x(x, x_new, y);
			xspeed = 0;
		}
		if (environment.tile_clear(x, y_new)) { y = y_new; }
		else {
			y += environment.dist_y(y, x, y_new);
			yspeed = 0;
		}
	}

	public void draw(Graphics g) {
		super.draw(g);
	}
}