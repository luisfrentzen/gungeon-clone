package application.controller;

import java.util.HashMap;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundController {

	private HashMap<Integer, AudioClip> sound;
	private HashMap<Integer, MediaPlayer> music;
	
	public static final int SFX_MENU_SELECT = 0;
	public static final int SFX_MENU_CONFIRM = 1;
	public static final int SFX_MENU_PAUSE = 2;
	public static final int SFX_MENU_CANCEL = 3;
	public static final int SFX_GAMEOVER = 4;
	
	public static final int SFX_DEAGLE_SHOT_1 = 5;
	public static final int SFX_DEAGLE_SHOT_2 = 6;
	public static final int SFX_DEAGLE_SHOT_3 = 7;
	public static final int SFX_DEAGLE_RELOAD = 8;

	public static final int SFX_MAGNUM_SHOT_1 = 9;
	public static final int SFX_MAGNUM_SHOT_2 = 10;
	public static final int SFX_MAGNUM_SHOT_3 = 11;
	public static final int SFX_MAGNUM_RELOAD = 12;
	
	public static final int SFX_PLAYER_HIT = 13;
	public static final int SFX_PLAYER_DEATH = 14;
	
	public static final int SFX_PLAYER_LEAP_1 = 15;
	public static final int SFX_PLAYER_LEAP_2 = 16;
	public static final int SFX_PLAYER_LEAP_3 = 17;
	
	public static final int SFX_PLAYER_ROLL_1 = 18;
	public static final int SFX_PLAYER_ROLL_2 = 19;
	public static final int SFX_PLAYER_ROLL_3 = 20;
	
	public static final int SFX_PLAYER_STEP_1 = 21;
	public static final int SFX_PLAYER_STEP_2 = 22;
	public static final int SFX_PLAYER_STEP_3 = 23;
	
	public static final int SFX_ENEMY_HIT = 24;
	public static final int SFX_ENEMY_DEATH_1 = 25;
	public static final int SFX_ENEMY_SPAWN_1 = 26;
	public static final int SFX_ENEMY_SPAWN_2 = 27;
	public static final int SFX_ENEMY_SPAWN_3 = 28;
	public static final int SFX_ENEMY_DEATH_2 = 30;
	public static final int SFX_ENEMY_STEP_1 = 31;
	public static final int SFX_ENEMY_STEP_2 = 32;
	public static final int SFX_ENEMY_STEP_3 = 33;
	public static final int SFX_ENEMY_HURT_1 = 34;
	public static final int SFX_ENEMY_HURT_2 = 35;
	
	public static final int SFX_PLAYER_SPAWN = 36;
	
	public static final int MUSIC_TITLE_MENU = 0;
	public static final int MUSIC_GAME_1 = 1;
	
	public SoundController() {
		// TODO Auto-generated constructor stub
		this.loadSfxs();
	}
	
	public void loadSfxs() {
		this.sound = new HashMap<Integer, AudioClip>();
		this.music = new HashMap<Integer, MediaPlayer>();
		
		this.sound.put(SoundController.SFX_MENU_SELECT, this.loadSfx("/sfx/menu/menu_select.wav"));
		this.sound.put(SoundController.SFX_MENU_CONFIRM, this.loadSfx("/sfx/menu/menu_confirm.wav"));
		this.sound.put(SoundController.SFX_MENU_PAUSE, this.loadSfx("/sfx/menu/menu_pause.wav"));
		this.sound.put(SoundController.SFX_MENU_CANCEL, this.loadSfx("/sfx/menu/menu_cancel.wav"));
		this.sound.put(SoundController.SFX_GAMEOVER, this.loadSfx("/sfx/menu/gameover.wav"));
		this.sound.put(SoundController.SFX_DEAGLE_SHOT_1, this.loadSfx("/sfx/player/gunshot/deserteagle_shot_01.wav"));
		this.sound.put(SoundController.SFX_DEAGLE_SHOT_2, this.loadSfx("/sfx/player/gunshot/deserteagle_shot_02.wav"));
		this.sound.put(SoundController.SFX_DEAGLE_SHOT_3, this.loadSfx("/sfx/player/gunshot/deserteagle_shot_03.wav"));
		this.sound.put(SoundController.SFX_DEAGLE_RELOAD, this.loadSfx("/sfx/player/reload/deserteagle_reload_01.wav", 0.6));
		this.sound.put(SoundController.SFX_MAGNUM_SHOT_1, this.loadSfx("/sfx/enemy/gunshot/magnum_shot_01.wav"));
		this.sound.put(SoundController.SFX_MAGNUM_SHOT_2, this.loadSfx("/sfx/enemy/gunshot/magnum_shot_02.wav"));
		this.sound.put(SoundController.SFX_MAGNUM_SHOT_3, this.loadSfx("/sfx/enemy/gunshot/magnum_shot_03.wav"));
		this.sound.put(SoundController.SFX_MAGNUM_RELOAD, this.loadSfx("/sfx/enemy/reload/ak47_reload_01.wav"));
		this.sound.put(SoundController.SFX_PLAYER_HIT, this.loadSfx("/sfx/player/hit/general_hurt_01.wav"));
		this.sound.put(SoundController.SFX_PLAYER_DEATH, this.loadSfx("/sfx/player/death/general_death_01.wav"));
		this.sound.put(SoundController.SFX_PLAYER_LEAP_1, this.loadSfx("/sfx/player/leap/dodge_leap_01.wav", 0.8));
		this.sound.put(SoundController.SFX_PLAYER_LEAP_2, this.loadSfx("/sfx/player/leap/dodge_leap_02.wav", 0.8));
		this.sound.put(SoundController.SFX_PLAYER_LEAP_3, this.loadSfx("/sfx/player/leap/dodge_leap_03.wav", 0.8));
		this.sound.put(SoundController.SFX_PLAYER_ROLL_1, this.loadSfx("/sfx/player/roll/dodge_roll_01.wav", 0.8));
		this.sound.put(SoundController.SFX_PLAYER_ROLL_2, this.loadSfx("/sfx/player/roll/dodge_roll_02.wav", 0.8));
		this.sound.put(SoundController.SFX_PLAYER_ROLL_3, this.loadSfx("/sfx/player/roll/dodge_roll_03.wav", 0.8));
		this.sound.put(SoundController.SFX_PLAYER_STEP_1, this.loadSfx("/sfx/player/steps/boot_stone_01.wav", 0.6));
		this.sound.put(SoundController.SFX_PLAYER_STEP_2, this.loadSfx("/sfx/player/steps/boot_stone_02.wav", 0.6));
		this.sound.put(SoundController.SFX_PLAYER_STEP_3, this.loadSfx("/sfx/player/steps/boot_stone_03.wav", 0.6));
		this.sound.put(SoundController.SFX_ENEMY_HIT, this.loadSfx("/sfx/enemy/hit/SAA_impact_01.wav"));
		this.sound.put(SoundController.SFX_ENEMY_DEATH_1, this.loadSfx("/sfx/enemy/death/metalBullet_death_01.wav"));
		this.sound.put(SoundController.SFX_ENEMY_SPAWN_1, this.loadSfx("/sfx/enemy/spawn/fs_mimic_01.wav"));
		this.sound.put(SoundController.SFX_ENEMY_SPAWN_2, this.loadSfx("/sfx/enemy/spawn/fs_mimic_02.wav"));
		this.sound.put(SoundController.SFX_ENEMY_SPAWN_3, this.loadSfx("/sfx/enemy/spawn/fs_mimic_03.wav"));
		this.sound.put(SoundController.SFX_ENEMY_DEATH_2, this.loadSfx("/sfx/enemy/death/metalBullet_death_02.wav"));
		this.sound.put(SoundController.SFX_ENEMY_STEP_1, this.loadSfx("/sfx/enemy/steps/metalBullet_step_01.wav", 0.1));
		this.sound.put(SoundController.SFX_ENEMY_STEP_2, this.loadSfx("/sfx/enemy/steps/metalBullet_step_02.wav", 0.1));
		this.sound.put(SoundController.SFX_ENEMY_STEP_3, this.loadSfx("/sfx/enemy/steps/metalBullet_step_03.wav", 0.1));
		this.sound.put(SoundController.SFX_ENEMY_HURT_1, this.loadSfx("/sfx/enemy/hurt/metalBullet_hurt_01.wav"));
		this.sound.put(SoundController.SFX_ENEMY_HURT_2, this.loadSfx("/sfx/enemy/hurt/metalBullet_hurt_02.wav"));
		this.sound.put(SoundController.SFX_PLAYER_SPAWN, this.loadSfx("/sfx/player/spawn/pit_fall_01.wav"));

		this.music.put(SoundController.MUSIC_TITLE_MENU, this.loadMusic("/music/menu/menu_bgm.mp3"));
		this.music.put(SoundController.MUSIC_GAME_1, this.loadMusic("/music/game/03 GUNGEON UP GUNGEON DOWN.mp3", 0.6));
	}
	
	public MediaPlayer loadMusic(String path) {
		Media m = new Media(getClass().getResource(path).toExternalForm());
		return new MediaPlayer(m);
	}
	
	public MediaPlayer loadMusic(String path, double vol) {
		Media m = new Media(getClass().getResource(path).toExternalForm());
		MediaPlayer mp = new MediaPlayer(m);
		mp.setVolume(vol);
		return mp;
	}
	
	public AudioClip loadSfx(String path) {
		return new AudioClip(getClass().getResource(path).toExternalForm());
	}
	
	public AudioClip loadSfx(String path, double vol) {
		AudioClip a = new AudioClip(getClass().getResource(path).toExternalForm());
		a.setVolume(vol);
		return a;
	}
	
	public void playRandomSfx(int ...id) {
		int pick = (int)(Math.random() * id.length);
		this.sound.get(id[pick]).play();
	}
	
	public void playSfx(int id) {
		this.sound.get(id).play();
	}
	
	public void playMusic(int id) {
		this.music.get(id).play();
		this.music.get(id).setCycleCount(MediaPlayer.INDEFINITE);
	}
	
	public void pauseMusic(int id) {
		this.music.get(id).pause();
	}
	
	public void stopMusic(int id) {
		this.music.get(id).stop();
	}
	
	public void setMusicVolume(double vol) {
		this.music.forEach((k, v) -> {
			v.setVolume(vol);
		});
	}
	
	public void setSoundVolume(double vol) {
		this.sound.forEach((k, v) -> {
			v.setVolume(vol);
		});
	}

}
