package application.controller;

import java.util.Vector;

import application.MainApplication;
import application.model.EnemyModel;
import application.model.PlayerModel;
import application.model.VFXModel;
import application.view.GameSceneView;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

public class EnemyController extends CharacterController{
	private EnemyModel model;
	private PlayerController player;
	
	public EnemyController(double x, double y, Canvas canvas, CameraController camera, BarrierController barrier, PlayerController player) {
		this.canvas = canvas;
		this.gc = canvas.getGraphicsContext2D();
		this.barrier = barrier;
		this.camera = camera;
		this.player = player;
		
		this.model = new EnemyModel(x, y, 4);
		
		this.drawTick = 0;
		this.globalTick = 0;
		
		this.sprites = model.getSprites(EnemyModel.IDLE, EnemyModel.FRONT_RIGHT);
		this.hand = model.getHand();
		this.pistol = model.getGunSprites(EnemyModel.GUN_IDLE);
		this.flare = new VFXModel(EnemyModel.PATH_FLARE, model.getScale() * 0.8, 0, 0);
		this.flare.setDone(true);
		
		this.currentVector = model.getVectors();
		
		this.vfxRender = new Vector<VFXModel>();
	}
	
	public int getState() {
		return this.model.getState();
	}
	
	public void setState(int s) {
		if (this.getState() == s) {
			return;
		}
		
		this.drawTick = 0;
		model.setState(s);
	}
	
	public double getModelX() {
		return this.model.getX();
	}
	
	public double getModelY() {
		return this.model.getY();
	}

	@Override
	public void move(double x, double y) {
		// TODO Auto-generated method stub
		this.setModelX(x);
		this.setModelY(y);
	}
	
	public void setModelX(double x) {
		double off = sprites.getWidth(0) / 2;
		if (x - off < this.barrier.getMinX()) {
			x = this.barrier.getMinX() + off;
		}
		else if (x + off > this.barrier.getMaxX()) {
			x = this.barrier.getMaxX() - off;
		}
		
		this.model.setX(x);
	}
	
	public void setModelY(double y) {
		double off = sprites.getHeight(0) / 2;
		if (y - off < this.barrier.getMinY()) {
			y = this.barrier.getMinY() + off;
		}
		else if (y + off > this.barrier.getMaxY()) {
			y = this.barrier.getMaxY() - off;
		}
		
		this.model.setY(y);
	}
	
	public void drawShadow() {
		double h = this.sprites.getHeight(0);
		double w = this.sprites.getWidth(0);
		
		this.gc.save();
		this.gc.setGlobalAlpha(0.6);
		this.gc.setFill(Color.BLACK);
		this.gc.fillOval(this.camera.getXMapRelative(this.getModelX()) - w * 0.25, this.camera.getYMapRelative(this.getModelY()) + h * 0.45, w * 0.5, h / 8);
		this.gc.restore();
	}
	
	public void setModelFacing(int f) {
		this.model.setFacing(f);
	}
	
	public int getModelFacing() {
		return this.model.getFacing();
	}
	
	public void setModelAngle(double ang) {
		this.model.setAngle(ang);
	}
	
	public double getModelAngle() {
		return this.model.getAngle();			
	}
	
	public void drawModel() {
		int actionTick = 24;
		this.sprites = this.model.getSprites(this.getState(), this.getModelFacing());
		
		double tpf = (double)actionTick / (double)this.sprites.getLen();
		int i = (int)(Math.floor(drawTick++ % actionTick) / tpf);
		
		double h = this.sprites.getHeight(i);
		double w = this.sprites.getWidth(i);
		this.model.setW(w * 0.5);
		this.model.setH(h * 0.5);
		
		if (this.isFlip()) {
			w = -w;
		}		
		
		Image p = this.sprites.get(i);
		double centerX = model.getX() - w / 2;
		double centerY = model.getY() - h / 2;
		
		this.camera.draw(this.gc, p, ((int)centerX) + .5, ((int)centerY) + .5, w, h);
		
		this.gc.setFill(Color.RED);
		this.gc.fillOval(this.camera.getXMapRelative(model.getX()) - 5, this.camera.getYMapRelative(model.getY()) - 5, 10, 10);
		this.gc.strokeRect(this.camera.getXMapRelative(this.model.getBoundX()), this.camera.getYMapRelative(this.model.getBoundY()), this.model.getW(), this.model.getH());
	}
	
	public void drawHand() {
		double h = this.hand.getHeight(0);
		double w = this.hand.getWidth(0);
		
		double ph = this.sprites.getHeight(0);
		double pw = this.sprites.getWidth(0);
		
		if (this.getModelAngle() < 90 || this.getModelAngle() > 270) {
			pw = -pw;
			w = -w;
		}
//
//		double centerX = model.getX() + pw * (40.0/100.0);
//		double centerY = model.getY() + ph * (22.0/100.0);
		
		Image p = this.hand.get(0);
		
		this.camera.draw(this.gc, p, handX - w / 2, handY - h / 2, w, h);
	}
	
