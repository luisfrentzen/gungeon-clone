package application;

import application.controller.MainController;
import application.factory.SceneFactory;
import application.model.MainModel;
import application.view.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApplication extends Application{

	public MainController controller;
	public MainView view;
	public MainModel model;
	
	public SceneFactory sceneFactory;
	
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		stage.setMaximized(true);
		stage.initStyle(StageStyle.UNDECORATED);
		
		sceneFactory = new SceneFactory();
		
		stage.setScene(sceneFactory.makeScene("G"));
		stage.show();
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
