package com.ludumdare.game;

import com.ludumdare.Dash_component;
import com.ludumdare.game.effects.Effect;
import com.ludumdare.game.entity.*;
import com.ludumdare.game.entity.Enemy;
import com.ludumdare.game.helper.Art;
import com.ludumdare.game.helper.GameScreen;
import com.ludumdare.game.helper.Timer;
import gamemath.GameMath;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

/**
 * Created by Emil on 2014-07-19.
 */
public class Game {
	public static float delta_time, real_delta_time;
	public Player player;

	public final static String message_1 = "The future is now and the past is but a shadow.";
	public final static String message_2 = "Press x to dash back into the past,";
	public final static String message_3 = "slaying every enemy in your wake!";

	public Enemy enemy_list[] = new Enemy[20];
	public int enemy_index = 0;
	Timer enemy_spawn_timer = new Timer(2f, false);

	public Environment environment;
	public GameScreen game_screen;

	private Effect effect_list[] = new Effect[100];
	private int effect_i = 0;

	Timer player_die_timer = new Timer(1.8f, false);
	float black_screen = 1f;

	float logo_alpha = 2f;
	public boolean show_tut = true;
	float tut_alpha = 0f;

	public Game() {
		player_die_timer.real = true;
		start_new_game();
	}

	public void start_new_game() {
		environment = new Environment(this);

		float player_x, player_y;

		do {
			player_x = (float)GameMath.getRndDouble(0, environment.num_wide * environment.tile_width);
			player_y = (float)GameMath.getRndDouble(0, environment.num_high * environment.tile_height);
		} while(environment.collision(player_x, player_y, 8, 16));

		player = new Player(player_x, player_y, 8, 16, true, Actor.face.RIGHT, this);

		game_screen = new GameScreen(this);
		enemy_list = new Enemy[20];
	}

	public void spawn_enemy() {
		float spawn_x, spawn_y;

		do {
			spawn_x = (float)GameMath.getRndDouble(0, environment.num_wide * environment.tile_width);
			spawn_y = (float)GameMath.getRndDouble(0, environment.num_high * environment.tile_height);
		} while(environment.collision(spawn_x, spawn_y, 13, 13) ||
				(spawn_x >= game_screen.get_x() && spawn_x < game_screen.get_x() + game_screen.width &&
				spawn_y >= game_screen.get_y() && spawn_y < game_screen.get_y() + game_screen.height));

		int enemy_type =  GameMath.getRndInt(0, 7);
		if (enemy_type > 0) { /* Flyer */
			enemy_list[enemy_index] = new Flyer(spawn_x, spawn_y, 12, 8, Actor.face.LEFT, this);
		} else if (enemy_type == 0) { /* Bouncer */
			int w = 13, h = 13;
			enemy_list[enemy_index] = new Bouncer(spawn_x, spawn_y, w, h, Actor.face.LEFT, this);
		}
		enemy_index = (enemy_index + 1) % enemy_list.length;
	}

	public void add_effect(Effect e) {
		effect_list[effect_i] = e;
		effect_i = (effect_i + 1) % effect_list.length;
	}

	public void logic() {
		delta_time = real_delta_time * Math.max(0, 1 - player_die_timer.percentageDone() * 0.8f);

		if (!player.is_alive())
			player_die_timer.logic();

		if (player_die_timer.isDone()) {
			black_screen += real_delta_time / 0.5f;
			if (black_screen >= 1f) {
				black_screen = 1f;
				player_die_timer.reset();
				start_new_game();
			}
		} else black_screen -= real_delta_time / 0.5f;

		if (black_screen < 0) black_screen = 0;

		logo_alpha -= delta_time * 0.4f;
		if (show_tut) {
			if (logo_alpha <= 0) {
				tut_alpha += delta_time * 0.9f;
				if (tut_alpha > 1) tut_alpha = 1;
			}
		} else tut_alpha -= delta_time * 0.4f;

		player.logic();
		for(Enemy e : enemy_list) if (e != null) e.logic();

		if (logo_alpha <= 0) enemy_spawn_timer.logic();
		if (enemy_spawn_timer.isDone()) {
			spawn_enemy();
			enemy_spawn_timer.reset();
		}

		game_screen.logic();

		for(Effect e : effect_list) if (e != null) e.logic();
	}

	public void draw(Graphics g) {
		environment.draw(g);
		for(Enemy e : enemy_list) if (e != null) e.draw(g);
		player.draw(g);

		for(Effect e : effect_list) if (e != null) e.draw(g);

		if (black_screen > 0f) {
			g.setColor(new Color(0f, 0f, 0f, black_screen));
			g.fillRect(0, 0, Dash_component.GAME_W, Dash_component.GAME_H);
		}

		if (tut_alpha > 0f) {
			int x = 10,
					y = 20;

			g.setColor(new Color(0, 109, 138, (int)(255 * tut_alpha)));
			g.drawString(message_1, x, y);
			g.drawString(message_2, x, y + 15);
			g.drawString(message_3, x, y + 30);

			x += 1;
			y -= 1;

			g.setColor(new Color(0, 194, 245, (int)(GameMath.getRndInt(0, 156) * tut_alpha)));
			g.drawString(message_1, x, y);
			g.drawString(message_2, x, y+15);
			g.drawString(message_3, x, y+30);
		}

		if (logo_alpha > 0) {
			int w = Art.logo.getWidth(),
					h = Art.logo.getHeight(),
					s = 4;

			BufferedImage img = new BufferedImage(w*s, h*s, BufferedImage.TYPE_INT_ARGB);
			for(int x=0; x<w*s; x++) for(int y=0; y<h*s; y++) img.setRGB(x, y, 0x00000000);

			img.getGraphics().drawImage(Art.logo, 0, 0, w*s, h*s, null);

			float[] values = {1f, 1f, 1f, Math.min(1, logo_alpha)};
			float[] offset = new float[4];
			RescaleOp rop = new RescaleOp(values, offset, null);

			((Graphics2D) g).drawImage(img, rop, Dash_component.GAME_W / 2 - (w/2)*s, 40);

			for(int x=0; x<w*s; x++) for(int y=0; y<h*s; y++) img.setRGB(x, y, 0x00000000);
			img.getGraphics().drawImage(Art.logo_ripple, 0, 0, w*s, h*s, null);

			values = new float[] {1f, 1f, 1f, Math.min(1, logo_alpha) * (float)GameMath.getRndDouble()};
			rop = new RescaleOp(values, offset, null);

			((Graphics2D) g).drawImage(img, rop, Dash_component.GAME_W / 2 - (w/2) * s, 40);

			g.setColor(new Color(0, 109, 138, (int)(255 * Math.min(1, logo_alpha))));
			g.drawString("A game by Emil&Emil", 2, Dash_component.GAME_H - 12);

			g.setColor(new Color(0, 194, 245, (int)(GameMath.getRndInt(0, 156) * Math.min(1, logo_alpha))));
			g.drawString("A game by Emil&Emil", 2 + 1, Dash_component.GAME_H - 12 - 1);
		}
	}
}
