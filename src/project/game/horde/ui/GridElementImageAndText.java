package project.game.horde.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.Color;
import project.game.horde.main.Handler;
import project.game.horde.utils.Utils;

public class GridElementImageAndText extends UIObject {
	private BufferedImage image;
	private String text;
	private Font font;
	private ClickListener clicker;
	private Rectangle imageBounds, textBounds;
	private int imageBorder = 3;
	protected boolean isSelected = false;
	protected int counter;
	protected BlessingDisplayInfo blessingInfo;

	public static class BlessingDisplayInfo {

		public String blessing;
		public int rarity;
		public String description;
		public int amount;
		
		public BlessingDisplayInfo(String blessing, int rarity, String description, int amount) {
			this.blessing = blessing;
			this.rarity = rarity;
			this.description = description;
			this.amount = amount;
		}
		

	}

	public GridElementImageAndText(Handler handler, String text, BufferedImage image, Font font) {
		super(handler);
		this.image = image;
		this.text = text;
		this.font = font;
		imageBounds = new Rectangle();
		textBounds = new Rectangle();
	}

	public GridElementImageAndText(Handler handler, int counter, BufferedImage image, Font font) {
		super(handler);
		this.image = image;
		this.counter = counter;
		this.font = font;
		imageBounds = new Rectangle();
		textBounds = new Rectangle();
		imageBorder = 10;
	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics g) {
		if (font == null) {
			font = new Font(Font.DIALOG, Font.PLAIN, 12);
		}
		if (isSelected) {
			g.setColor(handler.getSettings().getHudColor().darker().darker().darker());
			g.fillRect((int) x + 2, (int) y + 2, width - 4, height - 4);
			g.setColor(handler.getSettings().getHudColor());
		}
		if (text != null) {
			Utils.drawCenteredString(g, text, textBounds, font);
			g.drawLine((int) x, (int) (y + height / 5), (int) (x + width), (int) (y + height / 5));
			if (image != null)
				g.drawImage(image, imageBounds.x + imageBorder, imageBounds.y + height / 5 + imageBorder,
						imageBounds.width - imageBorder * 2, imageBounds.height - imageBorder * 2, null);
		} else {
			if (image != null)
				g.drawImage(image, imageBounds.x + imageBorder, imageBounds.y + imageBorder,
						imageBounds.width - imageBorder * 2, imageBounds.height - imageBorder * 2, null);
			int cornerX = (int) (x + width / 2);
			int cornerY = (int) (y + height / 5);
			g.setColor(Color.black);
			g.fillRect(textBounds.x, textBounds.y, textBounds.width, textBounds.height);
			g.setColor(handler.getSettings().getHudColor());
			Utils.drawCenteredString(g, Integer.toString(counter), textBounds, font);
			g.drawLine(cornerX, cornerY, (int) (x + width), (int) (y + height / 5));
			g.drawLine(cornerX, (int) y, cornerX, cornerY);
		}
	}
	
	public void setInfo(String blessing, int rarity, String description, int amount) {
		blessingInfo = new BlessingDisplayInfo(blessing, rarity, description, amount);
	}
	
	public BlessingDisplayInfo getInfo() {
		return blessingInfo;
	}

	@Override
	public void onClick(UIObject ui) {
		clicker.onClick(ui);
		isSelected = true;
	}

	public void setY(float y) {
		this.y = y;
		bounds.y = (int) y;
		if (text != null) {
			textBounds.y = (int) y;
			imageBounds.y = (int) (y + height / 5);
		} else {
			textBounds.y = (int) y + height / 6;
			imageBounds.y = (int) y;

		}

	}

	public void setX(float x) {
		this.x = x;
		bounds.x = (int) x;
		if (text != null) {
			textBounds.x = (int) x;
			imageBounds.x = (int) x;
		} else {
			imageBounds.x = (int) x;
			textBounds.x = (int) (x + width / 2);
		}
	}

	public void setWidth(int width) {
		this.width = width;
		bounds.width = width;
		if (text != null) {
			textBounds.width = width;
			imageBounds.width = width;
		} else {
			textBounds.width = width / 2;
			imageBounds.width = width;

		}
		setSquareImage();
	}

	public void setHeight(int height) {
		this.height = height;
		bounds.height = height;
		if (text != null) {
			textBounds.height = height / 5;
			imageBounds.height = 4 * height / 5;
		} else {
			textBounds.height = height / 5;
			imageBounds.height = height;

		}
		setSquareImage();
	}

	public void setSquareImage() {
		if (imageBounds.width == 0) {
			imageBounds.width = imageBounds.height;
		}
		if (imageBounds.height == 0) {
			imageBounds.height = imageBounds.width;
		}
		int size = Math.min(imageBounds.width, imageBounds.height);
		imageBounds.width = size;
		imageBounds.height = size;
		int newX = (int) (width - size) / 2;
		imageBounds.x = (int) (x + newX);
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public boolean isSelected() {
		return isSelected;
	}

}
