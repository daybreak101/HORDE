package project.game.horde.hud;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import project.game.horde.entities.creatures.Player;
import project.game.horde.entities.facade.PlayerMP;
import project.game.horde.entities.powerups.PowerUps;
import project.game.horde.entities.statics.InteractableStaticEntity;
import project.game.horde.graphics.Assets;
import project.game.horde.main.BlessingInventory;
import project.game.horde.main.Handler;
import project.game.horde.perks.Perk;
import project.game.horde.utils.Utils;
import project.game.horde.weapons.Gun;

public class GameplayElement extends HudElement {

    private int grenades, points, health, round, currentStamina, maxStamina, zombiesLeft, armor;
    private String gunText = "", interactText = "";
    private Perk[] perks;
    private Gun gun;// = handler.getWorld().getEntityManager().getPlayer().getInv().getGun();
    private Color hudColor;
    private float blessingMeter;
    private Player player;

    public GameplayElement(Handler handler, Player player) {
        super(0, 0, 0, 0, handler);
        this.player = player;
        maxStamina = 1;
        grenades = 0;
        round = 0;
        zombiesLeft = 0;
        blessingMeter = 0;
    }

    @Override
    public void tick() {
        //System.out.println("Ticking HUD");

        hudColor = handler.getSettings().getHudColor();

        grenades = player.getInv().getGrenades();
        health = player.getHealth();

        interactText = "";

        Ellipse2D.Float radius = new Ellipse2D.Float(player.getX() - 50, player.getY() - 50, player.getWidth() + 50,
                player.getHeight() + 50);
        InteractableStaticEntity closestInteract = null;
        float closestDist = 2000000;
        float eDist;

        // first get if player is downed and can be revived near you
        Ellipse2D.Float radius2 = new Ellipse2D.Float(player.getX() - 100, player.getY() - 100, 200, 200);
        boolean skipInteracts = false;
        String userRevive = "";

        for (PlayerMP others : handler.getWorld().getEntityManager().getOtherPlayers()) {
            if (radius2.intersects(others.getCollisionBounds(0, 0)) && others.getHealth() <= 0) {
                skipInteracts = true;
                userRevive = others.getUsername();
            }

        }

        if (!skipInteracts) {
            for (InteractableStaticEntity e : handler.getWorld().getEntityManager().getInteractables()) {
                eDist = Utils.getEuclideanDistance(player.getX(), player.getY(), e.getX(), e.getY());
                if (closestInteract == null) {
                    closestInteract = e;
                    closestDist = eDist;
                }
                if (eDist < closestDist) {
                    closestInteract = e;
                    closestDist = eDist;
                }
            }

            if (closestInteract != null) {
                if (radius.intersects(closestInteract.getTriggerRange())) {
                    interactText = closestInteract.getTriggerText();

                }
            }
        } else {
            interactText = "Hold F to revive " + userRevive;
        }

        perks = player.getInv().getPerks();
        points = player.getInv().getPoints();
        currentStamina = player.getPlayerSprint().getCurrentStamina();
        maxStamina = player.getPlayerSprint().getMaxStamina();
        round = handler.getRoundLogic().getCurrentRound();
        zombiesLeft = handler.getRoundLogic().getZombiesLeft();
        armor = player.getArmor();

        gun = player.getInv().getGun();
        if (gun == null) {
            gunText = ""; 
        }else if (gun.isDual()) {
            gunText = gun.getName() + "         " + gun.getCurrentClip() + " | " + gun.getCurrentAltClip() + " / " + gun.getCurrentReserve(); 
        }else {
            gunText = gun.getName() + "         " + gun.getCurrentClip() + " / " + gun.getCurrentReserve();
        }

        blessingMeter = player.getInv().getBlessings().getBlessingMeter();
    }

    public void renderGrenades(Graphics g) {
        // render grenades
        g.setColor(hudColor);

        for (int i = 0; i < 4; i++) {
            g.drawOval((int) (handler.getWidth() - 300 + i * 25), (int) (handler.getHeight() - 80), 20, 20);
        }
        for (int i = 0; i < grenades; i++) {
            g.fillOval((int) (handler.getWidth() - 300 + i * 25), (int) (handler.getHeight() - 80), 20, 20);
        }

        if (player.getInv().getSpecialGrenadeType() != -1) {
            for (int i = 0; i < player.getInv().getSpecialGrenadeAmt(); i++) {
                g.fillOval((int) (handler.getWidth() - 300 + i * 25), (int) (handler.getHeight() - 50), 20, 20);
            }
        }

    }

