package com.ludumdare.game;

import java.awt.*;
import com.ludumdare.Dash_component;

/**
 * Created by J on 19/07/2014.
 */

public class Environment {
	private int num_wide = 30, num_high = 20,
			tile_width = 32, tile_height = 32; /* 30x20 */
	public int tiles[];

	public boolean tile_clear(float x, float y) {
		int tile = find_tile(x, y);
		if (tile < 0 || tile >= tiles.length) { return false; }
		return (tiles[tile] == 0);
	}
	public int find_tile(float x, float y) {
		return get_tile((int)(x / tile_width), (int)(y / tile_height));
	}
	public int get_x(int tile) {
		return get_xth(tile) * tile_width;
	}
	public int get_y(int tile) {
		return get_yth(tile) * tile_height;
	}
	public float dist_x(float x, float tile_x, float tile_y) {
		int left = get_x(find_tile(tile_x, tile_y));
		int right = left + tile_width;
		if (Math.abs(x - left) < Math.abs(x - right)) { return x - left; }
		else { return x - right; }
	}
	public float dist_y(float y, float tile_x, float tile_y) {
		int up = get_y(find_tile(tile_x, tile_y));
		int down = up + tile_height;
		if (Math.abs(y - up) < Math.abs(y - down)) { return y - up; }
		else { return y - down; }
	}
	public void draw(Graphics g) {
		for (int i = 0 ; i < tiles.length ; i++) {
			if (tiles[i] != 0) { draw_tile(g, i); }
		}
	}
	public Environment() {
		tiles = new int[num_wide * num_high];
		for (int i = 0 ; i < tiles.length ; i++) { tiles[i] = 0; }
	}
	/* Private */
	private int get_tile(int xth, int yth) {
		return (num_high * xth) + yth;
	}
	private int get_xth(int tile) {
		return tile / num_high;
	}
	private int get_yth(int tile) {
		return tile % num_high;
	}
	private void draw_tile(Graphics g, int tile) {

	}
}