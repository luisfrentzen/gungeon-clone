package application.controller;


import java.awt.MouseInfo;
import java.util.Arrays;

import application.model.PlayerModel;
import application.model.SpriteModel;
import application.view.PlayerView;
import application.view.SceneView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

public class PlayerController extends CharacterController{
	
	private GraphicsContext gc;
	private Canvas canvas;
	private SceneView scene;
	
	private PlayerModel playerModel;
	private PlayerView playerView;
	
	private SpriteModel sprites;
	private SpriteModel hand;
	private SpriteModel pistol;
	
	private int drawTick;
	private int[] currentVector;
	private double currentAngle;
	
	public PlayerController(Canvas canvas, SceneView scene) {
		// TODO Auto-generated constructor stub
		this.canvas = canvas;
		this.scene = scene;
		this.gc = canvas.getGraphicsContext2D();
		
//		this.playerModel = new PlayerModel(MainApplication.mapWidth(50), MainApplication.mapHeight(50), 1);
		this.playerModel = new PlayerModel(canvas.getWidth() / 2, canvas.getHeight() / 2, 4);
		
		this.drawTick = 0;
		this.sprites = playerModel.getSprites(PlayerModel.IDLE, PlayerModel.FRONT);
		this.hand = playerModel.getHand();
		this.pistol = playerModel.getPistol();
		
		this.currentVector = playerModel.getVectors();

	}
	
	public int getPlayerState() {
		return this.playerModel.getState();
	}
	
	public void setPlayerState(int s) {
		if (this.getPlayerState() == s) {
			return;
		}
		
		this.drawTick = 0;
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
		this.sprites = this.playerModel.getSprites(this.getPlayerState(), this.getPlayerFacing());
		
		double tpf = (double)actionTick / (double)this.sprites.getLen();
//		int i = (int)(drawTick++ / 6 % this.sprites.getLen());
		int i = (int)(Math.floor(drawTick++ % actionTick) / tpf);
		
		double h = this.sprites.getHeight(i);
		double w = this.sprites.getWidth(i);
		
		if (this.isFlip()) {
			w = -w;
		}
		
		Image p = this.sprites.get(i);
		double centerX = playerModel.getX() - w / 2;
		double centerY = playerModel.getY() - h / 2;
		
		this.gc.drawImage(p, ((int)centerX) + .5, ((int)centerY) + .5, w, h);
	}
	
	public boolean isFlip() {
		if (this.getPlayerState() == PlayerModel.DODGE) {
			return this.getPlayerFacing() > 2;
		}
		else {
			return (this.getPlayerAngle() < 90 || this.getPlayerAngle() > 270);			
		}
	}
	
	public void drawHand() {
		double h = this.hand.getHeight(0);
		double w = this.hand.getWidth(0);
		
		double ph = this.sprites.getHeight(0);
		double pw = this.sprites.getWidth(0);
		
		if (this.getPlayerAngle() < 90 || this.getPlayerAngle() > 270) {
			pw = -pw;
			w = -w;
		}

		double centerX = playerModel.getX() + pw * (25/100.0);
		double centerY = playerModel.getY() + ph * (22.0/100.0);
		
		Image p = this.hand.get(0);
		
		this.gc.drawImage(p, centerX, centerY, w, h);
	}
	
	public void drawPistol() {
		double h = this.pistol.getHeight(0);
		double w = this.pistol.getWidth(0);
		
		double ph = this.sprites.getHeight(0);
		double pw = this.sprites.getWidth(0);
		
		double ang = this.getPlayerAngle();
		
		if (this.getPlayerAngle() < 90 || this.getPlayerAngle() > 270) {
			pw = -pw;
			w = -w;
		}
		else {
			ang -= 180;
		}
		
		double handX = playerModel.getX() + pw * (35/100.0);
		double handY = playerModel.getY() + ph * (30/100.0);
		
		double centerX = playerModel.getX() + pw * (21.0/100.0);
		double centerY = playerModel.getY() + ph * (5/100.0);
				
		Image p = this.pistol.get(0);
		
		this.gc.save();
		Rotate r = new Rotate(ang, handX, handY);
		gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
		
		this.gc.drawImage(p, centerX, centerY, w, h);
		
		this.gc.restore();
	}
	
