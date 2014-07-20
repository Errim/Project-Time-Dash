package com.ludumdare.game.helper;

import com.ludumdare.game.Game;

import java.awt.*;

/**
 * Created by Emil on 2014-07-19.
 */
public class Animation {
	public Tileset tileset;
	int origin_x, origin_y;
	public int animation_length;
	float frame_length;

	float frame_index;

	public Animation(Tileset tileset, int origin_x, int origin_y, int animation_length, float frame_length) {
		this.tileset = tileset;
		this.origin_x = origin_x;
		this.origin_y = origin_y;
		this.animation_length = animation_length;
		this.frame_length = frame_length;
	}

	public void logic(float factor) {
		frame_index += Game.delta_time / frame_length * factor;
		if (frame_index >= animation_length) frame_index -= animation_length;
	}

	public void draw(int x, int y, Graphics g) {
		draw(x, y, false, 1f, frame_index, g);
	}
	public void draw(int x, int y, boolean flip, Graphics g) {
		draw(x, y, flip, 1f, frame_index, g);
	}
	public void draw(int x, int y, boolean flip, float opacity, Graphics g) {
		draw(x, y, flip, opacity, frame_index, g);
	}

	public void draw(int x, int y, float frame, Graphics g) {
		draw(x, y, false, 1f, frame, g);
	}
	public void draw(int x, int y, boolean flip, float opacity, float frame, Graphics g) {
		tileset.drawTile(x, y, origin_x + (int)frame, origin_y, flip, opacity, g);
	}
}
