package project.game.horde.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import project.game.horde.main.Handler;

public class UIImageButton extends UIObject {
	
	private BufferedImage[] images;
	private ClickListener clicker;

	public UIImageButton(Handler handler, float x, float y, int width, int height, BufferedImage[] images, ClickListener clicker) {
		super(handler, x, y, width, height);
		this.images = images;
		this.clicker = clicker;
	}

	@Override
	public void tick() {}

	@Override
	public void render(Graphics g) {
		if(hovering)
			g.drawImage(images[1], (int) x, (int) y, width, height, null);
		else
			g.drawImage(images[0], (int) x, (int) y, width, height, null);
		
	}

	@Override
	public void onClick(UIObject ui) {
		clicker.onClick(ui);
		
	}

	@Override
	protected Object getInfo() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
