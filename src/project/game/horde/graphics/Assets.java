package project.game.horde.graphics;

import java.awt.image.BufferedImage;

public class Assets {
	private static int upscaleFactor = 3;

	private static final String folder = "/textures/normal/";
	private static final int width = 100 * upscaleFactor, height = 100 * upscaleFactor;
	//static means it can be accessed from anywhere
	public static BufferedImage player[], 
	
								shadow, dirt, grass, stone, tree, 
								
								mysteryBox, ammoBox, toxen, perkvendor, 
								
								zombieBlood, lickerBlood, toxenBlood, stokerBlood,
								
								jugg, fasthand, doubletap, deadshot, phd, stam, vamp, mule,
								revive, bandolier, luna, stronghold,
								
								powerup, doublepoints, instakill, deathmachine, infiniteammo,
								
								crawler, frozenZombie, iceEnhancedZombie,
								
								aurora[];
	
	public static BufferedImage factoryMap;
	
	public static BufferedImage[] zombieAnim, zombieAttackAnim,
								  crawlerAnim, crawlerAttackAnim,
								  enhancedZombieAnim, 
								  btn_start, btn_quit, lickerAnim;
	public static BufferedImage[][] flamethrower_bullet, upgraded_flamethrower_bullet;
	public static BufferedImage[] gas_cloud;
	public static BufferedImage rpg_rocket;
	public static BufferedImage[] explosion, upgradedExplosion;

	public static void init() {
		SpriteSheet sheet2 = new SpriteSheet(ImageLoader.loadImage(folder + "Map.png"));
		factoryMap = sheet2.crop(0, 0, 3400 * upscaleFactor, 1700 * upscaleFactor);
		
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage(folder + "entities.png"));
	
		jugg = sheet.crop(0, 7 * height, width, height);
		fasthand = sheet.crop(width, 7*  height, width, height);
		doubletap = sheet.crop(2 * width, 7 * height, width, height);
		deadshot = sheet.crop(3 * width, 7 * height, width, height);
		phd = sheet.crop(4 * width, 7 * height, width, height);
		stam = sheet.crop(5 * width, 7 * height, width, height);
		vamp = sheet.crop(6 * width, 7 * height, width, height);
		mule = sheet.crop(7 * width, 7 * height, width, height);
		revive = sheet.crop(8 * width, 7 * height, width, height);
		bandolier = sheet.crop(9 * width, 7 * height, width, height);
		luna = sheet.crop(10 * width, 7 * height, width, height);
		stronghold = sheet.crop(11 * width, 7 * height, width, height);
	
		
		powerup = sheet.crop(0, 8 * height, width, height);
		doublepoints = sheet.crop(0, 8 * height, width, height);
		instakill = sheet.crop(width, 8 * height, width, height);
		deathmachine = sheet.crop(2 * width, 8 * height, width, height);
		infiniteammo = sheet.crop(3 * width, 8 * height, width, height);
		
		SpriteSheet zombieSheet = new SpriteSheet(ImageLoader.loadImage(folder + "zombieAnim.png"));
		zombieAnim = new BufferedImage[17];
		zombieAnim[0] = zombieSheet.crop(0, 0, width, height);
		zombieAnim[1] = zombieSheet.crop(width, 0, width, height);
		zombieAnim[2] = zombieSheet.crop(2 * width, 0, width, height);
		zombieAnim[3] = zombieSheet.crop(3 * width, 0, width, height);
		zombieAnim[4] = zombieSheet.crop(4 * width, 0, width, height);
		zombieAnim[5] = zombieSheet.crop(5 * width, 0, width, height);
		zombieAnim[6] = zombieSheet.crop(6 * width, 0, width, height);
		zombieAnim[7] = zombieSheet.crop(7 * width, 0, width, height);
		zombieAnim[8] = zombieSheet.crop(8 * width, 0, width, height);
		zombieAnim[16] = zombieSheet.crop(0, 0, width, height);
		zombieAnim[15] = zombieSheet.crop(width, 0, width, height);
		zombieAnim[14] = zombieSheet.crop(2 * width, 0, width, height);
		zombieAnim[13] = zombieSheet.crop(3 * width, 0, width, height);
		zombieAnim[12] = zombieSheet.crop(4 * width, 0, width, height);
		zombieAnim[11] = zombieSheet.crop(5 * width, 0, width, height);
		zombieAnim[10] = zombieSheet.crop(6 * width, 0, width, height);
		zombieAnim[9] = zombieSheet.crop(7 * width, 0, width, height);
		
