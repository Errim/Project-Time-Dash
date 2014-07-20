package com.ludumdare.game.effects;

import com.ludumdare.game.Game;
import com.ludumdare.game.helper.Timer;
import gamemath.GameMath;

import java.awt.*;

/**
 * Created by Emil on 2014-07-19.
 */
public class Effect_dash extends Effect {
	float x, y, dir, len;
	Timer effect_timer = new Timer(0.8f, false);

	Effect_ring effect_ring;

	public Effect_dash(float x, float y, float target_x, float target_y, Game game) {
		super(game);
		this.x = x;
		this.y = y;
		dir = (float)GameMath.getDirection(x, y, target_x, target_y);
		len = (float)GameMath.getDistance(x, y, target_x, target_y);

		effect_ring = new Effect_ring(target_x, target_y, 40f, 0.8f, game);
	}

	public void logic() {
		if (effect_timer.isDone()) return;

		effect_timer.logic();
		effect_ring.logic();
	}

	public void draw(Graphics g) {
		if (effect_timer.isDone()) return;

		float factor = (float)Math.pow(Math.E, -effect_timer.percentageDone() * 5f);

		Polygon p = new Polygon();
		float xx = x - game.game_screen.get_x(),
				yy = y - game.game_screen.get_y();

		p.addPoint((int)(xx - GameMath.lengthDirX(dir, 5)),
				(int)(yy - GameMath.lengthDirY(dir, 5)));
		p.addPoint((int)(xx + GameMath.lengthDirX(dir + 90, 20 * factor)),
				(int)(yy + GameMath.lengthDirY(dir + 90, 20 * factor)));
		p.addPoint((int)(xx + GameMath.lengthDirX(dir, len * 0.8f)),
				(int)(yy + GameMath.lengthDirY(dir, len * 0.8f)));
		p.addPoint((int)(xx + GameMath.lengthDirX(dir - 90, 20 * factor)),
				(int)(yy + GameMath.lengthDirY(dir - 90, 20 * factor)));

		g.setColor(Color.WHITE);
		g.fillPolygon(p);

		effect_ring.draw(g);
	}
}