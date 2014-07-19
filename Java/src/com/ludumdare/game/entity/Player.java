package com.ludumdare.game.entity;

/**
 * Created by J on 19/07/2014.
 */

public class Player extends Actor {
	public enum states {GROUND, AIR, WALL};
	public states state;

	public Player(float x, float y, float height, float width, boolean collision, face facing) {
		super(x, y, height, width, collision, facing);
	}
	public void dash() {
	}
	public void slide() {
	}
	public void draw() {
		if (state == states.GROUND) {
		} else if (state == states.AIR) {
		} else if (state == states.WALL) {
		} else {
		}
	}
}
