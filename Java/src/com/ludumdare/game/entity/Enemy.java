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
	public int health = 10;
	public float float_speed = 70f;

	Effect_blood effect_blood;

	Animation animation = new Animation(Art.batSet, 0, 0, 6, 0.1f);

	public Enemy(float x, float y, float height, float width, boolean collision, face facing, Game game) {
		super(x, y, height, width, collision, facing, game);
		flying = true;
	}

	public boolean is_alive() { return health > 0; }

	public void kill(float dir) {
		health = 0;
		effect_blood = new Effect_blood(get_center_x(), get_center_y(), dir, game);
	}

	public void logic() {
		if (effect_blood != null) effect_blood.logic();

		if (!is_alive()) return;

		animation.logic(1f);

		super.logic();

		float player_x = game.player.get_x(),
				player_y = game.player.get_y();

		if (player_x > x) facing = face.RIGHT;
		else facing = face.LEFT;

		double dir = GameMath.getDirection(x, y, player_x, player_y);
		x += GameMath.lengthDirX((float)dir, float_speed * Game.delta_time);
		y += GameMath.lengthDirY((float)dir, float_speed * Game.delta_time);
	}

	public void draw(Graphics g) {
		if (effect_blood != null) effect_blood.draw(g);

		if (!is_alive()) return;

		//g.setColor(game.player.collides_with(this) ? Color.RED : Color.CYAN);
		//g.fillRect(get_screen_x(), get_screen_y(), get_width(), get_height());

		animation.draw(get_screen_x(), get_screen_y(), facing == face.RIGHT ? false : true, g);
	}
}