package project.game.horde.main;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Color;

import javax.swing.JOptionPane;

import project.game.horde.display.Display;
import project.game.horde.graphics.Assets;
import project.game.horde.graphics.BWAssets;
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

	public Game(String title, int width, int height) {
		this.width = width;
		this.height = height;
		this.title = title;
		ColorIndex.init();
		handler = new Handler(this);
		String username = JOptionPane.showInputDialog(this, "Please enter a username");
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

		// add settings for these
//        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//        g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
//        g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
//        g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);

		// clear screen
		g.clearRect(0, 0, width, height);
		// g.setColor(Color.BLACK);
		// g.drawRect(0,0,width, height);
		// draw here
	    GraphicsConfiguration gc = display.getFrame().getGraphicsConfiguration();
	    AffineTransform tx = gc.getDefaultTransform();
	 //   double scaleX = tx.getScaleX();
	  //  double scaleY = tx.getScaleY();
		int targetWidth = 1920;
		int targetHeight = 1080;
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;
	    
		double scaleX = (double) screenWidth / targetWidth * tx.getScaleX();
		double scaleY = (double) screenHeight / targetHeight * tx.getScaleY();
//		double scale = Math.min(scaleX, scaleY);
		g.scale(scaleX, scaleY);
		if (State.getState() != null) {
			State.getState().render(g);
//	        Runtime runtime = Runtime.getRuntime();
//
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
		
		int refreshRate = getRefreshRate();
		double timePerFrame = 1000000000 / refreshRate;

		int fps = 60;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;
		int frameCount = 0;
		
		while (running) {
			now = System.nanoTime();

			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;
			
			

			if(State.getState() instanceof LoadingState) {
				tick();
			}
			render();
			frameCount++;
			if (delta >= 1) {
				tick();	
				ticks++;
				delta = delta - (int) delta;
			}

			if (timer >= 1000000000) {
				//frames = ticks;
				frames = frameCount;
				frameCount = 0;
				ticks = 0;
				timer = 0;
			}
			
            long timeToWait = System.nanoTime() - now;
            if (timeToWait < timePerFrame) {
                try {
                    Thread.sleep((long) ((timePerFrame - timeToWait) / 1000000));  // Sleep to maintain the refresh rate
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

		}

		try {
			stop();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
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
