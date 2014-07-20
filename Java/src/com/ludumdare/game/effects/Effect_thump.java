package com.ludumdare.game.effects;

import com.ludumdare.game.Game;

import java.awt.*;

/**
 * Created by Emil on 2014-07-20.
 */
public class Effect_thump extends Effect {
	Effect_dust dust[] = new Effect_dust[2];
	Effect_ring effect_ring;

	public Effect_thump(float x, float y, float intensity, Game game) {
		super(game);

		dust[0] = new Effect_dust(x, y, -90 - 75, 1 + intensity * 0.2f, (int)(15 * intensity), 0.8f, game);
		dust[1] = new Effect_dust(x, y, -90 + 75, 1 + intensity * 0.2f, (int)(15 * intensity), 0.8f, game);
		effect_ring = new Effect_ring(x, y, 20f * intensity, 0.3f * intensity, game);
	}

	public void logic() {
		dust[0].logic();
		dust[1].logic();
		effect_ring.logic();
	}

	public void draw(Graphics g) {
		dust[0].draw(g);
		dust[1].draw(g);
		//effect_ring.draw(g);
	}
}
