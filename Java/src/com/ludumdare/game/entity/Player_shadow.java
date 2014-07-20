package com.ludumdare.game.entity;

import com.ludumdare.game.helper.Animation;
import com.ludumdare.game.helper.Art;
import gamemath.GameMath;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by Emil on 2014-07-19.
 */
public class Player_shadow {
	Player player;

	Animation animation_run = new Animation(Art.characterSet, 0, 0, 6, 0.1f),
			animation_idle = new Animation(Art.characterSet, 0, 1, 2, 0.4f);

	float xspeed, yspeed;
	int look_dir = 1;

	float dash_list_x[] = new float[(int)(Player.dash_accuracy * Player.dash_length * 2)];
	float dash_list_y[] = new float[(int)(Player.dash_accuracy * Player.dash_length * 2)];
	int dash_list_i = 0;

	public Player_shadow(Player p) {
		player = p;
		clear_position_list();
	}

	public int get_x() { return (int) get_position()[0]; }
	public int get_y() { return (int) get_position()[1]; }
	public int get_screen_x() { return get_x() - player.game.game_screen.get_x(); }
	public int get_screen_y() { return get_y() - player.game.game_screen.get_y(); }
	public int get_width() { return (int)player.width; }
	public int get_height() { return (int)player.height; }

	public void dash() {
		dash_list_i = (dash_list_i + dash_list_x.length/2) % dash_list_x.length;

		clear_position_list(dash_list_i, dash_list_x.length / 2);
	}

	public void clear_position_list() {
		for(int i=0; i<dash_list_x.length; i++) {
			dash_list_x[i] = player.x;
			dash_list_y[i] = player.y;
		}
	}

	public void clear_position_list(int index, int n) {
		for(int i=0; i<n; i++) {
			dash_list_x[(index + i) % dash_list_x.length] = player.x;
			dash_list_y[(index + i) % dash_list_y.length] = player.y;
		}
	}

	public void record_position(float x, float y) {
		dash_list_x[dash_list_i] = x;
		dash_list_y[dash_list_i] = y;
		dash_list_i = (dash_list_i + 1) % dash_list_x.length;
	}

	public int get_position_index() {
		return (dash_list_i + dash_list_x.length / 2) % dash_list_x.length;
	}

	public float[] get_position() {
		int index = get_position_index();
		return get_position(index);
	}
	public float[] get_position(int index) {
		float ret[] = new float[2];

		ret[0] = dash_list_x[index];
		ret[1] = dash_list_y[index];

		return ret;
	}

	public float[] get_speed() {
		int index = get_position_index();
		float pos1[] = get_position(index);
		float pos2[] = get_position((int) GameMath.mod(index - 1, dash_list_x.length));

		return new float[]{(pos1[0] - pos2[0]) * Player.dash_accuracy, (pos1[1] - pos2[1]) * Player.dash_accuracy};
	}

	public boolean is_on_ground() {
		float pos[] = get_position();
		return (player.game.environment.collision(pos[0], pos[1] + 2, get_width(), get_height()));
	}

	public int can_wall_jump() {
		if (player.game.environment.collision(get_x()+1, get_y(), get_width(), get_height())) return 1;
		if (player.game.environment.collision(get_x()-1, get_y(), get_width(), get_height())) return -1;

		return 0;
	}

	public void logic() {
		if (player.dash_ability_value < 1f) return;

		float current_speed[] = get_speed();
		xspeed = current_speed[0];
		yspeed = current_speed[1];

		if (xspeed > 5) look_dir = 1;
		if (xspeed < 5) look_dir = -1;

		animation_run.logic(Math.abs(xspeed) / Player.max_speed);
	}

	public void draw(Graphics g) {
		if (player.dash_ability_value < 1f) return;

		boolean flip_sprite = (look_dir != 1);

		if (is_on_ground()) {
			if (Math.abs(xspeed) > 0.01f) {
				animation_run.draw(get_screen_x(), get_screen_y(), flip_sprite, 0.4f, g);
			}
			else
				animation_idle.draw(get_screen_x(), get_screen_y(), flip_sprite, 0.4f, g);
		} else {
			int wall_jump = can_wall_jump();
			if (wall_jump != 0)
				Art.characterSet.drawTile(get_screen_x(), get_screen_y(), 0, 4, wall_jump == 1 ? false : true, 0.4f, g);
			else if (yspeed > 0)
				Art.characterSet.drawTile(get_screen_x(), get_screen_y(), 1, 2, flip_sprite, 0.4f, g);
			else
				Art.characterSet.drawTile(get_screen_x(), get_screen_y(), 0, 2, flip_sprite, 0.4f, g);
		}
	}
}
