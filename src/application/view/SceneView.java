package application.view;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public abstract class SceneView {

	protected Pane root;
	protected Scene scene;
	
	protected abstract void initComponents();
	
	protected abstract void addComponents();
	
	protected abstract void initScene();
	
	public Scene generateScene() {
		this.initComponents();
		this.addComponents();
		this.initScene();
		
		return this.scene;
	}

}
