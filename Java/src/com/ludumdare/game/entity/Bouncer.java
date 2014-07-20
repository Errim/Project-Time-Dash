package com.ludumdare.game.entity;

import com.ludumdare.game.Game;
import java.awt.Graphics;
import com.ludumdare.game.effects.Effect_dust;
import com.ludumdare.game.helper.Art;
import com.ludumdare.game.helper.Timer;
import gamemath.GameMath;

/**
 * Created by Admin on 20/07/2014.
 */
public class Bouncer extends Enemy {
	private static final float big_jump = 300f, small_jump = 120f,
			small_time = 0.2f, big_time = 3.5f;
	private int jump = 0; /* 0-1 small ; 2 big */
	private Timer jump_timer;
	private boolean was_on_ground = false;


	public Bouncer(float x, float y, float height, float width, face facing, Game game) {
		super(x, y, height, width, true, facing, game);
		jump_timer = new Timer(small_time, true);
		flying = false;
		hp = 2;
	}

	public void jump(float force) {
		yspeed = -force;
		game.add_effect(new Effect_dust(get_center_x(), get_y() + get_height(), (float) GameMath.getDirection(0, 0, xspeed, yspeed), 1f, 10, 0.2f, game));
	}

	public void logic() {
		if (!is_alive()) return;
		if (is_on_ground()) {
			jump_timer.logic(Game.delta_time);
			if (!was_on_ground) { jump_timer.reset(); System.out.println("resett");}
			else if (jump_timer.isDone()) {
				if (jump < 2) {
					jump(small_jump);
					jump++;
				} else {
					jump(big_jump);
					jump = 0;
				}
			}
			was_on_ground = true;
		}

		super.logic();
	}

	public void draw(Graphics g) {
		//super.draw(g);

		if (!is_alive()) return;

		Art.gorillaSet.drawTile(get_screen_x(), get_screen_y(), is_on_ground() ? 0 : 1, 0, g);
	}
}
