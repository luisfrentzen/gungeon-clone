package application.controller;

import java.util.Vector;

import application.MainApplication;
import application.model.EnemyModel;
import application.model.PlayerModel;
import application.model.SpriteModel;
import application.model.VFXModel;
import application.view.GameSceneView;
import application.view.SceneView;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

public class EnemyController extends CharacterController {
	private EnemyProjectileController bullet;
	private EnemyModel model;
	private PlayerController player;
	private int[] pistolAngle;
	private int angleIndex;

	private double vX;
	private double vY;

	private SpriteModel hitFrame;
	private SpriteModel deathFrame;
	private boolean isActive;
	private int deathLinger;
	
	private double lastX;
	private double lastY;

	public EnemyController(double x, double y, EnemyProjectileController bullet, Canvas canvas, CameraController camera,
			BarrierController barrier, PlayerController player, SceneView scene, SoundController sound) {
		this.canvas = canvas;
		this.gc = canvas.getGraphicsContext2D();
		this.barrier = barrier;
		this.camera = camera;
		this.player = player;
		this.bullet = bullet;
		this.pistolAngle = new int[] { 64, 8 };
		this.angleIndex = -1;
		this.deathLinger = MainApplication.FPS * 4;
		this.sound = sound;

		this.model = new EnemyModel(x, y, 4);
		this.scene = scene;

		this.drawTick = 0;
		this.globalTick = 0;
		this.lastX = x;
		this.lastY = y;

		this.sprites = model.getSprites(EnemyModel.IDLE, EnemyModel.FRONT_RIGHT);
		this.hand = model.getHand();
		this.pistol = model.getGunSprites(EnemyModel.GUN_IDLE);
		this.flare = new VFXModel(EnemyModel.PATH_FLARE, model.getScale() * 0.8, 0, 0);
		this.flare.setDone(true);

		this.currentVector = model.getVectors();

		this.vfxRender = new Vector<VFXModel>();
		this.hitFrame = model.getSprites(EnemyModel.HIT, this.getModelFacing());
		this.hitFrame.setDone(true);

		this.deathFrame = model.getSprites(EnemyModel.DEATH, this.getModelFacing());
		this.deathFrame.setDone(true);

		this.colorHit = new ColorAdjust();
		this.colorHit.setBrightness(1);

		this.colorDead = new ColorAdjust();
		this.colorDead.setBrightness(-0.5);
		this.hasDied = false;
		this.isActive = false;
	}
	
	public void reset() {
		this.model.reset();
		
		this.model.setX(((GameSceneView)scene).getMapW() * 0.1 + Math.random() * (((GameSceneView)scene).getMapW() * 0.8));
		this.model.setY(((GameSceneView)scene).getMapH() * 0.1 + Math.random() * (((GameSceneView)scene).getMapH() * 0.8));
		this.lastX = this.model.getX();
		this.lastY = this.model.getY();
		
		this.deathLinger = MainApplication.FPS * 4;
		this.sprites = model.getSprites(EnemyModel.IDLE, EnemyModel.FRONT_RIGHT);
		this.hand = model.getHand();
		this.pistol = model.getGunSprites(EnemyModel.GUN_IDLE);
		this.flare = new VFXModel(EnemyModel.PATH_FLARE, model.getScale() * 0.8, 0, 0);
		this.flare.setDone(true);
		
		this.currentVector = model.getVectors();
		
		this.drawTick = 0;
		this.hitFrame.setDone(true);
		this.deathFrame.setDone(true);
		this.hasDied = false;
		this.isActive = false;
	}

	public void hit(double magX, double magY) {
		this.vX = magX;
		this.vY = magY;

		this.model.setHp(this.model.getHp() - 1);
		if (this.model.getHp() == 0 && this.hasDied == false) {
			this.deathFrame = model.getSprites(EnemyModel.DEATH, this.getModelFacing());
			this.deathFrame.reset();
			this.hasDied = true;
			this.sound.playSfx(SoundController.SFX_ENEMY_HIT);
			this.sound.playRandomSfx(SoundController.SFX_ENEMY_DEATH_1, SoundController.SFX_ENEMY_DEATH_2);
			((GameSceneView)this.scene).enemyDied();
		} else {
			this.sound.playSfx(SoundController.SFX_ENEMY_HIT);
			this.hitFrame = model.getSprites(EnemyModel.HIT, this.model.getFacing());
			this.sound.playRandomSfx(SoundController.SFX_ENEMY_HURT_1, SoundController.SFX_ENEMY_HURT_2);
			this.hitFrame.reset();
		}
	}

