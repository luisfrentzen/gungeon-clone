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
	
	public static final int MUSIC_TITLE_MENU = 0;
	
	public SoundController() {
		// TODO Auto-generated constructor stub
		this.loadSfxs();
	}
	
	public void loadSfxs() {
		this.sound = new HashMap<Integer, AudioClip>();
		this.music = new HashMap<Integer, MediaPlayer>();
		
		this.sound.put(SoundController.SFX_MENU_SELECT, this.loadSfx("/sfx/menu/menu_select.wav"));
		this.sound.put(SoundController.SFX_MENU_CONFIRM, this.loadSfx("/sfx/menu/menu_confirm.wav"));
		
		this.music.put(SoundController.MUSIC_TITLE_MENU, this.loadMusic("/music/menu/menu_bgm.mp3"));
	}
	
	public MediaPlayer loadMusic(String path) {
		Media m = new Media(getClass().getResource(path).toExternalForm());
		return new MediaPlayer(m);
	}
	
	public AudioClip loadSfx(String path) {
		return new AudioClip(getClass().getResource(path).toExternalForm());
	}
	
	public void playSfx(int id) {
		this.sound.get(id).play();
	}
	
	public void playMusic(int id) {
		this.music.get(id).play();
		this.music.get(id).setCycleCount(MediaPlayer.INDEFINITE);
	}

}
