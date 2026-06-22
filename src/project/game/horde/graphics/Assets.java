package project.game.horde.graphics;

import java.awt.image.BufferedImage;

public class Assets {
	private static int upscaleFactor = 3;

	private static final String folder = "/textures/normal/";
	private static final int width = 100 * upscaleFactor, height = 100 * upscaleFactor;
	
	//friendly assets
	public static BufferedImage player[], aurora[];
	//unused textures
	public static BufferedImage	shadow, dirt, grass, stone, tree; 	
	//interactable assets
	public static BufferedImage	mysteryBox, ammoBox, toxen, perkvendor, fryer,
								barricade, damagedBarricade, brokenBarricade;	
	//blood assets
	public static BufferedImage zombieBlood, lickerBlood, toxenBlood, stokerBlood;	
	//perk assets
	public static BufferedImage	jugg, fasthand, doubletap, deadshot, phd, stam, vamp, mule,
								revive, bandolier, luna, stronghold;	
	//powerup assets
	public static BufferedImage	powerup, doublepoints, instakill, deathmachine, infiniteammo,
								nuke, healthUp, maxAmmo,
								blueStar, greenStar, pinkStar, purpleStar, redStar,
								whiteStar, yellowStar;
	//zombie assets
	public static BufferedImage crawler, frozenZombie, iceEnhancedZombie;
	public static BufferedImage[] zombieAnim, zombieAttackAnim,
								  crawlerAnim, crawlerAttackAnim,
								  enhancedZombieAnim, lickerAnim;; 
	//weapon assets
	public static BufferedImage aa12, ak47, awp, flamethrower, gasGrenades, glock17, grenadeLauncher,
								m4, p90, rpd, rpg, winchester1901, arisaka, bren, doubleBarrel, g18,
								m1Garand, m16, m1911, m60, python, thompson, type100, uzi,
								
								aa12_top, ak47_top, awp_top, flamethrower_top, glock17_top, grenadeLauncher_top,
								m4_top, p90_top, rpd_top, rpg_top, winchester1901_top, minigun_top,
								arisaka_top, bren_top, doubleBarrel_top, g18_top, m1Garand_top, m16_top, m1911_top,
								m60_top, python_top, thompson_top, type100_top, uzi_top;	
	public static BufferedImage[][] flamethrower_bullet, upgraded_flamethrower_bullet;
	public static BufferedImage[] gas_cloud;
	public static BufferedImage rpg_rocket;
	public static BufferedImage[] explosion, upgradedExplosion;	
	//map assets		
	public static BufferedImage firstFloorFarm, secondFloorFarm;
	public static BufferedImage seattle_needle, seattle_transport, seattle_central;
	public static BufferedImage iceland;


	public static void loadFarm() {
		firstFloorFarm = ImageLoader.loadImage(folder + "farmMap/firstFloorFarm.png");
		secondFloorFarm = ImageLoader.loadImage(folder + "farmMap/secondFloorFarm.png");
		init();
	}
	
	public static void loadSeattle() {
		seattle_needle = ImageLoader.loadImage(folder + "seattleMap/spaceNeedleArea.png");
		seattle_transport = ImageLoader.loadImage(folder + "seattleMap/transportArea.png");
		seattle_central = ImageLoader.loadImage(folder + "seattleMap/centralArea.png");
		init();
	}
	
	public static void loadIceland() {
		iceland = ImageLoader.loadImage(folder + "icelandMap/iceland.png");
		init();
	}
	
