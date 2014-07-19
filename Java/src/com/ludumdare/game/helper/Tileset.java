package com.ludumdare.game.helper;

import java.awt.Graphics;
import java.awt.Image;

public class Tileset {
	public Image tilesetAsset;
	public int tilewidth, tileheight;

	public Tileset(Image tilesetAsset, int tilewidth, int tileheight) {
		init(tilesetAsset, tilewidth, tileheight);
	}
	
	public void init(Image tilesetAsset, int tileWidth, int tileHeight) {
		this.tilesetAsset = tilesetAsset;
		this.tilewidth = tileWidth;
		this.tileheight = tileHeight;
	}
	
	public void drawTile(int x, int y, int tilex, int tiley, Graphics g) {
		drawTile(x, y, tilex, tiley, 1, 1, g);
	}
	public void drawTile(int x, int y, int tilex, int tiley, boolean flip, Graphics g) {
		drawTile(x, y, tilex, tiley, flip ? -1 : 1, 1, g);
	}
	public void drawTile(int x, int y, int tilex, int tiley, float scale_x, float scale_y, Graphics g) {
		if (scale_x < 0) x -= scale_x * tilewidth;
		if (scale_y < 0) y -= scale_y * tileheight;
		g.drawImage(tilesetAsset, x, y, (int)(x + scale_x*tilewidth), (int)(y + scale_y*tileheight), tilex*tilewidth + tilex, tiley*tileheight + tiley, (tilex+1)*tilewidth + tilex, (tiley+1)*tileheight + tiley, null);
	}
}