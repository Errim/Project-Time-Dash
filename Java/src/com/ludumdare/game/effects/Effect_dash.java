package com.ludumdare.game.effects;

import com.ludumdare.game.helper.Timer;
import gamemath.GameMath;

import java.awt.*;

/**
 * Created by Emil on 2014-07-19.
 */
public class Effect_dash {
	float x, y, dir, len;
	Timer effect_timer = new Timer(0.6f, false);

	public Effect_dash(float x, float y, float dir, float len) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.len = len;
	}

	public void logic() {
		if (effect_timer.isDone()) return;

		effect_timer.logic();
	}

	public void draw(Graphics g) {
		if (effect_timer.isDone()) return;

		float factor = (float)Math.pow(Math.E, -effect_timer.percentageDone() * 10f);

		Polygon p = new Polygon();
		p.addPoint((int)(x - GameMath.lengthDirX(dir, 5)), (int)(y - GameMath.lengthDirY(dir, 5)));
		p.addPoint((int)(x + GameMath.lengthDirX(dir + 90, 20 * factor)), (int)(y + GameMath.lengthDirY(dir + 90, 20 * factor)));
		p.addPoint((int)(x + GameMath.lengthDirX(dir, len * 0.8f)), (int)(y + GameMath.lengthDirY(dir, len * 0.8f)));
		p.addPoint((int)(x + GameMath.lengthDirX(dir - 90, 20 * factor)), (int)(y + GameMath.lengthDirY(dir - 90, 20 * factor)));

		g.setColor(Color.WHITE);
		g.fillPolygon(p);
	}
}
