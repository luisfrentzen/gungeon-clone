package application.view;


import application.MainApplication;
import application.controller.CameraController;
import application.controller.MapController;
import application.controller.PlayerController;
import application.controller.PlayerProjectileController;
import application.model.CameraModel;
import application.model.PlayerModel;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;

public class GameSceneView extends SceneView{
	
	PlayerController playerController;
	PlayerProjectileController ppController;
	MapController map;
	
	private boolean mPrimaryDown;
	private boolean mSecondaryDown;
	
	private CameraController camera;
	
	@Override
	protected void initComponents() {
		// TODO Auto-generated method stub
		this.root = new StackPane();
		this.root.setMinHeight(MainApplication.H);
		this.root.setMinWidth(MainApplication.W);
		
		this.canvas = new Canvas(MainApplication.W, MainApplication.H);
		this.canvas.setCache(true);
		this.canvas.setCacheHint(CacheHint.SPEED);
		StackPane.setAlignment(this.canvas, Pos.TOP_LEFT); 
		
		Scale s = new Scale();
		s.setX(5);
		s.setY(5);
		
		camera = new CameraController();
		
		this.mSecondaryDown = false;
		this.mPrimaryDown = false;
		
		this.gc = canvas.getGraphicsContext2D();
		this.gc.setImageSmoothing(false);
		this.gc.setFont(Font.loadFont("file:resources/font/minecraftia/Minecraftia-Regular.ttf", 16 * MainApplication.globalScale));
		
		this.ppController = new PlayerProjectileController(this.canvas, this.camera);
		this.playerController = new PlayerController(this.canvas, this, this.ppController, this.camera);
		this.map = new MapController(4, this.camera, this.canvas);
	}
	
	public CameraController getCamera() {
		return this.camera;
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
		this.mPrimaryDown = false;
		this.mSecondaryDown = false;
		
		this.scene.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				// TODO Auto-generated method stub
				switch (e.getButton()) {
				case SECONDARY:
					playerController.doDodge();
					mSecondaryDown = true;
					break;
				case PRIMARY:
					mPrimaryDown = true;
					break;
				default:
					break;
				}
			}
			
		});
		
		this.scene.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				// TODO Auto-generated method stub
				switch (e.getButton()) {
				case SECONDARY:
					mSecondaryDown = false;
					break;
				case PRIMARY:
					mPrimaryDown = false;
					break;
				default:
					break;
				}
			}
			
		});
		
		
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
				case R:
					playerController.doReload();
					break;
				default:
					break;
				}
			}
			
		});
		
		return this.scene;
	}

	public boolean ismSecondaryDown() {
		return mSecondaryDown;
	}

	public void setmSecondaryDown(boolean mSecondaryDown) {
		this.mSecondaryDown = mSecondaryDown;
	}

	public boolean ismPrimaryDown() {
		return mPrimaryDown;
	}

	public void setmPrimaryDown(boolean mPrimaryDown) {
		this.mPrimaryDown = mPrimaryDown;
	}
	
	@Override
	public double getPointerX() {
		// TODO Auto-generated method stub
		return this.camera.getXCamRelative(super.getPointerX());
	}
	
	@Override
	public double getPointerY() {
		// TODO Auto-generated method stub
		return this.camera.getYCamRelative(super.getPointerY());
	}

	@Override
	public void renderFrame() {
		// TODO Auto-generated method stub
//        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		map.render();
		ppController.render();
        playerController.render();
    
	}

	@Override
	public void updateFrame() {
		// TODO Auto-generated method stub
		playerController.update();
		ppController.update();
	}

}
