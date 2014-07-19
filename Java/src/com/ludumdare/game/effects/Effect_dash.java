package com.ludumdare.game.effects;

import com.ludumdare.game.helper.Timer;
import gamemath.GameMath;

import java.awt.*;

/**
 * Created by Emil on 2014-07-19.
 */
public class Effect_dash {
	float x, y, target_x, target_y, dir, len;
	Timer effect_timer = new Timer(0.5f, false);

	public Effect_dash(float x, float y, float target_x, float target_y) {
		this.x = x;
		this.y = y;
		this.target_x = target_x;
		this.target_y = target_y;
		dir = (float)GameMath.getDirection(x, y, target_x, target_y);
		len = (float)GameMath.getDistance(x, y, target_x, target_y);
	}

	public void logic() {
		if (effect_timer.isDone()) return;

		effect_timer.logic();
	}

	public void draw(Graphics g) {
		if (effect_timer.isDone()) return;

		float factor = (float)Math.pow(Math.E, -effect_timer.percentageDone() * 5f);

		Polygon p = new Polygon();
		p.addPoint((int)(x - GameMath.lengthDirX(dir, 5)), (int)(y - GameMath.lengthDirY(dir, 5)));
		p.addPoint((int)(x + GameMath.lengthDirX(dir + 90, 20 * factor)), (int)(y + GameMath.lengthDirY(dir + 90, 20 * factor)));
		p.addPoint((int)(x + GameMath.lengthDirX(dir, len * 0.8f)), (int)(y + GameMath.lengthDirY(dir, len * 0.8f)));
		p.addPoint((int)(x + GameMath.lengthDirX(dir - 90, 20 * factor)), (int)(y + GameMath.lengthDirY(dir - 90, 20 * factor)));

		g.setColor(Color.WHITE);
		g.fillPolygon(p);

		float r = (1 - factor) * 40f;

		g.setColor(new Color(1f, 1f, 1f, 1 - effect_timer.percentageDone()));
		g.drawOval((int)(target_x - r), (int)(target_y - r), (int)(r*2), (int)(r*2));
	}
}