package application.factory;

import application.MainApplication;
import application.controller.SoundController;
import application.model.SpriteModel;
import application.view.GameSceneView;
import application.view.MenuSceneView;
import application.view.SceneView;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;

public class SceneFactory {
	
	private SpriteModel cursor;
	
	public Scene makeScene(String sceneType, SoundController sound, MainApplication app) {
		SceneView scene = null;
		
		switch (sceneType) {
		case "M":
			scene = new MenuSceneView();
			break;
		case "G":
			scene = new GameSceneView();
			break;
		}
		
		this.cursor = new SpriteModel("/app/cursor/", 1);
		ImageCursor im = new ImageCursor(cursor.get(0));
		scene.generateScene(sound, app);
		scene.getScene().setCursor(im);
		
		return scene.getScene();
 	}
		
}
