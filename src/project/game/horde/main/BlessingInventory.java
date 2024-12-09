package project.game.horde.main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Map.Entry;

import project.game.horde.graphics.MenuAssets;
import project.game.horde.utils.Utils;
import project.game.horde.utils.saved.SaveFileReader;
import project.game.horde.utils.saved.SaveFileUtils;
import project.game.horde.utils.saved.SaveFileWriter;

public class BlessingInventory {
	public Handler handler;
	public static HashMap<String, Integer> inventory = new HashMap<String, Integer>();
	
	public static final int COMMON = 0, RARE = 1, EPIC = 2, LEGENDARY = 3;
	
	public static final String
			SPAWN_DOUBLE_POINTS = "Double Time", 
			SPAWN_NUKE = "KABOOM",
			SPAWN_MAX_AMMO = "Full Supply",
			SPAWN_INFINITE_AMMO = "Infinite Supply",
			SPAWN_INSTAKILL = "No Mercy",
			GAIN_POINTS = "EZ Points", 
			SPAWN_HEALTH = "HP up", 
			SPAWN_MINIGUN = "Death Machine!",
			FORCE_CRAWLERS = "Crawl Space", // change to "Beg For Me"
			POINTS_MULTIPLY = "Extra Change", // change to "GREED"
			GUARANTEE_HEADSHOTS = "So No Head?",
			UPGRADE_WEAPON = "Graded Up",
			RANDOM_PERK = "Extra Sodium", // change to																				// "GLUTTONY"
			SPAWN_ALL_DROPS = "Reign Drops", // change to "Overwhelmed? Overpowered"
			ROUND_SKIP = "Round Robbin", // "SLOTH"
			RANDOM_POWERUP = "I'm Feeling Lucky",
			TELEPORT = "Anywhere But Here", 
			INVISIBILITY = "In Plain Sight",
			FREEZE_ALL_ZOMBIES = "Brain Freeze",
			GIVE_ALL_PERKS = "Carb-o-load";
	
	private ArrayList<String> equipped;
	private int limit = 5;

	public static BufferedImage getBlessingImage(String blessing) {
		switch(blessing) {
		case SPAWN_DOUBLE_POINTS:
			return MenuAssets.doubleTimeBlessing;
		case SPAWN_NUKE:
			return MenuAssets.kaboomBlessing;
		case SPAWN_MAX_AMMO:
			return MenuAssets.fullSupplyBlessing;
		case SPAWN_INFINITE_AMMO:
			return MenuAssets.infiniteSupplyBlessing;
		case SPAWN_INSTAKILL:
			return MenuAssets.noMercyBlessing;
		case GAIN_POINTS:
			return MenuAssets.ezPointsBlessing;
		case SPAWN_HEALTH:
			return MenuAssets.hpUpBlessing;
		case SPAWN_MINIGUN:
			return MenuAssets.deathMachineBlessing;
		case FORCE_CRAWLERS:
			return MenuAssets.crawlSpaceBlessing;
		case POINTS_MULTIPLY:
			return MenuAssets.extraChangeBlessing;
		case GUARANTEE_HEADSHOTS:
			return MenuAssets.soNoHeadBlessing;
		case UPGRADE_WEAPON:
			return MenuAssets.gradedUpBlessing;
		case RANDOM_PERK:	
			return MenuAssets.extraSodiumBlessing;																
		case SPAWN_ALL_DROPS:
			return MenuAssets.reignDropsBlessing;
		case ROUND_SKIP:
			return MenuAssets.roundRobbinBlessing;
		case RANDOM_POWERUP:
			return MenuAssets.imFeelingLuckyBlessing;
		case TELEPORT:
			return MenuAssets.anywhereButHereBlessing;
		case INVISIBILITY:
			return MenuAssets.inPlainSightBlessing;
		case FREEZE_ALL_ZOMBIES:
			return MenuAssets.brainFreezeBlessing;
		case GIVE_ALL_PERKS:
			return MenuAssets.carboloadBlessing;
		}
		return null;
	}
	
