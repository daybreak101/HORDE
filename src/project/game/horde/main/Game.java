package project.game.horde.main;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import project.game.horde.display.Display;
import project.game.horde.graphics.CharAssets;
import project.game.horde.graphics.ImageLoader;
import project.game.horde.graphics.MenuAssets;
import project.game.horde.input.GameMouseManager;
import project.game.horde.input.KeyManager;
import project.game.horde.input.MouseManager;
import project.game.horde.sounds.MenuSounds;
import project.game.horde.sounds.Sounds;
import project.game.horde.states.LoadingState;
import project.game.horde.states.MenuState;
import project.game.horde.states.State;
import project.game.horde.ui.ColorIndex;
import project.game.horde.utils.Timer;

public class Game implements Runnable {
	private Display display;
	private int width, height;

	public String title;

	private boolean running = false;
	private Thread thread;

	// buffer strategy creates buffers (hidden screens) to prevent screen flickering
	private BufferStrategy bs;
	private Graphics2D g;

	// States
	public State gameState, menuState;

	// input
	private KeyManager keyManager;
	private MouseManager mouseManager;

	// camera
	// private GameCamera gameCamera;

	// handler
	private Handler handler;

	/// network shizz
	private User user;

	public boolean captureImage = false;

	public Game(String title, int width, int height) {
		this.width = width;
		this.height = height;
		this.title = title;
		ColorIndex.init();
		handler = new Handler(this);
		// String username = JOptionPane.showInputDialog(this, "Please enter a
		// username");
		Random rand = new Random();
		int rng = rand.nextInt(9999);
		ImageIcon customIcon = new ImageIcon("/textures/normal/zombie.png");
		// String username = JOptionPane.showInputDialog(null, "Please enter a
		// username", "HORDE", JOptionPane.PLAIN_MESSAGE);
		String username = (String) JOptionPane.showInputDialog(null, // Parent component (null for no parent)
				"Please enter a username", // The message inside the dialog
				"HORDE", // Title of the dialog
				JOptionPane.PLAIN_MESSAGE, // Message type
				customIcon, // No icon
				null, // Custom options (null here)
				"User" + rng // Default input text
		);
		user = new User(username);
		start();

	}

	// initializes graphics
	private void init() {
		display = new Display(this, title, width, height);
		keyManager = new KeyManager(handler);
		mouseManager = new MouseManager(handler);
		display.getFrame().addKeyListener(keyManager);
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getFrame().addMouseWheelListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseWheelListener(mouseManager);

		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

		// Create a new blank cursor.
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");

		// Set the blank cursor to the JFrame.
		display.getFrame().getContentPane().setCursor(blankCursor);

		Sounds.initHandler(handler);
		MenuAssets.init();
		MenuSounds.init(handler);
		CharAssets.init();
//		Assets.init();
//		BWAssets.init();
		menuState = new MenuState(handler, user);
		State.setState(menuState);

	}

	public void resetManagers() {
		keyManager = new KeyManager(handler);
		display.getFrame().addKeyListener(keyManager);
	}

	int x = 0;

	// update variables, positions of objects, etc.
	private void tick() {
		keyManager.tick();
		if (State.getState() != null) {
			State.getState().tick();
		}
	}

	public void setBS(BufferStrategy bs) {
		this.bs = bs;
	}

	Timer setSave = new Timer(1000);

