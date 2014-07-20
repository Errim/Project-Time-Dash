package com.ludumdare.game.entity;

import com.emilstrom.input.InputEngine;
import com.emilstrom.input.KeyboardInput;
import com.ludumdare.game.Environment;
import com.ludumdare.game.Game;
import com.ludumdare.game.effects.*;
import com.ludumdare.game.helper.Animation;
import com.ludumdare.game.helper.Art;
import com.ludumdare.game.helper.Timer;
import gamemath.GameMath;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by J on 19/07/2014.
 */

public class Player extends Actor {
	public static final float acceleration = 1200, max_speed = 120, friction = 800, friction_air = 140, jump_force = 200, jump_hold_inc = 400, jump_hold_limit = 40, vertical_friction = 300,
		wall_jump_force_x = 160, wall_jump_force_y = 200, thump_threshold = 200, hit_force = 340, slide_interval = 0.25f, slide_force = 300;
	public int player_score = 0;
	Animation animation_run = new Animation(Art.characterSet, 0, 0, 6, 0.1f),
		animation_idle = new Animation(Art.characterSet, 0, 1, 2, 0.4f),
		animation_thump = new Animation(Art.characterSet, 0, 5, 3, 0.25f);

	KeyboardInput old_input;

	@Override
	public int get_screen_x() { return super.get_screen_x() - (Art.characterSet.tilewidth - get_width()) / 2; }

	//Dashing stuff
	public static final float dash_length = 0.6f, dash_accuracy = 50f; //IN SECONDS WHOO

	public Player_shadow player_shadow;

	float dash_record_timer = 0f;
	float dash_ability_value = 0f;

	//Jumping around
	int jump_points = 1;
	float wall_sticky = 0;
	int prev_wall_jump = 0;
	float thump_value = 0;

	float slide_timer = 0;
	float gravity_immunity = 0f;

	//Dying
	Player_die_blood die_blood_list[] = new Player_die_blood[20];
	int die_blood_list_i = 0;
	Timer die_blood_timer = new Timer(0.05f, false);

	public Player(float x, float y, float width, float height, boolean collision, face facing, Game game) {
		super(x, y, width, height, collision, facing, game);
		player_shadow = new Player_shadow(this);
		hp = 2;
	}

	public void take_hit(int dmg, Entity source) {
		super.take_hit(dmg);

		float dir = (float)GameMath.getDirection(source.get_center_x(), source.get_center_y(), get_center_x(), get_center_y());
		xspeed = (float)GameMath.lengthDirX(dir, hit_force);
		yspeed = (float)GameMath.lengthDirY(dir, hit_force);

		game.add_effect(new Effect_blood(get_center_x(), get_center_y(), dir, game));
		game.add_effect(new Effect_slash(get_center_x(), get_center_y(), dir, game));

		if (!is_alive()) {
			kill();
		}
	}

	public void kill() {
		super.kill();
		//game.start_new_game();
	}

	public void increase_score(int score) {
		player_score += score;
	}

	public void jump() {
		int wall_jump = can_wall_jump();
		if (wall_jump != 0) {
			xspeed = -wall_jump_force_x * wall_jump;
			yspeed = -wall_jump_force_y;

			game.add_effect(new Effect_dust(get_center_x() + get_width()/2 * wall_jump, get_center_y(), (float)GameMath.getDirection(0, 0, xspeed, yspeed), 1f, 30, 0.8f, game));

			wall_sticky = 0;

			jump_points = 1;
		} else {
			if (jump_points <= 0) return;

			yspeed = -jump_force;
			if (!is_on_ground()) {
				jump_points--;
				game.add_effect(new Effect_dust(get_center_x(), get_y() + get_height(), (float)GameMath.getDirection(0, 0, xspeed, yspeed), 1f, 20, 0.9f, game));
			} else {
				game.add_effect(new Effect_dust(get_center_x(), get_y() + get_height(), (float)GameMath.getDirection(0, 0, xspeed, yspeed), 1f, 10, 0.2f, game));
			}
		}
	}

	public void jump_hold() {
		if (-yspeed > jump_hold_limit) {
			yspeed -= jump_hold_inc * Game.delta_time;
		}
	}

	public boolean can_dash() { return dash_ability_value >= 1f; }

