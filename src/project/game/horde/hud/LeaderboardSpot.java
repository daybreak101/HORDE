package project.game.horde.hud;

public class LeaderboardSpot {

    public String name;
    public int round;
    public int kills;
    public int downs;
    public int headshots;

    public LeaderboardSpot(String name, int round, int kills, int headshots, int downs) {
        this.name = name;
        this.round = round;
        this.kills = kills;
        this.headshots = headshots;
        this.downs = downs;
    }
}