	// draw all graphics to screen
	private void render() {
		if (display.getCanvas() == null || !display.getCanvas().isDisplayable() || display.isChangingDisplay()) {
			return;
		}
		bs = display.getCanvas().getBufferStrategy();
		if (bs == null) {
			display.getCanvas().createBufferStrategy(3); // you dont need more than 3 buffers
			return;
		}

		g = (Graphics2D) bs.getDrawGraphics();

		if (g == null) {
			return;
		}

		// clear screen
		g.clearRect(0, 0, width, height);
		GraphicsConfiguration gc = display.getFrame().getGraphicsConfiguration();
		AffineTransform tx = gc.getDefaultTransform();
		int targetWidth = 1920;
		int targetHeight = 1080;
		Rectangle2D screenBounds = Screen.getPrimary().getBounds();
		int screenWidth = (int) screenBounds.getWidth();
		int screenHeight = (int) screenBounds.getHeight();

		double scaleX = (double) screenWidth / targetWidth * tx.getScaleX();
		double scaleY = (double) screenHeight / targetHeight * tx.getScaleY();
//		double scale = Math.min(scaleX, scaleY);
		g.scale(scaleX, scaleY);
		if (State.getState() != null) {
			BufferedImage bufferedImage = null;
			Graphics2D imageGraphics = null;
			if (captureImage) {
				bufferedImage = new BufferedImage(10200, 5100, BufferedImage.TYPE_INT_ARGB);
				imageGraphics = bufferedImage.createGraphics();
			}

			State.getState().render(g);

			if (captureImage) {
				State.getState().render(imageGraphics);
				setSave.tick();
				if (setSave.isReady()) {
					// Step 4: Write the BufferedImage to a PNG file

					File outputFile = new File("output_image.png");
					try {
						ImageIO.write(bufferedImage, "PNG", outputFile);
						System.out.println("Image saved successfully!");
					} catch (IOException e) {
						System.err.println("Error saving image: " + e.getMessage());
					}
				}
			}

	//        Runtime runtime = Runtime.getRuntime();

//	        long totalMemory = runtime.totalMemory();
//	        long freeMemory = runtime.freeMemory();
//	        long usedMemory = totalMemory - freeMemory;
//	        long maxMemory = runtime.maxMemory();
//	        g.drawString("Total Memory: " + totalMemory / (1024 * 1024) + " MB", 0, 0);
//	        g.drawString("Free Memory: " + freeMemory / (1024 * 1024) + " MB", 0, 50);
//	        g.drawString("Used Memory: " + usedMemory / (1024 * 1024) + " MB", 0, 100);
//	        g.drawString("Max Memory: " + maxMemory / (1024 * 1024) + " MB", 0, 150);

		}

		// end drawing
		bs.show();
		g.dispose();
	}

	public int frames;

	@Override
	public void run() {
		init();
		final int TICKS_PER_SECOND = 60;
		final long NS_PER_TICK = 1_000_000_000L / TICKS_PER_SECOND;
		final long NS_PER_FRAME = 1_000_000_000L / getRefreshRate(); // render cap

		long lastTickTime = System.nanoTime();
		long lastFrameTime = System.nanoTime();

		long tickAccumulator = 0;

		while (running) {
			if(State.getState() instanceof LoadingState) {
				tick();
			}
			long now = System.nanoTime();

			long tickDelta = now - lastTickTime;
			lastTickTime = now;
			tickAccumulator += tickDelta;

			// ----- FIXED LOGIC STEP -----
			while (tickAccumulator >= NS_PER_TICK) {
				tick();
				tickAccumulator -= NS_PER_TICK;
			}

			// ----- CAPPED RENDER -----
			if (now - lastFrameTime >= NS_PER_FRAME) {
				render();
				lastFrameTime = now;
			} else {
				// Yield instead of sleep for better precision
				Thread.yield();
			}
		}

		try {
			stop();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private int getRefreshRate() {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int refreshRate = gd.getDisplayMode().getRefreshRate();
		return refreshRate > 0 ? refreshRate : 60; // Default to 60Hz if unable to retrieve
	}

	public KeyManager getKeyManager() {
		return keyManager;
	}

	public MouseManager getMouseManager() {
		return mouseManager;
	}

	public void setKeyManager(KeyManager keyManager) {
		// this.keyManager = keyManager;
		display.getFrame().addKeyListener(keyManager);
	}

	public void setMouseManager(MouseManager mouseManager) {
		// this.mouseManager = mouseManager;
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getFrame().addMouseWheelListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseWheelListener(mouseManager);
	}

	public void setMouseManager(GameMouseManager mouseManager) {
		// this.mouseManager = mouseManager;
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getFrame().addMouseWheelListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseWheelListener(mouseManager);
	}

//	public GameCamera getGameCamera() {
//		return gameCamera;
//	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public synchronized void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start(); // calls run method
	}

	public synchronized void stop() throws InterruptedException {
		if (!running)
			return;
		thread.join();
	}

	public void closeGame() {
		Sounds.shutdownThreadPool();
		System.exit(0);
	}

	public int getFPS() {
		return frames;
	}

	public Display getDisplay() {
		return display;
	}

	public Handler getHandler() {
		return handler;
	}

	public User getUser() {
		return user;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
