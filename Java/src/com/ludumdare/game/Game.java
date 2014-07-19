package com.ludumdare.game;

import com.ludumdare.game.entity.Actor;
import com.ludumdare.game.entity.Player;
import com.ludumdare.game.entity.Enemy;

import java.awt.*;

/**
 * Created by Emil on 2014-07-19.
 */
public class Game {
	public static float delta_time;
	Player player;
	Environment environment;

	public Game() {
		player = new Player(0, 0, 16, 16, true, Actor.face.RIGHT);
		Environment environment = new Environment();
		environment.tiles[0] = 1;
	}
	public void logic() {
		player.logic(environment);
	}
	public void draw(Graphics g) {
		player.draw(g);
	}
}