	public void dash() {
		if (dash_ability_value < 1f) return;

		float dash_position[] = player_shadow.get_position();

		float dash_x = dash_position[0],
				dash_y = dash_position[1];

		//Gain some momentum from the dash
		float dir = (float)GameMath.getDirection(x, y, dash_x, dash_y),
				len = (float)GameMath.getDistance(x, y, dash_x, dash_y);

		game.add_effect(new Effect_dash(get_center_x(), get_center_y(), dash_x + width/2, dash_y + height/2, game));

		//KILL STUFF YEYEYEEYYE AHDUIAWHDHWID FEELS GOOD AROUND MY DICK - wau
		final int precision = (int)(len / 5);
		float check_x = x, check_y = y;

		java.util.List<Enemy> enemy_hit_list = new ArrayList<Enemy>();

		for(int i=0; i<precision; i++) {
			check_x += (float)GameMath.lengthDirX(dir, len/precision);
			check_y += (float)GameMath.lengthDirY(dir, len/precision);

			for(Enemy e : game.enemy_list) {
				if (e == null || !e.is_alive() || enemy_hit_list.contains(e)) continue;

				if (e.collides_with(check_x, check_y, get_width(), get_height())) {
					e.take_hit(dmg, dir);
					enemy_hit_list.add(e);
				}
			}
		}

		xspeed = (float)GameMath.lengthDirX(dir, 200f + 75f * (len / 100f));
		yspeed = (float)GameMath.lengthDirY(dir, 200f + 75f * (len / 100f));

		x = dash_position[0];
		y = dash_position[1];

		player_shadow.dash();

		dash_ability_value -= 1f;
		jump_points = 1;
	}

	public void slide(int dir) {
		if (!is_on_ground()) {
			if (jump_points <= 0) return;

			xspeed = slide_force * dir * 0.8f;
			yspeed = 0;
			game.add_effect(new Effect_dust(get_center_x(), get_center_y(), (float)GameMath.getDirection(0, 0, xspeed, yspeed), 3f, 30, 1f, game));
			gravity_immunity = 0.2f;
			jump_points--;
		} else {
			xspeed = slide_force * dir;
			game.add_effect(new Effect_dust(get_center_x(), get_y() + get_height(), (float) GameMath.getDirection(0, 0, xspeed, yspeed), 2f, 30, 0.95f, game));
		}
	}

	public int can_wall_jump() {
		if (is_on_ground()) return 0;
		if (game.environment.collision(get_x() + 1, get_y(), get_width(), get_height())) return 1;
		if (game.environment.collision(get_x()-1, get_y(), get_width(), get_height())) return -1;

		return 0;
	}

	public void logic() {
		animation_run.logic(Math.abs(xspeed) / max_speed);
		animation_idle.logic(1f);

		if (!is_alive()) {
			die_blood_timer.logic();
			if (die_blood_timer.isDone()) {
				die_blood_list[die_blood_list_i] = new Player_die_blood(get_center_x(), get_center_y(), game);
				die_blood_list_i = (die_blood_list_i + 1) % die_blood_list.length;
				die_blood_timer.reset();
			}
		}

		KeyboardInput in = InputEngine.getKeyboardInput();
		if (old_input == null) old_input = in;

		if (is_on_ground() && yspeed >= 0) jump_points = 1;

		float f = is_on_ground() ? friction : friction_air;
		if (Math.abs(xspeed) > max_speed && is_on_ground()) f *= 0.6f;

		int wall_jump = can_wall_jump();

		if (wall_jump != prev_wall_jump) {
			wall_sticky = wall_jump;
		}
		float v_f = wall_jump != 0 ? vertical_friction : 0;

		if (wall_sticky > 0) wall_sticky = Math.max(0, wall_sticky - Game.delta_time);
		if (wall_sticky < 0) wall_sticky = Math.min(0, wall_sticky + Game.delta_time);

		prev_wall_jump = wall_jump;

		if (!is_in_air() || !in.isKeyDown(KeyEvent.VK_RIGHT))
			if (xspeed > 0) xspeed = Math.max(0, xspeed - f * Game.delta_time);
		if (!is_in_air() || !in.isKeyDown(KeyEvent.VK_LEFT))
			if (xspeed < 0) xspeed = Math.min(0, xspeed + f * Game.delta_time);

		if (v_f > 0) {
			if (yspeed > 0) yspeed = Math.max(0, yspeed - v_f * Game.delta_time);
			if (yspeed < 0) yspeed = Math.min(0, yspeed + v_f * Game.delta_time);
		}

		if (is_alive()) {
			if (in.isKeyDown(KeyEvent.VK_RIGHT)) {
				if (wall_jump == 1) wall_sticky = 1;

				if (wall_sticky < 0) {
					wall_sticky += 7f * Game.delta_time;
					if (wall_sticky >= 0) wall_sticky = 0;
				} else if (xspeed < max_speed)
					xspeed = Math.min(xspeed + (acceleration + f) * Game.delta_time, max_speed);

				if (!old_input.isKeyDown(KeyEvent.VK_RIGHT)) {
					if (slide_timer < slide_interval)
						slide(1);
					else slide_timer = 0f;
				}
			}
			if (in.isKeyDown(KeyEvent.VK_LEFT)) {
				if (wall_jump == -1) wall_sticky = -1;

				if (wall_sticky > 0) {
					wall_sticky -= 7f * Game.delta_time;
					if (wall_sticky <= 0) wall_sticky = 0;
				} else if (xspeed > -max_speed)
					xspeed = Math.max(xspeed - (acceleration + f) * Game.delta_time, -max_speed);

				if (!old_input.isKeyDown(KeyEvent.VK_LEFT)) {
					if (slide_timer < slide_interval)
						slide(-1);
					else slide_timer = 0f;
				}
			}
			if (in.isKeyDown(KeyEvent.VK_Z)) jump_hold();
			if (in.isKeyDown(KeyEvent.VK_Z) && !old_input.isKeyDown(KeyEvent.VK_Z)) jump();
			if (in.isKeyDown(KeyEvent.VK_X) && !old_input.isKeyDown(KeyEvent.VK_X)) dash();
		}

		slide_timer += Game.delta_time;
		gravity_immunity -= Game.delta_time;
		flying = gravity_immunity > 0;

		//Record position LEL
		dash_record_timer += Game.delta_time;
		if (dash_record_timer >= 1 / dash_accuracy) {
			player_shadow.record_position(x, y);
			dash_record_timer -= 1 / dash_accuracy;
		}

		//Update dash ability value
		dash_ability_value += Game.delta_time / dash_length * 0.6f;
		if (dash_ability_value > 1f) dash_ability_value = 1f;

		//Direction
		if (xspeed > 0) facing = face.RIGHT;
		if (xspeed < 0) facing = face.LEFT;

		old_input = in;

		//Thump to the ground!
		thump_value -= Game.delta_time / 0.4f;

		if (game.environment.collision(x, y + yspeed * Game.delta_time, get_width(), get_height()) && get_speed() > thump_threshold && yspeed > 0) {
			game.add_effect(new Effect_thump(get_center_x() + xspeed * Game.delta_time, get_y() + yspeed * Game.delta_time + get_height(), yspeed / thump_threshold, game));
			thump_value = 1f;
		}

		super.logic();

		player_shadow.logic();
	}

