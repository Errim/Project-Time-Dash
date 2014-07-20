package com.ludumdare.game.entity;

import com.ludumdare.game.Game;
import java.awt.Graphics;
import com.ludumdare.game.effects.Effect_dust;
import com.ludumdare.game.helper.Art;
import com.ludumdare.game.helper.Timer;
import gamemath.GameMath;

/**
 * Created by J on 20/07/2014.
 */

public class Bouncer extends Enemy {
	private static final float jump_force = 360f, jump_time = 0.9f, hunt_speed = 45f;
	private Timer jump_timer;
	private boolean was_on_ground = false;


	public Bouncer(float x, float y, float height, float width, face facing, Game game) {
		super(x, y, height, width, true, facing, game);
		jump_timer = new Timer(jump_time, true);
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
			xspeed = 0;
			jump_timer.logic(Game.delta_time);
			if (!was_on_ground) {
				jump_timer.reset();
			}
			else if (jump_timer.isDone() && yspeed <= 0) {
				jump(jump_force);
			}
		} else {
			float player_x = game.player.get_x();

			target_x += (player_x - target_x) * 1.5f * Game.delta_time;

			if (target_x > x) {
				facing = face.RIGHT;
				xspeed = hunt_speed;
			} else {
				facing = face.LEFT;
				xspeed = -hunt_speed;
			}
		}
		was_on_ground = is_on_ground();
		super.logic();
	}

	public void draw(Graphics g) {
		//super.draw(g);

		if (!is_alive()) return;

		Art.gorillaSet.drawTile(get_screen_x(), get_screen_y(), is_on_ground() ? 0 : 1, 0, g);
	}
}
