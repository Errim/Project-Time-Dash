package com.ludumdare.game.effects;

import com.ludumdare.game.Game;

import java.awt.*;

/**
 * Created by Emil on 2014-07-20.
 */
public class Effect {
	Game game;
	public Effect(Game game) {
		this.game = game;
	}

	public void logic() {}
	public void draw(Graphics g) {}
}
