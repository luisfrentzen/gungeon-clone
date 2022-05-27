package application.factory;

import application.view.GameSceneView;
import application.view.MenuSceneView;
import application.view.SceneView;
import javafx.scene.Scene;

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
		
		return scene.generateScene();
 	}
}
