package project.game.horde.zombieLogic;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import project.game.horde.main.Handler;
import project.game.horde.worlds.World;

public class PlayerSpawns {

    private ArrayList<PlayerSpawn> spawns;

    public PlayerSpawns(Handler handler, String entityPath) {
        spawns = new ArrayList<>();
        createPlayerSpawns(entityPath);
    }

    public PlayerSpawn findAvailableSpawn() {
        for (PlayerSpawn spawn : spawns) {
           //find if other players are in the spawn
        }
        return null;
    }

    public void createPlayerSpawns(String entityPath) {
        System.out.println(
                World.class.getClassLoader().getResource(entityPath)
        );
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream is = World.class.getResourceAsStream(entityPath)) {

            if (is == null) {
                throw new RuntimeException("Could not find map: " + entityPath);
            }

            JsonNode root = mapper.readTree(is);
            JsonNode layers = root.get("layers");
            for (JsonNode layer : layers) {
                if (!"PlayerSpawn".equals(layer.get("name").asText())) {
                    continue;
                }

                JsonNode objects = layer.get("objects");
                for (JsonNode obj : objects) {
                    int x = obj.get("x").asInt();
                    int y = obj.get("y").asInt();
                    spawns.add(new PlayerSpawn(x, y));
                }
                // Use the JSON...
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

class PlayerSpawn {

    int x, y;

    public PlayerSpawn(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
