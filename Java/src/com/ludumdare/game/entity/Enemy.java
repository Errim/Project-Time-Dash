package com.ludumdare.game.entity;

import com.ludumdare.game.Game;
import com.ludumdare.game.effects.Effect_blood;
import com.ludumdare.game.helper.Animation;
import com.ludumdare.game.helper.Art;
import gamemath.GameMath;

import java.awt.*;

/**
 * Created by Admin on 19/07/2014.
 */

public class Enemy extends Actor {
	public int dmg = 1;
	public int score = 1;
	public float float_speed = 70f;

	Effect_blood effect_blood;

	Animation animation = new Animation(Art.batSet, 0, 0, 6, 0.1f);
	float target_x, target_y;

	public Enemy(float x, float y, float height, float width, boolean collision, face facing, Game game) {
		super(x, y, height, width, collision, facing, game);
		flying = true;
		hp = 1;
	}

	public void take_hit(int dmg, float dir) {
		super.take_hit(dmg);
		if (!is_alive()) {
			kill(dir);
		}
	}

	private void kill(float dir) {
		super.kill();
		game.player.increase_score(score);
		effect_blood = new Effect_blood(get_center_x(), get_center_y(), dir, game);
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