		zombieAttackAnim = new BufferedImage[9];		
		zombieAttackAnim[0] = zombieSheet.crop(2 * width, 2 * height, width, height);
		zombieAttackAnim[1] = zombieSheet.crop(3 * width, 2 * height, width, height);
		zombieAttackAnim[2] = zombieSheet.crop(4 * width, 2 * height, width, height);
		zombieAttackAnim[3] = zombieSheet.crop(4 * width, 2 * height, width, height);
		zombieAttackAnim[4] = zombieSheet.crop(3 * width, 2 * height, width, height);
		zombieAttackAnim[5] = zombieSheet.crop(3 * width, 2 * height, width, height);
		zombieAttackAnim[6] = zombieSheet.crop(2 * width, 2 * height, width, height);
		zombieAttackAnim[7] = zombieSheet.crop(width, 2 * height, width, height);
		zombieAttackAnim[8] = zombieSheet.crop(0, 2 * height, width, height);
		
		enhancedZombieAnim = new BufferedImage[17];
		for(int i = 0; i < 9; i++) {
			enhancedZombieAnim[i] = zombieSheet.crop(i * width, height, width, height);
		}
		for(int i = 9; i < 17; i++) {
			enhancedZombieAnim[i] = enhancedZombieAnim[16 - i];
		}
//		enhancedZombieAnim[0] = zombieSheet.crop(0, height, width, height);
//		enhancedZombieAnim[1] = zombieSheet.crop(width, height, width, height);
//		enhancedZombieAnim[2] = zombieSheet.crop(2 * width, height, width, height);
//		enhancedZombieAnim[3] = zombieSheet.crop(3 * width, height, width, height);
//		enhancedZombieAnim[4] = zombieSheet.crop(4 * width, height, width, height);
//		enhancedZombieAnim[5] = zombieSheet.crop(5 * width, height, width, height);
//		enhancedZombieAnim[6] = zombieSheet.crop(6 * width, height, width, height);
//		enhancedZombieAnim[7] = zombieSheet.crop(7 * width, height, width, height);
//		enhancedZombieAnim[8] = zombieSheet.crop(8 * width, height, width, height);
//		enhancedZombieAnim[16] = zombieSheet.crop(0, height, width, height);
//		enhancedZombieAnim[15] = zombieSheet.crop(width, height, width, height);
//		enhancedZombieAnim[14] = zombieSheet.crop(2 * width, height, width, height);
//		enhancedZombieAnim[13] = zombieSheet.crop(3 * width, height, width, height);
//		enhancedZombieAnim[12] = zombieSheet.crop(4 * width, height, width, height);
//		enhancedZombieAnim[11] = zombieSheet.crop(5 * width, height, width, height);
//		enhancedZombieAnim[10] = zombieSheet.crop(6 * width, height, width, height);
//		enhancedZombieAnim[9] = zombieSheet.crop(7 * width, height, width, height);
		
		crawlerAnim = new BufferedImage[20];
		for(int i = 0; i < 11; i++) {
			crawlerAnim[i] = zombieSheet.crop(i * width, 4 * height, width, height);
		}
		for(int i = 11; i < 20; i++) {
			crawlerAnim[i] = crawlerAnim[20 - i];
		}
		
		crawlerAttackAnim = new BufferedImage[11];	
		for(int i = 0; i < 6; i++) {
			crawlerAttackAnim[i] = zombieSheet.crop(i * width, 5 * height, width, height);
		}
		for(int i = 6; i < 11; i++) {
			crawlerAttackAnim[i] = crawlerAttackAnim[10 - i];
		}


