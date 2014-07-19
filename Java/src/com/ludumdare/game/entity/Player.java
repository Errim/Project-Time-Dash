package com.ludumdare.game.entity;

import com.emilstrom.input.InputEngine;
import com.emilstrom.input.KeyboardInput;
import com.ludumdare.game.Environment;
import com.ludumdare.game.Game;
import com.ludumdare.game.effects.Effect_blood;
import com.ludumdare.game.effects.Effect_dash;
import com.ludumdare.game.helper.Animation;
import com.ludumdare.game.helper.Art;
import gamemath.GameMath;

import java.awt.*;
import java.awt.event.KeyEvent;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by J on 19/07/2014.
 */

public class Player extends Actor {
	private static final float acceleration = 1200, max_speed = 120, friction = 800, friction_air = 140, jump_force = 200, jump_hold_inc = 400, jump_hold_limit = 40;

	public enum states {GROUND, AIR, WALL};
	public states state;

	Animation animation_run = new Animation(Art.characterSet, 0, 0, 6, 0.1f),
		animation_idle = new Animation(Art.characterSet, 0, 1, 2, 1.2f);

	Effect_dash effect_dash;

	KeyboardInput old_input;

	//Dashing stuff
	static final float dash_length = 1.2f, dash_accuracy = 50f; //IN SECONDS WHOO

	float dash_list_x[] = new float[(int)(dash_accuracy * dash_length * 2)];
	float dash_list_y[] = new float[(int)(dash_accuracy * dash_length * 2)];
	int dash_list_i = 0;

	float dash_record_timer = 0f;
	float dash_ability_value = 2f;

	//Double jumping
	int jump_points = 2;

	public Player(float x, float y, float height, float width, boolean collision, face facing, Game game) {
		super(x, y, height, width, collision, facing, game);

		clear_position_list();
	}

	public void jump() {
		if (jump_points <= 0) return;

		yspeed = -jump_force;
		jump_points--;
	}

	public void jump_hold() {
		if (-yspeed > jump_hold_limit) {
			yspeed -= jump_hold_inc * Game.delta_time;
		}
	}

	public void dash() {
		if (dash_ability_value < 1f) return;

		float dash_position[] = get_dash_position();

		float dash_x = dash_position[0],
				dash_y = dash_position[1];

		//Gain some momentum from the dash
		float dir = (float)GameMath.getDirection(x, y, dash_x, dash_y),
				len = (float)GameMath.getDistance(x, y, dash_x, dash_y);

		effect_dash = new Effect_dash(x, y, dir, len);

		//KILL STUFF YEYEYEEYYE AHDUIAWHDHWID FEELS GOOD AROUND MY DICK
		final int precision = (int)(len / 5);
		float check_x = x, check_y = y;

		for(int i=0; i<precision; i++) {
			check_x += (float)GameMath.lengthDirX(dir, len/precision);
			check_y += (float)GameMath.lengthDirY(dir, len/precision);

			for(Enemy e : game.enemy_list) {
				if (e != null && e.is_alive() && e.collides_with(check_x, check_y, get_width(), get_height())) e.kill(dir);
			}
		}

		xspeed = (float)GameMath.lengthDirX(dir, 200f + 75f * (len / 100f));
		yspeed = (float)GameMath.lengthDirY(dir, 200f + 75f * (len / 100f));

		x = dash_position[0];
		y = dash_position[1];

		dash_list_i = (dash_list_i + dash_list_x.length/2) % dash_list_x.length;

		clear_position_list(dash_list_i, dash_list_x.length/2);
		dash_ability_value -= 1f;
	}

	public void slide() {
	}

	public void clear_position_list() {
		for(int i=0; i<dash_list_x.length; i++) {
			dash_list_x[i] = x;
			dash_list_y[i] = y;
		}
	}

	public void clear_position_list(int index, int n) {
		for(int i=0; i<n; i++) {
			dash_list_x[(index + i) % dash_list_x.length] = x;
			dash_list_y[(index + i) % dash_list_y.length] = y;
		}
	}

	public void record_position(float x, float y) {
		dash_list_x[dash_list_i] = x;
		dash_list_y[dash_list_i] = y;
		dash_list_i = (dash_list_i + 1) % dash_list_x.length;
	}

