package project.game.horde.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import project.game.horde.main.Handler;


public class GridElementImage extends UIObject {
	private BufferedImage image;
	private ClickListener clicker;
	private int imageBorder = 3;
	protected boolean isSelected = false;

	public GridElementImage(Handler handler, BufferedImage image) {
		super(handler);
		this.image = image;
	}
	
	public GridElementImage(Handler handler, BufferedImage[] images) {
		super(handler);
        int width = Math.max(images[0].getWidth(), images[1].getWidth());
        int height = Math.max(images[0].getHeight(), images[1].getHeight());

        BufferedImage compiled = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = compiled.createGraphics();
        g.drawImage(images[0], 0, 0, null);
        g.drawImage(images[1], 0, 0, null);
        g.dispose();
        image = compiled;
	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics g) {
		if (isSelected) {
			g.setColor(handler.getSettings().getHudColor().darker().darker().darker());
			g.fillRect((int) x + 2, (int) y + 2, width - 4, height - 4);
			g.setColor(handler.getSettings().getHudColor());
		}
		if(image != null)
		g.drawImage(image, bounds.x + imageBorder, bounds.y + imageBorder,
				bounds.width - imageBorder * 2, bounds.height - imageBorder * 2, null);

	}

	@Override
	public void onClick(UIObject ui) {
		clicker.onClick(ui);
		isSelected = true;
	}

	public void setY(float y) {
		this.y = y;
		bounds.y = (int) y;
	}

	public void setX(float x) {
		this.x = x;
		bounds.x = (int) x;
	}

	public void setWidth(int width) {
		this.width = width;
		bounds.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
		bounds.height = height;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public boolean isSelected() {
		return isSelected;
	}

	@Override
	protected Object getInfo() {
		// TODO Auto-generated method stub
		return null;
	}

}
