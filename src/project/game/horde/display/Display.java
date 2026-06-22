package project.game.horde.display;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.tools.Tool;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import project.game.horde.graphics.ImageLoader;
import project.game.horde.main.Game;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;

public class Display {
	private Game game;
	private JFrame frame;
	private Canvas canvas;
	private JFXPanel fxPanel;

	private String title;
	private int width, height;
	public static final int STANDARD = 0, FULLSCREEN = 2, WINDOWEDFULLSCREEN = 1;
	private boolean isChangingDisplay = false;
	private double standardWidth, standardHeight;

	public Display(Game game, String title, int width, int height) {
		this.game = game;
		this.title = title;

		standardWidth = (width);
		standardHeight = (height);
		
		this.width = (int) standardWidth;
		this.height = (int) standardHeight;
		
		createDisplay(game.getHandler().getSettings().getDisplayType());

	}
	
	public void resetDisplay(int displayType) {
		isChangingDisplay = true;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.dispose();
		switch (displayType) {
		case STANDARD:
			//1000, 800
			width = (int) standardWidth;
			height = (int) standardHeight;
			game.setWidth(width);
			game.setHeight(height);
			frame.setUndecorated(false);
			frame.setSize(width, height);
			break;
		case FULLSCREEN:
			width = screenSize.width;
			height = screenSize.height;
			game.setWidth(screenSize.width);
			game.setHeight(screenSize.height);
			frame.setUndecorated(true);
			frame.setSize(width, height);
			break;
		case WINDOWEDFULLSCREEN:
			frame.setUndecorated(false);
			frame.setVisible(true);
			Insets insets = frame.getInsets();
			width = screenSize.width - insets.left - insets.right;
			height = screenSize.height - insets.top - insets.bottom;
			game.setWidth(width);
			game.setHeight(height);
			//frame.setSize(width, height);
		    frame.setSize(width + insets.left + insets.right, height + insets.top + insets.bottom);

			break;
		default:
			width = (int) standardWidth;
			height = (int) standardHeight;
			game.setWidth(width);
			game.setHeight(height);
			frame.setUndecorated(false);
			frame.setSize(width, height);
			break;
		}
		
		frame.setLocationRelativeTo(null);
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		canvas.setFocusable(false);
		canvas.revalidate();
		
	    frame.add(canvas);

		
		frame.setVisible(true);

	    // Ensure the canvas has a valid peer
	    if (!canvas.isDisplayable()) {
	        canvas.addNotify();
	    }
	    canvas.createBufferStrategy(3);
	    isChangingDisplay = false;
	}

	// use frame. for more suggestions to change properties of the window
	public void createDisplay(int displayType) {
		
		frame = new JFrame(title);

        GraphicsConfiguration gc = frame.getGraphicsConfiguration();
        AffineTransform tx = gc.getDefaultTransform();
        double scaleX = tx.getScaleX();
        double scaleY = tx.getScaleY();
        width = (int) (width * scaleX);
        height = (int) (height * scaleY);
        standardWidth = width;
        standardHeight = height;
        game.setWidth(width);
        game.setHeight(height);

		
        System.out.println("display width:" + width);
        System.out.println("display height: " + height);
        
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println("screen width:" + screenSize.width);
        System.out.println("screen height: " + screenSize.height);

		switch (displayType) {
		case STANDARD:
			break;
		case FULLSCREEN:
			width = screenSize.width;
			height = screenSize.height;
			game.setWidth(screenSize.width);
			game.setHeight(screenSize.height);
			frame.setUndecorated(true);
			break;
		case WINDOWEDFULLSCREEN:
			frame.setUndecorated(false);
			frame.setVisible(true);
			Insets insets = frame.getInsets();
			width = screenSize.width - insets.left - insets.right;
			height = screenSize.height - insets.top - insets.bottom;
			game.setWidth(width);
			game.setHeight(height);
		    frame.setSize(width + insets.left + insets.right, height + insets.top + insets.bottom);
			break;
		default:
			break;
		}
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setIconImage(ImageLoader.loadImage("/textures/normal/zombie.png"));
		
		canvas = new Canvas();

		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		canvas.setFocusable(false);
		canvas.setIgnoreRepaint(true);

		// Adding panel to the JFrame
		fxPanel = new JFXPanel();
		fxPanel.setSize(0, 0);

		frame.add(fxPanel);
		frame.add(canvas);
		frame.pack();
	}
	
	public void setIsChangingDisplay(boolean isChanging) {
		this.isChangingDisplay = isChanging;
	}
	
	public boolean isChangingDisplay() {
		return isChangingDisplay;
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public JFrame getFrame() {
		return frame;
	}

	public JFXPanel getFXPanel() {
		return fxPanel;
	}

	public void showFXPanel(Scene scene) {
		SwingUtilities.invokeLater(() -> {
			fxPanel.setScene(scene);
			fxPanel.setSize((int) scene.getWidth(), (int) scene.getHeight());
			fxPanel.setVisible(true);
			fxPanel.revalidate();
			fxPanel.repaint();
		});
	}

	public void hideFXPanel() {
		SwingUtilities.invokeLater(() -> {
			fxPanel.setScene(null);
			fxPanel.setSize(0, 0);
			fxPanel.setVisible(false);
			fxPanel.revalidate();
			fxPanel.repaint();
		});
	}

//	public void add(JPanel swingPanel) {
//		frame.add(swingPanel);
//		frame.add(canvas);
//
//	}
//
//	public void remove(JPanel swingPanel) {
//		frame.remove(swingPanel);
//
//	}

}
