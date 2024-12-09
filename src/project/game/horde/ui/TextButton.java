package project.game.horde.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

import project.game.horde.main.Handler;
import project.game.horde.utils.Utils;

public class TextButton extends UIObject {

	private BufferedImage image;
	private String text;
	private ClickListener clicker;
	protected boolean isSelected, imageBefore, isOutlined;
	protected int fontSize, imageBorder, imageWidth, imageHeight;
	protected Rectangle imageBounds, textBounds;
	protected Font font;

	public TextButton(Handler handler, float x, float y, int width, int height, String text, ClickListener clicker) {
		super(handler, x, y, width, height);
		this.text = text;
		this.fontSize = 20;
		this.clicker = clicker;
	}

	public TextButton(Handler handler, float x, float y, int width, int height, String text, int fontSize,
			ClickListener clicker) {
		super(handler, x, y, width, height);
		this.text = text;
		this.fontSize = fontSize;
		this.clicker = clicker;
	}
	
	public TextButton(Handler handler, float x, float y, int width, int height, String text, Font font,
			ClickListener clicker) {
		super(handler, x, y, width, height);
		this.text = text;
		this.font = font;
		this.clicker = clicker;
	}
	
	int r;
	int gr;
	int b;
	int rMin, gMin, bMin;
	boolean increase = true;

	@Override
	public void tick() {
        if (isHovering()) {
            //System.out.println("TextButton is hovering: " + text);
            if (handler.getMouseManager().isLeftPressed()) {
               // System.out.println("Mouse left pressed on TextButton: " + text);
               // clicker.onClick(this);
            }
        }
		Color setColor = handler.getSettings().getHudColor();
		rMin = setColor.getRed();
		gMin = setColor.getGreen();
		bMin = setColor.getBlue();
		int incrementBy = 4;
		if (hovering) {
			if (increase) {
				if (r >= 255)
					r = 255;
				else
					r += incrementBy;
				if (gr >= 255)
					gr = 255;
				else
					gr += incrementBy;
				if (b >= 255)
					b = 255;
				else
					b += incrementBy;

				if (r >= 255 && gr >= 255 && b >= 255)
					increase = false;
			} else {
				if (r <= 0)
					r = 0;
				else if (r <= rMin)
					r = rMin;
				else
					r -= incrementBy;
				if (gr <= 0)
					gr = 0;
				else if (gr <= gMin)
					gr = gMin;
				else
					gr -= incrementBy;
				if (b <= 0)
					b = 0;
				else if (b <= bMin)
					b = bMin;
				else
					b -= incrementBy;

				if (r <= rMin && gr <= gMin && b <= bMin)
					increase = true;
				else if (r <= 0 && gr <= 0 && b <= 0)
					increase = true;
			}

		} else {
			r = rMin;
			gr = gMin;
			b = bMin;
		}

	}

	@Override
	public void render(Graphics g) {
		if (r <= 0)
			r = 0;
		if (r >= 255)
			r = 255;
		if (gr <= 0)
			gr = 0;
		if (gr >= 255)
			gr = 255;
		if (b <= 0)
			b = 0;
		if (b >= 255)
			b = 255;
		if (isSelected || isOutlined) {
			g.setColor(Color.black);
			g.fillRect((int) x, (int) y, width, height);

			Graphics2D g2 = (Graphics2D) g;
			float thickness = 2;
			Stroke oldStroke = g2.getStroke();
			g2.setStroke(new BasicStroke(thickness));
			g2.setColor(handler.getSettings().getHudColor());
			g2.drawRect((int) x, (int) y, width, height);
			g2.setStroke(oldStroke);
		}
		if (hovering) {
			g.setColor(new Color(r, gr, b));

		} else {
			g.setColor(handler.getSettings().getHudColor());
		}

		if (image == null) {
			if(font == null) {
				Utils.drawCenteredString(g, text, bounds, new Font(Font.DIALOG, Font.PLAIN, fontSize));
			}
			else {
				Utils.drawCenteredString(g, text, bounds, font);
			}
		} else {

			g.drawImage(image, imageBounds.x, imageBounds.y, imageBounds.width, imageBounds.height, null);
			if (imageBefore) {
				if(font == null) {
					Utils.drawLeftAlignedString(g, text, textBounds, new Font(Font.DIALOG, Font.PLAIN, fontSize));
				}
				else {
					Utils.drawLeftAlignedString(g, text, textBounds, font);
				}
			}
			else {
				if(font == null) {
					Utils.drawRightAlignedString(g, text, textBounds, new Font(Font.DIALOG, Font.PLAIN, fontSize));
				}
				else {
					Utils.drawRightAlignedString(g, text, textBounds, font);
				}
			}

			// g.setColor(new Color(255,255,255,200));
			// g.fillRect(textBounds.x, textBounds.y, textBounds.width, textBounds.height);
//			g.fillRect(imageBounds.x, imageBounds.y, imageBounds.width, imageBounds.height);
//			System.out.println("UI: " + x + ", " + y + ", " + width + ", " + height);
//			System.out.println("imageBounds: " + imageBounds.x + ", " + imageBounds.y + ", " + imageBounds.width + ", " + imageBounds.height);
//			System.out.println("textBounds: " + textBounds.x + ", " + textBounds.y + ", " + textBounds.width + ", " + textBounds.height);

			// g.fillRect((int) x,(int) y, width, height);
		}

	}

	@Override
	public void onClick(UIObject ui) {
		clicker.onClick(ui);
		isSelected = true;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public void setImage(BufferedImage image, boolean imageBefore, int width, int height, int imageBorder) {
		this.image = image;
		this.imageBefore = imageBefore;
		this.imageBorder = imageBorder;
		this.imageWidth = width;
		this.imageHeight = height;
		if (imageBefore) {
			imageBounds = new Rectangle(bounds.x + imageBorder, bounds.y + imageBorder, width - imageBorder * 2,
					height - imageBorder * 2);
			textBounds = new Rectangle(bounds.x + width, bounds.y, bounds.width - width, bounds.height);
		} else {
			imageBounds = new Rectangle(bounds.x + bounds.width - width + imageBorder, bounds.y + imageBorder,
					width - imageBorder * 2, height - imageBorder * 2);
			textBounds = new Rectangle(bounds.x, bounds.y, bounds.width - width, bounds.height);

		}

	}
	
	public void setX(float x) {
		this.x = x;
		bounds.x = (int) x;
		if(image == null) {
			return;
		}
		if(imageBefore) {
			imageBounds.x = bounds.x + imageBorder;
			textBounds.x = bounds.x + width;
		}
		else {
			imageBounds.x = bounds.x + bounds.width - width + imageBorder;
			textBounds.x = bounds.x;
		}
	}


	public boolean isOutlined() {
		return isOutlined;
	}

	public void setIsOutlined(boolean isOutlined) {
		this.isOutlined = isOutlined;
	}

	@Override
	protected Object getInfo() {
		// TODO Auto-generated method stub
		return null;
	}

}