	public int get_dash_position_index() {
		return (dash_list_i + dash_list_x.length / 2) % dash_list_x.length;
	}

	public float[] get_dash_position() {
		int index = get_dash_position_index();
		return get_dash_position(index);
	}
	public float[] get_dash_position(int index) {
		float ret[] = new float[2];

		ret[0] = dash_list_x[index];
		ret[1] = dash_list_y[index];

		return ret;
	}

	public float[] get_shadow_speed() {
		int index = get_dash_position_index();
		float pos1[] = get_dash_position(index);
		float pos2[] = get_dash_position((int)GameMath.mod(index-1, dash_list_x.length));

		return new float[]{(pos1[0] - pos2[0]) * dash_accuracy, (pos1[1] - pos2[1]) * dash_accuracy};
	}

	public boolean get_shadow_on_ground() {
		float pos[] = get_dash_position();
		return (pos[1] > 100 - 2);
	}

	public void logic(Environment environment) {
		animation_run.logic(Math.abs(xspeed) / max_speed);
		animation_idle.logic(1f);

		if (effect_dash != null) effect_dash.logic();

		KeyboardInput in = InputEngine.getKeyboardInput();
		if (old_input == null) old_input = in;

		if (is_on_ground() && yspeed >= 0) jump_points = 2;

		float f = is_on_ground() ? friction : friction_air;

		if (xspeed > 0) xspeed = Math.max(0, xspeed - f * Game.delta_time);
		if (xspeed < 0) xspeed = Math.min(0, xspeed + f * Game.delta_time);

		if (in.isKeyDown(KeyEvent.VK_RIGHT)) xspeed = Math.min(xspeed + (acceleration + f) * Game.delta_time, max_speed);
		if (in.isKeyDown(KeyEvent.VK_LEFT)) xspeed = Math.max(xspeed - (acceleration + f) * Game.delta_time, -max_speed);
		if (in.isKeyDown(KeyEvent.VK_X)) jump_hold();
		if (in.isKeyDown(KeyEvent.VK_X) && !old_input.isKeyDown(KeyEvent.VK_X)) jump();
		if (in.isKeyDown(KeyEvent.VK_Z) && !old_input.isKeyDown(KeyEvent.VK_Z)) dash();

		//Record position LEL
		dash_record_timer += Game.delta_time;
		if (dash_record_timer >= 1 / dash_accuracy) {
			record_position(x, y);
			dash_record_timer -= 1 / dash_accuracy;
		}

		//Update dash ability value
		dash_ability_value += Game.delta_time / dash_length;
		if (dash_ability_value > 2f) dash_ability_value = 2f;

		//Direction
		if (xspeed > 0) facing = face.RIGHT;
		if (xspeed < 0) facing = face.LEFT;

		old_input = in;

		super.logic(environment);
	}

	public void draw(Graphics g) {
		//super.draw(g);

		if (dash_ability_value > 1f) {
			float shadow_position[] = get_dash_position();

			g.setColor(Color.RED);
			g.fillRect((int) shadow_position[0], (int) shadow_position[1], get_width(), get_height());
		}


		boolean flip_sprite = facing == face.LEFT;

		if (is_on_ground()) {
			if (Math.abs(xspeed) > 0.01f) {
				if (Math.abs(xspeed) <= max_speed && (old_input.isKeyDown(KeyEvent.VK_LEFT) || old_input.isKeyDown(KeyEvent.VK_RIGHT)))
					animation_run.draw(get_screen_x(), get_screen_y(), flip_sprite, g);
				else
					Art.characterSet.drawTile(get_screen_x(), get_screen_y(), 0, 3, flip_sprite, g);
			}
			else
				animation_idle.draw(get_screen_x(), get_screen_y(), flip_sprite, g);
		} else {
			if (yspeed > 0)
				Art.characterSet.drawTile(get_screen_x(), get_screen_y(), 1, 2, flip_sprite, g);
			else
				Art.characterSet.drawTile(get_screen_x(), get_screen_y(), 0, 2, flip_sprite, g);
		}


		if (state == states.GROUND) {
		} else if (state == states.AIR) {
		} else if (state == states.WALL) {
		} else {
		}

		if (effect_dash != null) effect_dash.draw(g);
	}
}
