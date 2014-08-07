package com.ludumdare.game;

import java.awt.*;

import com.ludumdare.Dash_component;
import com.ludumdare.game.helper.Art;
import gamemath.GameMath;

/**
 * Created by J on 19/07/2014.
 */

public class Environment {
	Game game;

	public int num_wide, num_high,
			tile_width = 17, tile_height = 17;
	public int tiles[];

	public boolean collision(float x, float y, float width, float height) {
		return !(tile_clear(x, y)
				&& tile_clear(x + width - 1, y)
				&& tile_clear(x, y + height - 1)
				&& tile_clear(x + width - 1, y + height - 1));
	}
	public int find_tile(float x, float y) {
		return get_tile((int)(x / tile_width), (int)(GameMath.mod(y, 0, num_high * tile_height) / tile_height));
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
		return (Math.abs(x - left) < Math.abs(x - right)) ? x - left : x - right;
	}
	public float dist_y(float y, float tile_x, float tile_y) {
		int up = get_y(find_tile(tile_x, tile_y));
		int down = up + tile_height;
		return (Math.abs(y - up) < Math.abs(y - down)) ? y - up : y - down;
	}
	public void draw(Graphics g) {
		draw_background(g);

		for (int i = 0 ; i < tiles.length ; i++) {
			if (tiles[i] != 0) { draw_tile(g, i); }
		}
	}
	public void draw_background(Graphics g) {
		int offset_x = (int)GameMath.mod(-game.game_screen.get_x() * 1f, tile_width) - tile_width,
				offset_y = (int)GameMath.mod(-game.game_screen.get_y() * 1f, tile_height) - tile_height;

		for(int xx = 0; xx < Dash_component.GAME_W / tile_width + 2; xx++)
			for(int yy=0; yy < Dash_component.GAME_H / tile_height + 2; yy++) {
				Art.tileset.drawTile(tile_width * xx + offset_x, tile_height * yy + offset_y, 1, 0, g);
			}
	}
	public Environment(Game game) {
		this.game = game;

		num_wide = Art.map.getWidth();
		num_high = Art.map.getHeight();
		int[] map_data = new int[num_wide * num_high];
		Art.map.getRGB(0, 0, num_wide, num_high, map_data, 0, num_wide);

		tiles = new int[num_wide * num_high];
		for (int i = 0 ; i < tiles.length ; i++) {
			if (map_data[i] == 0xffffffff) {
				tiles[i] = 0;
			} else {
				tiles[i] = 1;
			}
		}
	}
	/* Private */
	private boolean tile_clear(float x, float y) {
		int tile = find_tile(x, y);
		if (tile < 0 || tile >= tiles.length) { return false; }
		return (tiles[tile] == 0);
	}
	private int get_tile(int xth, int yth) {
		return (num_wide * yth) + xth;
	}
	private int get_xth(int tile) {
		return tile % num_wide;
	}
	private int get_yth(int tile) {
		return tile / num_wide;
	}
	private void draw_tile(Graphics g, int tile) {
		Art.tileset.drawTile(get_x(tile) - game.game_screen.get_x(), get_y(tile) - game.game_screen.get_y(), 0, 0, g);
	}
}