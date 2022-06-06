package application.factory;

import java.net.URISyntaxException;

import application.model.SpriteModel;
import application.view.GameSceneView;
import application.view.MenuSceneView;
import application.view.SceneView;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class SceneFactory {
	
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
		
		scene.generateScene();
		
		SpriteModel cursor = new SpriteModel("/app/cursor", 10);
		System.out.println(cursor.getWidth(0));
		scene.getScene().setCursor(new ImageCursor(cursor.get(0), cursor.getWidth(0) / 2, cursor.getHeight(0) / 2));
		
		return scene.getScene();
 	}
}
