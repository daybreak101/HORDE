package project.game.horde.sounds;

import java.net.URL;

import project.game.horde.entities.Entity;
import project.game.horde.main.Handler;
import project.game.horde.utils.RandomUtil;
import project.game.horde.utils.Utils;

public class ZombieSounds {
	public static URL attack1, attack2, attack3, attack4, attack5,
					  death1, death2, death3, death4, death5, death6, death7,
					  growl1, growl2, growl3, growl4, growl5, growl6, growl7,
					  growl8, growl9, growl10, growl11, growl12, growl13, growl14,
					  growl15, growl16, growl17, growl18, impact, headshot;
	public static final String ZOMBIE_ATTACK1_ID = "zombieAttack1", ZOMBIE_ATTACK2_ID = "zombieAttack2",
							   ZOMBIE_ATTACK3_ID = "zombieAttack3", ZOMBIE_ATTACK4_ID = "zombieAttack4",
							   ZOMBIE_ATTACK5_ID = "zombieAttack5",
							   ZOMBIE_DEATH1_ID = "zombieDeath1", ZOMBIE_DEATH2_ID = "zombieDeath2",
							   ZOMBIE_DEATH3_ID = "zombieDeath3", ZOMBIE_DEATH4_ID = "zombieDeath4",
							   ZOMBIE_DEATH5_ID = "zombieDeath5", ZOMBIE_DEATH6_ID = "zombieDeath6",
							   ZOMBIE_DEATH7_ID = "zombieDeath7",
							   ZOMBIE_GROWL1_ID = "zombieGrowl1", ZOMBIE_GROWL2_ID = "zombieGrowl2",
							   ZOMBIE_GROWL3_ID = "zombieGrowl3", ZOMBIE_GROWL4_ID = "zombieGrowl4",
							   ZOMBIE_GROWL5_ID = "zombieGrowl5", ZOMBIE_GROWL6_ID = "zombieGrowl6",
							   ZOMBIE_GROWL7_ID = "zombieGrowl7", ZOMBIE_GROWL8_ID = "zombieGrowl8",
							   ZOMBIE_GROWL9_ID = "zombieGrowl9", ZOMBIE_GROWL10_ID = "zombieGrowl0",
							   ZOMBIE_GROWL11_ID = "zombieGrowl11", ZOMBIE_GROWL12_ID = "zombieGrowl2",
							   ZOMBIE_GROWL13_ID = "zombieGrowl13", ZOMBIE_GROWL14_ID = "zombieGrowl4",
							   ZOMBIE_GROWL15_ID = "zombieGrowl15", ZOMBIE_GROWL16_ID = "zombieGrowl6",
							   ZOMBIE_GROWL17_ID = "zombieGrowl17", ZOMBIE_GROWL18_ID = "zombieGrowl8",
							   ZOMBIE_IMPACT = "zombieImpact", ZOMBIE_HEADSHOT = "zombieHeadshot";
	
