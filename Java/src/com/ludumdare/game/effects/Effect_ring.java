package com.ludumdare.game.effects;

import com.ludumdare.game.Game;
import com.ludumdare.game.helper.Timer;

import java.awt.*;

/**
 * Created by Emil on 2014-07-20.
 */
public class Effect_ring extends Effect {
	float x, y,
		size;

	Timer timer;

	public Effect_ring(float x, float y, float size, float t, Game game) {
		super(game);
		this.x = x;
		this.y = y;
		this.size = size;
		timer = new Timer(t, false);
	}

	public void logic() {
		timer.logic();
	}

	public void draw(Graphics g) {
		float factor = (float)Math.pow(Math.E, -timer.percentageDone() * 5f);
		float r = (1 - factor) * size;

		g.setColor(new Color(1f, 1f, 1f, 1 - timer.percentageDone()));
		g.drawOval((int)(x - r) - game.game_screen.get_x(), (int)(y - r) - game.game_screen.get_y(), (int)(r*2), (int)(r*2));
	}
}
