package project.game.horde.graphics;

import java.awt.image.BufferedImage;

public class MenuAssets {

    private static int upscaleFactor = 1;
    private static final String folder = "/textures/menu/";
    private static int width = 100 * upscaleFactor, height = 100 * upscaleFactor;

    public static BufferedImage coins[], jugg, fasthand, doubletap, deadshot, phd, stam, vamp, mule, revive, bandolier,
            luna, stronghold, farmhouseLoading,
            doubleTimeBlessing, kaboomBlessing, fullSupplyBlessing, infiniteSupplyBlessing,
            noMercyBlessing, ezPointsBlessing, hpUpBlessing, deathMachineBlessing,
            crawlSpaceBlessing, extraChangeBlessing, soNoHeadBlessing,
            gradedUpBlessing, extraSodiumBlessing, reignDropsBlessing,
            roundRobbinBlessing, imFeelingLuckyBlessing, anywhereButHereBlessing,
            inPlainSightBlessing, brainFreezeBlessing, carboloadBlessing;

    public static void init() {
        SpriteSheet coinSheet = new SpriteSheet(ImageLoader.loadImage(folder + "coins.png"));
        coins = new BufferedImage[4];
        for (int i = 0; i < 4; i++) {
            coins[i] = coinSheet.crop(0, 44 * i * 3, 40 * 3, 44 * 3);
        }

        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/normal/entities.png"));

        jugg = sheet.crop(0, 7 * height, width, height);
        fasthand = sheet.crop(width - 1, 7 * height, width, height);
        doubletap = sheet.crop(2 * width - 1, 7 * height, width, height);
        deadshot = sheet.crop(3 * width + 1, 7 * height, width, height);
        phd = sheet.crop(4 * width - 1, 7 * height, width, height);
        stam = sheet.crop(5 * width - 1, 7 * height, width, height);
        vamp = sheet.crop(6 * width + 1, 7 * height, width - 1, height);
        mule = sheet.crop(7 * width - 2, 7 * height, width, height);
        revive = sheet.crop(8 * width - 1, 7 * height, width, height);
        bandolier = sheet.crop(9 * width - 1, 7 * height, width, height);
        luna = sheet.crop(10 * width - 2, 7 * height, width - 1, height);
        stronghold = sheet.crop(11 * width, 7 * height, width + 2, height);

        farmhouseLoading = ImageLoader.loadImage(folder + "farmhouse.png");

        width = 300;
        height = 300;
        SpriteSheet blessings = new SpriteSheet(ImageLoader.loadImage(folder + "blessings.png"));
        doubleTimeBlessing = blessings.crop(0, 0, width, height);
        kaboomBlessing = blessings.crop(width, 0, width, height);
        fullSupplyBlessing = blessings.crop(2 * width, 0, width, height);
        infiniteSupplyBlessing = blessings.crop(3 * width, 0, width, height);
        noMercyBlessing = blessings.crop(0, height, width, height);
        ezPointsBlessing = blessings.crop(width, height, width, height);
        hpUpBlessing = blessings.crop(2 * width, height, width, height);
        deathMachineBlessing = blessings.crop(3 * width, height, width, height);
        crawlSpaceBlessing = blessings.crop(0, 2 * height, width, height);
        extraChangeBlessing = blessings.crop(width, 2 * height, width, height);
        soNoHeadBlessing = blessings.crop(2 * width, 2 * height, width, height);
        gradedUpBlessing = blessings.crop(3 * width, 2 * height, width, height);
        extraSodiumBlessing = blessings.crop(0, 3 * height, width, height);
        reignDropsBlessing = blessings.crop(width, 3 * height, width, height);
        roundRobbinBlessing = blessings.crop(2 * width, 3 * height, width, height);
        imFeelingLuckyBlessing = blessings.crop(3 * width, 3 * height, width, height);
        anywhereButHereBlessing = blessings.crop(0, 4 * height, width, height);
        inPlainSightBlessing = blessings.crop(width, 4 * height, width, height);
        brainFreezeBlessing = blessings.crop(2 * width, 4 * height, width, height);
        carboloadBlessing = blessings.crop(3 * width, 4 * height, width, height);

    }

}
