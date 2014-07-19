package com.ludumdare.game.helper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Art {	
	public static Tileset characterSet = new Tileset(loadImage("/character_set.png"), 16, 16);
	public static BufferedImage minBild = (BufferedImage)loadImage("/filnamn");
	
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