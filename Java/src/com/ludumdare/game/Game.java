package com.ludumdare.game;

import com.ludumdare.game.entity.Actor;
import com.ludumdare.game.entity.Enemy;
import com.ludumdare.game.entity.Player;
import com.ludumdare.game.entity.Enemy;
import com.ludumdare.game.helper.Timer;

import java.awt.*;

/**
 * Created by Emil on 2014-07-19.
 */
public class Game {
	public static float delta_time;
	public Player player;

	public Enemy enemy_list[] = new Enemy[20];
	public int enemy_index = 0;
	Timer enemy_spawn_timer = new Timer(2f, false);

	public Environment environment;

	public Game() {
		player = new Player(36, 0, 16, 16, true, Actor.face.RIGHT, this);
		environment = new Environment();
		environment.tiles[0] = 1;
	}

	public void spawn_enemy() {
		enemy_list[enemy_index] = new Enemy(100, 0, 12, 12, false, Actor.face.LEFT, this);
		enemy_index = (enemy_index + 1) % enemy_list.length;
	}

	public boolean get_enemy_collision(float x, float y, float w, float h) {
		for(Enemy e : enemy_list) {
			if (e != null && e.is_alive()) {
				if (e.collides_with(x, y, w, h)) return true;
			}
		}

		return false;
	}

	public void logic() {
		player.logic();
		for(Enemy e : enemy_list) if (e != null) e.logic();

		enemy_spawn_timer.logic();
		if (enemy_spawn_timer.isDone()) {
			spawn_enemy();
			enemy_spawn_timer.reset();
		}
	}

	public void draw(Graphics g) {
		player.draw(g);
		for(Enemy e : enemy_list) if (e != null) e.draw(g);
		environment.draw(g);
	}
}
