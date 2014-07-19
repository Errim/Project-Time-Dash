package com.ludumdare.game.entity;

import com.emilstrom.input.InputEngine;
import com.emilstrom.input.KeyboardInput;
import com.ludumdare.game.Game;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by J on 19/07/2014.
 */

public class Player extends Actor {
	private static final float acceleration = 500, max_speed = 400, friction = 800, friction_air = 300, jump_force = 1200, jump_hold_inc = 200, jump_hold_limit = 400;

	public enum states {GROUND, AIR, WALL};
	public states state;

	public Player(float x, float y, float height, float width, boolean collision, face facing) {
		super(x, y, height, width, collision, facing);
	}

	public void jump() {
		if (is_in_air()) return;

		
	}

	public void dash() {
	}

	public void slide() {
	}

	public void logic() {
		KeyboardInput in = InputEngine.getKeyboardInput();

		float f = is_in_air() ? friction_air : friction;

		if (in.isKeyDown(KeyEvent.VK_RIGHT)) xspeed += (acceleration + f) * Game.delta_time;
		if (in.isKeyDown(KeyEvent.VK_LEFT)) xspeed -= (acceleration + f) * Game.delta_time;
		if (in.isKeyDown(KeyEvent.VK_X))
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
