package com.ludumdare.game.effects;

import com.ludumdare.game.Game;
import gamemath.GameMath;

import java.awt.*;

/**
 * Created by Emil on 2014-07-19.
 */
public class Effect_blood {
	class Particle {
		float x, y,
			xspeed, yspeed, size, rotation;

		float alpha;

		public Particle(float x, float y, float dir, float force, float a, float size, float rotation) {
			this.x = x;
			this.y = y;

			xspeed = (float)GameMath.lengthDirX(dir, force);
			yspeed = (float)GameMath.lengthDirY(dir, force);
			alpha = a;
			this.size = size;
			this.rotation = rotation;
		}

		public int get_screen_x() { return (int)x - game.game_screen.get_x(); }
		public int get_screen_y() { return (int)y - game.game_screen.get_y(); }

		public void logic() {
			if (alpha <= 0) return;

			x += xspeed * Game.delta_time;
			y += yspeed * Game.delta_time;

			xspeed -= xspeed * 15.0f * Game.delta_time;
			yspeed -= yspeed * 15.0f * Game.delta_time;

			alpha -= 0.4f * Game.delta_time;
		}

		public void draw(Graphics g) {
			if (alpha <= 0) return;

			Polygon p = new Polygon();

			p.addPoint(get_screen_x() + (int)GameMath.lengthDirX(rotation, size), get_screen_y() + (int)GameMath.lengthDirY(rotation, size));
			p.addPoint(get_screen_x() + (int)GameMath.lengthDirX(rotation+90, size), get_screen_y() + (int)GameMath.lengthDirY(rotation+90, size));
			p.addPoint(get_screen_x() + (int)GameMath.lengthDirX(rotation+180, size), get_screen_y() + (int)GameMath.lengthDirY(rotation+180, size));
			p.addPoint(get_screen_x() + (int)GameMath.lengthDirX(rotation-90, size), get_screen_y() + (int)GameMath.lengthDirY(rotation-90, size));

			g.setColor(new Color(1f, 0f, 0f, alpha));
			g.fillPolygon(p);
		}
	}

	Game game;
	Particle particle_list[] = new Particle[80];

	public Effect_blood(float x, float y, float dir, Game game) {
		this.game = game;

		for(int i=0; i<particle_list.length; i++) {
			float particle_dir = dir + (float)Math.pow((float)GameMath.getRndDouble(-1f, 1f), 8f) * 20f,
					particle_force = (float)GameMath.getRndDouble(150f, 1250f),
					particle_alpha = (float)GameMath.getRndDouble(0.4f, 1f),
					particle_size = (float)GameMath.getRndDouble(2f, 6f),
					particle_rotation = (float)GameMath.getRndDouble(0f, 360f);

			if (i > particle_list.length - 30) {
				particle_dir = (float)GameMath.getRndDouble(0, 360);
				particle_force = (float)GameMath.getRndDouble(200f, 300f);
			}

			particle_list[i] = new Particle(x, y, particle_dir, particle_force, particle_alpha, particle_size, particle_rotation);
		}
	}

	public void logic() {
		for(Particle p : particle_list) p.logic();
	}

	public void draw(Graphics g) {
		for(Particle p: particle_list) p.draw(g);
	}
}
