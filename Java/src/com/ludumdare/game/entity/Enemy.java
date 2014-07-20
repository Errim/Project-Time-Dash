package com.ludumdare.game.entity;

import com.ludumdare.game.Game;
import com.ludumdare.game.effects.Effect_blood;
import com.ludumdare.game.effects.Effect_slash;
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

	float target_x, target_y;

	public Enemy(float x, float y, float height, float width, boolean collision, face facing, Game game) {
		super(x, y, height, width, collision, facing, game);
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

		dir += (float)GameMath.getRndDouble(-45f, 45f);

		game.add_effect(new Effect_blood(get_center_x(), get_center_y(), dir, game));
		game.add_effect(new Effect_slash(get_center_x(), get_center_y(), dir, game));
	}

	public void logic() {
		if (!is_alive()) return;

		super.logic();
		
		if (game.player.collides_with(this)) { game.player.take_hit(dmg, this); }
	}

	public void draw(Graphics g) {
		if (!is_alive()) return;

		super.draw(g);
	}
}