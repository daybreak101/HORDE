package project.game.horde.ui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import project.game.horde.main.BlessingInventory;
import project.game.horde.main.Handler;
import project.game.horde.utils.Utils;

public class RewardPopup extends UIObject {
	private ArrayList<BufferedImage> images;
	private ArrayList<String> rewards;
	private ClickListener clicker;
	protected int fontSize, imageBorder, imageWidth, imageHeight;
	protected Font font;

	public RewardPopup(Handler handler, ArrayList<String> rewards, Font font, ClickListener clicker) {
		super(handler, 0, 2 * handler.getHeight() / 10, handler.getWidth(), 6 * handler.getHeight() / 10);
		this.clicker = clicker;
		this.rewards = rewards;
		this.font = font;
	}

	@Override
	public void tick() {
	}

	@Override
	public void render(Graphics g) {
		Utils.drawCenteredString(g, "REWARDS", new Rectangle(handler.getWidth()/2, (int) (y + 50), 0, 50), font);
		g.setColor(handler.getSettings().getHudColor());
		int imageSize = handler.getWidth()/10;
		int imageSpacing = handler.getWidth()/10;
		switch(rewards.size()) {
		case 1:
			g.drawImage(
					BlessingInventory.getBlessingImage(rewards.get(0)),
					handler.getWidth()/2 - imageSize/2,
					(int) (y + height/2 - imageSize/2),
					imageSize, 
					imageSize, 
					null);
			Utils.drawCenteredString(g, rewards.get(0), 
					new Rectangle(handler.getWidth()/2,
									(int) (y + height/2 + imageSize),
									0,
									20), 
					font);
			break;
		case 2:
			g.drawImage(
					BlessingInventory.getBlessingImage(rewards.get(0)),
					handler.getWidth()/3 - imageSize/2,
					(int) (y + height/2 - imageSize/2),
					imageSize, 
					imageSize, 
					null);
			Utils.drawCenteredString(g, rewards.get(0), 
					new Rectangle(handler.getWidth()/3,
									(int) (y + height/2 + imageSize),
									0,
									20), 
					font);
			g.drawImage(
					BlessingInventory.getBlessingImage(rewards.get(1)),
					2 * handler.getWidth()/3 - imageSize/2,
					(int) (y + height/2 - imageSize/2),
					imageSize, 
					imageSize, 
					null);
			Utils.drawCenteredString(g, rewards.get(1), 
					new Rectangle(2 *handler.getWidth()/3,
									(int) (y + height/2 + imageSize),
									0,
									20), 
					font);
			break;
		case 3:
			g.drawImage(
					BlessingInventory.getBlessingImage(rewards.get(0)),
					handler.getWidth()/4 - imageSize/2,
					(int) (y + height/2 - imageSize/2),
					imageSize, 
					imageSize, 
					null);
			Utils.drawCenteredString(g, rewards.get(0), 
					new Rectangle(handler.getWidth()/4,
									(int) (y + height/2 + imageSize),
									0,
									20), 
					font);
			g.drawImage(
					BlessingInventory.getBlessingImage(rewards.get(1)),
					handler.getWidth()/2 - imageSize/2,
					(int) (y + height/2 - imageSize/2),
					imageSize, 
					imageSize, 
					null);
			Utils.drawCenteredString(g, rewards.get(1), 
					new Rectangle(handler.getWidth()/2,
									(int) (y + height/2 + imageSize),
									0,
									20), 
					font);
			g.drawImage(
					BlessingInventory.getBlessingImage(rewards.get(2)),
					3 * handler.getWidth()/4 - imageSize/2,
					(int) (y + height/2 - imageSize/2),
					imageSize, 
					imageSize, 
					null);
			Utils.drawCenteredString(g, rewards.get(2), 
					new Rectangle(3 * handler.getWidth()/4,
									(int) (y + height/2 + imageSize),
									0,
									20), 
					font);
			break;
		default:
			break;
		}
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
	
	public void clearRewards() {
		rewards.clear();
	}
	
	public void addRewards(ArrayList<String> blessings) {
		rewards.addAll(blessings);
	}
	
	public ArrayList<String> getRewards(){
		return rewards;
	}

}