		flamethrower_bullet = new BufferedImage[15][20];
		for(int i = 0; i < 15; i++) {
			for(int j = 0; j < 13; j++)
				flamethrower_bullet[i][j] 
				= ImageLoader.loadImage(
						folder + "flamethrower_bullet/" +  (i + 5) +  "_" + (j * 2) + ".png");
		}
		upgraded_flamethrower_bullet = new BufferedImage[15][20];
		for(int i = 0; i < 15; i++) {
			for(int j = 0; j < 13; j++)
				upgraded_flamethrower_bullet[i][j] 
				= ImageLoader.loadImage(
						folder + "upgraded_flamethrower_bullet/" +  (i + 5) +  "_" + (j * 2) + ".png");
		}
		
		rpg_rocket = ImageLoader.loadImage(folder + "rpg_rocket.png");
		gas_cloud = new BufferedImage[25];
		for(int i = 0; i < 25; i++) {
				gas_cloud[i] 
				= ImageLoader.loadImage(
						folder + "gas_grenade/gascloud_" +  i + ".png");
		}
		
		SpriteSheet explosionSheet = new SpriteSheet(ImageLoader.loadImage(folder + "exp2_0.png"));
		explosion = new BufferedImage[16];
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++)
				explosion[4 * i + j] 
				= explosionSheet.crop(j * 64* upscaleFactor, i * 64* upscaleFactor, 64* upscaleFactor, 64* upscaleFactor);
		}
		
		SpriteSheet upgradedExplosionSheet = new SpriteSheet(ImageLoader.loadImage(folder + "exp2_0_upgraded.png"));
		upgradedExplosion = new BufferedImage[16];
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++)
				upgradedExplosion[4 * i + j] 
				= upgradedExplosionSheet.crop(j * 64 * upscaleFactor, i * 64 * upscaleFactor, 64 * upscaleFactor, 64 * upscaleFactor);
		}
		
		crawler = sheet.crop(3 * width, height, width, height);
		frozenZombie = sheet.crop(5 * width, height, width, height);
		iceEnhancedZombie = sheet.crop(4 * width, height, width, height);
		
		lickerAnim = new BufferedImage[2];
		lickerAnim[0] = sheet.crop(0, 2 * height, width, height);
		lickerAnim[1] = sheet.crop(width, 2 * height, width, height);
		
		toxen = sheet.crop(0, 3 * height, width, height);
		
		btn_start = new BufferedImage[2];
		btn_start[0] = sheet.crop(0, height * 6, width * 2, height);
		btn_start[1] = sheet.crop(0, height * 5, width * 2, height);
		
		btn_quit = new BufferedImage[2];
		btn_quit[0] = sheet.crop(width * 3, height * 6, width * 2, height);
		btn_quit[1] = sheet.crop(width * 3, height * 5, width * 2, height);
		
		player = new BufferedImage[5];
		player[0] = sheet.crop(0,  0, width, height); //healthy
		player[1] = sheet.crop(width,  0, width, height); //hurt
		player[2] = sheet.crop(width * 2,  0, width, height); //damaged
		player[3] = sheet.crop(width * 3,  0, width, height); //dead
		player[4] = sheet.crop(width * 5, 0, width, height); //frozen
		
		shadow = sheet.crop(width * 4, 0, width, height);
		
		grass = sheet.crop(0, height * 4, width, height);
		dirt = sheet.crop(width, 4 * height, width, height);
		stone = sheet.crop(2 * width, 4 * height, width, height);
		tree = sheet.crop(3 * width, 4 * height, width, height);
		
		mysteryBox = sheet.crop(4 * width, 4 * height, 2 * width, height);
		ammoBox = sheet.crop(6 * width, 4 * height, width, height);
		perkvendor = sheet.crop(2 * width, 5 * height, width, 2 * height);
		
		zombieBlood = sheet.crop(2 * width, height, width, height);
		lickerBlood = sheet.crop(2 * width, 2 * height, width, height);
		toxenBlood = sheet.crop(width, 3 * height, width, height);
		stokerBlood = null;
		
		aurora = new BufferedImage[14];
		for(int i = 0; i < 8; i++) {
			aurora[i] = ImageLoader.loadImage(folder + "aurora/Aurora-" + i + ".png");
		}
		for(int i = 1; i < 7; i++) {
			aurora[7 + i] = ImageLoader.loadImage(folder + "aurora/Aurora-" + (7 -  i) + ".png");
		}
	}
}
