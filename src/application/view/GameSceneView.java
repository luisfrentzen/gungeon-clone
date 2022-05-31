package application.view;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class GameSceneView extends SceneView{

	private Canvas canvas;
	
	@Override
	protected void initComponents() {
		// TODO Auto-generated method stub
		this.root = new StackPane();
		this.canvas = new Canvas();
	}

	@Override
	protected void addComponents() {
		// TODO Auto-generated method stub
		this.root.getChildren().add(this.canvas);
	}

	@Override
	protected void initScene() {
		// TODO Auto-generated method stub
		this.scene = new Scene(root);
	}

}
