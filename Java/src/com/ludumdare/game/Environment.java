package com.ludumdare.game;

/**
 * Created by J on 19/07/2014.
 */

public class Environment {
	private int num_wide = 30, num_high = 20; /* 30x20 */
	public int tiles[] = new int[num_wide * num_high];

	public int get_tile(int xth, int yth) {
		return (num_high * xth) + yth;
	}
}