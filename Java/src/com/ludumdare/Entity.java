package com.ludumdare;

/**
 * Created by J on 19/07/2014.
 */

public class Entity {
    public float x, y, height, width;
	public boolean collision;

	public Entity(float x, float y, float height, float width, boolean collision) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.collision = collision;
	}
	public void draw() {
	}
}
