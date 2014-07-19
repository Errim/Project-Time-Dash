package com.ludumdare.game.entity;

import com.emilstrom.input.InputEngine;
import com.emilstrom.input.KeyboardInput;
import com.ludumdare.game.Environment;
import com.ludumdare.game.Game;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by J on 19/07/2014.
 */

public class Player extends Actor {
	private static final float acceleration = 1200, max_speed = 120, friction = 800, friction_air = 140, jump_force = 200, jump_hold_inc = 400, jump_hold_limit = 40;

	public enum states {GROUND, AIR, WALL};
	public states state;

	public Player(float x, float y, float height, float width, boolean collision, face facing) {
		super(x, y, height, width, collision, facing);
	}

	public void jump() {
		if (!is_on_ground()) {
			if (-yspeed > jump_hold_limit) {
				yspeed -= jump_hold_inc * Game.delta_time;
			}
		} else {
			yspeed = -jump_force;
		}
	}

	public void dash() {
	}

	public void slide() {
	}

	public void logic(Environment environment) {
		super.logic(environment);

		KeyboardInput in = InputEngine.getKeyboardInput();

		float f = is_on_ground() ? friction : friction_air;

		if (xspeed > 0) xspeed = Math.max(0, xspeed - f * Game.delta_time);
		if (xspeed < 0) xspeed = Math.min(0, xspeed + f * Game.delta_time);

		if (in.isKeyDown(KeyEvent.VK_RIGHT)) xspeed = Math.min(xspeed + (acceleration + f) * Game.delta_time, max_speed);
		if (in.isKeyDown(KeyEvent.VK_LEFT)) xspeed = Math.max(xspeed - (acceleration + f) * Game.delta_time, -max_speed);
		if (in.isKeyDown(KeyEvent.VK_X)) jump();
	}

	public void draw(Graphics g) {
		super.draw(g);

		if (state == states.GROUND) {
		} else if (state == states.AIR) {
		} else if (state == states.WALL) {
		} else {
		}
	}
}
