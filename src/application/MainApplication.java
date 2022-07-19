package application;

import java.sql.ResultSet;

import application.controller.MainController;
import application.controller.SoundController;
import application.factory.SceneFactory;
import application.model.MainModel;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApplication extends Application{

	public MainController controller;
	public MainModel model;
	public Database db;
	
	public static double recomW = 1920;
	public static double recomH = 1080;
	
	public static double W;
	public static double H;
	
	public static int FPS;
	public static double globalScale;
	
	public SceneFactory sceneFactory;
	
	public static final String MENU_SCENE = "M";
	public static final String GAME_SCENE = "G";
	
	public static boolean IS_SHAKE = true;
	public static final boolean DEFAULT_SHAKE = true;
	
	public static double SFX_VOLUME = 50;
	public static final double DEFAULT_SFX_VOL = 50;
	
	public static double MUSIC_VOLUME = 50;
	public static final double DEFAULT_MUSIC_VOL = 50;
	
	public Stage stage;
	
	public SoundController sound;
	
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		this.stage = stage;
		this.stage.setMaximized(true);
		this.stage.initStyle(StageStyle.UNDECORATED);
		this.stage.setTitle("FungeonS");
		
		this.stage.show();
		
		W = stage.getWidth();
		H = stage.getHeight();
		
		System.out.println(W + " " + H);
		
		globalScale = W / recomW;
		
		MainApplication.FPS = 60;
		
		this.sceneFactory = new SceneFactory();
		this.sound = new SoundController();
		
		this.sound.setMusicVolume(DEFAULT_MUSIC_VOL);
		this.sound.setSoundVolume(DEFAULT_SFX_VOL);
		
		this.stage.setResizable(false);
		this.stage.setScene(sceneFactory.makeScene(MainApplication.MENU_SCENE, sound, this));
		this.stage.sizeToScene();
		this.stage.show();
	}
	
	public void changeScene(String type) {
		this.stage.setScene(sceneFactory.makeScene(type, this.sound, this));
		this.stage.sizeToScene();
		this.stage.show();
	}
	
	public static double mapWidth(double w) {
		return w * (W / 100);
	}
	
	public static double mapHeight(double h) {
		return h * (H / 100);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
