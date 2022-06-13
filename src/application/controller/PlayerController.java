package application.controller;


import application.MainApplication;
import application.model.PlayerModel;
import application.model.SpriteModel;
import application.view.PlayerView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

public class PlayerController extends CharacterController{
	
	private GraphicsContext gc;
	private Canvas canvas;
	
	private PlayerModel playerModel;
	private PlayerView playerView;
	
	private SpriteModel sprites;
	private SpriteModel hand;
	private SpriteModel pistol;
	
	private int drawTick;
	
	public PlayerController(Canvas canvas) {
		// TODO Auto-generated constructor stub
		this.canvas = canvas;
		this.gc = canvas.getGraphicsContext2D();
		
//		this.playerModel = new PlayerModel(MainApplication.mapWidth(50), MainApplication.mapHeight(50), 1);
		this.playerModel = new PlayerModel(canvas.getWidth() / 2, canvas.getHeight() / 2, 4);
		
		this.drawTick = 0;
		this.sprites = playerModel.getSprites(PlayerModel.IDLE, PlayerModel.FRONT);
		this.hand = playerModel.getHand();
		this.pistol = playerModel.getPistol();

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
		this.sprites = this.playerModel.getSprites(this.playerModel.getState(), this.playerModel.getFacing());
		
		int i = (int)(drawTick++ / (int)(actionTick / this.sprites.getLen())) % this.sprites.getLen();
		double h = this.sprites.getHeight(i);
		double w = this.sprites.getWidth(i);
		
		if (this.getPlayerAngle() < 90 || this.getPlayerAngle() > 270) {
			w = -w;
		}
		
		Image p = this.sprites.get(i);
		double centerX = playerModel.getX() - w / 2;
		double centerY = playerModel.getY() - h / 2;
		
//		System.out.println(p.getHeight());

		this.gc.drawImage(p, ((int)centerX) + .5, ((int)centerY) + .5, w, h);			
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
		if (this.getPlayerState() == PlayerModel.RUN) {
			this.playerModel.setDodgeFrame(this.playerModel.getSprites(PlayerModel.DODGE, PlayerModel.FRONT).getLen() * 2);
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
			this.setPlayerState(PlayerModel.IDLE);
		}
		else {
			if (this.playerModel.getDodgeFrame() > 0) {
				this.setPlayerState(PlayerModel.DODGE);
				this.playerModel.setDodgeFrame(this.playerModel.getDodgeFrame() - 1);
			}
			else {
				this.setPlayerState(PlayerModel.RUN);				
			}
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
	public void setPlayerFacing(int f) {
		this.playerModel.setFacing(f);
	}
	
	public int getPlayerFacing() {
		return this.playerModel.getFacing();
	}
	
	public void setPlayerAngle(double ang) {
		this.playerModel.setAngle(ang);
	}
	
	public double getPlayerAngle() {
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


	
}
