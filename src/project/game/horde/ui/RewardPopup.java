package project.game.horde.ui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import project.game.horde.graphics.MenuAssets;
import project.game.horde.main.BlessingInventory;
import project.game.horde.main.Handler;
import project.game.horde.utils.Timer;
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

    BufferedImage random1, random2, random3;

    @Override
    public void tick() {
        if (isVisible) {
            spinTimer.tick();
            if (spinTimer.isReady()) {
                spinTimer.resetTimer();
                random1 = getRandomImage();
                random2 = getRandomImage();
                random3 = getRandomImage();
            }
            showRewardTimer.tick();
            if (showRewardTimer.isReady()) {
                showRewardTimer.resetTimer();
                if (!showReward1) {
                    showReward1 = true;
                } else if (!showReward2) {
                    showReward2 = true;
                } else if (!showReward3) {
                    showReward3 = true;
                }
            }
        }

    }

    public void reset() {
        showReward1 = false;
        showReward2 = false;
        showReward3 = false;
        showRewardTimer.resetTimer();
    }

    private ArrayList<BufferedImage> allImages = new ArrayList<>(Arrays.asList(
            MenuAssets.doubleTimeBlessing,
            MenuAssets.kaboomBlessing,
            MenuAssets.fullSupplyBlessing,
            MenuAssets.infiniteSupplyBlessing,
            MenuAssets.noMercyBlessing,
            MenuAssets.ezPointsBlessing,
            MenuAssets.hpUpBlessing,
            MenuAssets.deathMachineBlessing,
            MenuAssets.crawlSpaceBlessing,
            MenuAssets.extraChangeBlessing,
            MenuAssets.soNoHeadBlessing,
            MenuAssets.gradedUpBlessing,
            MenuAssets.extraSodiumBlessing,
            MenuAssets.reignDropsBlessing,
            MenuAssets.roundRobbinBlessing,
            MenuAssets.imFeelingLuckyBlessing,
            MenuAssets.anywhereButHereBlessing,
            MenuAssets.inPlainSightBlessing,
            MenuAssets.brainFreezeBlessing,
            MenuAssets.carboloadBlessing
    ));

    private BufferedImage getRandomImage() {
        Random rand = new Random();
        int rng = rand.nextInt(allImages.size());
        return allImages.get(rng);
    }

    boolean showReward1 = false;
    boolean showReward2 = false;
    boolean showReward3 = false;
    Timer showRewardTimer = new Timer(60);
    Timer spinTimer = new Timer(4);

    @Override
    public void render(Graphics g) {
        Utils.drawCenteredString(g, "REWARDS", new Rectangle(handler.getWidth() / 2, (int) (y + 50), 0, 50), font);
        g.setColor(handler.getSettings().getHudColor());
        int imageSize = handler.getWidth() / 10;
        int imageSpacing = handler.getWidth() / 10;
        int stringY = (int) y + height / 2 + imageSize / 2 + 20;
        switch (rewards.size()) {
            case 1 -> {
                g.drawImage(
                        showReward1 ? BlessingInventory.getBlessingImage(rewards.get(0)) : random1,
                        handler.getWidth() / 2 - imageSize / 2,
                        (int) (y + height / 2 - imageSize / 2),
                        imageSize,
                        imageSize,
                        null);
                if (showReward1) {
                    Utils.drawCenteredString(g, rewards.get(0),
                            new Rectangle(handler.getWidth() / 2,
                                    stringY,
                                    0,
                                    20),
                            new Font(Font.DIALOG, Font.PLAIN, 20));
                }
            }
            case 2 -> {

                g.drawImage(
                        showReward1 ? BlessingInventory.getBlessingImage(rewards.get(0)) : random1,
                        handler.getWidth() / 3 - imageSize / 2,
                        (int) (y + height / 2 - imageSize / 2),
                        imageSize,
                        imageSize,
                        null);
                if (showReward1) {
                    Utils.drawCenteredString(g, rewards.get(0),
                            new Rectangle(handler.getWidth() / 3,
                                    stringY,
                                    0,
                                    20),
                            new Font(Font.DIALOG, Font.PLAIN, 20));
                }
                g.drawImage(
                        showReward2 ? BlessingInventory.getBlessingImage(rewards.get(1)) : random2,
                        2 * handler.getWidth() / 3 - imageSize / 2,
                        (int) (y + height / 2 - imageSize / 2),
                        imageSize,
                        imageSize,
                        null);
                if (showReward2) {
                    Utils.drawCenteredString(g, rewards.get(1),
                            new Rectangle(2 * handler.getWidth() / 3,
                                    stringY,
                                    0,
                                    20),
                            new Font(Font.DIALOG, Font.PLAIN, 20));
                }
            }
            case 3 -> {
                g.drawImage(
                        showReward1 ? BlessingInventory.getBlessingImage(rewards.get(0)) : random1,
                        handler.getWidth() / 4 - imageSize / 2,
                        (int) (y + height / 2 - imageSize / 2),
                        imageSize,
                        imageSize,
                        null);
                if (showReward1) {
                    Utils.drawCenteredString(g, rewards.get(0),
                            new Rectangle(handler.getWidth() / 4,
                                    stringY,
                                    0,
                                    20),
                            new Font(Font.DIALOG, Font.PLAIN, 20));
                }
                g.drawImage(
                        showReward2 ? BlessingInventory.getBlessingImage(rewards.get(1)) : random2,
                        handler.getWidth() / 2 - imageSize / 2,
                        (int) (y + height / 2 - imageSize / 2),
                        imageSize,
                        imageSize,
                        null);
                if (showReward2) {
                    Utils.drawCenteredString(g, rewards.get(1),
                            new Rectangle(handler.getWidth() / 2,
                                    stringY,
                                    0,
                                    20),
                            new Font(Font.DIALOG, Font.PLAIN, 20));
                }
                g.drawImage(
                        showReward3 ? BlessingInventory.getBlessingImage(rewards.get(2)) : random3,
                        3 * handler.getWidth() / 4 - imageSize / 2,
                        (int) (y + height / 2 - imageSize / 2),
                        imageSize,
                        imageSize,
                        null);
                if (showReward3) {
                    Utils.drawCenteredString(g, rewards.get(2),
                            new Rectangle(3 * handler.getWidth() / 4,
                                    stringY,
                                    0,
                                    20),
                            new Font(Font.DIALOG, Font.PLAIN, 20));
                }
            }
            default -> {
            }
        }
    }

    public boolean shownAllRewards() {
        switch (rewards.size()) {
            case 1 -> {
                return showReward1;
            }
            case 2 -> {
                return showReward1 && showReward2;
            }
            case 3 -> {
                return showReward1 && showReward2 && showReward3;
            }
            default -> {
            }
        }
        return false;
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

    public ArrayList<String> getRewards() {
        return rewards;
    }

    @Override
    protected void postRender(Graphics g) {
        // TODO Auto-generated method stub
    }

}
