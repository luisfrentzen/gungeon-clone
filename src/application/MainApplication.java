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
	
	public static double W;
	public static double H;
	
	public static int FPS;
	
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
		
		FPS = 12;
		
		sceneFactory = new SceneFactory();
		scene = sceneFactory.makeScene("G");
		
		stage.setScene(scene);
		stage.show();
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
