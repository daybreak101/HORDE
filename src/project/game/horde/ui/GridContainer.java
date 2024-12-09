package project.game.horde.ui;

import java.util.ArrayList;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.BasicStroke;
import java.awt.Font;

import project.game.horde.main.BlessingInventory;
import project.game.horde.main.Handler;
import project.game.horde.ui.GridElementImageAndText.BlessingDisplayInfo;
import project.game.horde.utils.Utils;

public class GridContainer {
	private Handler handler;
	private int x, y, width, height, columns, rows;
	private int tileWidth, tileHeight;
	private ArrayList<UIObject> uiElements;
	private UIObject hoveringUI;
	private boolean displaysInfo = false;

	public GridContainer(Handler handler, int x, int y, int width, int height, int columns, int rows,
			boolean displaysInfo) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.columns = columns;
		this.rows = rows;
		uiElements = new ArrayList<UIObject>();
		tileWidth = width / columns;
		tileHeight = height / rows;
		this.displaysInfo = displaysInfo;
	}

	public void tick() {
		for (UIObject ui : uiElements) {
			ui.tick();
			if (ui.isHovering()) {
				hoveringUI = ui;
			}
		}
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(3));
		// top line
		g2d.drawLine(x, y, x + width, y);
		// left line
		g2d.drawLine(x, y, x, y + height);
		// right line
		g2d.drawLine(x + width, y, x + width, y + height);
		// bottom line
		g2d.drawLine(x, y + height, x + width, y + height);

		// internal lines
		for (int j = 1; j <= rows; j++) {
			g.drawLine(x, y + j * tileHeight, x + width, y + j * tileHeight);
		}
		for (int i = 1; i <= columns; i++) {
			g.drawLine(x + i * tileWidth, y, x + i * tileWidth, y + height);
		}
		g2d.setStroke(new BasicStroke(1));
		for (UIObject ui : uiElements) {
			ui.render(g);
		}
		if (hoveringUI != null && displaysInfo) {
			Utils.drawLeftAlignedString(g2d, ((BlessingDisplayInfo) hoveringUI.getInfo()).blessing,
					new Rectangle(handler.getWidth() / 2, handler.getHeight() / 4, 0, 0),
					new Font(Font.DIALOG, Font.PLAIN, 45));
			//TODO: rarity info here
			String rarity = "";
			Color color = Color.WHITE;
			switch(((BlessingDisplayInfo)hoveringUI.getInfo()).rarity){
				case BlessingInventory.COMMON: // common
					rarity = "Common";
					color = Color.GREEN;
					break;
				case BlessingInventory.RARE: //rare
					rarity = "Rare";
					color = Color.CYAN;
					break;
				case BlessingInventory.EPIC: //legendary
					rarity = "Epic";
					color = new Color(205, 0, 255);
					break;
				case BlessingInventory.LEGENDARY: //epic
					rarity = "Legendary";
					color = Color.yellow;
					break;
					
			}
			g.setColor(color);
			g.setFont(new Font(Font.DIALOG, Font.PLAIN, 22));
			Utils.drawParagraph(g2d, rarity,
					handler.getWidth()/2, 
					handler.getHeight()/4 + 40, 
					handler.getWidth()/3);
			g.setColor(handler.getSettings().getHudColor());
			g.setFont(new Font(Font.DIALOG, Font.PLAIN, 18));
			Utils.drawParagraph(g2d,"  (Owned: " + ((BlessingDisplayInfo) hoveringUI.getInfo()).amount + ")",
					handler.getWidth()/2, 
					handler.getHeight()/4 + 70, 
					handler.getWidth()/3);
			g.setFont(new Font(Font.DIALOG, Font.ITALIC, 20));
			Utils.drawParagraph(g2d, ((BlessingDisplayInfo) hoveringUI.getInfo()).description,
					handler.getWidth()/2, 
					handler.getHeight()/4 + 150, 
					handler.getWidth()/3);
		}
	}

	public void addElement(UIObject ui) {
		int currentRow = uiElements.size() / columns;
		int currentColumn = uiElements.size() % columns;
		if (uiElements.size() < rows * columns) {
			ui.setWidth(tileWidth);
			ui.setX(x + currentColumn * tileWidth);
			ui.setY(y + currentRow * tileHeight);
			ui.setHeight(tileHeight);
			uiElements.add(ui);
		} else
			System.out.println("Grid already at max capacity");
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getTileWidth() {
		return tileWidth;
	}

	public void setTileWidth(int tileWidth) {
		this.tileWidth = tileWidth;
	}

	public int getTileHeight() {
		return tileHeight;
	}

	public void setTileHeight(int tileHeight) {
		this.tileHeight = tileHeight;
	}

	public ArrayList<UIObject> getUiElements() {
		return uiElements;
	}

	public void setUiElements(ArrayList<UIObject> uiElements) {
		this.uiElements = uiElements;
	}

	public UIObject getHoveringUI() {
		return hoveringUI;
	}

}
