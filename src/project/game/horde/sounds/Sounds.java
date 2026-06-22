package project.game.horde.sounds;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import project.game.horde.main.Handler;
import project.game.horde.utils.Utils;

public class Sounds {
	public static class ClipWrapper {
		String clipId;
		Clip clip;
		boolean isPaused = false; // Add this field
		float volume;
		boolean isLooping; // New field to store looping state

		public ClipWrapper(String clipId, Clip clip, float volume, boolean isLooping) {
			this.clipId = clipId;
			this.clip = clip;
			this.volume = volume;
			this.isLooping = isLooping;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			ClipWrapper that = (ClipWrapper) o;
			return clipId.equals(that.clipId) && clip.equals(that.clip);
		}

		@Override
		public int hashCode() {
			return Objects.hash(clipId, clip);
		}
	}

	private static Handler thehandler;
	private static float masterVolume;
	private static boolean resetting = false;
	private static ConcurrentHashMap<String, CopyOnWriteArrayList<Clip>> clipPools = new ConcurrentHashMap<>();
	private static ExecutorService threadPool = Executors.newFixedThreadPool(2000);
	private static CopyOnWriteArrayList<ClipWrapper> playingClips = new CopyOnWriteArrayList<>();
	private static ConcurrentHashMap<ClipWrapper, Long> pausedPositions = new ConcurrentHashMap<>();

	public static final String BACKGROUND_MUSIC_ID = "backgroundMusic";
	public static final String SHOOT_BETA_ID = "shootBeta";
	public static final String SWOOSH_ID = "swoosh";
	public static final String YUM_ID = "yum";

	public static URL backgroundMusic;
	public static URL shootBeta;
	public static URL swoosh;
	public static URL yum;

	public static void initHandler(Handler handler) {
		thehandler = handler;
	}

	public static void init(Handler handler) {
		thehandler = handler;
		masterVolume = thehandler.getSettings().getMasterVolume();

		backgroundMusic = Utils.class.getResource("/music/NightOfShadows.wav");
		shootBeta = Utils.class.getResource("/sounds/gunSounds/shootBeta.wav");
		swoosh = Utils.class.getResource("/sounds/gunSounds/swoosh.wav");
		yum = Utils.class.getResource("/sounds/gunSounds/yum.wav");

		preloadClip(BACKGROUND_MUSIC_ID, backgroundMusic, 1, 1); // Preload 10 instances
		preloadClip(SHOOT_BETA_ID, shootBeta, 10, 1);
		preloadClip(SWOOSH_ID, swoosh, 10, 1);
		preloadClip(YUM_ID, yum, 10, 1);

		GunSounds.init(handler);
		PowerupSounds.init(handler);
		Music.init(handler);
		InteractSounds.init(handler);
		ZombieSounds.init(handler);
		CreatureSounds.init(handler);
		MiscWeaponSounds.init(handler);
	}

	public static void convertFile(File file) {
		try {
			File audioFile = new File("path/to/problematic/sound.wav");
			AudioInputStream originalStream;
			originalStream = AudioSystem.getAudioInputStream(audioFile);
			AudioFormat originalFormat = originalStream.getFormat();
			
            AudioFormat targetFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    44100.0f,    // sample rate
                    16,          // sample size in bits
                    2,           // channels (1 = mono, 2 = stereo)
                    4,           // frame size (bytes per frame)
                    44100.0f,    // frame rate
                    false        // little-endian
            );
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void preloadClip(String clipId, URL clipFile, int instances, float speed) {
	        CopyOnWriteArrayList<Clip> clipList = new CopyOnWriteArrayList<>();
	        for (int i = 0; i < instances; i++) {
	            try {
	                AudioInputStream originalStream = AudioSystem.getAudioInputStream(clipFile);
	                AudioFormat baseFormat = originalStream.getFormat();
		            AudioFormat targetFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    44100.0f, // sample rate
                    16,       // sample size in bits
                    2,        // channels (1 = mono, 2 = stereo)
                    4,        // frame size (bytes per frame)
                    44100.0f, // frame rate
                    false     // little-endian
                );
		            AudioInputStream audioInputStream = originalStream;
		            if (!baseFormat.matches(targetFormat)) {
		                audioInputStream = AudioSystem.getAudioInputStream(targetFormat, originalStream);
		            }
	                byte[] audioBytes = audioInputStream.readAllBytes();
	                byte[] spedUpBytes = speedUpAudio(audioBytes, baseFormat, speed);

	                InputStream byteArrayInputStream = new ByteArrayInputStream(spedUpBytes);
	                AudioInputStream spedUpStream = new AudioInputStream(byteArrayInputStream, targetFormat,
	                        spedUpBytes.length / targetFormat.getFrameSize());

	                Clip clip = AudioSystem.getClip();
	                clip.open(spedUpStream);
	                clipList.add(clip);
	            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
	                e.printStackTrace();
	            }
	        }
	        clipPools.put(clipId, clipList);
	    
	}
	
