package com.ludumdare.game.entity;

import com.ludumdare.game.Game;
import com.ludumdare.game.helper.Animation;
import com.ludumdare.game.helper.Art;
import gamemath.GameMath;

import java.awt.*;

/**
 * Created by J on 20/07/2014.
 */

public class Flyer extends Enemy {
	public float float_speed = 70f;

	Animation animation = new Animation(Art.batSet, 0, 0, 6, 0.1f);

	public Flyer(float x, float y, float height, float width, face facing, Game game) {
		super(x, y, height, width, false, facing, game);
		flying = true;
	}

	public void logic() {
		if (effect_blood != null) effect_blood.logic();

		if (!is_alive()) return;

		animation.logic(1f);

		super.logic();

		float player_x = game.player.get_x(),
				player_y = game.player.get_y();

		target_x += (player_x - target_x) * 1.5f * Game.delta_time;
		target_y += (player_y - target_y) * 1.5f * Game.delta_time;

		if (target_x > x) facing = face.RIGHT;
		else facing = face.LEFT;

		double dir = GameMath.getDirection(x, y, target_x, target_y);
		x += GameMath.lengthDirX((float)dir, float_speed * Game.delta_time);
		y += GameMath.lengthDirY((float)dir, float_speed * Game.delta_time);

		if (game.player.collides_with(this)) { game.player.take_hit(dmg); }
	}

	public void draw(Graphics g) {
		if (effect_blood != null) effect_blood.draw(g);

		if (!is_alive()) return;

		//g.setColor(game.player.collides_with(this) ? Color.RED : Color.CYAN);
		//g.fillRect(get_screen_x(), get_screen_y(), get_width(), get_height());

		animation.draw(get_screen_x(), get_screen_y(), facing == face.RIGHT ? false : true, g);
	}
}