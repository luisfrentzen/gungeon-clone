package application.view;

import application.factory.SceneFactory;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class MenuSceneView extends SceneView{

	private Label menuLabel;
	
	@Override
	protected void initComponents() {
		// TODO Auto-generated method stub
		this.root = new StackPane();
		this.menuLabel = new Label("Main Menu");
	}

	@Override
	protected void addComponents() {
		// TODO Auto-generated method stub
		this.root.getChildren().add(menuLabel);
	}

	@Override
	protected void initScene() {
		// TODO Auto-generated method stub
		this.scene = new Scene(root);
	}

}