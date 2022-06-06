package application.factory;

import java.net.URISyntaxException;

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
		
		String path = this.getClass().getResource("/app/cursor.png").toExternalForm();
		
		Image cursor = new Image(path);
		scene.getScene().setCursor(new ImageCursor(cursor, cursor.getWidth() / 2, cursor.getHeight() / 2));
		
		return scene.getScene();
 	}
}