    public void renderGun(Graphics g) {
		//System.out.println("Rendering GUN" + gun);
        if (gun != null) {
            g.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(gunText);
            Rectangle rect = new Rectangle((int) handler.getWidth() - 300, (int) handler.getHeight() - 120, textWidth,
                    20);

            g.setColor(new Color(0, 0, 0, 30));
            g.fillRect(rect.x, rect.y, rect.width, rect.height);

            g.setColor(hudColor);
            Utils.drawLeftAlignedString(g, gunText, rect, new Font(Font.DIALOG, Font.PLAIN, 20));

            String alert = "";
            Color color = Color.white;
            if (gun.getIsReloading()) {
                alert = "Reloading...";
                color = Color.white;
            } else if (gun.getCurrentClip() == 0 && gun.getCurrentReserve() == 0) {
                alert = "No Ammo";
                color = Color.red;
            } else if (gun.getCurrentClip() < (float) (4 * gun.getClip() / 10) && gun.getCurrentReserve() == 0) {
                alert = "Low Ammo";
                color = Color.yellow;
            } else if (gun.getCurrentClip() < (float) (4 * gun.getClip() / 10)) {
                alert = "Reload";
                color = Color.yellow;
            }

            g.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
            fm = g.getFontMetrics();
            textWidth = fm.stringWidth(alert);
            rect = new Rectangle(handler.getWidth() / 2 - textWidth / 2, 3 * handler.getHeight() / 4, textWidth, 20);

            g.setColor(new Color(0, 0, 0, 30));
            g.fillRect(rect.x, rect.y, rect.width, rect.height);

            g.setColor(color);
            Utils.drawCenteredString(g, alert, rect, new Font(Font.DIALOG, Font.PLAIN, 20));

            if (gun.getIsReloading()) {
                g.setColor(Color.black);
                g.fillRect((int) handler.getWidth() - 300, (int) handler.getHeight() - 95, 100, 10);
                g.setColor(hudColor);
                g.fillRect((int) handler.getWidth() - 300, (int) handler.getHeight() - 95,
                        (int) (gun.getReloadProgress() * 100), 10);
            }
        }

    }

    public void renderHealthAndArmor(Graphics g) {
        // render health

        g.setColor(new Color(128, 0, 0));
        switch (player.getInv().getJugg()) {
            case 0 ->
                g.fillRect((int) 100, (int) handler.getHeight() - 100, 110, 50);
            case 1 ->
                g.fillRect((int) 100, (int) handler.getHeight() - 100, 125, 50);
            case 2 ->
                g.fillRect((int) 100, (int) handler.getHeight() - 100, 150, 50);
            case 3 ->
                g.fillRect((int) 100, (int) handler.getHeight() - 100, 200, 50);
            default ->
                g.fillRect((int) 100, (int) handler.getHeight() - 100, 100, 50);
        }
        g.setColor(hudColor);
        g.fillRect((int) 100, (int) handler.getHeight() - 100, health, 50);

        g.setColor(new Color(220, 0, 0));
        g.fillRect((int) 100 + health, (int) handler.getHeight() - 100, player.getTempHealth(), 50);

        // g.drawString(Integer.toString(health + player.getTempHealth()), 100, (int)
        // handler.getHeight() - 100);
        g.setColor(Color.BLUE);
        g.fillRect((int) 100, (int) handler.getHeight() - 60, armor, 10);

        g.setColor(new Color(128, 0, 0));
        g.fillOval(49, (int) handler.getHeight() - 105, 60, 60);
        g.setColor(Color.black);

        Graphics2D g2 = (Graphics2D) g;
        float thickness = 5;
        Stroke oldStroke = g2.getStroke();
        g2.setStroke(new BasicStroke(thickness));

        g.drawOval(49, (int) handler.getHeight() - 105, 60, 60);
        g2.setStroke(oldStroke);

        g.setFont(new Font(Font.DIALOG, Font.BOLD, 100));
        g.setColor(Color.white);
        g.drawString("+", 50, (int) handler.getHeight() - 40);

    }

    public void renderInteractionText(Graphics g) {
        // render interact
        g.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(interactText);
        Rectangle rect = new Rectangle((int) handler.getWidth() / 2 - textWidth / 2,
                (int) handler.getHeight() / 2 + 150, textWidth, 20);
        g.setColor(new Color(0, 0, 0, 30));
        g.fillRect(rect.x, rect.y, rect.width, rect.height);

        g.setColor(hudColor);
        Utils.drawCenteredString(g, interactText, rect, new Font(Font.DIALOG, Font.PLAIN, 20));

    }

    public void renderPerks(Graphics g) {
        // render perks
        if (perks != null) {
            for (int i = 0; i < perks.length; i++) {
                if (perks[i] != null) {
                    g.drawImage(perks[i].getIcon(), (int) (50 + (i * 60)), (int) handler.getHeight() - 200, 50, 50,
                            null);
                    if (perks[i].getLevel() != 0) {
                        g.drawString(Integer.toString(perks[i].getLevel()), (int) (90 + (i * 60)),
                                (int) handler.getHeight() - 150);
                    }
                }
            }
        }
    }

