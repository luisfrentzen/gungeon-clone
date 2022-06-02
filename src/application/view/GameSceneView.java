package application.view;

import application.MainApplication;
import application.controller.PlayerController;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class GameSceneView extends SceneView{
	
	PlayerController playerController;
	
	@Override
	protected void initComponents() {
		// TODO Auto-generated method stub
		this.root = new StackPane();
		this.canvas = new Canvas(MainApplication.W, MainApplication.H);
		
		this.gc = canvas.getGraphicsContext2D();
		
		this.playerController = new PlayerController(this.canvas);
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
		
		this.scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				// TODO Auto-generated method stub
				switch(e.getCode()) {
				case W:
					playerController.setVectorUp(1);
					break;
				case A:
					playerController.setVectorLeft(1);
					break;
				case S:
					playerController.setVectorDown(1);
					break;
				case D:
					playerController.setVectorRight(1);
					break;
				default:
					break;
				}
			}
			
		});
		
		this.scene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				// TODO Auto-generated method stub
				switch(e.getCode()) {
				case W:
					playerController.setVectorUp(0);
					break;
				case A:
					playerController.setVectorLeft(0);
					break;
				case S:
					playerController.setVectorDown(0);
					break;
				case D:
					playerController.setVectorRight(0);
					break;
				default:
					break;
				}
			}
			
		});
		
		return this.scene;
	}


	@Override
	public void renderFrame() {
		// TODO Auto-generated method stub
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        playerController.render();
        
        System.out.println("render");
	}

	@Override
	public void updateFrame() {
		// TODO Auto-generated method stub
		playerController.update();
		
		System.out.println("update");
	}

}
