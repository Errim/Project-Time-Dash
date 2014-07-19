package com.ludumdare.game.entity;

import java.awt.*;

/**
 * Created by J on 19/07/2014.
 */

public class Entity {
    public float x, y, height, width;
	public boolean collision;

	public int getWidth() { return (int)width; }
	public int getHeight() { return (int)height; }
	public float getX() { return x; }
	public float getY() { return y; }
	public int getIntX() { return (int)getX(); }
	public int getIntY() { return (int)getY(); }
	public int getScreenX() { return getIntX(); }
	public int getScreenY() { return getIntY(); }

	public Entity(float x, float y, float height, float width, boolean collision) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.collision = collision;
	}

	public void logic() {

	}

	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(getScreenX(), getScreenY(), getWidth(), getHeight());
	}
}
