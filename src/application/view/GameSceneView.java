package application.view;


import java.util.Vector;

import application.MainApplication;
import application.controller.BarrierController;
import application.controller.CameraController;
import application.controller.EnemyController;
import application.controller.EnemyProjectileController;
import application.controller.HudController;
import application.controller.MapController;
import application.controller.PlayerController;
import application.controller.PlayerProjectileController;
import application.controller.SoundController;
import application.factory.SceneFactory;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Scale;

public class GameSceneView extends SceneView{
	
	PlayerController playerController;
	PlayerProjectileController ppController;
	
	Vector<EnemyController> enemies;
	EnemyProjectileController epController;
	
	MapController map;
	BarrierController barrier;
	
	private boolean mPrimaryDown;
	private boolean mSecondaryDown;
	
	private double mapW;
	private double mapH;
	
	private CameraController camera;
	private HudController hud;
	private int enemiesLeft;
	private int score;
	private int waveDelay;
	private int enemyIndex;
	
	private int deathFade;
	private boolean screenChanged;
	
	protected double mouseX;
	protected double mouseY;
	private int enemyBatch;
	
	private StackPane pausePane;
	protected boolean onPause;
	
	@Override
	protected void initComponents() {
		// TODO Auto-generated method stub
		this.root = new StackPane();
		this.root.setMinHeight(MainApplication.H);
		this.root.setMinWidth(MainApplication.W);
		this.root.getStyleClass().add("option-container");
		
		this.canvas = new Canvas(MainApplication.W, MainApplication.H);
		this.canvas.setCache(true);
		this.canvas.setCacheHint(CacheHint.SPEED);
		
		StackPane.setAlignment(this.canvas, Pos.TOP_LEFT); 
		
		Scale s = new Scale();
		s.setX(5);
		s.setY(5);
		
		this.deathFade = 0;
		this.screenChanged = false;
		
		camera = new CameraController();
		
		this.mSecondaryDown = false;
		this.mPrimaryDown = false;
		
		this.gc = canvas.getGraphicsContext2D();
		this.gc.setImageSmoothing(false);
		this.gc.setFont(Font.loadFont("file:resources/font/minecraftia/Minecraftia-Regular.ttf", 16 * MainApplication.globalScale));
						
		this.map = new MapController(4, this.camera, this.canvas);
		this.mapH = this.map.getMapHeight();
		this.mapW = this.map.getMapWidth();
		this.barrier = new BarrierController(this.canvas, this.camera, 0, -80, mapW, mapH + 80);
		this.enemies = new Vector<EnemyController>();
		
		this.ppController = new PlayerProjectileController(this.canvas, this.camera, this.barrier, this.enemies);
		this.playerController = new PlayerController(this.canvas, this, this.ppController, this.camera, this.barrier);
		
		this.epController = new EnemyProjectileController(this.canvas, this.camera, this.barrier, this.playerController);
		this.hud = new HudController(camera, canvas, playerController);
		
		this.enemiesLeft = 0;
		this.score = 0;
		this.enemyIndex = 0;
		this.waveDelay = MainApplication.FPS * 2;
		
		this.enemyBatch = 2;
		
		this.generateEnemies(36);
		
		initPauseMenu();
	}
	
	public double getMouseX() {
		return mouseX + ImageCursor.getBestSize(64, 64).getWidth() / 2;
	}

	public double getMouseY() {
		return mouseY + ImageCursor.getBestSize(64, 64).getHeight() / 2;
	}

	public void generateEnemies(int n) {
		for (int i = 0; i < n; i++) {
			this.enemies.add(new EnemyController(-1000, 1000, this.epController, canvas, camera, barrier, playerController, this));
		}
	}
	
	public void activateEnemies(int n) {
		this.enemiesLeft += n;
		
		this.enemyBatch = this.enemyBatch + (int)(Math.random() * 2);
		
		for (int i = 0; i < n; i++) {
			this.enemies.get(enemyIndex).activate();
			this.enemyIndex = (this.enemyIndex + 1) % this.enemies.size();
		}

		if (this.enemyBatch > 12) this.enemyBatch = 12;
	}
	
