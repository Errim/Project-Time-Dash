package com.ludumdare.game.entity;

import com.ludumdare.game.Game;
import java.awt.Graphics;
import com.ludumdare.game.effects.Effect_dust;
import gamemath.GameMath;

/**
 * Created by Admin on 20/07/2014.
 */
public class Bouncer extends Enemy {
	private static final float big_jump = 300f, small_jump = 120f;
	private int jump = 0; /* 0-1 small ; 2 big */

	public Bouncer(float x, float y, float height, float width, face facing, Game game) {
		super(x, y, height, width, true, facing, game);
		flying = false;
		hp = 2;
	}

	public void jump() {
		if (jump < 2) {
			yspeed = -small_jump;
			jump++;
		} else {
			yspeed = -big_jump;
			jump = 0;
		}

		game.add_effect(new Effect_dust(get_center_x(), get_y() + get_height(), (float)GameMath.getDirection(0, 0, xspeed, yspeed), 10, 0.2f, game));
	}

	public void logic() {
		if (is_on_ground()) { jump(); }
		super.logic();
	}

	public void draw(Graphics g) {
		super.draw(g);
	}
}
