package application.view;

import application.MainApplication;
import application.model.MainModel;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class GameSceneView extends SceneView{
	
	@Override
	protected void initComponents() {
		// TODO Auto-generated method stub
		this.root = new StackPane();
		this.canvas = new Canvas(MainApplication.W, MainApplication.H);
		
		this.gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.RED);
        gc.fillRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
	}

	@Override
	protected Pane addComponents() {
		// TODO Auto-generated method stub
		this.root.getChildren().add(this.canvas);
		
		return this.root;
	}

	@Override
	protected Scene initScene() {
		// TODO Auto-generated method stub
		this.scene = new Scene(root);
		
		return this.scene;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		AnimationTimer loop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                	
            }
        };
        
        loop.start();
	}

}