	public void draw(Graphics g) {
		boolean flip_sprite = facing == face.LEFT;

		if (!is_alive()) {
			if (is_on_ground()) Art.characterSet.drawTile(get_screen_x(), get_screen_y(), 2, 6, flip_sprite, g);
			else if (yspeed < 0) Art.characterSet.drawTile(get_screen_x(), get_screen_y(), 0, 6, flip_sprite, g);
			else Art.characterSet.drawTile(get_screen_x(), get_screen_y(), 1, 6, flip_sprite, g);

			for(Player_die_blood b : die_blood_list) if (b != null) b.draw(g);

			return;
		}

		player_shadow.draw(g);

		g.setColor(Color.BLACK);
		g.drawString(Integer.toString(player_score) + " Pts", get_screen_x() - 25, get_screen_y());

		if (is_on_ground()) {
			if (Math.abs(xspeed) > 20f) {
				if (Math.abs(xspeed) <= max_speed) {
					if (old_input.isKeyDown(KeyEvent.VK_LEFT) || old_input.isKeyDown(KeyEvent.VK_RIGHT))
						animation_run.draw(get_screen_x(), get_screen_y(), flip_sprite, g);
					else
						Art.characterSet.drawTile(get_screen_x(), get_screen_y(), 0, 5, flip_sprite, g);
				}
				else
					Art.characterSet.drawTile(get_screen_x(), get_screen_y(), 0, 3, flip_sprite, g);
			}
			else {
				if (thump_value <= 0) {
					animation_idle.draw(get_screen_x(), get_screen_y(), flip_sprite, g);
				} else {
					animation_thump.draw(get_screen_x(), get_screen_y(), flip_sprite, 1f, (1-thump_value) * animation_thump.animation_length, g);
				}
			}
		} else {
			int wall_jump = can_wall_jump();
			if (wall_jump != 0 && wall_sticky != 0)
				Art.characterSet.drawTile(get_screen_x(), get_screen_y(), 0, 4, wall_jump != 1, g);
			else if (Math.abs(xspeed) > max_speed && Math.abs(yspeed) < 100f)
				Art.characterSet.drawTile(get_screen_x(), get_screen_y(), 3, 2, flip_sprite, g);
			else if (yspeed > 0)
				Art.characterSet.drawTile(get_screen_x(), get_screen_y(), yspeed > thump_threshold ? 2 : 1, 2, flip_sprite, g);
			else
				Art.characterSet.drawTile(get_screen_x(), get_screen_y(), 0, 2, flip_sprite, g);
		}
	}
}