    public void renderPoints(Graphics g) {
        // render points
        g.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
        String string = Integer.toString(points);
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(string);
        Rectangle rect = new Rectangle((int) 40, (int) handler.getHeight() / 2 + 150, textWidth, 20);

        g.setColor(new Color(0, 0, 0, 30));
        g.fillRect(rect.x, rect.y, rect.width, rect.height);

        g.setColor(hudColor);
        Utils.drawLeftAlignedString(g, string, rect, new Font(Font.DIALOG, Font.PLAIN, 30));
    }

    ArrayList<PowerUps> powerups = new ArrayList<>(4);
    int oneX = handler.getWidth() / 2 - 25, twoX = handler.getWidth() / 2 - 25, threeX = handler.getWidth() / 2 - 25,
            fourX = handler.getWidth() / 2 - 25;

    public void renderPowerups(Graphics g) {

        // make sure to remove those that are inactive
        for (PowerUps e : handler.getWorld().getEntityManager().getPowerups()) {
            if (e.isPickedUp() && e.getIcon() != null && !powerups.contains(e)) {

                powerups.add(e);

                if (e.getName().equals("Death Machine")
                        && !e.getPlayerPicked().equals(handler.getCurrentPlayer().getUsername())) {
                    powerups.remove(e);
                }
            }
        }
        for (PowerUps e : powerups) {
            if (!handler.getWorld().getEntityManager().getPowerups().contains(e)) {
                powerups.remove(e);
                break;
            }
        }

        int powerupY = (int) handler.getHeight() - 100;
        int square = 50;

        // change powerups .size() to an array that only contains powerups picked up
        switch (powerups.size()) {
            case 1 -> {
                if (oneX < handler.getWidth() / 2 - 25) {
                    oneX++;
                }
            }
            case 2 -> {
                if (oneX > handler.getWidth() / 2 - 55) {
                    oneX--;
                } else if (oneX < handler.getWidth() / 2 - 55) {
                    oneX++;
                }
                if (twoX > handler.getWidth() / 2 + 5) {
                    twoX--;
                } else if (twoX < handler.getWidth() / 2 + 5) {
                    twoX++;
                }
            }
            case 3 -> {
                if (oneX > handler.getWidth() / 2 - 85) {
                    oneX--;
                } else if (oneX < handler.getWidth() / 2 - 85) {
                    oneX++;
                }
                if (twoX > handler.getWidth() / 2 - 25) {
                    twoX--;
                } else if (twoX < handler.getWidth() / 2 - 25) {
                    twoX++;
                }
                if (threeX > handler.getWidth() / 2 + 35) {
                    threeX--;
                } else if (threeX < handler.getWidth() / 2 + 35) {
                    threeX++;
                }
            }
            case 4 -> {
                if (oneX > handler.getWidth() / 2 - 115) {
                    oneX--;
                } else if (oneX < handler.getWidth() / 2 - 115) {
                    oneX++;
                }
                if (twoX > handler.getWidth() / 2 - 55) {
                    twoX--;
                } else if (twoX < handler.getWidth() / 2 - 55) {
                    twoX++;
                }
                if (threeX > handler.getWidth() / 2 + 5) {
                    threeX--;
                } else if (threeX < handler.getWidth() / 2 + 5) {
                    threeX++;
                }
                if (fourX > handler.getWidth() / 2 + 65) {
                    fourX--;
                } else if (fourX < handler.getWidth() / 2 + 65) {
                    fourX++;
                }
            }
            default -> {
            }
        }

        int[] xs = {oneX, twoX, threeX, fourX};
        int j = 0;
        int timeLeft = 0;
        for (int i = 0; i < powerups.size(); i++) {
            if (powerups.get(i).isPickedUp() && powerups.get(i).getIcon() != null) {
                // g.drawImage(powerups.get(i).getIcon(), xs[j], powerupY, square, square,
                // null);
                BufferedImage image = powerups.get(i).getIcon();
                if (j < 4) {
                    g.drawImage(image, xs[j], powerupY, square, square, null);
                    timeLeft = (1800 - powerups.get(i).getActiveCounter()) / 60;
                    // g.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
                    // g.drawString(Integer.toString(timeLeft), xs[j] + 10, powerupY + 75);

                    Rectangle rect = new Rectangle(xs[j], powerupY + 35, 50, 50);
                    g.setColor(hudColor);
                    Utils.drawCenteredString(g, Integer.toString(timeLeft + 1), rect,
                            new Font(Font.DIALOG, Font.PLAIN, 20));
                }

                j++;
            }
        }
    }

