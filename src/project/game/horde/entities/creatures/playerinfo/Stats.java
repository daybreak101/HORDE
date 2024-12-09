package project.game.horde.entities.creatures.playerinfo;

import project.game.horde.main.Handler;


public class Stats {

	private Handler handler;
	//current game
	private int kills, headshots, downs, score;

	
	public Stats(Handler handler) {
		this.handler = handler;
		kills = 0;
		headshots = 0;
		score = 0;
		downs = 0;
		
	}
	

	
	public void gainKill() {
		handler.getProgression().gainXP(100);
		handler.getGlobalStats().gainKill();
		kills++;
	}
	
	public void gainScore(int dScore) {
		score += dScore;
	}
	
	public void gainDown() {
		downs++;
		handler.getGlobalStats().gainDown();
	}

	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

	public int getDowns() {
		return downs;
	}

	public void setDowns(int downs) {
		this.downs = downs;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}



	public int getHeadshots() {
		return headshots;
	}



	public void addHeadshot() {
		handler.getProgression().gainXP(50);
		this.headshots++;
		handler.getGlobalStats().addHeadshot();
	}


}
