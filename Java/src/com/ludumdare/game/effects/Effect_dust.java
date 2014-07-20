package com.ludumdare.game.effects;

import gamemath.GameMath;
import com.ludumdare.game.*;

import java.awt.*;

/**
 * Created by Emil on 2014-07-20.
 */
public class Effect_dust extends Effect {
	class Particle {
		float x, y,
				xspeed, yspeed, size, rotation;

		float alpha, whiteness;

		Polygon p;

		public Particle(float x, float y, float dir, float force, float a, float whiteness, float size, float rotation) {
			this.x = x;
			this.y = y;

			xspeed = (float)GameMath.lengthDirX(dir, force);
			yspeed = (float)GameMath.lengthDirY(dir, force);
			alpha = a;
			this.whiteness = whiteness;
			this.size = size;
			this.rotation = rotation;

			p = new Polygon();
			for(int i=0; i<4; i++) {
				float v_dir = (360f / 4f) * i + 45,
						v_len = size;

				p.addPoint((int)GameMath.lengthDirX(v_dir, v_len), (int)GameMath.lengthDirY(v_dir, v_len));
			}
		}

		public int get_screen_x() { return (int)x - game.game_screen.get_x(); }
		public int get_screen_y() { return (int)y - game.game_screen.get_y(); }

		public void logic() {
			if (alpha <= 0) return;

			x += xspeed * Game.delta_time;
			y += yspeed * Game.delta_time;

			xspeed -= xspeed * 5.0f * Game.delta_time;
			yspeed -= yspeed * 5.0f * Game.delta_time;

			alpha -= 1.4f * Game.delta_time;
		}

		public void draw(Graphics g) {
			if (alpha <= 0) return;

			Polygon new_p = new Polygon();

			for(int i=0; i<p.npoints; i++)
				new_p.addPoint(get_screen_x() + p.xpoints[i], get_screen_y() + p.ypoints[i]);

			g.setColor(new Color(whiteness, whiteness, whiteness, alpha));
			g.fillPolygon(new_p);
		}
	}

	Particle particle_list[];

	public Effect_dust(float x, float y, float dir, int n, float perc, Game game) {
		super(game);

		particle_list = new Particle[n];

		for(int i=0; i<particle_list.length; i++) {
			float particle_dir = dir + (float)Math.pow((float)GameMath.getRndDouble(-1f, 1f), 5f) * 40f,
					particle_force = (float)GameMath.getRndDouble(20f, 150f),
					particle_alpha = (float)GameMath.getRndDouble(0.4f, 1f),
					particle_whiteness = (float)GameMath.getRndDouble(0.6f, 0.8f),
					particle_size = (float)GameMath.getRndDouble(1f, 3f),
					particle_rotation = (float)GameMath.getRndDouble(0f, 360f);

			if (i > particle_list.length * perc) {
				particle_dir = (float)GameMath.getRndDouble(0, 360);
				particle_force = (float)GameMath.getRndDouble(0f, 90f);
			}

			particle_list[i] = new Particle(x, y, particle_dir, particle_force, particle_alpha, particle_whiteness, particle_size, particle_rotation);
		}
	}

	public void logic() {
		for(Particle p : particle_list) p.logic();
	}

	public void draw(Graphics g) {
		for(Particle p: particle_list) p.draw(g);
	}
}
