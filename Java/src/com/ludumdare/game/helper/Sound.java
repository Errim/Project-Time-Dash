package com.ludumdare.game.helper;

import javax.sound.sampled.*;

public class Sound {
	public static Sound jump = loadSound("/snd/jump.wav"),
		dash = loadSound("/snd/dash.wav"),
		death = loadSound("/snd/death.wav");

	public static Sound loadSound(String fileName) {
		Sound snd = new Sound();
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(Sound.class.getResource(fileName));
			
			Clip clip = AudioSystem.getClip();
			clip.open(ais);

			snd.clip = clip;
		}	catch(Exception e) {
			System.out.println("Couldn't load sound " + fileName);
			System.out.println(e);
		}

		snd.setVolume(-20f);
		return snd;
	}
	
	public Clip clip;
	
	public boolean isPlaying() {
		return clip.isRunning();
	}

	public void setVolume(float v) {
		FloatControl c = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
		c.setValue(v);
	}
	
	public void play() {		
		try {
			if (clip != null) {
				clip.stop();
				
				new Thread() {
					public void run() {
						synchronized(clip) {
							clip.setFramePosition(0);
							clip.start();
						}
					}
				}.start();
			}
		} catch(Exception e) {System.out.println(e);}
	}
	
	public void pause() {clip.stop();}
	public void resume() {clip.start();}
}