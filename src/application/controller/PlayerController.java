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
		
		this.playerModel = new PlayerModel(MainApplication.mapWidth(50), MainApplication.mapHeight(50), 4);
		
		this.drawTick = 0;
		this.sprites = playerModel.getSprites(PlayerModel.IDLE, PlayerModel.FRONT);

	}
	
	public int getPlayerState() {
		return this.playerModel.getState();
	}
	
	public void setPlayerState(int s) {
		playerModel.setState(s);
	}
	
	public double getPlayerX() {
		return this.playerModel.getX();
	}
	
	public double getPlayerY() {
		return this.playerModel.getY();
	}
	
	public void drawPlayer() {
		int actionTick = 24;
		this.gc.setFill(Color.BLUE);
		this.sprites = this.playerModel.getSprites(this.playerModel.getState(), this.playerModel.getFacing());
		
		int i = (int)(drawTick++ / (int)(actionTick / this.sprites.getLen())) % this.sprites.getLen();
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
		
		if (ang >= 20 && ang < 65) {
			f = PlayerModel.BACK_LEFT;
		}
		else if (ang >= 65 && ang < 115) {
			f = PlayerModel.BACK;
		}
		else if (ang >= 115 && ang < 160) {
			f = PlayerModel.BACK_RIGHT;
		}
		else if (ang >= 160 && ang < 245) {
			f = PlayerModel.FRONT_RIGHT;
		}
		else if (ang >= 245 && ang < 295) {
			f = PlayerModel.FRONT;
		}
		else {
			f = PlayerModel.FRONT_LEFT;
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
		
		int n = 0;
		for (int v : this.playerModel.getVectors()) {
			n += v;
		}
		
		if (n == 0) {
			this.playerModel.setState(PlayerModel.IDLE);
		}
		else {
			this.playerModel.setState(PlayerModel.RUN);
		}
		
		double vX = (-this.getVectorLeft()) + 
				(this.getVectorRight());
		
		double vY = (-this.getVectorUp()) + 
				(this.getVectorDown());
		
		double mag = Math.sqrt(vX * vX + vY * vY);
		
		if (mag > 0) {
			vX /= mag;
			vY /= mag;
		}
		
		vX *= this.playerModel.getSpeed();
		vY *= this.playerModel.getSpeed();
		
		cX += vX;
		cY += vY;
		
		this.move(cX, cY);
	}

	@Override
	public void onCollide() {
		// TODO Auto-generated method stub
		
	}

	
}
