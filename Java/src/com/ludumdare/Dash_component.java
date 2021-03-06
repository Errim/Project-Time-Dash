package com.ludumdare;

import com.emilstrom.input.InputEngine;
import com.ludumdare.game.Game;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.FileInputStream;

/**
 * Created by Emil on 2014-07-19.
 */

public class Dash_component extends Applet implements Runnable {
	public static final int GAME_W = 400, GAME_H = 300, GAME_S = 3;
	private boolean running = false;

	Thread game_thread;

	public Dash_component() {
		this.setPreferredSize(new Dimension(GAME_W * GAME_S, GAME_H * GAME_S));

		InputEngine ie = new InputEngine();

		this.addKeyListener(ie);
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

		Font font = new Font("serif", Font.BOLD, 12);
		bbg.setFont(font);

		long prev_time = System.nanoTime();

		Game game = new Game();

		this.requestFocus();

		while(running) {
			//Delta time
			long new_time = System.nanoTime();
			float delta_time = (new_time - prev_time) * 0.000000001f;  //Seconds
			prev_time = new_time;
			Game.real_delta_time = delta_time;

			//Logic
			if (this.hasFocus())
				game.logic();

			//Draw
			bbg.setColor(new Color(0.5f, 0.5f, 0.5f));
			bbg.fillRect(0, 0, GAME_W, GAME_H);
			game.draw(bbg);

			if (!this.hasFocus()) {
				bbg.setColor(new Color(0f, 0f, 0f, 0.8f));
				bbg.fillRect(0, 0, GAME_W, GAME_H);

				float a = (float)(Math.sin(System.currentTimeMillis() * 0.01) + 1) / 2;

				bbg.setColor(new Color(1f, 1f, 1f, a));
				bbg.drawString("Click to focus!", GAME_W/2 - 50, GAME_H/2 + 5);
			}
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
