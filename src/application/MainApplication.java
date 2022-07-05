package application;

import application.controller.MainController;
import application.factory.SceneFactory;
import application.model.MainModel;
import application.view.MainView;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApplication extends Application{

	public MainController controller;
	public MainView view;
	public MainModel model;
	
	public static double recomW = 1920;
	public static double recomH = 1080;
	
	public static double W;
	public static double H;
	
	public static int FPS;
	public static double globalScale;
	
	public SceneFactory sceneFactory;
	public static Scene scene;
	
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		stage.setMaximized(true);
		stage.initStyle(StageStyle.UNDECORATED);
		
		stage.show();
		
		W = stage.getWidth();
		H = stage.getHeight();
		
		System.out.println(W + " " + H);
		
//		globalScale = W / recomW;
		globalScale = 1;
		
		FPS = 60;
		
		sceneFactory = new SceneFactory();
		scene = sceneFactory.makeScene("G");
		
		stage.setResizable(false);
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
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
