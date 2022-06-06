package application.controller;


import application.MainApplication;
import application.model.PlayerModel;
import application.model.SpriteModel;
import application.view.PlayerView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class PlayerController extends CharacterController{
	
	private GraphicsContext gc;
	private Canvas canvas;
	
	private PlayerModel playerModel;
	private PlayerView playerView;
	
	private SpriteModel sprites;
	
	private int drawTick;
	
	public PlayerController(Canvas canvas) {
		// TODO Auto-generated constructor stub
		this.canvas = canvas;
		this.gc = canvas.getGraphicsContext2D();
		
		this.playerModel = new PlayerModel(MainApplication.mapWidth(50), MainApplication.mapHeight(50), 5);
		
		this.drawTick = 0;
		this.sprites = playerModel.getFrontIdleSprites();

	}
	
	public double getPlayerX() {
		return this.playerModel.getX();
	}
	
	public double getPlayerY() {
		return this.playerModel.getY();
	}
	
	public void drawPlayer() {
		this.gc.setFill(Color.BLUE);
		
		int i = (int)(drawTick++ / 8) % 4;
		double h = this.sprites.getHeight(i);
		double w = this.sprites.getWidth(i);
		
		if (this.getPlayerFacing() == PlayerModel.BACK_LEFT || this.getPlayerFacing() == PlayerModel.FRONT_LEFT) {
			w = -w;
		}
		
		Image p = this.sprites.get(i);
		double centerX = playerModel.getX() - w / 2;
		double centerY = playerModel.getY() - h / 2;
		
//		System.out.println(w / 3 + " " + h / 3);

		this.gc.drawImage(p, centerX, centerY, w, h);			
	}
	
	public void updatePlayerFacing(double ang) {
		int f = 0;
		SpriteModel s = null;
		
		if (ang >= 45 && ang < 135) {
			f = PlayerModel.BACK;
			s = playerModel.getBackIdleSprites();
		}
		else if (ang >= 135 && ang < 180) {
			f = PlayerModel.BACK_RIGHT;
			s = playerModel.getBackRightIdleSprites();
		}
		else if (ang >= 180 && ang < 225) {
			f = PlayerModel.FRONT_RIGHT;
			s = playerModel.getFrontRightIdleSprites();
		}
		else if (ang >= 225 && ang < 315) {
			f = PlayerModel.FRONT;
			s = playerModel.getFrontIdleSprites();
		}
		else if (ang >= 315){
			f = PlayerModel.FRONT_LEFT;
			s = playerModel.getFrontRightIdleSprites();
		}
		else {
			f = PlayerModel.BACK_LEFT;
			s = playerModel.getBackRightIdleSprites();
		}
		
		if (f != this.getPlayerFacing()) {
			this.sprites = s;
		}
		
		this.setPlayerFacing(f);
	}
	
	public void setPlayerFacing(int f) {
		this.playerModel.setFacing(f);
	}
	
	public int getPlayerFacing() {
		return this.playerModel.getFacing();
	}
	
	public void setPlayerAngle(double ang) {
		this.playerModel.setAngle(ang);
	}
	
	public double getPlayerAngle(int a) {
		return this.playerModel.getAngle();
	}
	
	//[UP, LEFT, DOWN, RIGHT]
	public int getVectorUp() {
		return this.playerModel.getVectors()[0];
	}
	
	public int getVectorLeft() {
		return this.playerModel.getVectors()[1];
	}
	
	public int getVectorDown() {
		return this.playerModel.getVectors()[2];
	}
	
	public int getVectorRight() {
		return this.playerModel.getVectors()[3];
	}
	
	public void setVectorUp(int v) {
		// TODO Auto-generated method stub
		this.playerModel.setVectorUp(v);
	}
	
	public void setVectorLeft(int v) {
		// TODO Auto-generated method stub
		this.playerModel.setVectorLeft(v);
	}
	
	public void setVectorDown(int v) {
		// TODO Auto-generated method stub
		this.playerModel.setVectorDown(v);
	}
	
	public void setVectorRight(int v) {
		// TODO Auto-generated method stub
		this.playerModel.setVectorRight(v);
	}

	@Override
	public void move(double x, double y) {
		// TODO Auto-generated method stub
		this.playerModel.setX(x);
		this.playerModel.setY(y);
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		this.drawPlayer();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		double cX = this.playerModel.getX();
		double cY = this.playerModel.getY();
		
		double vX = (this.getVectorLeft() * -this.playerModel.getSpeed()) + 
				(this.getVectorRight() * this.playerModel.getSpeed());
		
		double vY = (this.getVectorUp() * -this.playerModel.getSpeed()) + 
				(this.getVectorDown() * this.playerModel.getSpeed());
		
		cX += vX;
		cY += vY;
		
		this.move(cX, cY);
	}

	@Override
	public void onCollide() {
		// TODO Auto-generated method stub
		
	}

	
}
