package application.controller;

import application.model.PlayerModel;
import application.view.PlayerView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PlayerController extends CharacterController{
	
	private GraphicsContext gc;
	private Canvas canvas;
	
	private PlayerModel playerModel;
	private PlayerView playerView;
	
	public PlayerController(Canvas canvas) {
		// TODO Auto-generated constructor stub
		this.canvas = canvas;
		this.gc = canvas.getGraphicsContext2D();
		
		this.playerModel = new PlayerModel(this.canvas.getWidth() / 2, this.canvas.getHeight() / 2);
	}
	
	public void drawPlayer() {
		this.gc.setFill(Color.BLUE);
		
		double centerX = playerModel.getX() - playerModel.getW() / 2;
		double centerY = playerModel.getY() - playerModel.getH() / 2;
		
		this.gc.fillRect(centerX, centerY, playerModel.getW(), playerModel.getH());
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
		
//		System.out.println(vX + " " + vY);
//		System.out.println(this.playerModel.getX() + " " + this.playerModel.getY());
		
//		System.out.println(this.playerModel.getVectors()[0] + " " +
//				this.playerModel.getVectors()[1] + " " +
//				this.playerModel.getVectors()[2] + " " +
//				this.playerModel.getVectors()[3]);
	}

	@Override
	public void onCollide() {
		// TODO Auto-generated method stub
		
	}

	
}
