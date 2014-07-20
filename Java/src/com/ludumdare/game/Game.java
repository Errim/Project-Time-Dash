package com.ludumdare.game;

import com.ludumdare.game.effects.Effect;
import com.ludumdare.game.entity.*;
import com.ludumdare.game.entity.Enemy;
import com.ludumdare.game.helper.GameScreen;
import com.ludumdare.game.helper.Timer;
import gamemath.GameMath;

import java.awt.*;

/**
 * Created by Emil on 2014-07-19.
 */
public class Game {
	public static float delta_time;
	public static Timer first_game_timer;
	public Player player;

	public final static String message = "You are trapped in the future.\nPress x to dash back into the past,\nslaying every enemy in your wake!";

	public Enemy enemy_list[] = new Enemy[20];
	public int enemy_index = 0;
	Timer enemy_spawn_timer = new Timer(2f, false);

	public Environment environment;
	public GameScreen game_screen;

	private Effect effect_list[] = new Effect[100];
	private int effect_i = 0;

	public Game() {
		first_game_timer = new Timer(15f, true);
		start_new_game();
	}

	public void start_new_game() {
		player = new Player(36, 36, 16, 16, true, Actor.face.RIGHT, this);
		environment = new Environment(this);

		game_screen = new GameScreen(this);
		enemy_list = new Enemy[20];
	}

	public void spawn_enemy() {
		int enemy_type =  GameMath.getRndInt(0, 4);
		if (enemy_type < 4) { /* Flyer */
			enemy_list[enemy_index] = new Flyer(GameMath.getRndInt(0, environment.num_wide * environment.tile_width), GameMath.getRndInt(0, environment.num_high * environment.tile_height), 12, 12, Actor.face.LEFT, this);
		} else if (enemy_type == 4) { /* Bouncer */
			int w = 10, h = 10,
					x = GameMath.getRndInt(0, environment.num_high * environment.tile_height),
					y = GameMath.getRndInt(0, environment.num_wide * environment.tile_width);
			while (environment.collision(x, y, w, h)) {
				x = GameMath.getRndInt(0, environment.num_high * environment.tile_height);
				y = GameMath.getRndInt(0, environment.num_wide * environment.tile_width);
			}
			enemy_list[enemy_index] = new Bouncer(x, y, w, h, Actor.face.LEFT, this);
		}
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

	public void add_effect(Effect e) {
		effect_list[effect_i] = e;
		effect_i = (effect_i + 1) % effect_list.length;
	}

	public void logic() {
		player.logic();
		for(Enemy e : enemy_list) if (e != null) e.logic();

		enemy_spawn_timer.logic();
		if (enemy_spawn_timer.isDone()) {
			spawn_enemy();
			enemy_spawn_timer.reset();
		}

		game_screen.logic();

		for(Effect e : effect_list) if (e != null) e.logic();

		first_game_timer.logic(delta_time);
	}

	public void draw(Graphics g) {
		environment.draw(g);
		for(Enemy e : enemy_list) if (e != null) e.draw(g);
		player.draw(g);

		if (!first_game_timer.isDone()) {
			g.setColor(Color.BLACK);
			g.drawString(message, player.get_screen_x(), player.get_screen_y());
		}

		for(Effect e : effect_list) if (e != null) e.draw(g);
	}
}
