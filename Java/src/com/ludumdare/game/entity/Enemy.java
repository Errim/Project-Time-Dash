package com.ludumdare.game.entity;

/**
 * Created by Admin on 19/07/2014.
 */
public class Enemy extends Actor {
	public int hp; /* How many hits it takes to kill the sucker */

	public Enemy(float x, float y, float height, float width, boolean collision, face facing) {
		super(x, y, height, width, collision, facing);
	}

	@java.lang.Override
	public void logic() {
		super.logic();
		/* TODO - Something about death here? Or in a class that handles an array of all enemies? */
	}
}
