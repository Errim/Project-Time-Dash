package com.ludumdare.game.helper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Art {
	public static Tileset characterSet = new Tileset(loadImage("/character_set.png"), 16, 16),
		batSet = new Tileset(loadImage("/bat.png"), 12, 12);
	public static BufferedImage map = (BufferedImage)loadImage("/map.png");
	
	public static Image loadImage(String path) {
		Image newImage = null;
		
		try {
			newImage = ImageIO.read(Art.class.getResource(path));
		} catch(Exception e) {
			System.out.println("Couldn't load image at " + path + "!");
		}
		
		return newImage;
	}
}