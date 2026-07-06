package project.game.horde.sounds;

import java.net.URL;

import project.game.horde.entities.Entity;
import project.game.horde.main.Handler;
import project.game.horde.utils.Utils;

public class InteractSounds {
	public static URL purchase, cantAfford,
	fryerStatic, fryerUpgrading, fryerDone,
	vendingStatic, vendingActivation, vendingGrab,
	mysteryBoxMusic, mysteryBoxOpen, mysteryBoxClose,
	barrierBreak, barrierDamage, barrierRepair; 
    public static final String PURCHASE_ID = "purchase";
    public static final String CANTAFFORD_ID = "cantAfford";
    public static final String FRYER_UPGRADING = "fryerUpgrading", FRYER_DONE = "fryerDone", FRYER_STATIC = "fryerStatic";
    public static final String VENDING_STATIC = "vendingStatic", VENDING_ACTIVATION = "vendingActivation", VENDING_GRAB = "vendingGrab";
	public static final String MYSTERYBOX_MUSIC = "mysteryBoxMusic", MYSTERYBOX_OPEN = "mysteryBoxOpen", MYSTERYBOX_CLOSE = "mysteryBoxClose";
	public static final String BARRIER_BREAK = "barrierBreak", BARRIER_DAMAGE = "barrierDamage", BARRIER_REPAIR = "barrierRepair";
	
	public static void init(Handler handler) {
		purchase = Utils.class.getResource("/sounds/interacts/purchase.wav");
		cantAfford = Utils.class.getResource("/sounds/interacts/cantAfford.wav");

		Sounds.preloadClip(PURCHASE_ID, purchase, 2, 1);
		Sounds.preloadClip(CANTAFFORD_ID, cantAfford, 2, 1);

		fryerStatic = Utils.class.getResource("/sounds/deepFryer/fryer_static.wav");
		fryerUpgrading = Utils.class.getResource("/sounds/deepFryer/upgrade_process.wav");
		fryerDone = Utils.class.getResource("/sounds/deepFryer/upgrade_ready.wav");	
		Sounds.preloadClip(FRYER_STATIC, fryerStatic, 1, 1);
		Sounds.preloadClip(FRYER_UPGRADING, fryerUpgrading, 1, 1);
		Sounds.preloadClip(FRYER_DONE, fryerDone, 1, 1);
		
		vendingStatic = Utils.class.getResource("/sounds/perkMachine/vending_machine_static.wav");
		vendingActivation = Utils.class.getResource("/sounds/perkMachine/vending_machine_activation.wav");
		vendingGrab = Utils.class.getResource("/sounds/perkMachine/vending_machine_grab.wav");
		Sounds.preloadClip(VENDING_STATIC, vendingStatic, 1, 1);
		Sounds.preloadClip(VENDING_ACTIVATION, vendingActivation, 1, 1);
		Sounds.preloadClip(VENDING_GRAB, vendingGrab, 1, 1);
		 
		mysteryBoxMusic = Utils.class.getResource("/sounds/mysteryBox/mystery_box_music.wav");
		mysteryBoxClose = Utils.class.getResource("/sounds/mysteryBox/mystery_box_close.wav");
		mysteryBoxOpen = Utils.class.getResource("/sounds/mysteryBox/mystery_box_open.wav");
		Sounds.preloadClip(MYSTERYBOX_MUSIC, mysteryBoxMusic, 1, 1);
		Sounds.preloadClip(MYSTERYBOX_CLOSE, mysteryBoxClose, 1, 1);
		Sounds.preloadClip(MYSTERYBOX_OPEN, mysteryBoxOpen, 1, 1);
		
		barrierBreak = Utils.class.getResource("/sounds/barriers/barrier_break.wav");
		barrierDamage = Utils.class.getResource("/sounds/barriers/barrier_damage.wav");
		barrierRepair = Utils.class.getResource("/sounds/barriers/barrier_repair.wav");
		Sounds.preloadClip(BARRIER_BREAK, barrierBreak, 5, 1);
		Sounds.preloadClip(BARRIER_DAMAGE, barrierDamage, 10, 1);
		Sounds.preloadClip(BARRIER_REPAIR, barrierRepair, 5, 1);

	}
	
	public static float calculateVolumeBasedOffDistance(Entity a, Entity b) {
		float dist = Utils.getEuclideanDistance(a.getCenterX(), a.getCenterY(),
				b.getCenterX(), b.getCenterY());
		return ((float) (1.0f - (float) (dist / 800)));
	}
	
}