	public static void init(Handler handler) {
		attack1 = Utils.class.getResource("/sounds/zombie/attack1.wav");
		attack2= Utils.class.getResource("/sounds/zombie/attack2.wav");
		attack3 = Utils.class.getResource("/sounds/zombie/attack3.wav");
		attack4= Utils.class.getResource("/sounds/zombie/attack4.wav");
		attack5= Utils.class.getResource("/sounds/zombie/attack5.wav");
		death1= Utils.class.getResource("/sounds/zombie/death1.wav");
		death2= Utils.class.getResource("/sounds/zombie/death2.wav");
		death3= Utils.class.getResource("/sounds/zombie/death3.wav");
		death4= Utils.class.getResource("/sounds/zombie/death4.wav");
		death5 = Utils.class.getResource("/sounds/zombie/death5.wav");
		death6 = Utils.class.getResource("/sounds/zombie/death6.wav");
		death7= Utils.class.getResource("/sounds/zombie/death7.wav");
		growl1 = Utils.class.getResource("/sounds/zombie/growl1.wav");
		growl2= Utils.class.getResource("/sounds/zombie/growl2.wav");
		growl3= Utils.class.getResource("/sounds/zombie/growl3.wav");
		growl4 = Utils.class.getResource("/sounds/zombie/growl4.wav");
		growl5= Utils.class.getResource("/sounds/zombie/growl5.wav");
		growl6= Utils.class.getResource("/sounds/zombie/growl6.wav");
		growl7 = Utils.class.getResource("/sounds/zombie/growl7.wav");
		growl8= Utils.class.getResource("/sounds/zombie/growl8.wav");
		growl9= Utils.class.getResource("/sounds/zombie/growl9.wav");
		growl10 = Utils.class.getResource("/sounds/zombie/growl10.wav");
		growl11= Utils.class.getResource("/sounds/zombie/growl11.wav");
		growl12= Utils.class.getResource("/sounds/zombie/growl12.wav");
		growl13 = Utils.class.getResource("/sounds/zombie/growl13.wav");
		growl14= Utils.class.getResource("/sounds/zombie/growl14.wav");
		growl15= Utils.class.getResource("/sounds/zombie/growl15.wav");
		growl16 = Utils.class.getResource("/sounds/zombie/growl16.wav");
		growl17= Utils.class.getResource("/sounds/zombie/growl17.wav");
		growl18= Utils.class.getResource("/sounds/zombie/growl18.wav");
		impact = Utils.class.getResource("/sounds/zombie/impact.wav");
		headshot = Utils.class.getResource("/sounds/zombie/headshot.wav");
		
		Sounds.preloadClip(ZOMBIE_ATTACK1_ID, attack1, 2, 1);
		Sounds.preloadClip(ZOMBIE_ATTACK2_ID, attack2, 2, 1);
		Sounds.preloadClip(ZOMBIE_ATTACK3_ID, attack3, 2, 1);
		Sounds.preloadClip(ZOMBIE_ATTACK4_ID, attack4, 2, 1);
		Sounds.preloadClip(ZOMBIE_ATTACK5_ID, attack5, 2, 1);
		Sounds.preloadClip(ZOMBIE_DEATH1_ID, death1, 2, 1);
		Sounds.preloadClip(ZOMBIE_DEATH2_ID, death2, 2, 1);
		Sounds.preloadClip(ZOMBIE_DEATH3_ID, death3, 2, 1);
		Sounds.preloadClip(ZOMBIE_DEATH4_ID, death4, 2, 1);
		Sounds.preloadClip(ZOMBIE_DEATH5_ID, death5, 2, 1);
		Sounds.preloadClip(ZOMBIE_DEATH6_ID, death6, 2, 1);
		Sounds.preloadClip(ZOMBIE_DEATH7_ID, death7, 2, 1);
		Sounds.preloadClip(ZOMBIE_GROWL1_ID, growl1, 2, 1);
		Sounds.preloadClip(ZOMBIE_GROWL2_ID, growl2, 2, 1);
		Sounds.preloadClip(ZOMBIE_GROWL3_ID, growl3, 2, 1);
		Sounds.preloadClip(ZOMBIE_GROWL4_ID, growl4, 2, 1);
		Sounds.preloadClip(ZOMBIE_GROWL5_ID, growl5, 2, 1);
		Sounds.preloadClip(ZOMBIE_GROWL6_ID, growl6, 2, 1);
		Sounds.preloadClip(ZOMBIE_GROWL7_ID, growl7, 2, 1);
		Sounds.preloadClip(ZOMBIE_GROWL8_ID, growl8, 2, 1);
		Sounds.preloadClip(ZOMBIE_GROWL9_ID, growl9, 2, 1);
		Sounds.preloadClip(ZOMBIE_GROWL10_ID, growl10, 2, 1);
		Sounds.preloadClip(ZOMBIE_GROWL11_ID, growl11, 2, 1);
		Sounds.preloadClip(ZOMBIE_GROWL12_ID, growl12, 2, 1);
		Sounds.preloadClip(ZOMBIE_GROWL13_ID, growl13, 2, 1);
		Sounds.preloadClip(ZOMBIE_GROWL14_ID, growl14, 2, 1);
		Sounds.preloadClip(ZOMBIE_GROWL15_ID, growl15, 2, 1);
		Sounds.preloadClip(ZOMBIE_GROWL16_ID, growl16, 2, 1);
		Sounds.preloadClip(ZOMBIE_GROWL17_ID, growl17, 2, 1);
		Sounds.preloadClip(ZOMBIE_GROWL18_ID, growl18, 2, 1);
		Sounds.preloadClip(ZOMBIE_IMPACT, impact, 60, 1);
		Sounds.preloadClip(ZOMBIE_HEADSHOT, headshot, 60, 1);
	}
	
