package com.ludumdare.game.helper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class Art {
	public static Tileset characterSet = new Tileset(loadImage("/character_set.png"), 16, 16),
		batSet = new Tileset(loadImage("/bat.png"), 12, 12),
		bouncerSet = new Tileset(loadImage("/bouncer2.png"), 13, 13),
		tileset = new Tileset(loadImage("/tileset.png"), 17, 17);
	public static BufferedImage map = (BufferedImage)loadImage("/map.png"),
		logo = (BufferedImage)loadImage("/logo.png"),
		logo_ripple = (BufferedImage)loadImage("/logo_ripple.png");
	
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