package com.ludumdare;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Emil on 2014-07-19.
 */
public class Dash_component extends Applet implements Runnable {
	public static final int GAME_W = 400, GAME_H = 300, GAME_S = 2;
	private boolean running = false;

	Thread game_thread;

	public Dash_component() {
		this.setPreferredSize(new Dimension(GAME_W * GAME_S, GAME_H * GAME_S));
	}

	public void start() {
		if (running) return;

		running = true;

		game_thread = new Thread(this);
		game_thread.start();
	}

	public void stop() {
		if (!running) return;

		running = false;
	}

	public void run() {
		BufferedImage back_buffer = new BufferedImage(GAME_W, GAME_H, BufferedImage.TYPE_INT_ARGB);
		Graphics bbg = back_buffer.getGraphics(),
				g = this.getGraphics();

		long prev_time = System.nanoTime();

		while(running) {
			//Delta time
			long new_time = System.nanoTime();
			float delta_time = (new_time - prev_time) * 0.000000001f;  //Seconds
			prev_time = new_time;

			//Logic
			//game.logic()

			//Draw
			bbg.clearRect(0, 0, GAME_W, GAME_H);
			//game.draw(bbg);
			bbg.drawString(Integer.toString((int)(1f / delta_time)), 2, 12);
			g.drawImage(back_buffer, 0, 0, GAME_W * GAME_S, GAME_H * GAME_S, 0, 0, GAME_W, GAME_H, null);

			try{ Thread.sleep(2); } catch(Exception e) {}
		}
	}

	public static void main(String[] args) {
		Dash_component component = new Dash_component();
		JFrame frame = new JFrame("Time Dash");

		frame.setLayout(new BorderLayout());
		frame.add(component, BorderLayout.CENTER);
		frame.pack();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);

		component.start();
	}
}