	public void enemyDied() {
		this.enemiesLeft -= 1;
		this.score += 100 * this.playerController.getPlayerHp();
	}
	
	public double getMapH() {
		return this.mapH;
	}
	
	public double getMapW() {
		return this.mapW;
	}
	
	public CameraController getCamera() {
		return this.camera;
	}

	@Override
	protected Pane addComponents() {
		// TODO Auto-generated method stub
		this.root.getChildren().add(this.canvas);
		this.root.getChildren().add(this.pausePane);
		
		return this.root;
	}

	@Override
	protected Scene initScene() {
		// TODO Auto-generated method stub
		this.scene = new Scene(root);
		this.scene.getStylesheets().add(getClass().getResource("../style/menu-view.css").toExternalForm());
		this.mPrimaryDown = false;
		this.mSecondaryDown = false;
		
		this.scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		        mouseX = event.getSceneX();
		        mouseY = event.getSceneY();
		    }
		});
		
		this.scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		        mouseX = event.getSceneX();
		        mouseY = event.getSceneY();
		    }
		});
		
		this.scene.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				// TODO Auto-generated method stub
				if (playerController.isHasDied()) return;
				
				switch (e.getButton()) {
				case SECONDARY:
					if (!onPause) playerController.doDodge();
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
				if (playerController.isHasDied()) return;
				
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
				if (playerController.isHasDied()) return;
				
				switch(e.getCode()) {
				case W:
					if (!onPause) playerController.setVectorUp(1);
					break;
				case A:
					if (!onPause) playerController.setVectorLeft(1);
					break;
				case S:
					if (!onPause) playerController.setVectorDown(1);
					break;
				case D:
					if (!onPause) playerController.setVectorRight(1);
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
				
				if (playerController.isHasDied()) return;
				
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
				case ESCAPE:
					togglePause();
					break;
				default:
					break;
				}
			}
			
		});
		
		return this.scene;
	}
	
	public void togglePause() {
		onPause = !onPause;
		pausePane.setVisible(onPause);
		sound.playSfx(onPause? SoundController.SFX_MENU_PAUSE : SoundController.SFX_MENU_CANCEL);
	}
	
	public void initPauseMenu() {
		this.pausePane = new StackPane();
		this.pausePane.getStyleClass().add("option-container");
		this.pausePane.setVisible(false);
		this.onPause = false;
		
		Pane layoutPane = new Pane();
		
		Image im = new Image(MenuSceneView.class.getResource("/layout/layout_pause.png").toExternalForm());
		ImageView iv = new ImageView();
		
		Font fontSmall = Font.loadFont("file:resources/font/minecraftia/Minecraftia-Regular.ttf", MainApplication.H * 0.03);
		
		EventHandler<Event> hover = new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				// TODO Auto-generated method stub
				sound.playSfx(SoundController.SFX_MENU_SELECT);
			}
			
		};
		
		Button resumeBtn = new Button("RESUME");
		Button menuBtn = new Button("MAIN MENU");
		Button exitBtn = new Button("EXIT GAME");
		
		resumeBtn.setPrefWidth(MainApplication.W * 0.3);
		menuBtn.setPrefWidth(MainApplication.W * 0.3);
		exitBtn.setPrefWidth(MainApplication.W * 0.3);
		
		resumeBtn.setOnMouseEntered(hover);
		menuBtn.setOnMouseEntered(hover);
		exitBtn.setOnMouseEntered(hover);
		
		resumeBtn.setFont(fontSmall);
		menuBtn.setFont(fontSmall);
		exitBtn.setFont(fontSmall);
		
		resumeBtn.setLayoutX(MainApplication.W * 0.35);
		menuBtn.setLayoutX(MainApplication.W * 0.35);
		exitBtn.setLayoutX(MainApplication.W * 0.35);
		
		resumeBtn.setLayoutY(MainApplication.H * 0.375);
		menuBtn.setLayoutY(MainApplication.H * 0.44);
		exitBtn.setLayoutY(MainApplication.H * 0.505);
		
		resumeBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				sound.playSfx(SoundController.SFX_MENU_CANCEL);
				pausePane.setVisible(false);
				onPause = false;
			}
		});
		
		menuBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				sound.playSfx(SoundController.SFX_MENU_CONFIRM);
				if (screenChanged == false) app.changeScene(MainApplication.MENU_SCENE);
	        	screenChanged = true;
			}
		});

		exitBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				sound.playSfx(SoundController.SFX_MENU_CONFIRM);
				Platform.exit();
			}
		});

		Pane btnPane = new Pane();
		btnPane.getChildren().addAll(resumeBtn, menuBtn, exitBtn);
		
		iv.setImage(im);
		iv.setPreserveRatio(true);
		iv.setFitWidth(MainApplication.W * 0.45);
		iv.setLayoutY(MainApplication.H * 0.05);
		iv.setLayoutX(MainApplication.W * 0.5 - iv.getFitWidth() * 0.55);
		iv.setSmooth(true);
		iv.setCache(true);
		
		layoutPane.getChildren().add(iv);
		this.pausePane.getChildren().add(layoutPane);
		this.pausePane.getChildren().add(btnPane);
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
		return super.getPointerX();
	}
	
	@Override
	public double getPointerY() {
		// TODO Auto-generated method stub
		return super.getPointerY();
	}

	@Override
	public void renderFrame() {
		// TODO Auto-generated method stub
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		map.render();
        barrier.render();

        for (EnemyController e : enemies) {
        	gc.setGlobalAlpha(1);
			e.renderShadow();
		}
        playerController.renderShadow();
        
        for (EnemyController e : enemies) {
        	gc.setGlobalAlpha(1);
			e.render();
		}
        
        epController.render();
        ppController.render();
        
        hud.render();
        
        if (playerController.isHasDied()) {
        	gc.setGlobalAlpha(0.7);
        	gc.setFill(Color.BLACK);
        	gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        	gc.setGlobalAlpha(1);
        }
        
        playerController.render();
        
    	if (this.deathFade == 0 && playerController.isHasDied()) {
    		this.deathFade = (int) (MainApplication.FPS * 4.0);
    	}
    	else if (this.deathFade > 0 && playerController.isHasDied()){
    		this.deathFade -= 1;
    		gc.setGlobalAlpha(-((this.deathFade - (MainApplication.FPS * 2.0))/ (MainApplication.FPS * 2.0)));
        	gc.setFill(Color.BLACK);
        	gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        	
        	if (this.deathFade == 0) {
        		this.deathFade = -1;
        	}
    	}
    	else if (this.deathFade == -1) {
    		gc.setGlobalAlpha(1);
        	gc.setFill(Color.BLACK);
        	gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        	
        	if (this.screenChanged == false) this.app.changeScene(MainApplication.MENU_SCENE);
        	this.screenChanged = true;
    	}
	}

	@Override
	public void updateFrame() {
		// TODO Auto-generated method stub
		if (onPause) return;
		playerController.update();
		hud.update();
		camera.update();
		
		
		if (!playerController.isHasDied()) {
			int i = 0;
			for (EnemyController e : enemies) {
				e.update();
			}
			
			ppController.update();
			epController.update();
		}
				
		if (this.waveDelay > 0 && this.enemiesLeft == 0) {
			this.waveDelay -= 1;
		}
		else if (this.waveDelay == 0 && this.enemiesLeft == 0) {
			this.activateEnemies(this.enemyBatch);
			System.out.println(this.enemyBatch);
			System.out.println(this.enemiesLeft);
			this.waveDelay = MainApplication.FPS * 2;
		}
	}

}
