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
			small_time = 0.6f, big_time = 1.2f, hunt_speed = 35f;
	private int jump = 0; /* 0-1 small ; 2 big */
	private Timer jump_timer;
	private boolean was_on_ground = false;


	public Bouncer(float x, float y, float height, float width, face facing, Game game) {
		super(x, y, height, width, true, facing, game);
		jump_timer = new Timer(big_time, true);
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
			if (!was_on_ground) {
				if (jump == 0) {
					jump_timer.reset(big_time);
				} else {
					jump_timer.reset(small_time);
				}
			}
			else if (jump_timer.isDone() && yspeed <= 0) {
				if (jump < 2) {
					jump(small_jump);
					jump++;
				} else {
					jump(big_jump);
					jump = 0;
				}
			}
		}
		was_on_ground = is_on_ground();

		float player_x = game.player.get_x();

		target_x += (player_x - target_x) * 1.5f * Game.delta_time;

		if (target_x > x) facing = face.RIGHT;
		else facing = face.LEFT;

		double dir = GameMath.getDirection(x, y, target_x, target_y);
		x += GameMath.lengthDirX((float)dir, hunt_speed * Game.delta_time);

		super.logic();
	}

	public void draw(Graphics g) {
		//super.draw(g);

		if (!is_alive()) return;

		Art.gorillaSet.drawTile(get_screen_x(), get_screen_y(), is_on_ground() ? 0 : 1, 0, g);
	}
}