	public BlessingInventory(Handler handler) {
		this.handler = handler;
		inventory = new HashMap<String, Integer>();
		equipped = new ArrayList<String>(limit);
		String blessingData;
		String blessingDataPath = Handler.SAVE_FOLDER + File.separator + Handler.BLESSINGS_FILE;

		if (!SaveFileUtils.fileExists(Handler.SAVE_FOLDER, Handler.BLESSINGS_FILE)) {
			blessingData = "0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0";
			SaveFileWriter.writeToFile(Handler.SAVE_FOLDER, Handler.BLESSINGS_FILE, blessingData);
		} else {
			blessingData = SaveFileReader.readFromFile(Handler.SAVE_FOLDER, Handler.BLESSINGS_FILE);

		}
		
		inventory.put(SPAWN_DOUBLE_POINTS, 0);
		inventory.put(SPAWN_NUKE, 0);
		inventory.put(SPAWN_MAX_AMMO, 0);
		inventory.put(SPAWN_INFINITE_AMMO , 0);
		inventory.put(SPAWN_INSTAKILL, 0);
		inventory.put(GAIN_POINTS , 0);
		inventory.put(SPAWN_HEALTH, 0);
		inventory.put(SPAWN_MINIGUN, 0);
		inventory.put(FORCE_CRAWLERS, 0);
		inventory.put(POINTS_MULTIPLY, 0);
		inventory.put(GUARANTEE_HEADSHOTS, 0);
		inventory.put(UPGRADE_WEAPON, 0);
		inventory.put(RANDOM_PERK, 0);
		inventory.put(SPAWN_ALL_DROPS, 0);
		inventory.put(ROUND_SKIP, 0);
		inventory.put(RANDOM_POWERUP, 0);
		inventory.put(TELEPORT, 0);
		inventory.put(INVISIBILITY, 0);
		inventory.put(FREEZE_ALL_ZOMBIES, 0);
		inventory.put(GIVE_ALL_PERKS, 0);

		readBlessings(blessingData);
	}

	private void readBlessings(String file) {
		String[] tokens = file.split("[\\n\\s]+");
		int i = 0;
		for (Entry<String, Integer> entry : inventory.entrySet()) {
			inventory.replace(entry.getKey(), Utils.parseInt(tokens[i]));
			i++;
		}

	}

	public void writeToFile() {
		String saveFolderPath = System.getProperty("user.home") + File.separator + "Documents" + File.separator
				+ Handler.SAVE_FOLDER;
		String unlocksFilePath = saveFolderPath + File.separator + Handler.BLESSINGS_FILE;

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(unlocksFilePath))) {
			for (Entry<String, Integer> entry : inventory.entrySet()) {
				writer.write(Long.toString(entry.getValue()));
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean equipOrRemoveBlessing(String blessing) {
		//check if already equipped. if so, remove and return
		System.out.println("Equipped blessings:");
		for(String b: equipped) {
			System.out.print(b + ", ");
		}
		for(String b: equipped) {
			if(b.equals(blessing)) {
				equipped.remove(b);
				System.out.println("removed: " + b);
				return false;
			}
		}
		//if not equipped, equip then return
		if(equipped.size() >= 5) {
			return false;
		}
		equipped.add(blessing);
		System.out.println("added: " + blessing);
		return true;
	}
	
//	public void removeBlessing(String blessing) {
//		for(String b: equipped) {
//			if(b.equals(blessing)) {
//				equipped.remove(b);
//				return;
//			}
//		}
//	}

	public int getAmount(String blessing) {
		return inventory.get(blessing);
	}
	
	public void addBlessings(ArrayList<String> blessings) {
		for(String b: blessings) {
			addBlessing(b);
		}
	}
	
	public void addBlessing(String blessing) {
		int amount = inventory.get(blessing) + 1;
		inventory.replace(blessing, amount);
		writeToFile();
	}
	


	public boolean consumeBlessing(String blessing) {
		int amount = inventory.get(blessing);
		if (amount > 0) {
			inventory.replace(blessing, amount - 1);
			writeToFile();
			return true;
		}
		return false;
	}
	
	public ArrayList<String> getEquipped(){
		return equipped;
	}
}
