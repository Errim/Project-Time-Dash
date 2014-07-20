package com.ludumdare.game.effects;

import com.ludumdare.game.Game;
import com.ludumdare.game.helper.Timer;
import gamemath.GameMath;

import java.awt.*;

/**
 * Created by Emil on 2014-07-20.
 */
public class Effect_slash extends Effect {
	float x, y, dir;

	Timer timer = new Timer(0.6f, false);

	public Effect_slash(float x, float y, float dir, Game g) {
		super(g);

		this.x = x;
		this.y = y;
		this.dir = dir;
	}

	public void logic() {
		timer.logic();
	}

	public void draw(Graphics g) {
		Polygon p = new Polygon();
		float factor = (float)Math.pow(5, -timer.percentageDone() * 10f);
		float pos = -40f + 80f * (1-factor);

		float xx = x - game.game_screen.get_x() + (float)GameMath.lengthDirX(dir, pos),
				yy = y - game.game_screen.get_y() + (float)GameMath.lengthDirY(dir, pos);

		p.addPoint((int)(xx - GameMath.lengthDirX(dir, 30f * factor)),
				(int)(yy - GameMath.lengthDirY(dir, 30f * factor)));
		p.addPoint((int)(xx + GameMath.lengthDirX(dir + 90, 5f * factor)),
				(int)(yy + GameMath.lengthDirY(dir + 90, 5f * factor)));
		p.addPoint((int)(xx + GameMath.lengthDirX(dir, 40f * factor)),
				(int)(yy + GameMath.lengthDirY(dir, 40f * factor)));
		p.addPoint((int)(xx + GameMath.lengthDirX(dir - 90, 5f * factor)),
				(int)(yy + GameMath.lengthDirY(dir - 90, 5f * factor)));

		g.setColor(Color.WHITE);
		g.fillPolygon(p);
	}
}
