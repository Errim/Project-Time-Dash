package com.ludumdare.game.entity;

/**
 * Created by J on 19/07/2014.
 */

public class Actor extends Entity {
	public enum face {RIGHT, LEFT}
	public face facing = face.RIGHT;

	float xspeed, yspeed;

	public Actor(float x, float y, float height, float width, boolean collision, face facing) {
		super(x, y, height, width, collision);
		this.facing = facing;
	}

	public void logic() {

	}
}