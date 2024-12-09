package project.game.horde.graphics;

import java.awt.image.BufferedImage;

public class Animation {
	private int speed, index;
	private long lastTime, timer;
	private BufferedImage[] frames;
	private boolean stops;
	
	
					//speed in ms
	public Animation(int speed, BufferedImage[] frames) {
		this.speed = speed;
		this.frames = frames;
		index = 0;
		timer = 0;
		lastTime = System.currentTimeMillis();
		this.stops = false;
	}
	
	public Animation(int speed, BufferedImage[] frames, boolean stops) {
		this.speed = speed;
		this.frames = frames;
		index = 0;
		timer = 0;
		lastTime = System.currentTimeMillis();
		this.stops = true;
	}
	
	public void tick() {
		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		//if animation ends at last index	

		if(stops && timer > speed && index < frames.length) {
			index++;
			timer = 0;
		}
		//if animation repeats
		else if(timer > speed) {
			index++;
			timer = 0;	
			if(index >= frames.length)
				index = 0;
		}
		if(stops && index >= frames.length - 1) {
			index = frames.length - 1;
		}
	}
	
	public void resetAnim() {
		index = 0;
	}
	
	public BufferedImage getCurrentFrame() {
		return frames[index];
	}
}
