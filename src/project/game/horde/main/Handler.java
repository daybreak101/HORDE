package project.game.horde.main;

import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import project.game.horde.entities.Entity;
import project.game.horde.entities.creatures.Player;
import project.game.horde.entities.facade.PlayerMP;
import project.game.horde.entities.statics.Wall;
import project.game.horde.graphics.GameCamera;
import project.game.horde.hud.HudManager;
import project.game.horde.input.KeyManager;
import project.game.horde.input.MouseManager;
import project.game.horde.utils.Utils;
import project.game.horde.worlds.World;
import project.game.horde.zombieLogic.RoundLogic;

public class Handler {

	public static final String SAVE_FOLDER = "horde_saved_data";
	public static final String PROGRESSION_FILE = "horde_progression.txt";
	public static final String OVERALL_STATS_FILE = "horde_player_stats.txt";
	public static final String SETTINGS_FILE = "horde_settings.txt";
	public static final String UNLOCKS_FILE = "horde_unlocks.txt";
	public static final String BLESSINGS_FILE = "horde_blessings.txt";
	public static final String CUSTOMSKIN_FILE = "horde_skins.txt";
	public static final String CUSTOMHAT_FILE = "horde_hats.txt";

	private Game game;
	private World world;
	private RoundLogic rounds;
	private HudManager hud;
	private Settings settings;
	private GlobalStats globalStats;
	private Progression progression;
	private Player currentPlayer;
	private Unlocks unlocks;
	private BlessingInventory blessings;
	private CustomSkinInventory customSkin;
	private CustomHatInventory customHat;

	public Handler(Game game) {
		this.game = game;
		settings = new Settings(this);
		globalStats = new GlobalStats(this);
		progression = new Progression(this);
		unlocks = new Unlocks(this);
		blessings = new BlessingInventory(this);
		customSkin = new CustomSkinInventory(this);
		customHat = new CustomHatInventory(this);
	}

