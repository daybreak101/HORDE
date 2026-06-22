package project.game.horde.entities.bullets;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import project.game.horde.entities.creatures.Player;
import project.game.horde.entities.statics.InteractableStaticEntity;
import project.game.horde.entities.statics.Wall;
import project.game.horde.main.Handler;

public class ToxenBullet extends Bullet {

	public ToxenBullet(Handler handler, float x, float y, int range, Player player) {
		super(handler, x, y, range, player);
		this.speed = 10;

		xMove = 0;
		yMove = 0;

		float moveToX = player.getCenterX() - x;
		float moveToY = player.getCenterY() - y;

		float angle = (float) Math.atan2(moveToY, moveToX);
		xMove = (float) (Math.cos(angle));
		yMove = (float) (Math.sin(angle));
	}

	int color = 0;

	@Override
	public void render(Graphics g) {
		if (color < 10)
			g.setColor(Color.yellow);
		else if (color < 20)
			g.setColor(new Color(144, 238, 144));
		else if (color < 30)
			g.setColor(new Color(144, 255, 200));
		else if (color < 40)
			g.setColor(Color.green);

		g.fillRect((int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()),
				width, height);
		color += 1;
		if (color >= 40) {
			color = 0;
		}
	}
	
	@Override
	public void renderBW(Graphics g) {
		if (color < 10)
			g.setColor(new Color(225,225,225));
		else if (color < 20)
			g.setColor(new Color(214,214,214));
		else if (color < 30)
			g.setColor(new Color(244,244,244));
		else if (color < 40)
			g.setColor(new Color(76,76,76));

		g.fillRect((int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()),
				width, height);
		color += 1;
		if (color >= 40) {
			color = 0;
		}
	}


        @Override
	public boolean checkForImpact() {
		cb = new Rectangle((int) (x + bounds.x - 1), (int) (y + bounds.y - 1), bounds.width + 1, bounds.height + 1);
		boolean impactedPlayer = false;
		Player p = handler.getCurrentPlayer();
		if (p.getCollisionBounds(0, 0).intersects(cb)) {
			p.takeDamage(5);
			handler.getWorld().getEntityManager().getEntities().remove(this);
			impactedPlayer = true;
		}

		if (impactedPlayer) {
			return true;
		}

		for (InteractableStaticEntity e : handler.getWorld().getEntityManager().getInteractables()) {
			if (e.getCollisionBounds(0, 0).intersects(cb)) {
				handler.getWorld().getEntityManager().getEntities().remove(this);
				return true;
			}
		}
		for (Wall e : handler.getWorld().getEntityManager().getWalls()) {
			if ( e.getCollisionBounds(0, 0).intersects(cb)) {
				handler.getWorld().getEntityManager().getEntities().remove(this);
				return true;
			}
		}
		return false;
	}

}
