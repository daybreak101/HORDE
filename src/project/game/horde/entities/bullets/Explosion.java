package project.game.horde.entities.bullets;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Arc2D;

import project.game.horde.entities.blood.Blood;
import project.game.horde.graphics.Assets;
import project.game.horde.graphics.BWAssets;
import project.game.horde.main.Handler;

public class Explosion extends Blood {

	Color color;
	boolean isArc;
	float startAngle, arcAngle;
	boolean isUpgraded;

	public Explosion(Handler handler, float x, float y, int z, int width, int height, boolean isUpgraded) {
		super(handler, x, y, z, -1);
		this.timer = 20;
		this.width = width;
		this.height = height;
		this.color = Color.orange;
		isArc = false;
		this.isUpgraded = isUpgraded;
	}

//	public Explosion(Handler handler, float x, float y, int width, int height, Color color) {
//		super(handler, x, y, -1);
//		this.timer = 20;
//		this.width = width;
//		this.height = height;
//		this.color = color;
//		isArc = false;
//	}

	public Explosion(Handler handler, Arc2D.Float arc, int z) {
		super(handler, arc.x, arc.y, z, -1);
		this.timer = 20;
		this.width = (int) arc.width;
		this.height = (int) arc.height;
		this.color = Color.orange;
		arcAngle = (float) arc.getAngleExtent();
		startAngle = (float) arc.getAngleStart();
		isArc = true;
	}

	int i = 0;
	public void render(Graphics g) {
		if(i < 16) {
			if(!isUpgraded)
					g.drawImage(Assets.explosion[i], (int) (x - handler.getGameCamera().getxOffset()),
							(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
			else
				g.drawImage(Assets.upgradedExplosion[i], (int) (x - handler.getGameCamera().getxOffset()),
						(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		}

		i++;
		
//		g.setColor(color);
//		if (isArc) {
//			g.fillArc((int) (x - handler.getGameCamera().getxOffset()),
//					(int) (y - handler.getGameCamera().getyOffset()), width, height, (int) Math.toDegrees(arcAngle),
//					(int) Math.toDegrees(arcAngle));
//
//		} else {
//			g.fillOval((int) (x - handler.getGameCamera().getxOffset()),
//					(int) (y - handler.getGameCamera().getyOffset()), width, height);
//
//		}
	}
	
	public void renderBW(Graphics g) {
		if(i < 16) {
			if(!isUpgraded)
					g.drawImage(BWAssets.explosion[i], (int) (x - handler.getGameCamera().getxOffset()),
							(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
			else
				g.drawImage(BWAssets.upgradedExplosion[i], (int) (x - handler.getGameCamera().getxOffset()),
						(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		}

		i++;
//		g.setColor(Color.white);
//		if (isArc) {
//			g.fillArc((int) (x - handler.getGameCamera().getxOffset()),
//					(int) (y - handler.getGameCamera().getyOffset()), width, height, (int) Math.toDegrees(arcAngle),
//					(int) Math.toDegrees(arcAngle));
//
//		} else {
//			g.fillOval((int) (x - handler.getGameCamera().getxOffset()),
//					(int) (y - handler.getGameCamera().getyOffset()), width, height);
//
//		}
	}

}
