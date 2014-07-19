package com.ludumdare;

/**
 * Created by J on 19/07/2014.
 */

public class Actor extends Entity {
	public enum face {RIGHT, LEFT}
	public face facing = face.RIGHT;

	public Actor(float x, float y, float height, float width, boolean collision, face facing) {
		super(x, y, height, width, collision);
		this.facing = facing;
	}
}