package project.game.horde.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import project.game.horde.hud.LeaderboardSpot;
import project.game.horde.utils.saved.SaveFileReader;
import project.game.horde.utils.saved.SaveFileUtils;
import project.game.horde.utils.saved.SaveFileWriter;

public class Leaderboard {

    private Handler handler;
    private ArrayList<LeaderboardSpot> spots = new ArrayList<>();

    public Leaderboard(Handler handler) {
        this.handler = handler;
        String leaderboardData;
        if (!SaveFileUtils.fileExists(Handler.SAVE_FOLDER, Handler.LEADERBOARD_FILE)) {
            leaderboardData = "";
            SaveFileWriter.writeToFile(Handler.SAVE_FOLDER, Handler.LEADERBOARD_FILE, leaderboardData);
        } else {
            leaderboardData = SaveFileReader.readFromFile(Handler.SAVE_FOLDER, Handler.LEADERBOARD_FILE);
        }
        readLeaderboard(leaderboardData);
        organize();
    }

    private void readLeaderboard(String file) {
        if (file == null || file.trim().isEmpty()) {
            return;
        }
        String[] tokens = file.split("\\s+");
        for (int i = 0; i < tokens.length; i += 5) {
            String name = tokens[i];
            int round = Integer.parseInt(tokens[i + 1]);
            int kills = Integer.parseInt(tokens[i + 2]);
            int headshots = Integer.parseInt(tokens[i + 3]);
            int downs = Integer.parseInt(tokens[i + 4]);
            spots.add(new LeaderboardSpot(name, round, kills, headshots, downs));
        }

    }

    public void writeToFile() {
        String saveFolderPath = System.getProperty("user.home") + File.separator + "Documents" + File.separator
                + Handler.SAVE_FOLDER;
        String leaderboardFilePath = saveFolderPath + File.separator + Handler.LEADERBOARD_FILE;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(leaderboardFilePath))) {
            for (LeaderboardSpot spot : spots) {
                writer.write(spot.name);
                writer.write(" ");
                writer.write(Integer.toString(spot.round));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
        }
    }

    public void addLeaderboard(int round, int kills, int headshots, int downs) {
        String username = handler.getGame().getUser().getUsername();

        LeaderboardSpot newSpot = new LeaderboardSpot(username, round, kills, headshots, downs);
        if (spots.size() < 10) {
            ArrayList<LeaderboardSpot> temp = new ArrayList<>();
            temp = spots;
            temp.add(newSpot);
            spots = temp;
            organize();
        } else if (spots.get(spots.size() - 1).round < round) {
            spots.set(spots.size() - 1, newSpot);
            organize();
        }
        writeToFile();
    }

    public void organize() {
        spots.sort(new Comparator<LeaderboardSpot>() {

            //highest rounds first
            //then resolve ties by kills
            @Override
            public int compare(LeaderboardSpot o1, LeaderboardSpot o2) {
                if (o1.round > o2.round) {
                    return -1;
                } else if (o1.round < o2.round) {
                    return 1;
                }
                else if (o1.kills > o2.kills) {
                    return -1;
                }
                else if (o1.kills < o2.kills) {
                    return 1;
                }
                else {
                    return 0;
                }
            }

        });

        for (int i = 0; i < spots.size(); i++) {
            System.out.println(spots.get(i).name + ", " + spots.get(i).round);
        }

    }

    public ArrayList<LeaderboardSpot> getSpots() {
        return spots;
    }
}
