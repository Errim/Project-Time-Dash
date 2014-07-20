package com.ludumdare.game.entity;

import com.ludumdare.game.Game;

/**
 * Created by J on 20/07/2014.
 */

public class Crawler extends Enemy {


	public Crawler(float x, float y, float height, float width, face facing, Game game) {
		super(x, y, height, width, true, facing, game);
	}
}
