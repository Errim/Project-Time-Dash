package com.ludumdare.game;

import java.awt.*;
import com.ludumdare.Dash_component;

/**
 * Created by J on 19/07/2014.
 */

public class Environment {
	private int num_wide = 30, num_high = 20,
			tile_width = 32, tile_height = 32; /* 30x20 */
	public int tiles[] = new int[num_wide * num_high];

	public int get_tile(int xth, int yth) {
		return (num_high * xth) + yth;
	}
	public int get_xth(int tile) {
		return tile / num_high;
	}
	public int get_yth(int tile) {
		return tile % num_high;
	}
	public int get_x(int tile) {
		return get_xth(tile) * tile_width;
	}
	public int get_y(int tile) {
		return get_yth(tile) * tile_height;
	}
	public int find_tile(float x, float y) {
		return get_tile(Dash_component.GAME_W % tile_width, Dash_component.GAME_H % tile_width);
	}
	public boolean tile_clear(int x, int y) {
		return (tiles[find_tile(x, y)] == 0);
	}
	public void draw(Graphics g) {
		for (int i = 0 ; i < tiles.length ; i++) {
			if (tiles[i] != 0) { draw_tile(g, i); }
		}
	}
	private void draw_tile(Graphics g, int tile) {

	}
}