package project.game.horde.worlds;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.*;
import java.awt.Point;

import project.game.horde.entities.areas.Floor;
import project.game.horde.main.Handler;
import project.game.horde.utils.Utils;

public class FloorLoader {

	// Method to load floor data from a file
	public static void loadFloors(Handler handler, String filePath) throws IOException {
		// List<Floor> floors = new ArrayList<>();
		InputStream sr = Utils.class.getResourceAsStream(filePath);
		InputStreamReader is = new InputStreamReader(sr);
		BufferedReader reader = new BufferedReader(is);
		String line;
		//Floor currentFloor = null;
		List<Integer> xPoints = new ArrayList<>();
		List<Integer> yPoints = new ArrayList<>();
		int z = 0;

		while ((line = reader.readLine()) != null) {
			System.out.println("Current line: " + line);
			line = line.trim();

			if (line.isEmpty())
				continue;

			if (line.startsWith("floor")) {
				//if (currentFloor == null) {
					// Save the previous floor and add it to the list
					int[] xArray = xPoints.stream().mapToInt(i -> i).toArray();
					int[] yArray = yPoints.stream().mapToInt(i -> i).toArray();
					//currentFloor = new Floor(handler, xArray, yArray, z); // Pass handler as null for now
					handler.getWorld().getEntityManager().addArea(new Floor(handler, xArray, yArray, z));
					System.out.println("Added new Floor at z=" + z);
					//currentFloor = null;
				//}

				// Reset points and z for the next floor
				xPoints.clear();
				yPoints.clear();
				z = 0;
			} else if (line.startsWith("z=")) {
				z = Integer.parseInt(line.split("=")[1].trim()); // Read the z-coordinate
			} else if (line.startsWith("points=")) {
				String[] pointStrings = line.split("=")[1].trim().split(" ");
				for (String pointStr : pointStrings) {
					String[] coords = pointStr.split(",");
					int x = Integer.parseInt(coords[0]);
					int y = Integer.parseInt(coords[1]);
					xPoints.add(x);
					yPoints.add(y);
				}
			}
		}
		// Add the last floor if it exists
		if (!xPoints.isEmpty() && !yPoints.isEmpty()) {
			int[] xArray = xPoints.stream().mapToInt(i -> i).toArray();
			int[] yArray = yPoints.stream().mapToInt(i -> i).toArray();
			//currentFloor = new Floor(handler, xArray, yArray, z);
			handler.getWorld().getEntityManager().addArea(new Floor(handler, xArray, yArray, z));
			System.out.println("Added last Floor at z=" + z);

		}

		reader.close();
	}

}