	public EnemyModel getModel() {
		return this.model;
	}

	public boolean isActive() {
		return isActive;
	}

	public void activate() {
		this.reset();
		this.sound.playRandomSfx(SoundController.SFX_ENEMY_SPAWN_1, SoundController.SFX_ENEMY_SPAWN_2, SoundController.SFX_ENEMY_SPAWN_3);
		this.isActive = true;
	}
	
	public void deactivate() {
		this.isActive = false;
		this.model.setX(-1000);
		this.model.setY(1000);
		
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
		
		double dx = (this.lastX - this.model.getX()) * (this.lastX - this.model.getX());
		double dy = (this.lastY - this.model.getY()) * (this.lastY - this.model.getY());

		double d = Math.sqrt(dx + dy);

		if (d > 27) {
			this.sound.playRandomSfx(SoundController.SFX_ENEMY_STEP_1, SoundController.SFX_ENEMY_STEP_2, SoundController.SFX_ENEMY_STEP_3);
			this.lastX = this.model.getX();
			this.lastY = this.model.getY();
		}
	}

	public void setModelX(double x) {
		double off = sprites.getWidth(0) / 2;
		if (x - off < this.barrier.getMinX()) {
			x = this.barrier.getMinX() + off;
		} else if (x + off > this.barrier.getMaxX()) {
			x = this.barrier.getMaxX() - off;
		}

		this.model.setX(x);
	}

