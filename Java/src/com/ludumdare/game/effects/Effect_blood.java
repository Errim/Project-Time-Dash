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
			xspeed, yspeed, size;

		float alpha;

		public Particle(float x, float y, float dir, float force, float a, float size) {
			this.x = x;
			this.y = y;

			xspeed = (float)GameMath.lengthDirX(dir, force);
			yspeed = (float)GameMath.lengthDirY(dir, force);
			alpha = a;
			this.size = size;
		}

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

			g.setColor(new Color(1f, 0f, 0f, alpha));
			g.fillRect((int)x, (int)y, (int)size, (int)size);
		}
	}

	Particle particle_list[] = new Particle[40];

	public Effect_blood(float x, float y, float dir) {
		for(int i=0; i<particle_list.length; i++) {
			float particle_dir = dir + (float)GameMath.getRndDouble(-20f, 20f),
					particle_force = (float)GameMath.getRndDouble(150f, 1250f),
					particle_alpha = (float)GameMath.getRndDouble(0.4f, 1f),
					particle_size = (float)GameMath.getRndDouble(1f, 4f);

			particle_list[i] = new Particle(x, y, particle_dir, particle_force, particle_alpha, particle_size);
		}
	}

	public void logic() {
		for(Particle p : particle_list) p.logic();
	}

	public void draw(Graphics g) {
		for(Particle p: particle_list) p.draw(g);
	}
}
