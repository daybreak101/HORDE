package project.game.horde.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import project.game.horde.utils.Utils;
import project.game.horde.utils.saved.SaveFileReader;
import project.game.horde.utils.saved.SaveFileUtils;
import project.game.horde.utils.saved.SaveFileWriter;

public class GlobalStats {

	private Handler handler;

	// global stats
	private long globalKills, globalHeadshots, globalDowns, totalGames, perksAte, perkSpins, boxSpins, boxPulls,
			gunsUpgraded, trapPulls;
	private double averageRound;

	public GlobalStats(Handler handler) {
		this.handler = handler;
		String statsData;
		String statsFilePath = Handler.SAVE_FOLDER + File.separator + Handler.OVERALL_STATS_FILE;

		if (!SaveFileUtils.fileExists(Handler.SAVE_FOLDER, Handler.OVERALL_STATS_FILE)) {
			// Create a new save file with default data
			statsData = "0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0";
			SaveFileWriter.writeToFile(Handler.SAVE_FOLDER, Handler.OVERALL_STATS_FILE, statsData);
			//System.out.println("Stats file created with default data.");
		} else {
			// Load existing save file
			statsData = SaveFileReader.readFromFile(Handler.SAVE_FOLDER, Handler.OVERALL_STATS_FILE);
			//System.out.println("Loaded Stats Data:");
			//System.out.println(statsData);
		}

		readGlobalStats(statsData);

	}

	public void readGlobalStats(String file) {
		// String file = Utils.loadFileAsString("/info/globalStats.txt");
		/// String[] tokens = file.split("\\s+");
		String[] tokens = file.split("[\\n\\s]+");

		if (tokens.length == 0) {
			globalKills = 0;
			globalHeadshots = 0;
			globalDowns = 0;
			totalGames = 0;
			perksAte = 0;
			perkSpins = 0;
			boxPulls = 0;
			boxSpins = 0;
			gunsUpgraded = 0;
			trapPulls = 0;
			averageRound = 0;
			return;
		}
		globalKills = Utils.parseInt(tokens[0]);
		globalHeadshots = Utils.parseInt(tokens[1]);
		globalDowns = Utils.parseInt(tokens[2]);
		totalGames = Utils.parseInt(tokens[3]);
		perksAte = Utils.parseInt(tokens[4]);
		perkSpins = Utils.parseInt(tokens[5]);
		boxPulls = Utils.parseInt(tokens[6]);
		boxSpins = Utils.parseInt(tokens[7]);
		gunsUpgraded = Utils.parseInt(tokens[8]);
		trapPulls = Utils.parseInt(tokens[9]);
		averageRound = Utils.parseDouble(tokens[10]);
	}

	// make new file global stats
	public void writeToFile() {
		String saveFolderPath = System.getProperty("user.home") + File.separator + "Documents" + File.separator
				+ Handler.SAVE_FOLDER;
		String statsFilePath = saveFolderPath + File.separator + Handler.OVERALL_STATS_FILE;

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(statsFilePath))) {
			writer.write(Long.toString(globalKills));
			writer.newLine();
			writer.write(Long.toString(globalHeadshots));
			writer.newLine();
			writer.write(Long.toString(globalDowns));
			writer.newLine();
			writer.write(Long.toString(totalGames));
			writer.newLine();
			writer.write(Long.toString(perksAte));
			writer.newLine();
			writer.write(Long.toString(perkSpins));
			writer.newLine();
			writer.write(Long.toString(boxPulls));
			writer.newLine();
			writer.write(Long.toString(boxSpins));
			writer.newLine();
			writer.write(Long.toString(gunsUpgraded));
			writer.newLine();
			writer.write(Long.toString(trapPulls));
			writer.newLine();
			writer.write(Double.toString(averageRound));

			writer.close();
		} catch (IOException e) {
		}
	}

	public void gainKill() {
		globalKills++;
	}

	public void gainDown() {
		globalDowns++;
	}

	public void addGame() {
		totalGames++;
	}

	public long getGlobalKills() {
		return globalKills;
	}

	public long getGlobalDowns() {
		return globalDowns;
	}

	public long getTotalGames() {
		return totalGames;
	}

	public long getPerksAte() {
		return perksAte;
	}

	public void addPerk() {
		this.perksAte++;
	}

	public long getPerkSpins() {
		return perkSpins;
	}

	public void addPerkSpin() {
		perkSpins++;
	}

	public long getBoxSpins() {
		return boxSpins;
	}

	public void addBoxSpin() {
		this.boxSpins++;
	}

	public long getBoxPulls() {
		return boxPulls;
	}

	public void addBoxPull() {
		this.boxPulls++;
		;
	}

	public long getGunsUpgraded() {
		return gunsUpgraded;
	}

	public void addGunUpgrade() {
		this.gunsUpgraded++;
	}

	public long getTrapPulls() {
		return trapPulls;
	}

	public void addTrapPull() {
		this.trapPulls++;
	}

	public double getAverageRound() {
		return averageRound;
	}

	public void calculateNewAverageRound(int newRound) {
		averageRound = ((averageRound * (totalGames - 1)) + newRound) / (totalGames);
	}

	public long getGlobalHeadshots() {
		return globalHeadshots;
	}

	public void addHeadshot() {
		this.globalHeadshots++;
	}

}
