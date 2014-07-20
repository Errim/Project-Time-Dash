package com.ludumdare.game.entity;

import com.ludumdare.game.Environment;
import com.ludumdare.game.Game;
import com.ludumdare.game.helper.Timer;
import gamemath.GameMath;

import java.awt.*;

/**
 * Created by J on 19/07/2014.
 */

public class Actor extends Entity {
	public static final float GRAVITY_FACTOR = 600f;
	protected float gravity_multi = 1.0f;

	public enum face {RIGHT, LEFT}
	public face facing = face.RIGHT;
	public boolean flying = false;
	public int hp = 1;
	public final int dmg = 1;

	final float invincibiliy_time = 0.1f;

	protected Timer hit_timer;

	//Physics
	public float xspeed=0f, yspeed=0f;

	public float get_speed() { return (float)GameMath.getDistance(0, 0, xspeed, yspeed); }

	public Actor(float x, float y, float width, float height, boolean collision, face facing, Game game) {
		super(x, y, width, height, collision, game);
		this.facing = facing;
		hit_timer = new Timer(invincibiliy_time, true);
	}

	public boolean is_alive() { return hp > 0; }
	public boolean is_in_air() { return !is_on_ground(); }
	public boolean is_on_ground() { return game.environment.collision(x, y + 2f, width, height); }

	public float minabs(float a, float b) {
		return Math.abs(a) <= Math.abs(b) ? a : b;
	}

	public void take_hit(int dmg) {
		if (hit_timer.isDone()) {
			hit_timer.reset();
			hp -= dmg;
		}
	}

	public void take_hit() { take_hit(1); }

	public void kill() {}

	public void logic() {
		hit_timer.logic();
		if (!flying && is_in_air()) {
			yspeed += GRAVITY_FACTOR * gravity_multi * Game.delta_time;
		}

		float x_new = x + xspeed * Game.delta_time;
		float y_new = y + yspeed * Game.delta_time;

		if (collision) {
			if (!game.environment.collision(x_new, y, width, height)) { x = x_new; }
			else {
				x += minabs(game.environment.dist_x(x, x_new, y), game.environment.dist_x(x + width - 1, x_new, y));
				xspeed = 0;
			}
			if (!game.environment.collision(x, y_new, width, height)) { y = y_new; }
			else {
				y += minabs(game.environment.dist_y(y, x, y_new), game.environment.dist_y(y + height - 1, x, y_new));
				yspeed = 0;
			}
		} else {
			x = x_new;
			y = y_new;
		}
	}

	public void draw(Graphics g) {
		super.draw(g);
	}
}