	public void setModelY(double y) {
		double off = sprites.getHeight(0) / 2;
		if (y - off < this.barrier.getMinY()) {
			y = this.barrier.getMinY() + off;
		} else if (y + off > this.barrier.getMaxY()) {
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
		this.gc.fillOval(this.camera.getXMapRelative(this.getModelX() - (w) / 2),
				this.camera.getYMapRelative(this.getModelY() + (h * 0.45)), w, h / 8);
		this.gc.setGlobalAlpha(1);
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

	public double getvX() {
		return vX;
	}

	public void setvX(double vX) {
		this.vX = vX;
	}

	public double getvY() {
		return vY;
	}

	public void setvY(double vY) {
		this.vY = vY;
	}

	public void drawModel() {
		int actionTick = 24;
		this.sprites = this.model.getSprites(this.getState(), this.getModelFacing());
		if (!this.model.getSpawnSprite().isDone()) {
			this.sprites = this.model.getSpawnSprite();
		} else if (!this.hitFrame.isDone()) {
			this.sprites = this.hitFrame;
		} else if (!this.deathFrame.isDone()) {
			this.sprites = this.deathFrame;
		}

		double tpf = (double) actionTick / (double) this.sprites.getLen();
		int i = (int) (Math.floor(drawTick % actionTick) / tpf);

		if (this.isFlip()) {
//			w = -w;
		}

		double h = this.sprites.getHeight(i);
		double w = this.sprites.getWidth(i);

		Image p = this.sprites.get(i);
		if (!this.model.getSpawnSprite().isDone()) {
			p = this.sprites.getNext();
		} else if (!this.hitFrame.isDone()) {
			p = this.hitFrame.getNext();
		} else if (!this.deathFrame.isDone()) {
			p = this.deathFrame.getNext();
		} else if (this.deathFrame.isDone() && this.hasDied) {
			int di = this.deathFrame.getLen() - 1;
			p = this.deathFrame.get(di);
			h = this.deathFrame.getHeight(di);
			w = this.deathFrame.getWidth(di);
		}

		this.model.setW(w * 0.6);
		this.model.setH(h * 0.7);

		double centerX = this.model.getX() - w / 2;
		double centerY = this.model.getY() - h / 2;
		
		this.gc.save();
		
		this.gc.setEffect(null);
		if (this.hitFrame.getNFrame() < 1 && !this.hitFrame.isDone() && !this.hasDied) {
			this.gc.setEffect(this.colorHit);
		}
		else if (this.hasDied) { 
			this.gc.setEffect(this.colorDead);
			this.gc.setGlobalAlpha(this.deathLinger / (MainApplication.FPS * 2.0));
		}
		
		this.camera.draw(this.gc, p, ((int) centerX) + .5, ((int) centerY) + .5, w, h);
		this.gc.restore();
		
		this.gc.setFill(Color.RED);
		this.gc.fillOval(this.camera.getXMapRelative(model.getX()) - 5, this.camera.getYMapRelative(model.getY()) - 5,
				10, 10);
		this.gc.strokeRect(this.camera.getXMapRelative(this.model.getBoundX()),
				this.camera.getYMapRelative(this.model.getBoundY()), this.model.getW(), this.model.getH());
	}

	public void drawHand() {
		double h = this.hand.getHeight(0);
		double w = this.hand.getWidth(0);

		double pw = this.sprites.getWidth(0);

		if (this.getModelAngle() < 90 || this.getModelAngle() > 270) {
			pw = -pw;
			w = -w;
		}

		Image p = this.hand.get(0);

		this.camera.draw(this.gc, p, handX - w / 2, handY - h / 2, w, h);
	}

	public void drawPistol() {
		double h = this.pistol.getHeight(0);
		double w = this.pistol.getWidth(0);

		double ph = this.sprites.getHeight(0);
		double pw = this.sprites.getWidth(0);

		double ang = this.getModelAngle() - (this.angleIndex > 0 ? this.pistolAngle[this.angleIndex--] : 0);

		if (this.getModelAngle() < 90 || this.getModelAngle() > 270) {
			pw = -pw;
			w = -w;
		} else {
			ang -= 180;
		}

		this.handX = model.getX() + pw * (65.0 / 100.0);
		this.handY = model.getY() + ph * (30.0 / 100.0);

		double centerX = model.getX() + pw * (56.0 / 100.0);
		double centerY = model.getY() - ph * (10.0 / 100.0);

		Image p;

		if (this.pistol.getLen() == 1) {
			p = this.pistol.get(0);
		} else {
			p = this.pistol.getNext();
			if (this.pistol.isDone()) {
				this.pistol = model.getGunSprites(EnemyModel.GUN_IDLE);

				this.doShoot();
				this.angleIndex += (pistolAngle.length - 1);
			}
		}

		this.gc.save();
		Rotate r = new Rotate(ang, this.camera.getXMapRelative(this.handX), this.camera.getYMapRelative(this.handY));
		gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());

		this.camera.draw(this.gc, p, centerX, centerY, w, h);

		if (!this.flare.isDone()) {
			this.camera.draw(this.gc, this.flare.getNext(), centerX + w,
					centerY + (h * 0.3) - this.flare.getHeight(this.flare.getNFrame() - 1) / 2,
					(this.getModelAngle() < 90 || this.getModelAngle() > 270)
							? -this.flare.getWidth(this.flare.getNFrame() - 1)
							: this.flare.getWidth(this.flare.getNFrame() - 1),
					this.flare.getHeight(this.flare.getNFrame() - 1));
		}

		this.gc.restore();

		double pointX = this.handX + (w);
		double pointY = this.handY - (h * 0.4);

		Point2D rotated = this.getRotated(ang, pointX, pointY, this.handX, this.handY);

		this.gc.setFill(Color.RED);
		this.gc.fillOval(this.camera.getXMapRelative(this.handX - 5), this.camera.getYMapRelative(this.handY - 5), 10,
				10);

		this.shootX = rotated.getX();
		this.shootY = rotated.getY();
	}

	public void updateModelFacing(double ang) {
		int f = 0;

		if (ang >= 20 && ang < 90) {
			f = EnemyModel.BACK_LEFT;
		} else if (ang >= 90 && ang < 160) {
			f = EnemyModel.BACK_RIGHT;
		} else if (ang >= 160 && ang < 270) {
			f = EnemyModel.FRONT_RIGHT;
		} else {
			f = EnemyModel.FRONT_LEFT;
		}

		this.setModelFacing(f);
	}

	public boolean isFlip() {
		return (this.getModelAngle() < 90 || this.getModelAngle() > 270);
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		this.drawVFX();

		if (this.model.getSpawnSprite().isDone() && !this.hasDied)
			this.drawPistol();
		
		this.drawModel();

		if (this.model.getSpawnSprite().isDone() && !this.hasDied)
			this.drawHand();
		
	}

	@Override
	public void renderShadow() {
		// TODO Auto-generated method stub
		if (!this.hasDied)
			this.drawShadow();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if (!this.isActive) return;
		
		if (isColliding(this.model, player.getModel()) && !player.hasDied && !player.isInvulnerable() && !this.hasDied) {
			player.hit();
			this.model.setHp(this.model.getHp() + 1);
			this.hit(1, 0);
		}
		
		if (!this.model.getSpawnSprite().isDone())
			return;
		this.globalTick++;
		this.drawTick++;
		double cX = this.model.getX();
		double cY = this.model.getY();

		double dX = this.player.getPlayerX() - this.getModelX();
		double dY = this.player.getPlayerY() - this.getModelY();

		double ang = (Math.atan2(dY, dX) * 180 / Math.PI) + 180;

		this.setModelAngle(ang);
		this.updateModelFacing(ang);

		double mag = Math.sqrt(dX * dX + dY * dY);
		double rX = 0;
		double rY = 0;

		if (this.hitFrame.isDone() && !this.hasDied) {
			this.vX = dX / mag;
			this.vY = dY / mag;

			rX = this.vX * this.model.getSpeed();
			rY = this.vY * this.model.getSpeed();

			if (mag < model.getNoticeRadius()) {
				rX = 0;
				rY = 0;
			}
		} else {
			rX = this.vX * this.model.getKnockbackSpeed();
			rY = this.vY * this.model.getKnockbackSpeed();
		}

		if (rX == 0 && rY == 0) {
			this.setState(EnemyModel.IDLE);
		} else if (!this.hasDied) {
			this.setState(EnemyModel.RUN);
		}

		if (this.model.getGunDownTime() > 0) {
			this.model.setGunDownTime(this.model.getGunDownTime() - 1);
		}

		if (this.hasDied && this.deathFrame.isDone()) {
			rX = 0;
			rY = 0;
			this.deathLinger -= 1;
			
			if (this.deathLinger == 0) {
				this.deactivate();
				return;
			}
		}

		cX += rX;
		cY += rY;
		
		if (this.model.getGunDownTime() == 0) {
			if (this.model.getMagSize() > 0 && mag < this.model.getNoticeRadius()) {
				this.initiateShoot();
				this.model.setGunDownTime(this.model.getFireCooldown());
				this.model.setMagSize(this.model.getMagSize() - 1);
			} else if (mag < this.model.getNoticeRadius()){
				this.doReload();
				this.model.setGunDownTime(this.model.getFireCooldown());
			}
		}

		this.move(cX, cY);
	}

	private void initiateShoot() {
		this.pistol = this.model.getGunSprites(EnemyModel.GUN_FIRE);
		this.pistol.reset();
	}

	public void doReload() {
		this.pistol.reset();
		this.sound.playSfx(SoundController.SFX_MAGNUM_RELOAD);
		this.model.setMagSize(this.model.getMagCap());
	}

	private void doShoot() {
		// TODO Auto-generated method stub
		double ang = model.getAngle();
		double w = this.pistol.getWidth(0);
		double h = this.pistol.getHeight(0);

		this.flare.reset();
		this.flare.setX(this.shootX);
		this.flare.setY(this.shootY);

		if (this.getModelAngle() < 90 || this.getModelAngle() > 270) {
			w = -w;
		} else {
			ang -= 180;
		}

		double pointX = this.handX + w;
		double pointY = this.handY - h * 0.4;

		Point2D rresult = this.getRotated(ang, pointX, pointY, this.handX, this.handY);

		this.sound.playRandomSfx(SoundController.SFX_MAGNUM_SHOT_1, SoundController.SFX_MAGNUM_SHOT_2, SoundController.SFX_MAGNUM_SHOT_3);
		this.bullet.shootBullet(this.shootX, this.shootY, rresult.getX(), rresult.getY());
	}

	@Override
	public void onCollide() {
		// TODO Auto-generated method stub

	}

	public int getVectorSum() {
		return Math.abs(this.getCVectorDown() - this.getCVectorUp())
				+ Math.abs(this.getCVectorLeft() - this.getCVectorRight());
	}

	// [UP, LEFT, DOWN, RIGHT]
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