    public void renderRound(Graphics g) {
        // render round
        g.drawImage(Assets.zombieBlood, (int) 20, (int) 25, 100, 100, null);

        Rectangle rect = new Rectangle(20, 25, 100, 100);
        g.setColor(hudColor);
        Utils.drawCenteredString(g, Integer.toString(round), rect, new Font(Font.DIALOG, Font.PLAIN, 60));
    }

    public void renderStamina(Graphics g) {
        // render stamina
        g.setColor(Color.BLACK);
        g.fillRect((int) 100, (int) handler.getHeight() - 120, 100, 10);

        g.setColor(hudColor);
        if (maxStamina != 0) {
            g.fillRect((int) 100, (int) handler.getHeight() - 120, (int) ((currentStamina * 100 / maxStamina)), 10);
        }
    }

    public void renderZombiesLeft(Graphics g) {
        // render zombiesleft
        g.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
        String string = "Zombies Left: " + Integer.toString(zombiesLeft);
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(string);
        Rectangle rect = new Rectangle((int) handler.getWidth() - 200, 300, textWidth, 20);

        g.setColor(new Color(0, 0, 0, 30));
        g.fillRect(rect.x, rect.y, rect.width, rect.height);

        g.setColor(hudColor);
        Utils.drawLeftAlignedString(g, string, rect, new Font(Font.DIALOG, Font.PLAIN, 20));
    }

    public void renderBlessingMeter(Graphics g) {
        String blessing = player.getInv().getBlessings().getBlessing();
        if (blessing != "") {
            BufferedImage blessingImage = BlessingInventory.getBlessingImage(blessing);
            int blessingAmount = handler.getBlessings().getAmount(blessing);

            g.setColor(Color.black);
            g.fillRect(handler.getWidth() - 100, handler.getHeight() - 270, 20, 100);
            g.setColor(hudColor);
            g.fillRect(handler.getWidth() - 100, (int) (handler.getHeight() - 170 - (float) (100 * blessingMeter)), 20,
                    (int) (float) (100 * blessingMeter));
            // g.drawString(handler.getPlayer().getInv().getBlessings().getBlessing(),
            // handler.getWidth() - 177, handler.getHeight() - 300);

            Rectangle rect = new Rectangle(handler.getWidth() - 200, handler.getHeight() - 320, 200, 50);
            g.setColor(Color.black);
            // g.fillRect(rect.x, rect.y, rect.width, rect.height);
            g.setColor(hudColor);
//			Utils.drawCenteredString(g, blessing, rect,
//					new Font(Font.DIALOG, Font.PLAIN, 20));
            g.drawImage(blessingImage, handler.getWidth() - 115, handler.getHeight() - 320, 50, 50, null);
            g.drawString(Integer.toString(blessingAmount), handler.getWidth() - 85, handler.getHeight() - 310);
            rect = new Rectangle(handler.getWidth() - 200, handler.getHeight() - 300, 200, 50);

            // debug
            // Utils.drawCenteredString(g,
            // player.getInv().getBlessings().getCurrent() + " / " +
            // player.getInv().getBlessings().getMax(), rect,
            // new Font(Font.DIALOG, Font.PLAIN, 20));
        }
        if (blessingMeter == 1) {
            // g.setColor(Color.black);
            Graphics2D g2d = (Graphics2D) g;
            g.setColor(Color.black);
            g2d.fillRoundRect(handler.getWidth() - 105, (int) (handler.getHeight() - 170), 30, 30, 10, 10);
            g.setColor(hudColor);
            g2d.drawRoundRect(handler.getWidth() - 105, (int) (handler.getHeight() - 170), 30, 30, 10, 10);
            g.drawString("X", handler.getWidth() - 97, handler.getHeight() - 148);
        }

    }

    public void renderFPS(Graphics g) {
        g.drawString(Integer.toString(handler.getGame().getFPS()), handler.getWidth() - 100, 50);
        g.drawString(Integer.toString(handler.getWorld().getEntityManager().numOfEntities()), handler.getWidth() - 100,
                100);

    }

    @Override
    public void render(Graphics g) {
        //System.out.println("Rendering HUD");
        renderGrenades(g);
        renderGun(g);
        renderHealthAndArmor(g);
        renderInteractionText(g);
        renderPerks(g);
        renderPoints(g);
        renderPowerups(g);
        renderRound(g);
        renderStamina(g);
        if (handler.getSettings().isZombieCounter()) {
            renderZombiesLeft(g);
        }
        renderBlessingMeter(g);
        renderFPS(g);

//		Point2D center = new Point2D.Float(500, 500);
//		float radius = 250;
//		float[] dist = { 0.0f, 0.2f, 1.0f };
//		Color[] colors = { Color.RED, Color.WHITE, Color.BLUE };
//		RadialGradientPaint p = new RadialGradientPaint(center, radius, dist, colors);
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform old = g2d.getTransform();
        // old.
        // g2d.(p);
        g2d.setTransform(old);

    }

}
