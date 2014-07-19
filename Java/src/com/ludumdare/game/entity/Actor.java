package com.ludumdare.game.entity;

import com.ludumdare.game.Game;

import java.awt.*;

/**
 * Created by J on 19/07/2014.
 */

public class Actor extends Entity {
	public static final float GRAVITY_FACTOR = 350f;

	public enum face {RIGHT, LEFT}
	public face facing = face.RIGHT;

	//Physics
	float xspeed=0f, yspeed=0f;

	public Actor(float x, float y, float height, float width, boolean collision, face facing) {
		super(x, y, height, width, collision);
		this.facing = facing;
	}

	public boolean is_in_air() { return y > 100; }

	public void logic() {
		yspeed += GRAVITY_FACTOR * Game.delta_time;

		//Temp bounce
		if (y + yspeed * Game.delta_time > 100) yspeed *= -0.4f;

		x += xspeed * Game.delta_time;
		y += yspeed * Game.delta_time;
	}

	public void draw(Graphics g) {
		super.draw(g);
	}
}