package com.ludumdare.game.entity;

import com.ludumdare.game.Game;

/**
 * Created by J on 20/07/2014.
 */

public class Crawler extends Enemy {
	private enum extra_faces {UP, DOWN, FLAT}
	private extra_faces extra_facing = extra_faces.FLAT;
	private float crawl_speed = 50f;

	public Crawler(float x, float y, float height, float width, face facing, Game game) {
		super(x, y, height, width, true, facing, game);
	}

	private boolean contact_vertical(int mult) { return game.environment.collision(x, y + (2 * mult), width, height); }
	private boolean contact_vertical() {return (contact_vertical(1) || contact_vertical(-1)); }
	private boolean contact_horizontal(int mult) { return game.environment.collision(x - (2 * mult), y, width, height); }
	private boolean contact_horizontal() {return (contact_horizontal(1) || contact_horizontal(-1)); }

	public void logic() {
		int xmult = 0, ymult = 0;

		if (contact_vertical(-1)) {                 /*Up*/
			if (contact_horizontal(-1)) {               /*Left*/
				ymult = 1;
			} else if (contact_horizontal(1)) {         /*Right*/
				ymult = -1;
			} else {                                   /*Just up*/
				xmult = 1;
			}
		} else if (contact_vertical(1)) {           /*Down*/
			if (contact_horizontal(-1)) {               /*Left*/
				ymult = -1;
			} else if (contact_horizontal(1)) {         /*Right*/
				ymult = 1;
			} else {                                    /*Just down*/
				xmult = -1;
			}
		} else {                                    /*Just side*/
			if (contact_horizontal(-1)) {               /*Left*/
				ymult = -1;
			} else if (contact_horizontal(1)) {         /*Right*/
				ymult = 1;
			}
		}

		flying = contact_horizontal() || contact_vertical();

		if(contact_horizontal() || contact_vertical()) {
			xspeed = xmult * crawl_speed;
			yspeed = ymult * crawl_speed;
		}

		super.logic();
	}
}
