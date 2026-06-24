package project.game.horde.ui;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Objects;

public class ColorIndex {
	public static HashMap<Integer, Color> colorIndex = new HashMap<>();
	public static final int green = 0, yellow = 1, red = 2, blue = 3, 
							magenta = 4, cyan = 5, orange = 6, white = 7;
	public static void init() {
		colorIndex.put(green, Color.green);
		colorIndex.put(yellow, Color.yellow);
		colorIndex.put(red, Color.red);
		colorIndex.put(blue, Color.blue);
		colorIndex.put(magenta, Color.magenta);
		colorIndex.put(cyan, Color.cyan);
		colorIndex.put(orange, Color.orange);
		colorIndex.put(white, Color.white);
	}
	
	public static Color getColor(int i) {
		return colorIndex.get(i);
	}
	
	public static int getKeyByValue(Color value) {
	    for (Entry<Integer, Color> entry : colorIndex.entrySet()) {
	        if (Objects.equals(value, entry.getValue())) {
	            return entry.getKey();
	        }
	    }
	    return green;
	}

}
