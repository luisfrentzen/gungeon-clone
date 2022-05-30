package application.view;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public abstract class SceneView {

	protected Pane root;
	protected Scene scene;
	
	protected abstract void initComponents();
	
	protected abstract Pane addComponents();
	
	protected abstract Scene initScene();
	
	public void generateScene() {
		this.initComponents();
		this.root = this.addComponents();
		this.scene = this.initScene();
	}

	public Pane getRoot() {
		return root;
	}

	public void setRoot(Pane root) {
		this.root = root;
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}
	

}
