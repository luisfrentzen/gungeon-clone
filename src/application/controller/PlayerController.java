package application.controller;


import java.util.Arrays;
import java.util.Vector;

import javafx.geometry.Point2D;
import application.MainApplication;
import application.model.PlayerModel;
import application.model.SpriteModel;
import application.model.VFXModel;
import application.view.GameSceneView;
import application.view.PlayerView;
import application.view.SceneView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

public class PlayerController extends CharacterController{
	
	private GraphicsContext gc;
	private Canvas canvas;
	private SceneView scene;
	
	private PlayerProjectileController ppController;
	
	private PlayerModel playerModel;
	private PlayerView playerView;
	
	private SpriteModel sprites;
	private SpriteModel hand;
	private SpriteModel pistol;
	private VFXModel flare;
	
	private Vector<VFXModel> vfxRender;
	
	private int drawTick;
	private int globalTick;
	
	private int[] currentVector;
	private double currentAngle;
	
	private double shootX;
	private double shootY;
	
	private double handX;
	private double handY;
	
	public PlayerController(Canvas canvas, SceneView scene, PlayerProjectileController ppController) {
		// TODO Auto-generated constructor stub
		this.canvas = canvas;
		this.scene = scene;
		this.ppController = ppController;
		this.gc = canvas.getGraphicsContext2D();
		
//		this.playerModel = new PlayerModel(MainApplication.mapWidth(50), MainApplication.mapHeight(50), 1);
		this.playerModel = new PlayerModel(canvas.getWidth() / 2, canvas.getHeight() / 2, 4);
		
		this.drawTick = 0;
		this.globalTick = 0;
		this.sprites = playerModel.getSprites(PlayerModel.IDLE, PlayerModel.FRONT);
		this.hand = playerModel.getHand();
		this.pistol = playerModel.getGunSprites(PlayerModel.GUN_IDLE);
		this.flare = new VFXModel(PlayerModel.PATH_FLARE, playerModel.getScale() * 0.65, 0, 0);
		this.flare.setDone(true);
		
		this.currentVector = playerModel.getVectors();
		
		vfxRender = new Vector<VFXModel>();

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
	
	public void addVFX(String vfx, int x, int y) {
		vfxRender.add(new VFXModel(PlayerModel.PATH_FLARE, playerModel.getScale(), 100, 100));
	}
	
	public void drawVFX() {
		for (VFXModel fx : this.vfxRender) {
			if (!fx.isDone()) {
				this.gc.drawImage(fx.getNext(), fx.getX(), fx.getY(), fx.getWidth(fx.getNFrame() - 1), fx.getHeight(fx.getNFrame() - 1));
			}
		}
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
		double angOffset = 20;
		
		if (this.getPlayerAngle() < 90 || this.getPlayerAngle() > 270) {
			pw = -pw;
			w = -w;
		}
		else {
			ang -= 180;
			angOffset *= -1;
		}
		
		this.handX = playerModel.getX() + pw * (35/100.0);
		this.handY = playerModel.getY() + ph * (30/100.0);
		
		double centerX = playerModel.getX() + pw * (21.0/100.0);
		double centerY = playerModel.getY() + ph * (5/100.0);
				
		Image p = this.pistol.get(0);
		
		this.gc.save();
		Rotate r = new Rotate(ang, this.handX, this.handY);
		gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
		
		this.gc.drawImage(p, centerX, centerY, w, h);
		
		if (!this.flare.isDone()) {
			this.gc.drawImage(this.flare.getNext(), centerX + w, centerY + (h * 0.3) - this.flare.getHeight(this.flare.getNFrame() - 1) / 2, (this.getPlayerAngle() < 90 || this.getPlayerAngle() > 270) ? -this.flare.getWidth(this.flare.getNFrame()-1) : this.flare.getWidth(this.flare.getNFrame()-1), this.flare.getHeight(this.flare.getNFrame()-1));
		}
		
		this.gc.restore();
		
		double pointX = this.handX + w * 0.8;
		double pointY = this.handY - h * 0.4;
		
		Point2D rotated = getRotated(ang, pointX, pointY, handX, handY);
		
		this.shootX = rotated.getX();
		this.shootY = rotated.getY();
		
//		this.gc.setFill(Color.RED);
//		this.gc.fillOval(this.shootX - 5, this.shootY - 5, 10, 10);
//		this.gc.setFill(Color.BLUE);
//		this.gc.fillOval(handX - 5, handY - 5, 10, 10);
	}
	
	public Point2D getRotated(double ang, double pointX, double pointY, double pivotX, double pivotY) {
		double newX = pivotX + (pointX - pivotX) * Math.cos(Math.toRadians(ang)) - (pointY - pivotY)* Math.sin(Math.toRadians(ang));
		double newY = pivotY + (pointX - pivotX) * Math.sin(Math.toRadians(ang)) + (pointY - pivotY)* Math.cos(Math.toRadians(ang));
		return new Point2D(newX, newY);
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
		if (this.getPlayerState() != PlayerModel.DODGE) {
			double ang = playerModel.getAngle();
			double w = this.pistol.getWidth(0);
			double h = this.pistol.getHeight(0);
			
			this.flare.reset();
			this.flare.setX(this.shootX);
			this.flare.setY(this.shootY);
			
			if (this.getPlayerAngle() < 90 || this.getPlayerAngle() > 270) {
				w = -w;
			}
			else {
				ang -= 180;
			}
			
			double pointX = this.handX + w;
			double pointY = this.handY - h * 0.4;
			
			Point2D rresult = this.getRotated(ang, pointX, pointY, this.handX, this.handY);

			this.ppController.shootBullet(this.shootX, this.shootY, rresult.getX(), rresult.getY());			
		}
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
			this.drawHand();
			this.drawPistol();
		}
		
		if (this.playerModel.getMagSize() == 0 && (this.globalTick / 16) % 2 == 0) {
			this.gc.setFill(Color.WHITE);
			this.gc.fillText("reload", this.getPlayerX() - (this.sprites.getWidth(0) * 0.34), this.getPlayerY() - this.sprites.getHeight(0) * 0.35);
		}
		
		this.drawVFX();
		this.drawPlayer();
	}
	
	public int getVectorSum() {
		return Math.abs(this.getCVectorDown() - this.getCVectorUp()) + Math.abs(this.getCVectorLeft() - this.getCVectorRight());
	}
	
	public void doReload() {
		this.playerModel.setMagSize(this.playerModel.getMagCap());
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		this.globalTick++;
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
		
		if (this.playerModel.getGunDownTime() > 0) {
			this.playerModel.setGunDownTime(this.playerModel.getGunDownTime() - 1);
		}
		
		cX += vX;
		cY += vY;
		
		if (((GameSceneView) this.scene).ismPrimaryDown() && this.playerModel.getGunDownTime() == 0 && this.playerModel.getMagSize() > 0) {
			this.doShoot();
			this.playerModel.setGunDownTime(12);
			this.playerModel.setMagSize(this.playerModel.getMagSize() - 1);
		}
		
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
