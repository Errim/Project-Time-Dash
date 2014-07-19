package com.ludumdare.game.entity;

import com.ludumdare.game.Game;

import java.awt.*;

/**
 * Created by J on 19/07/2014.
 */

public class Entity {
	Game game;
    public float x, y, height, width;
	public boolean collision;

	public int get_width() { return (int)width; }
	public int get_height() { return (int)height; }
	public int get_x() { return (int)x; }
	public int get_y() { return (int)y; }
	public int get_int_x() { return (int)get_x(); }
	public int get_int_y() { return (int)get_y(); }
	public int get_screen_x() { return get_x(); }
	public int get_screen_y() { return get_y(); }

	public boolean collides_with(Entity e) { return collides_with(e.get_x(), e.get_y(), e.get_width(), e.get_height()); }
	public boolean collides_with(float xx, float yy, float w, float h) {
		return (xx + w > x &&
				yy + h > y &&
				xx < x + width &&
				yy < y + height);
	}

	public Entity(float x, float y, float height, float width, boolean collision, Game game) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.collision = collision;
		this.game = game;
	}
	public void logic() {

	}
	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(get_screen_x(), get_screen_y(), get_width(), get_height());
	}
}
