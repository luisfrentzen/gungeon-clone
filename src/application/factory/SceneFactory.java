package application.factory;

import java.awt.MouseInfo;
import java.net.URISyntaxException;

import application.model.SpriteModel;
import application.view.GameSceneView;
import application.view.MenuSceneView;
import application.view.SceneView;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class SceneFactory {
	
	private SpriteModel cursor;
	
	public Scene makeScene(String sceneType) {
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
		scene.generateScene();
		scene.getScene().setCursor(im);
		
		return scene.getScene();
 	}
		
}