	public void doDodge() {
		if (this.getPlayerState() == PlayerModel.RUN && this.getVectorSum() > 0) {
			this.currentAngle = this.getPlayerAngle();
			this.setPlayerState(PlayerModel.DODGE);
			this.hideGun();
			this.currentVector = Arrays.copyOf(this.playerModel.getVectors(), this.playerModel.getVectors().length);
			this.playerModel.setDodgeFrame(24);
		}
	}
	
	public int getDodgeDir() {
		int vX = this.getCVectorRight() - this.getCVectorLeft();
		int vY = this.getCVectorDown() - this.getCVectorUp();
		
		if (vX > 0 && vY > 0) {
			return PlayerModel.FRONT_RIGHT;
		}
		else if (vX < 0 && vY < 0) {
			return PlayerModel.BACK_LEFT;
		}
		else if (vX > 0 && vY < 0) {
			return PlayerModel.BACK_RIGHT;
		}
		else if (vX < 0 && vY > 0) {
			return PlayerModel.FRONT_LEFT;
		}
		else if (vX > 0 && vY == 0) {
			return PlayerModel.FRONT_RIGHT;
		}
		else if (vX < 0 && vY == 0) {
			return PlayerModel.FRONT_LEFT;
		}
		else if (vX == 0 && vY > 0) {
			return PlayerModel.FRONT;
		}
		else {
			return PlayerModel.BACK;
		}
	}
	
	public void updatePlayerFacing(double ang) {
		int f = 0;
		
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
	
	public void hideGun() {
		this.playerModel.setShowGun(false);
	}
	
	public void unhideGun() {
		this.playerModel.setShowGun(true);
	}
	
	public void doShoot() {
		System.out.println("a");
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
		if (this.playerModel.isShowGun()) {
			this.drawPistol();
			this.drawHand();
		}
		
		this.drawPlayer();
	}
	
	public int getVectorSum() {
		return Math.abs(this.getCVectorDown() - this.getCVectorUp()) + Math.abs(this.getCVectorLeft() - this.getCVectorRight());
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		double cX = this.playerModel.getX();
		double cY = this.playerModel.getY();
		
		double dX = this.scene.getPointerX() - this.getPlayerX();
		double dY = this.scene.getPointerY() - this.getPlayerY();
		
		double ang = (Math.atan2(dY, dX) * 180 / Math.PI) + 180;
		
		this.setPlayerAngle(ang);
		this.updatePlayerFacing(ang);
		
		if (this.playerModel.getDodgeFrame() > 0) {
			this.playerModel.setDodgeFrame(this.playerModel.getDodgeFrame() - 1);
			
			if (this.playerModel.getDodgeFrame() == 0) {
				this.setPlayerState(PlayerModel.IDLE);
				this.resetVector();
				this.unhideGun();
			}
		}
		else {
			if (this.getVectorSum() == 0) {
				this.setPlayerState(PlayerModel.IDLE);
			}
			else {
				this.setPlayerState(PlayerModel.RUN);				
			}
		}
		
		
		double vX = (-this.getCVectorLeft()) + 
				(this.getCVectorRight());
		
		double vY = (-this.getCVectorUp()) + 
				(this.getCVectorDown());
		
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
	public void setPlayerFacing(int f) {
		this.playerModel.setFacing(f);
	}
	
	public int getPlayerFacing() {
		if (this.getPlayerState() == PlayerModel.DODGE) {
			return this.getDodgeDir();
		}
		else {
			return this.playerModel.getFacing();
		}
	}
	
	public void setPlayerAngle(double ang) {
		this.playerModel.setAngle(ang);
	}
	
	public double getPlayerAngle() {
		if (this.getPlayerState() == PlayerModel.DODGE) {
			return this.currentAngle;
		}
		else {
			return this.playerModel.getAngle();			
		}
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
	
	public int getCVectorUp() {
		return this.currentVector[0];
	}
	
	public int getCVectorLeft() {
		return this.currentVector[1];
	}
	
	public int getCVectorDown() {
		return this.currentVector[2];
	}
	
	public int getCVectorRight() {
		return this.currentVector[3];
	}
	
	public void resetVector() {
		this.currentVector = playerModel.getVectors();
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


	
}
