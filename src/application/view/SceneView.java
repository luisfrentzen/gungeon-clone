package application.view;

import java.awt.MouseInfo;

import application.MainApplication;
import application.model.SpriteModel;
import javafx.animation.AnimationTimer;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

public abstract class SceneView {

	protected Pane root;
	protected Scene scene;
	
	protected Canvas canvas;
	protected GraphicsContext gc;
	
	protected abstract void initComponents();
	
	protected abstract Pane addComponents();
	
	protected abstract Scene initScene();
	
	public void generateScene() {
		this.initComponents();
		this.root = this.addComponents();
		this.scene = this.initScene();
		
		this.start();
	}
	
	public double getPointerX() {
		return MouseInfo.getPointerInfo().getLocation().getX() + ImageCursor.getBestSize(64, 64).getWidth() / 2;
	}
	
	public double getPointerY() {
		return  MouseInfo.getPointerInfo().getLocation().getY() - ImageCursor.getBestSize(64,64).getHeight() * 0.75;
	}
	
	public abstract void updateFrame();
	
	public abstract void renderFrame();
	
	public void start() {
		
		AnimationTimer loop = new AnimationTimer() {
			double then = System.currentTimeMillis();
			double interval = 1000 / MainApplication.FPS;
			
            @Override
            public void handle(long n) {
            	double now = System.currentTimeMillis();
            	double elapsed = now - then;
            	
            	if (elapsed > interval) {
            		then = now;
            		updateFrame();
            		renderFrame();
            	}

            }
        };
        
        loop.start();
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
