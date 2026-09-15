package project.game.horde.display;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import static java.awt.Toolkit.getDefaultToolkit;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import project.game.horde.graphics.ImageLoader;
import project.game.horde.input.KeyManager;
import project.game.horde.main.Game;
import project.game.horde.main.Handler;

public class Display {

    private final Game game;
    private JFrame frame;
    private Canvas canvas;
    private JFXPanel fxPanel;

    private final String title;
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

    public void resetDisplay(int displayType, Handler handler) {
        isChangingDisplay = true;
        Dimension screenSize = getDefaultToolkit().getScreenSize();
        frame.dispose();
        frame = new JFrame(title);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setIconImage(ImageLoader.loadImage("/textures/normal/zombie.png"));

        switch (displayType) {
            case STANDARD -> {
                //1000, 800
                frame.setUndecorated(false);
                width = (int) standardWidth;
                height = (int) standardHeight;
                canvas.setPreferredSize(new Dimension(width, height));
                frame.add(canvas, BorderLayout.CENTER);
                frame.pack();
            }
            case FULLSCREEN -> {
                frame.setUndecorated(true);
                width = screenSize.width;
                height = screenSize.height;
                canvas.setPreferredSize(new Dimension(width, height));
                frame.add(canvas, BorderLayout.CENTER);
                frame.setSize(width, height);
            }
            case WINDOWEDFULLSCREEN -> {
                frame.setUndecorated(false);
                Insets insets = frame.getInsets();
                width = screenSize.width - insets.left - insets.right;
                height = screenSize.height - insets.top - insets.bottom;
                canvas.setPreferredSize(new Dimension(width, height));
                frame.add(canvas, BorderLayout.CENTER);
                frame.pack();
                frame.setSize(
                        screenSize.width,
                        screenSize.height
                );
            }
            default -> {
                frame.setUndecorated(false);
                width = (int) standardWidth;
                height = (int) standardHeight;
                canvas.setPreferredSize(new Dimension(width, height));
                frame.add(canvas, BorderLayout.CENTER);
                frame.pack();
            }
        }

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        canvas.revalidate();

        // Ensure the canvas has a valid peer
        if (!canvas.isDisplayable()) {
            canvas.addNotify();
        }
        canvas.createBufferStrategy(3);
        canvas.addKeyListener(new KeyManager(handler));
        isChangingDisplay = false;

    }

    // use frame. for more suggestions to change properties of the window
    private void createDisplay(int displayType) {

        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        frame.setIconImage(ImageLoader.loadImage("/textures/normal/zombie.png"));
        standardWidth = width;
        standardHeight = height;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        switch (displayType) {
            case STANDARD -> {
                frame.setUndecorated(false);
                width = (int) standardWidth;
                height = (int) standardHeight;
            }
            case FULLSCREEN -> {
                frame.setUndecorated(true);
                width = screenSize.width;
                height = screenSize.height;
            }
            case WINDOWEDFULLSCREEN -> {
                frame.setUndecorated(false);
                Insets insets = frame.getInsets();
                width = screenSize.width - insets.left - insets.right;
                height = screenSize.height - insets.top - insets.bottom;
            }
            default -> {
                frame.setUndecorated(false);
                width = (int) standardWidth;
                height = (int) standardHeight;
            }
        }

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setFocusable(false);
        canvas.setIgnoreRepaint(true);
        frame.add(canvas, BorderLayout.CENTER);

        // Adding panel to the JFrame
        fxPanel = new JFXPanel();
        fxPanel.setSize(0, 0);

        frame.pack();
        if (displayType == FULLSCREEN) {
            frame.setSize(screenSize.width, screenSize.height);
        }

        if (displayType == WINDOWEDFULLSCREEN) {
            frame.setSize(screenSize.width, screenSize.height);
        }

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
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
