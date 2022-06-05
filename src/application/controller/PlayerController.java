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
		
		this.playerModel = new PlayerModel(MainApplication.mapWidth(50), MainApplication.mapHeight(50), 3);
		
		this.drawTick = 0;
		this.sprites = playerModel.getIdleSprites();

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
		
		Image p = this.sprites.get(i);
		double centerX = playerModel.getX() - w / 2;
		double centerY = playerModel.getY() - h / 2;
		
		this.gc.drawImage(p, centerX, centerY, w, h);
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
