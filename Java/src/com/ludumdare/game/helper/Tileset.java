package com.ludumdare.game.helper;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.RescaleOp;

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
		drawTile(x, y, tilex, tiley, 1, 1, 1f, g);
	}
	public void drawTile(int x, int y, int tilex, int tiley, boolean flip, Graphics g) {
		drawTile(x, y, tilex, tiley, flip ? -1 : 1, 1, 1f, g);
	}
	public void drawTile(int x, int y, int tilex, int tiley, boolean flip, float opacity, Graphics g) {
		drawTile(x, y, tilex, tiley, flip ? -1 : 1, 1, opacity, g);
	}
	public void drawTile(int x, int y, int tilex, int tiley, float scale_x, float scale_y, float opacity, Graphics g) {
		if (opacity < 1f) {
			BufferedImage i = new BufferedImage((int)(Math.abs(scale_x)*tilewidth), (int)(Math.abs(scale_y)*tileheight), BufferedImage.TYPE_INT_ARGB);
			i.getGraphics().drawImage(tilesetAsset, scale_x > 0 ? 0 : (int)(Math.abs(scale_x) * tilewidth), scale_y > 0 ? 0 : (int)(Math.abs(scale_y) * tileheight),
					scale_x > 0 ? (int)(scale_x*tilewidth) : 0, scale_y > 0 ? (int)(scale_y*tileheight) : 0,
					tilex*tilewidth + tilex, tiley*tileheight + tiley, (tilex+1)*tilewidth + tilex, (tiley+1)*tileheight + tiley, null);

			float[] values = {1f, 1f, 1f, opacity};
			float[] offset = new float[4];
			RescaleOp rop = new RescaleOp(values, offset, null);
			((Graphics2D) g).drawImage(i, rop, x, y);
		} else {
			g.drawImage(tilesetAsset, x + (scale_x > 0 ? 0 : (int)(Math.abs(scale_x) * tilewidth)), y + (scale_y > 0 ? 0 : (int)(Math.abs(scale_y) * tileheight)),
					x + (scale_x > 0 ? (int)(scale_x*tilewidth) : 0), y + (scale_y > 0 ? (int)(scale_y*tileheight) : 0),
					tilex*tilewidth + tilex, tiley*tileheight + tiley, (tilex+1)*tilewidth + tilex, (tiley+1)*tileheight + tiley, null);
		}
	}
}