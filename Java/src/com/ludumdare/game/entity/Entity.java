package com.ludumdare.game.entity;

import java.awt.*;

/**
 * Created by J on 19/07/2014.
 */

public class Entity {
    public float x, y, height, width;
	public boolean collision;

	public int get_width() { return (int)width; }
	public int get_height() { return (int)height; }
	public int get_x() { return (int)x; }
	public int get_y() { return (int)y; }
	public int get_int_x() { return (int)getX(); }
	public int get_int_y() { return (int)getY(); }
	public int get_screen_x() { return getX(); }
	public int get_screen_y() { return getY(); }

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