	public static void init() {

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
		nuke = sheet.crop(4 * width, 8 * height, width, height);
		healthUp = sheet.crop(5 * width, 8 * height, width, height);
		maxAmmo = sheet.crop(6 * width, 8 * height, width, height);
		
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
//		frozenZombie = sheet.crop(5 * width, height, width, height);
//		iceEnhancedZombie = sheet.crop(4 * width, height, width, height);
//		
//		lickerAnim = new BufferedImage[2];
//		lickerAnim[0] = sheet.crop(0, 2 * height, width, height);
//		lickerAnim[1] = sheet.crop(width, 2 * height, width, height);
//		
//		toxen = sheet.crop(0, 3 * height, width, height);
		
		player = new BufferedImage[5];
		player[0] = sheet.crop(0,  0, width, height); //healthy
		player[1] = sheet.crop(width,  0, width, height); //hurt
		player[2] = sheet.crop(width * 2,  0, width, height); //damaged
		player[3] = sheet.crop(width * 3,  0, width, height); //dead
		player[4] = sheet.crop(width * 5, 0, width, height); //frozen
		
		shadow = sheet.crop(width * 4, 0, width, height);
		
		mysteryBox = ImageLoader.loadImage(folder + "interactables/mystery_box.png");
		ammoBox = sheet.crop(6 * width, 4 * height, width, height);
		fryer = ImageLoader.loadImage(folder + "interactables/fryer.png");
		perkvendor = ImageLoader.loadImage(folder + "interactables/vending.png");
		barricade = ImageLoader.loadImage(folder + "interactables/barricade.png");
		damagedBarricade = ImageLoader.loadImage(folder + "interactables/damaged_barricade.png");
		brokenBarricade = ImageLoader.loadImage(folder + "interactables/broken_barricade.png");
		
		zombieBlood = sheet.crop(2 * width, height, width, height);
//		lickerBlood = sheet.crop(2 * width, 2 * height, width, height);
//		toxenBlood = sheet.crop(width, 3 * height, width, height);
//		stokerBlood = null;
		
		aurora = new BufferedImage[14];
		for(int i = 0; i < 8; i++) {
			aurora[i] = ImageLoader.loadImage(folder + "aurora/Aurora-" + i + ".png");
		}
		for(int i = 1; i < 7; i++) {
			aurora[7 + i] = ImageLoader.loadImage(folder + "aurora/Aurora-" + (7 -  i) + ".png");
		}
		
		aa12 = ImageLoader.loadImage(folder + "guns/aa12.png");
		ak47  = ImageLoader.loadImage(folder + "guns/ak-47.png");
		awp = ImageLoader.loadImage(folder + "guns/awp.png");
		flamethrower = ImageLoader.loadImage(folder + "guns/flamethrower.png");
		gasGrenades = ImageLoader.loadImage(folder + "guns/gas_grenades.png");
		glock17 = ImageLoader.loadImage(folder + "guns/glock17.png");
		grenadeLauncher = ImageLoader.loadImage(folder + "guns/grenade_launcher.png");
		m4 = ImageLoader.loadImage(folder + "guns/m4.png");
		p90 = ImageLoader.loadImage(folder + "guns/p90.png");
		rpd = ImageLoader.loadImage(folder + "guns/rpd.png");
		rpg = ImageLoader.loadImage(folder + "guns/rpg.png");
		winchester1901 = ImageLoader.loadImage(folder + "guns/winchester1901.png");
		arisaka = ImageLoader.loadImage(folder + "guns/arisaka.png");
		bren = ImageLoader.loadImage(folder + "guns/bren.png"); 
		doubleBarrel = ImageLoader.loadImage(folder + "guns/double_barrel.png"); 
		g18 = ImageLoader.loadImage(folder + "guns/g18.png");
		m1Garand = ImageLoader.loadImage(folder + "guns/m1_garand.png"); 
		m16 = ImageLoader.loadImage(folder + "guns/m16.png");
		m1911 = ImageLoader.loadImage(folder + "guns/m1911.png"); 
		m60 = ImageLoader.loadImage(folder + "guns/m60.png");
		python = ImageLoader.loadImage(folder + "guns/python.png"); 
		thompson = ImageLoader.loadImage(folder + "guns/thompson.png"); 
		type100 = ImageLoader.loadImage(folder + "guns/type100.png"); 
		uzi = ImageLoader.loadImage(folder + "guns/uzi.png");
		
		aa12_top = ImageLoader.loadImage(folder + "gunTop/aa12_top.png");
		ak47_top  = ImageLoader.loadImage(folder + "gunTop/ak-47_top.png");
		awp_top = ImageLoader.loadImage(folder + "gunTop/awp_top.png");
		flamethrower_top = ImageLoader.loadImage(folder + "gunTop/flamethrower_top.png");
		glock17_top = ImageLoader.loadImage(folder + "gunTop/glock17_top.png");
		grenadeLauncher_top = ImageLoader.loadImage(folder + "gunTop/grenade_launcher_top.png");
		m4_top = ImageLoader.loadImage(folder + "gunTop/m4_top.png");
		p90_top = ImageLoader.loadImage(folder + "gunTop/p90_top.png");
		rpd_top = ImageLoader.loadImage(folder + "gunTop/rpd_top.png");
		rpg_top = ImageLoader.loadImage(folder + "gunTop/rpg_top.png");
		winchester1901_top = ImageLoader.loadImage(folder + "gunTop/winchester1901_top.png");
		minigun_top = ImageLoader.loadImage(folder + "gunTop/minigun_top.png");
		
		arisaka_top = ImageLoader.loadImage(folder + "gunTop/arisaka_top.png");
		bren_top = ImageLoader.loadImage(folder + "gunTop/bren_top.png"); 
		doubleBarrel_top = ImageLoader.loadImage(folder + "gunTop/double_barrel_top.png");
		g18_top = ImageLoader.loadImage(folder + "gunTop/g18_top.png");
		m1Garand_top = ImageLoader.loadImage(folder + "gunTop/m1_garand_top.png"); 
		m16_top = ImageLoader.loadImage(folder + "gunTop/m16_top.png");	
		m1911_top = ImageLoader.loadImage(folder + "gunTop/m1911_top.png"); 
		m60_top = ImageLoader.loadImage(folder + "gunTop/m60_top.png");
		python_top = ImageLoader.loadImage(folder + "gunTop/python_top.png"); 
		thompson_top = ImageLoader.loadImage(folder + "gunTop/thompson_top.png"); 
		type100_top = ImageLoader.loadImage(folder + "gunTop/type100_top.png"); 
		uzi_top = ImageLoader.loadImage(folder + "gunTop/uzi_top.png");
		
		blueStar = ImageLoader.loadImage(folder + "glow/blue-star.png");
		greenStar = ImageLoader.loadImage(folder + "glow/green-star.png");
		pinkStar = ImageLoader.loadImage(folder + "glow/pink-star.png");
		purpleStar = ImageLoader.loadImage(folder + "glow/purple-star.png");
		redStar = ImageLoader.loadImage(folder + "glow/red-star.png");
		whiteStar = ImageLoader.loadImage(folder + "glow/white-star.png");
		yellowStar = ImageLoader.loadImage(folder + "glow/yellow-star.png");
		
	}
}
