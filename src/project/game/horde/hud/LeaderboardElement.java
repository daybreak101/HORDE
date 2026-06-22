package project.game.horde.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;

import project.game.horde.entities.creatures.Player;
import project.game.horde.main.Handler;
import project.game.horde.main.User;
import project.game.horde.sounds.Sounds;
import project.game.horde.states.GameState;
import project.game.horde.states.LobbyState;
import project.game.horde.states.State;
import project.game.horde.utils.Utils;

public class LeaderboardElement extends HudElement {

	ArrayList<LeaderboardSpot> spots;
	boolean newScore = false;
	String newName = "";
	
	int typeTicker = 10, typeCooldown = 10;
	int newGameTicker = 0, newGameCountdown = 1600;
	private Player player;
	private User user;

	public LeaderboardElement(Handler handler, Player player, User user) {
		super(400, 100, 0, 0, handler);
		Sounds.stopClip("backgroundMusic");
		this.user = user;
		this.player = player;
		spots = new ArrayList<LeaderboardSpot>();
		handler.getGlobalStats().writeToFile();
		readFromFile();
		// check if top 10 then do...
		checkIfTop10();
		//writeToFile();
	}

	public void readFromFile() {
		try {
			//FileReader reader = new FileReader("res/info/leaderboard.txt");
			
			InputStream sr = Utils.class.getResourceAsStream("/info/leaderboard.txt");
			InputStreamReader is = new InputStreamReader(sr);
			BufferedReader buffer = new BufferedReader(is);
			String line, result[] = new String[2];
			LeaderboardSpot spot;

			while ((line = buffer.readLine()) != null) {
				result = line.split("-");

				spot = new LeaderboardSpot(result[0], Integer.parseInt(result[1]));
				spots.add(spot);
			}
			spots.trimToSize();

			buffer.close();
			is.close();
			sr.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void checkIfTop10() {
		int round = handler.getRoundLogic().getCurrentRound();
		LeaderboardSpot newSpot = new LeaderboardSpot("", round);
		if (spots.size() < 10) {
			ArrayList<LeaderboardSpot> temp = new ArrayList<LeaderboardSpot>();
			temp = spots;
			temp.add(newSpot);
			spots = temp;
			organize();
			newScore = true;
		} else if (spots.get(spots.size() - 1).round < round) {
			spots.set(spots.size() - 1, newSpot);
			organize();
			newScore = true;
		}

	}

	// implement your own sorting algorithm
	public void organize() {
		spots.sort(new Comparator<LeaderboardSpot>() {

			@Override
			public int compare(LeaderboardSpot o1, LeaderboardSpot o2) {
				if (o1.round > o2.round)
					return -1;
				else if (o1.round < o2.round)
					return 1;
				else
					return 0;
			}

		});

		for (int i = 0; i < spots.size(); i++) {
			System.out.println(spots.get(i).name + ", " + spots.get(i).round);
		}

	}

	public void writeToFile() {
		try {
			FileWriter writer = new FileWriter("res/info/leaderboard.txt");
			BufferedWriter buffer = new BufferedWriter(writer);
			for (int i = 0; i < spots.size(); i++) {
				buffer.write(spots.get(i).name);
				buffer.write("-");
				buffer.write(Integer.toString(spots.get(i).round));
				buffer.newLine();
			}
			buffer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void tick() {
		typeTicker++;
		if (newScore && typeTicker >= typeCooldown) {
			
			String key = checkInput();
			if (key == "~") {
				if(newName != "") {
					for(LeaderboardSpot e: spots)
						if(e.name == "")
							e.name = newName;
					newScore = false;
					writeToFile();
				}
				
			} else if (key == "-") {
				if(newName != null  && (newName.length() == 1 || newName.length() == 0)) {
					typeTicker = 0;
					newName = "";
				}
				else if (newName != null) {
					typeTicker = 0;
					newName = newName.substring(0, newName.length() - 1);
				}

			} else {
				if(key != "") {
					typeTicker = 0;
				}
				if(newName.length() < 12) {
					newName += key;
				}
				
			}

		}
		else if(newScore) {
			
		}
		else {
			if(newGameTicker > newGameCountdown - 255){
				transparency++;
			}
			newGameTicker++;
			if(newGameTicker >= newGameCountdown) {
				handler.getGlobalStats().addGame();
				handler.getGlobalStats().writeToFile();
				//handler.getGame().gameState = new GameState(handler, user);
				if(handler.getCurrentPlayer().getPeer() != null) {
					State.setState(handler.getCurrentPlayer().getPeer().getLobby());
				}
				else {
					State.setState(new LobbyState(handler, user));
				}
				//Sounds.shutdownThreadPool();
			}
		}

	}

	public String checkInput() {
		if (player.getKeyManager().a && player.getKeyManager().sprint)
			return "A";
		if (player.getKeyManager().b && player.getKeyManager().sprint)
			return "B";
		if (player.getKeyManager().c && player.getKeyManager().sprint)
			return "C";
		if (player.getKeyManager().d && player.getKeyManager().sprint)
			return "D";
		if (player.getKeyManager().e && player.getKeyManager().sprint)
			return "E";
		if (player.getKeyManager().f && player.getKeyManager().sprint)
			return "F";
		if (player.getKeyManager().g && player.getKeyManager().sprint)
			return "G";
		if (player.getKeyManager().h && player.getKeyManager().sprint)
			return "H";
		if (player.getKeyManager().i && player.getKeyManager().sprint)
			return "I";
		if (player.getKeyManager().j && player.getKeyManager().sprint)
			return "J";
		if (player.getKeyManager().k && player.getKeyManager().sprint)
			return "K";
		if (player.getKeyManager().l && player.getKeyManager().sprint)
			return "L";
		if (player.getKeyManager().m && player.getKeyManager().sprint)
			return "M";
		if (player.getKeyManager().n && player.getKeyManager().sprint)
			return "N";
		if (player.getKeyManager().o && player.getKeyManager().sprint)
			return "O";
		if (player.getKeyManager().p && player.getKeyManager().sprint)
			return "P";
		if (player.getKeyManager().q && player.getKeyManager().sprint)
			return "Q";
		if (player.getKeyManager().r && player.getKeyManager().sprint)
			return "R";
		if (player.getKeyManager().s && player.getKeyManager().sprint)
			return "S";
		if (player.getKeyManager().t && player.getKeyManager().sprint)
			return "T";
		if (player.getKeyManager().u && player.getKeyManager().sprint)
			return "U";
		if (player.getKeyManager().v && player.getKeyManager().sprint)
			return "V";
		if (player.getKeyManager().w && player.getKeyManager().sprint)
			return "W";
		if (player.getKeyManager().x && player.getKeyManager().sprint)
			return "X";
		if (player.getKeyManager().y && player.getKeyManager().sprint)
			return "Y";
		if (player.getKeyManager().z && player.getKeyManager().sprint)
			return "Z";
		if (player.getKeyManager().a)
			return "a";
		if (player.getKeyManager().b)
			return "b";
		if (player.getKeyManager().c)
			return "c";
		if (player.getKeyManager().d)
			return "d";
		if (player.getKeyManager().e)
			return "e";
		if (player.getKeyManager().f)
			return "f";
		if (player.getKeyManager().g)
			return "g";
		if (player.getKeyManager().h)
			return "h";
		if (player.getKeyManager().i)
			return "i";
		if (player.getKeyManager().j)
			return "j";
		if (player.getKeyManager().k)
			return "k";
		if (player.getKeyManager().l)
			return "l";
		if (player.getKeyManager().m)
			return "m";
		if (player.getKeyManager().n)
			return "n";
		if (player.getKeyManager().o)
			return "o";
		if (player.getKeyManager().p)
			return "p";
		if (player.getKeyManager().q)
			return "q";
		if (player.getKeyManager().r)
			return "r";
		if (player.getKeyManager().s)
			return "s";
		if (player.getKeyManager().t)
			return "t";
		if (player.getKeyManager().u)
			return "u";
		if (player.getKeyManager().v)
			return "v";
		if (player.getKeyManager().w)
			return "w";
		if (player.getKeyManager().x)
			return "x";
		if (player.getKeyManager().y)
			return "y";
		if (player.getKeyManager().z)
			return "z";
		if (player.getKeyManager().enter)
			return "~";
		if (player.getKeyManager().backspace)
			return "-";
		else
			return "";
	}

	int transparency = 0;
	@Override
	public void render(Graphics g) {
		g.setColor(handler.getSettings().getHudColor());
		g.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
		if (newScore) {
			g.drawString("Enter Your name", (int) x, (int) y);
			g.drawString(newName, (int) x, (int) y + 100);
		}  
		else {
		

			g.drawString("Leaderboard", (int) x, (int) y);

			for (int i = 0; i < (spots.size()); i++) {
				if(i == 10) {
					break;
				}
				g.drawString(spots.get(i).name, (int) x - 70, (int) y + ((i + 1) * 40));
				g.drawString(Integer.toString(spots.get(i).round), (int) x + 250, (int) y + ((i + 1) * 40));
			}
		}
		
		
		if(newGameTicker > newGameCountdown - 255){
			
			Color color = new Color(0, 0, 0, transparency);
			//transparency++;
			
			g.setColor(color);
			g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
			
		}

	}

}

class LeaderboardSpot {
	public String name;
	public int round;

	public LeaderboardSpot(String name, int round) {
		this.name = name;
		this.round = round;
	}
}