	public static float calculateVolumeBasedOffDistance(Entity a, Entity b) {
		float dist = Utils.getEuclideanDistance(a.getCenterX(), a.getCenterY(),
				b.getCenterX(), b.getCenterY());
		return ((float) (1.0f - (float) (dist / 1000)));
	}
	
	public static void playRandomAttack(float volume) {
		int rng = RandomUtil.nextInt(1, 6);
		switch(rng) {
		case 1:
			Sounds.playClip(ZOMBIE_ATTACK1_ID, 1, volume, false);
			break;
		case 2:
			Sounds.playClip(ZOMBIE_ATTACK2_ID, 1, volume, false);
			break;
		case 3:
			Sounds.playClip(ZOMBIE_ATTACK3_ID, 1, volume, false);
			break;
		case 4:
			Sounds.playClip(ZOMBIE_ATTACK4_ID, 1, volume, false);
			break;
		case 5:
			Sounds.playClip(ZOMBIE_ATTACK5_ID, 1, volume, false);
			break;
		default:
			Sounds.playClip(ZOMBIE_ATTACK1_ID, 1, volume, false);
			break;
		}
	}
	
	public static void playRandomDeath(float volume) {
		int rng = RandomUtil.nextInt(1, 8);
		switch(rng) {
		case 1:
			Sounds.playClip(ZOMBIE_DEATH1_ID, 1, volume, false);
			break;
		case 2:
			Sounds.playClip(ZOMBIE_DEATH2_ID, 1, volume, false);
			break;
		case 3:
			Sounds.playClip(ZOMBIE_DEATH3_ID, 1, volume, false);
			break;
		case 4:
			Sounds.playClip(ZOMBIE_DEATH4_ID, 1, volume, false);
			break;
		case 5:
			Sounds.playClip(ZOMBIE_DEATH5_ID, 1, volume, false);
			break;
		case 6:
			Sounds.playClip(ZOMBIE_DEATH6_ID, 1, volume, false);
			break;
		case 7:
			Sounds.playClip(ZOMBIE_DEATH7_ID, 1, volume, false);
			break;
		default:
			break;
		}
	}
	
	
	public static void playRandomGrowl(float volume) {
		int rng = RandomUtil.nextInt(1, 19);
		switch(rng) {
		case 1:
			Sounds.playClip(ZOMBIE_GROWL1_ID, 1, volume, false);
			break;
		case 2:
			Sounds.playClip(ZOMBIE_GROWL2_ID, 1, volume, false);
			break;
		case 3:
			Sounds.playClip(ZOMBIE_GROWL3_ID, 1, volume, false);
			break;
		case 4:
			Sounds.playClip(ZOMBIE_GROWL4_ID, 1, volume, false);
			break;
		case 5:
			Sounds.playClip(ZOMBIE_GROWL5_ID, 1, volume, false);
			break;
		case 6:
			Sounds.playClip(ZOMBIE_GROWL6_ID, 1, volume, false);
			break;
		case 7:
			Sounds.playClip(ZOMBIE_GROWL7_ID, 1, volume, false);
			break;
		case 8:
			Sounds.playClip(ZOMBIE_GROWL8_ID, 1, volume, false);
			break;
		case 9:
			Sounds.playClip(ZOMBIE_GROWL9_ID, 1, volume, false);
			break;
		case 10:
			Sounds.playClip(ZOMBIE_GROWL10_ID, 1, volume, false);
			break;
		case 11:
			Sounds.playClip(ZOMBIE_GROWL11_ID, 1, volume, false);
			break;
		case 12:
			Sounds.playClip(ZOMBIE_GROWL12_ID, 1, volume, false);
			break;
		case 13:
			Sounds.playClip(ZOMBIE_GROWL13_ID, 1, volume, false);
			break;
		case 14:
			Sounds.playClip(ZOMBIE_GROWL14_ID, 1, volume, false);
			break;
		case 15:
			Sounds.playClip(ZOMBIE_GROWL15_ID, 1, volume, false);
			break;
		case 16:
			Sounds.playClip(ZOMBIE_GROWL16_ID, 1, volume, false);
			break;
		case 17:
			Sounds.playClip(ZOMBIE_GROWL17_ID, 1, volume, false);
			break;
		case 18:
			Sounds.playClip(ZOMBIE_GROWL18_ID, 1, volume, false);
			break;
		default:
			break;
		}
	}
}