package project.game.horde.utils;

import java.util.Random;

public class RandomUtil {
	
	static Random rand = new Random();
	public static float nextFloat(float min, float max) {
		return min + rand.nextFloat() * (max - min); 
	}
	
	public static int nextInt(int min, int max) {
		return rand.nextInt(max - min + 1) + min;
	}
}