	public void setCurrentPlayer(Player player) {
		currentPlayer = player;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public boolean noVisibleOrAlivePlayers() {
		boolean visibleFound = false;
		ArrayList<Entity> players = new ArrayList<>();

		players.addAll(world.getEntityManager().getOtherPlayers());
		players.add(currentPlayer);
		for (Entity player : players) {
			if (player.getHealth() > 0) {
				if (player instanceof Player player1) {
					if (player1.getInv().getBlessings().getBlessing().equals("In Plain Sight")
							&& player1.getInv().getBlessings().isRunning()) {
					} else {
						visibleFound = true;
					}
				} else if (player instanceof PlayerMP playerMP) {
					if (playerMP.getBlessing().equals("In Plain Sight")) {

					} else {
						visibleFound = true;
					}
				}

			}

		}
		return visibleFound;
	}

	public Entity getClosestPlayerToZombie(float centerX, float centerY) {
		int zombieNode = world.getPathingLogic().getClosestNode(centerX, centerY);
		int playerNode;
		Entity closestPlayer = null;
		float closestDistance = 2000000, distance;
		Line2D.Float line;
		boolean wallFound = false;

		CopyOnWriteArrayList<PlayerMP> players = world.getEntityManager().getOtherPlayers();
		for (PlayerMP player : players) {
			if (!player.getBlessing().equals(BlessingInventory.INVISIBILITY) && player.getHealth() > 0) {
				for (Wall e : world.getEntityManager().getWalls()) {
					line = new Line2D.Float(player.getCenterX(), player.getCenterY(), centerX, centerY);
					if (line.intersects(e.getCollisionBounds(0, 0))) {
						wallFound = true;
						break;
					}
				}
				if (wallFound) {
					playerNode = world.getPathingLogic().getClosestNode(player.getCenterX(), player.getCenterY());
					if (playerNode == zombieNode) {
						distance = 0;
					} else {
						distance = world.getPathingLogic().getDistanceBetweenNodes(zombieNode, playerNode);
					}
				} else {
					distance = Utils.getEuclideanDistance(centerX, centerY, player.getX(), player.getY());
				}
				if (distance < closestDistance) {
					closestDistance = distance;
					closestPlayer = player;
					// closestNode = playerNode;
				}
			}
		}
		if(currentPlayer.getInv().getBlessings().getBlessing().equals(BlessingInventory.INVISIBILITY) &&
				currentPlayer.getInv().getBlessings().isRunning()) {
			
		}
		else if (currentPlayer.getHealth() > 0 ) {
			for (Wall e : world.getEntityManager().getWalls()) {
				line = new Line2D.Float(currentPlayer.getCenterX(), currentPlayer.getCenterY(), centerX, centerY);
				if (line.intersects(e.getCollisionBounds(0, 0))) {
					wallFound = true;
					break;
				}
			}

			if (wallFound) {
				playerNode = world.getPathingLogic().getClosestNode(currentPlayer.getCenterX(), currentPlayer.getCenterY());
				if (playerNode == zombieNode) {
					distance = 0;
				} else {
					distance = world.getPathingLogic().getDistanceBetweenNodes(zombieNode, playerNode);
				}
			} else {
				distance = Utils.getEuclideanDistance(centerX, centerY, currentPlayer.getX(), currentPlayer.getY());
			}
			if (distance < closestDistance) {
				closestDistance = distance;
				closestPlayer = currentPlayer;
				// closestNode = playerNode;
			}
		}
		return closestPlayer;
	}

	public Entity getClosestPlayer(float centerX, float centerY, boolean ignoreWalls) {
		float closestDistance = 2000000000;
		Entity closestPlayer = null;
		float distance;
		Line2D.Float line;
		boolean wallFound;

		ArrayList<Entity> players = new ArrayList<>();
		players.addAll(world.getEntityManager().getOtherPlayers());
		players.add(currentPlayer);
		for (Entity player : players) {
			wallFound = false;
			if (!ignoreWalls) {
				for (Wall e : world.getEntityManager().getWalls()) {
					line = new Line2D.Float(player.getCenterX(), player.getCenterY(), centerX, centerY);
					if (line.intersects(e.getCollisionBounds(0, 0))) {
						wallFound = true;
						break;
					}
				}
			}
			if (!wallFound) {
				distance = Utils.getEuclideanDistance(centerX, centerY, player.getX(), player.getY());
				if (closestPlayer == null) {
					closestPlayer = player;
					closestDistance = distance;
				}
				if (distance < closestDistance) {
					closestDistance = distance;
					closestPlayer = player;
				}
			}
		}
		return closestPlayer;

	}

	public GameCamera getGameCamera() {
		Player player = world.getEntityManager().getCurrentPlayer();
		return player.getGameCamera();
	}

	public KeyManager getKeyManager() {
		return game.getKeyManager();
	}

	public MouseManager getMouseManager() {
		return game.getMouseManager();
	}

	public int getWidth() {
		GraphicsConfiguration gc = game.getDisplay().getFrame().getGraphicsConfiguration();
		AffineTransform tx = gc.getDefaultTransform();
		int targetWidth = 1920;
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		int screenWidth = screenSize.width;
		double scaleX = (double) screenWidth / targetWidth * tx.getScaleX();
		return (int) (game.getWidth() / scaleX) + 1;
	}

	public int getHeight() {
		GraphicsConfiguration gc = game.getDisplay().getFrame().getGraphicsConfiguration();
		AffineTransform tx = gc.getDefaultTransform();
		int targetHeight = 1080;
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		int screenHeight = screenSize.height;
		double scaleY = (double) screenHeight / targetHeight * tx.getScaleY();
		return (int) (game.getHeight() / scaleY) + 1;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public RoundLogic getRoundLogic() {
		return rounds;
	}

	public void setRoundLogic(RoundLogic rounds) {
		this.rounds = rounds;
	}

	public Settings getSettings() {
		return settings;
	}

	public GlobalStats getGlobalStats() {
		return globalStats;
	}

	public void setGlobalStats(GlobalStats globalStats) {
		this.globalStats = globalStats;
	}

	public Progression getProgression() {
		return progression;
	}

	public void setProgression(Progression progression) {
		this.progression = progression;
	}

	public Unlocks getUnlocks() {
		return unlocks;
	}

	public void setBlessings(BlessingInventory blessings) {
		this.blessings = blessings;
	}

	public BlessingInventory getBlessings() {
		return blessings;
	}
	
	public void setCustomSkinsInv(CustomSkinInventory skins) {
		this.customSkin = skins;
	}
	
	public CustomSkinInventory getSkinInv() {
		return customSkin;
	}

	public CustomHatInventory getHatInv() {
		return customHat;
	}
}