	private static byte[] speedUpAudio(byte[] audioBytes, AudioFormat format, float speed) {
	    int frameSize = format.getFrameSize();
	    int framesCount = audioBytes.length / frameSize;
	    int newFramesCount = (int) (framesCount / speed);
	    byte[] newAudioBytes = new byte[newFramesCount * frameSize];

	    for (int i = 0; i < newFramesCount; i++) {
	        int originalIndex = (int) (i * speed) * frameSize;
	        int newIndex = i * frameSize;
	        System.arraycopy(audioBytes, originalIndex, newAudioBytes, newIndex, frameSize);
	    }

	    return newAudioBytes;
	}
	
	public static void pauseClips() {
	    // Create and start a new thread to reset sounds
	    new Thread(() -> {
	    	resetting = true;
	        pauseAllClips();
	        resetting = false;
	    }).start();
	}
	
	public static void resumeClips() {
	    // Create and start a new thread to reset sounds
	    new Thread(() -> {
	    	//wait to resume all clips to prevent crashing
	    	while(resetting) {
	    		
	    	}
	    	resetting = true;
	        resumeAllClips();
	        resetting = false;
	    }).start();
	}
	
	public static void resetSounds() {
	    // Create and start a new thread to reset sounds
	    new Thread(() -> {
	    	resetting = true;
	        pauseAllClips();

	        //small delay so the pause completes properly
	        try {
	            Thread.sleep(100);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	        resumeAllClips();
	        resetting = false;
	    }).start();
	}

	public static void playClip(String clipId, float speed, float volume, boolean persist) {
		if(resetting)
			return;
		threadPool.execute(() -> {
			class AudioListener implements LineListener {
				private boolean done = false;

				@Override
				public synchronized void update(LineEvent event) {
					if (event.getType() == LineEvent.Type.STOP || event.getType() == LineEvent.Type.CLOSE) {
						done = true;
						notifyAll();
					}
				}

				public synchronized void waitUntilDone() throws InterruptedException {
					while (!done) {
						wait();
					}
				}
			}
			AudioListener listener = new AudioListener();
			Clip clip = getClipFromPool(clipId);
			if (clip == null)
				return;

			try {
				setVolume(clip, volume); // Set the volume
				clip.setFramePosition(0); // Reset the clip to the beginning
				clip.addLineListener(listener);
				ClipWrapper clipWrapper = new ClipWrapper(clipId, clip, volume, persist);
				playingClips.add(clipWrapper); // Add to playing clips
				System.out.println("Playing clip: " + clipId);

				if (persist) {
					clip.loop(Clip.LOOP_CONTINUOUSLY);
				} else {
					clip.start();
				}
				listener.waitUntilDone();
				if (!persist) {
					clip.stop();
					playingClips.remove(clipWrapper); // Remove from playing clips
					returnClipToPool(clipId, clip); // Return the clip to the pool
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}

	public static void playClipFrom(String clipId, float speed, float volume, long millisecondPosition,
			boolean persist) {
		if(resetting)
			return;
		threadPool.execute(() -> {
			class AudioListener implements LineListener {
				private boolean done = false;

				@Override
				public synchronized void update(LineEvent event) {
					if (event.getType() == LineEvent.Type.STOP || event.getType() == LineEvent.Type.CLOSE) {
						done = true;
						notifyAll();
					}
				}

				public synchronized void waitUntilDone() throws InterruptedException {
					while (!done) {
						wait();
					}
				}
			}
			AudioListener listener = new AudioListener();
			Clip clip = getClipFromPool(clipId);
			if (clip == null)
				return;

			try {
	            long microsecondPosition = millisecondPosition * 1000;

	            System.out.println("Requested position: " + millisecondPosition + "ms, " + microsecondPosition + "µs");
	            
	            if (microsecondPosition < 0 || microsecondPosition > clip.getMicrosecondLength()) {
	                System.err.println("Invalid position: " + microsecondPosition);
	                return;
	            }
				setVolume(clip, volume); // Set the volume
				clip.setMicrosecondPosition(millisecondPosition * 1000); // Set the starting position
				clip.addLineListener(listener);
				ClipWrapper clipWrapper = new ClipWrapper(clipId, clip, volume, persist);
				playingClips.add(clipWrapper); // Add to playing clips
				System.out.println("Playing clip: " + clipId);

				if (persist) {
					clip.loop(Clip.LOOP_CONTINUOUSLY);
				} else {
					clip.start();
				}
				listener.waitUntilDone();
				if (!persist) {
					clip.stop();
					playingClips.remove(clipWrapper); // Remove from playing clips
					returnClipToPool(clipId, clip); // Return the clip to the pool
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}

	private static Clip getClipFromPool(String clipId) {
		CopyOnWriteArrayList<Clip> clipList = clipPools.get(clipId);
		if (clipList != null && !clipList.isEmpty()) {
			return clipList.remove(0);
		}
		return null;
	}

	private static void returnClipToPool(String clipId, Clip clip) {
		CopyOnWriteArrayList<Clip> clipList = clipPools.get(clipId);
		if (clipList != null) {
			clipList.add(clip);
		}
	}
	
	public static void adjustVolumeOfPlayingClip(String clipId, float newVolume) {
	    for (ClipWrapper clipWrapper : playingClips) {
	        if (clipWrapper.clipId.equals(clipId)) {
	            setVolume(clipWrapper.clip, newVolume);
	            clipWrapper.volume = newVolume; // Update the stored volume in ClipWrapper
	            System.out.println("Adjusted volume for clip: " + clipId + " to " + newVolume);
	        }
	    }
	}

	public static void setVolume(Clip clip, float volume) {
		if (clip != null) {
			FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			if (volume == -1.0f) {
				float min = volumeControl.getMinimum();
				float max = volumeControl.getMaximum();
				float newVolume = min + (max - min) * ((float) thehandler.getSettings().getMasterVolume() / 10);
				volumeControl.setValue(newVolume);
			} else {
				float min = volumeControl.getMinimum();
				float max = volumeControl.getMaximum();
				float newVolume = min
						+ (max - min) * volume * ((float) thehandler.getSettings().getMasterVolume() / 10);
				volumeControl.setValue(newVolume);
			}
		}
	}

	public static void stopClip(String clipId) {
		//System.out.println("Attempting to stop clip: " + clipId);

		CopyOnWriteArrayList<Clip> clipList = clipPools.get(clipId);
		if (clipList != null) {
			//System.out.println("Clip list is not null for: " + clipId);

			for (ClipWrapper clipWrapper : playingClips) {
				//System.out.println("Current clip: " + clipWrapper.clip.toString());

				if (clipWrapper.clipId.equals(clipId)) {
					System.out.println("Stopping clip: " + clipId);
					clipWrapper.clip.stop();
					playingClips.remove(clipWrapper);
					returnClipToPool(clipId, clipWrapper.clip);
				} else {
					//System.out.println("Does not contain: " + clipId);
				}
			}
		}
	}


	public static void pauseAllClips() {
		shutdownThreadPool();
		synchronized (playingClips) {
			System.out.println("Pausing all clips.");
			for (ClipWrapper clipWrapper : playingClips) {
				synchronized (clipWrapper) {
					if (clipWrapper.clip.isRunning() && !clipWrapper.isPaused) {
						long position = clipWrapper.clip.getMicrosecondPosition();
						clipWrapper.clip.stop();
						clipWrapper.isPaused = true;
						pausedPositions.put(clipWrapper, position);
						System.out.println("Paused clip: " + clipWrapper.clipId);
					} else {
						System.out.println("Clip already paused or not running: " + clipWrapper.clipId);
					}
				}
				returnClipToPool(clipWrapper.clipId, clipWrapper.clip); // Return clip to the pool after pausing
			}
			playingClips.clear(); // Clear playingClips since all clips are returned to pool
			threadPool = Executors.newFixedThreadPool(2000);

		}
	}
	
	public static long getMillisecondPosition(String clipId, int updater) {
		for (ClipWrapper clipWrapper : playingClips) {
			if (clipWrapper.clipId.equals(clipId)) {
				long position = clipWrapper.clip.getMicrosecondPosition();
				long clipLength = clipWrapper.clip.getMicrosecondLength();
				long adjustedPosition = position % clipLength; // Calculate the position within the loop
				long returnThis = (long) (adjustedPosition/1000);
				System.out.println("clip length in ms:" + (clipLength/1000));
				System.out.println("return this:" + returnThis);
				if(clipLength/1000 - returnThis <= (updater * 60))
					returnThis = 0;
				return returnThis;
			}
		}
		return 0;
	}
	
	public static long getMicrosecondLength(String clipId) {
		for (ClipWrapper clipWrapper : playingClips) {
			if (clipWrapper.clipId.equals(clipId)) {
				return clipWrapper.clip.getMicrosecondLength();
			}
		}
		return 0;
	}

	public static void resumeAllClips() {
		synchronized (pausedPositions) {
			System.out.println("Resuming all clips.");
			for (ClipWrapper clipWrapper : pausedPositions.keySet()) {
				long position = pausedPositions.remove(clipWrapper);
				Clip clip = getClipFromPool(clipWrapper.clipId);
				if (clip != null) {
					long clipLength = clip.getMicrosecondLength();
					long adjustedPosition = position % clipLength; // Calculate the position within the loop
					clip.setMicrosecondPosition(adjustedPosition);
					// clip.setMicrosecondPosition(position);
					System.out.println(position);
					setVolume(clip, clipWrapper.volume);
					ClipWrapper resumedClipWrapper = new ClipWrapper(clipWrapper.clipId, clip, clipWrapper.volume,
							 clipWrapper.isLooping);
					playingClips.add(resumedClipWrapper);
					System.out.println("Resumed clip: " + clipWrapper.clipId);
					if (clipWrapper.isLooping) {
						clip.start();
						clip.loop(Clip.LOOP_CONTINUOUSLY);
					} else {
						clip.start();
					}
				} else {
					System.out.println("Unable to resume clip: " + clipWrapper.clipId + ". Clip not found in pool.");
				}
			}
			pausedPositions.clear();
		}
	}

	public static void checkToRestartClip(String clipId) {
		CopyOnWriteArrayList<Clip> clipList = clipPools.get(clipId);
		if (clipList != null) {
			for (Clip clip : clipList) {
				if (!clip.isRunning()) {
					clip.setFramePosition(0);
					setVolume(clip, masterVolume); // Set volume to masterVolume or specific volume setting
					clip.start();
					ClipWrapper clipWrapper = new ClipWrapper(clipId, clip, masterVolume, false); // Store volume in
																									// ClipWrapper
					playingClips.add(clipWrapper);
				}
			}
		}
	}

	public static void shutdownThreadPool() {
		threadPool.shutdown();
		try {
			if (!threadPool.awaitTermination(1, TimeUnit.SECONDS)) {
				threadPool.shutdownNow();
			}
		} catch (InterruptedException e) {
			threadPool.shutdownNow();
		}
	}
}