	public void drawPistol() {
		double h = this.pistol.getHeight(0);
		double w = this.pistol.getWidth(0);
		
		double ph = this.sprites.getHeight(0);
		double pw = this.sprites.getWidth(0);
		
		double ang = this.getModelAngle();
		
		if (this.getModelAngle() < 90 || this.getModelAngle() > 270) {
			pw = -pw;
			w = -w;
		}
		else {
			ang -= 180;
		}
		
		this.handX = model.getX() + pw * (65.0/100.0);
		this.handY = model.getY() + ph * (30.0/100.0);
		
		double centerX = model.getX() + pw * (56.0/100.0);
		double centerY = model.getY() - ph * (10.0/100.0);
		
		Image p;
		
		if (this.pistol.getLen() == 1) {
			p = this.pistol.get(0);			
		}
		else {
			p = this.pistol.getNext();
			if (this.pistol.isDone()) {
				this.pistol = model.getGunSprites(EnemyModel.GUN_IDLE);
			}
		}
		
		this.gc.save();
		Rotate r = new Rotate(ang, this.camera.getXMapRelative(this.handX), this.camera.getYMapRelative(this.handY));
		gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
		
		this.camera.draw(this.gc, p, centerX, centerY, w, h);
		
		if (!this.flare.isDone()) {
			this.camera.draw(this.gc, this.flare.getNext(), centerX + w, centerY + (h * 0.3) - this.flare.getHeight(this.flare.getNFrame() - 1) / 2, (this.getModelAngle() < 90 || this.getModelAngle() > 270) ? -this.flare.getWidth(this.flare.getNFrame()-1) : this.flare.getWidth(this.flare.getNFrame()-1), this.flare.getHeight(this.flare.getNFrame()-1));
		}
		
		this.gc.restore();
		
		double pointX = this.handX + (w * 0.8);
		double pointY = this.handY - (h * 0.4);
		
		Point2D rotated = this.getRotated(ang, pointX, pointY, this.handX, this.handY);
		System.out.println(this.camera.getXMapRelative(this.handY));
		
		this.gc.setFill(Color.RED);
		this.gc.fillOval(this.camera.getXMapRelative(this.handX - 5), this.camera.getYMapRelative(this.handY - 5), 10, 10);
		
		this.shootX = rotated.getX();
		this.shootY = rotated.getY();
	}
	
	public void updateModelFacing(double ang) {
		int f = 0;
		
		if (ang >= 20 && ang < 90) {
			f = EnemyModel.BACK_LEFT;
		}
		else if (ang >= 90 && ang < 160) {
			f = EnemyModel.BACK_RIGHT;
		}
		else if (ang >= 160 && ang < 270) {
			f = EnemyModel.FRONT_RIGHT;
		}
		else {
			f = EnemyModel.FRONT_LEFT;
		}
		
		this.setModelFacing(f);
		System.out.println(this.getModelFacing());
	}
	
	public boolean isFlip() {
		return (this.getModelAngle() < 90 || this.getModelAngle() > 270);			
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		this.drawVFX();
		
		this.drawShadow();
		this.drawPistol();
		
		this.drawModel();
		
		this.drawHand();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		this.globalTick++;
		double cX = this.model.getX();
		double cY = this.model.getY();
		
		double dX = this.player.getPlayerX() - this.getModelX();
		double dY = this.player.getPlayerY() - this.getModelY();
	
		double ang = (Math.atan2(dY, dX) * 180 / Math.PI) + 180;
		
		this.setModelAngle(ang);
		this.updateModelFacing(ang);
		
//		this.zeroVector();
//		if (dX < 0) {
//			this.setVectorLeft(1);
//		}
//		else {
//			this.setVectorRight(1);
//		}
//		
//		if (dY < 0) {
//			this.setVectorUp(1); 
//		}
//		else {
//			this.setVectorDown(1);
//		}
//		
//		
//		double vX = (-this.getCVectorLeft()) + 
//				(this.getCVectorRight());
//		
//		double vY = (-this.getCVectorUp()) + 
//				(this.getCVectorDown());
//		
//		double mag = Math.sqrt(vX * vX + vY * vY);
//		
//		if (mag > 0) {
//			vX /= mag;
//			vY /= mag;
//		}
		
		double mag = Math.sqrt(dX * dX + dY * dY);
		
		double vX = dX / mag;
		double vY = dY / mag;
		
		vX *= this.model.getSpeed();
		vY *= this.model.getSpeed();
		
		if (vX == 0 && vY == 0) {
			this.setState(EnemyModel.IDLE);
		}
		else {
			this.setState(EnemyModel.RUN);				
		}
		
		
		if (this.model.getGunDownTime() > 0) {
			this.model.setGunDownTime(this.model.getGunDownTime() - 1);
		}
		
		cX += vX;
		cY += vY;
		
		if (this.model.getGunDownTime() == 0 && this.model.getMagSize() > 0) {
//			this.doShoot();
			this.model.setGunDownTime(12);
			this.model.setMagSize(this.model.getMagSize() - 1);
		}
		
		this.move(cX, cY);
	}

	@Override
	public void onCollide() {
		// TODO Auto-generated method stub
		
	}
	
	public int getVectorSum() {
		return Math.abs(this.getCVectorDown() - this.getCVectorUp()) + Math.abs(this.getCVectorLeft() - this.getCVectorRight());
	}

	//[UP, LEFT, DOWN, RIGHT]
	public int getVectorUp() {
		return this.model.getVectors()[0];
	}
	
	public int getVectorLeft() {
		return this.model.getVectors()[1];
	}
	
	public int getVectorDown() {
		return this.model.getVectors()[2];
	}
	
	public int getVectorRight() {
		return this.model.getVectors()[3];
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

	public void zeroVector() {
		this.setVectorUp(0);
		this.setVectorDown(0);
		this.setVectorLeft(0);
		this.setVectorRight(0);
	}
	
	public void setVectorUp(int v) {
		// TODO Auto-generated method stub
		this.model.setVectorUp(v);
	}
	
	public void setVectorLeft(int v) {
		// TODO Auto-generated method stub
		this.model.setVectorLeft(v);
	}
	
	public void setVectorDown(int v) {
		// TODO Auto-generated method stub
		this.model.setVectorDown(v);
	}
	
	public void setVectorRight(int v) {
		// TODO Auto-generated method stub
		this.model.setVectorRight(v);
	}
}