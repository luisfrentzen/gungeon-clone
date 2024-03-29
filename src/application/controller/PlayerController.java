package application.controller;

import java.util.Arrays;
import java.util.Vector;

import javafx.geometry.Point2D;
import application.MainApplication;
import application.model.PlayerModel;
import application.model.VFXModel;
import application.view.GameSceneView;
import application.view.SceneView;
import javafx.scene.canvas.Canvas;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

public class PlayerController extends CharacterController {
	private PlayerProjectileController ppController;

	private PlayerModel playerModel;

	private double lastX;
	private double lastY;

	private double cameraOffset;
	private double cameraOffsetX;
	private double cameraOffsetY;
	
	private double vectX;
	private double vectY;

	private int hitFrame;
	private int vulnTime;

	public PlayerController(Canvas canvas, SceneView scene, PlayerProjectileController ppController,
			CameraController camera, BarrierController barrier, SoundController sound) {
		// TODO Auto-generated constructor stub
		this.canvas = canvas;
		this.scene = scene;
		this.ppController = ppController;
		this.gc = canvas.getGraphicsContext2D();
		this.barrier = barrier;
		this.sound = sound;

		this.camera = camera;
		this.cameraOffset = 0.1;
		this.cameraOffsetX = 0;
		this.cameraOffsetY = 0;
		
		this.vectX = 0;
		this.vectY = 0;

//		this.playerModel = new PlayerModel(MainApplication.mapWidth(50), MainApplication.mapHeight(50), 1);
		this.playerModel = new PlayerModel(canvas.getWidth() / 2, canvas.getHeight() / 2, 4);
		this.lastX = this.getPlayerX();
		this.lastY = this.getPlayerY();

		this.drawTick = 0;
		this.globalTick = 0;
		this.sprites = playerModel.getSprites(PlayerModel.IDLE, PlayerModel.FRONT);
		this.hand = playerModel.getHand();
		this.pistol = playerModel.getGunSprites(PlayerModel.GUN_IDLE);
		this.flare = new VFXModel(PlayerModel.PATH_FLARE, playerModel.getScale() * 0.8, 0, 0);
		this.flare.setDone(true);

		this.currentVector = playerModel.getVectors();

		this.vfxRender = new Vector<VFXModel>();
		this.hitFrame = 0;
		this.vulnTime = (int) (MainApplication.FPS * 1.25);

		this.colorHit = new ColorAdjust();
		this.colorHit.setBrightness(1);
	}

	public void hit() {
		this.playerModel.setHp(this.playerModel.getHp() - 1);
		if (this.playerModel.getHp() == 0) {
			this.sound.playSfx(SoundController.SFX_PLAYER_DEATH);
			this.sound.stopMusic(SoundController.MUSIC_GAME_1);
			this.hasDied = true;
		} else {
			this.sound.playSfx(SoundController.SFX_PLAYER_HIT);
			this.hitFrame = (int) (MainApplication.FPS * 1.25);
		}
		
		this.camera.shake(0, 0);
	}

	public boolean isInvulnerable() {
		return this.hitFrame > 0 || this.getPlayerState() == PlayerModel.DODGE;
	}

	public PlayerModel getModel() {
		return this.playerModel;
	}

	public int getPlayerHp() {
		return this.playerModel.getHp();
	}

	public int getPlayerMaxHp() {
		return this.playerModel.getMaxHp();
	}

	public int getAmmo() {
		return this.playerModel.getMagSize();
	}

	public int getAmmoCap() {
		return this.playerModel.getMagCap();
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

	public void setPlayerX(double x) {
		double off = sprites.getWidth(0) / 2;
		if (x - off < this.barrier.getMinX()) {
			x = this.barrier.getMinX() + off;
		} else if (x + off > this.barrier.getMaxX()) {
			x = this.barrier.getMaxX() - off;
		}

		this.playerModel.setX(x);
	}

	public void setPlayerY(double y) {
		double off = sprites.getHeight(0) / 2;
		if (y - off < this.barrier.getMinY()) {
			y = this.barrier.getMinY() + off;
		} else if (y + off > this.barrier.getMaxY()) {
			y = this.barrier.getMaxY() - off;
		}

		this.playerModel.setY(y);
	}

	public void drawShadow() {
		double h = this.sprites.getHeight(0);
		double w = this.sprites.getWidth(0);

		this.gc.save();
		this.gc.setGlobalAlpha(0.6);
		this.gc.setFill(Color.BLACK);
		this.gc.fillOval(this.camera.getXMapRelative(playerModel.getX()) - w * 0.25,
				this.camera.getYMapRelative(playerModel.getY()) + h * 0.45, w * 0.5, h / 8);
		this.gc.setGlobalAlpha(1);
		this.gc.restore();
	}

	public void drawPlayer() {
		int actionTick = 24;
		
		if(this.hasDied) {
			this.sprites = this.playerModel.getDeath();
		}
		else {
			this.sprites = this.playerModel.getSprites(this.getPlayerState(), this.getPlayerFacing());
		}

		double tpf = (double) actionTick / (double) this.sprites.getLen();
		int i = (int) (Math.floor(drawTick % actionTick) / tpf);

		double h = this.sprites.getHeight(i);
		double w = this.sprites.getWidth(i);
		this.playerModel.setW(w * 0.5);
		this.playerModel.setH(h * 0.5);

		if (this.isFlip()) {
			w = -w;
		}

		Image p = this.sprites.get(i);
		
		if (this.hasDied && !this.playerModel.getDeath().isDone()) {
			p = this.playerModel.getDeath().getNext();
		}
		else if(this.hasDied && this.playerModel.getDeath().isDone()) {
			p = this.playerModel.getDeath().get(this.playerModel.getDeath().getLen() - 1);
		}
		
		double centerX = playerModel.getX() - w / 2;
		double centerY = playerModel.getY() - h / 2;

		this.gc.save();

		if (this.hitFrame > (this.vulnTime - 2)) {
			this.gc.setEffect(this.colorHit);
		} else if (this.hitFrame > 0) {
			this.gc.setGlobalAlpha(((int) (this.hitFrame / (MainApplication.FPS * 0.1))) % 2 == 0 ? 0.85 : 0.35);
		}

		this.camera.draw(this.gc, p, ((int) centerX) + .5, ((int) centerY) + .5, w, h);
		this.gc.setEffect(null);
		this.gc.setGlobalAlpha(1);
		this.gc.restore();

//		this.gc.setFill(Color.RED);
//		this.gc.fillOval(this.camera.getXMapRelative(playerModel.getX()) - 5,
//				this.camera.getYMapRelative(playerModel.getY()) - 5, 10, 10);
//		this.gc.strokeRect(this.camera.getXMapRelative(this.playerModel.getBoundX()),
//				this.camera.getYMapRelative(this.playerModel.getBoundY()), this.playerModel.getW(),
//				this.playerModel.getH());

//		this.gc.strokeOval(
//				this.camera.getXMapRelative(
//						playerModel.getX() - (75 * this.playerModel.getScale() * MainApplication.globalScale)),
//				this.camera.getYMapRelative(
//						playerModel.getY() - (75 * this.playerModel.getScale() * MainApplication.globalScale)),
//				150 * this.playerModel.getScale() * MainApplication.globalScale,
//				150 * this.playerModel.getScale() * MainApplication.globalScale);
	}

	public boolean isFlip() {
		if (this.getPlayerState() == PlayerModel.DODGE) {
			return this.getPlayerFacing() > 2;
		} else {
			return (this.getPlayerAngle() < 60 || this.getPlayerAngle() > 300);
		}
	}

	public void drawHand() {
		double h = this.hand.getHeight(0);
		double w = this.hand.getWidth(0);

		double ph = this.sprites.getHeight(0);
		double pw = this.sprites.getWidth(0);

		if (this.isFlip()) {
			pw = -pw;
			w = -w;
		}

		double centerX = playerModel.getX() + pw * (25 / 100.0);
		double centerY = playerModel.getY() + ph * (22.0 / 100.0);

		Image p = this.hand.get(0);

		this.camera.draw(this.gc, p, centerX, centerY, w, h);
	}

	public void drawPistol() {
		double h = this.pistol.getHeight(0);
		double w = this.pistol.getWidth(0);

		double ph = this.sprites.getHeight(0);
		double pw = this.sprites.getWidth(0);

		double dX = ((GameSceneView) this.scene).getMouseX() - this.camera.getXMapRelative(this.shootX);
		double dY = ((GameSceneView) this.scene).getMouseY() - this.camera.getYMapRelative(this.shootY);
		double dist = Math.sqrt(
				(
				(((GameSceneView) this.scene).getMouseX() - this.camera.getXMapRelative(this.getPlayerX())) * 
				(((GameSceneView) this.scene).getMouseX() - this.camera.getXMapRelative(this.getPlayerX()))
				) + (
				(((GameSceneView) this.scene).getMouseY() - this.camera.getYMapRelative(this.getPlayerY())) * 
				(((GameSceneView) this.scene).getMouseY() - this.camera.getYMapRelative(this.getPlayerY()))
				)
				);
		

		double ang = 0;
		if (dist < MainApplication.W * 0.1) {
			ang = this.getPlayerAngle();
		}
		else {
			ang = (Math.atan2(dY, dX) * 180 / Math.PI) + 180;			
		}
		
		if (this.isFlip()) {
			pw = -pw;
			w = -w;
		} else {
			ang -= 180;
		}

		this.handX = playerModel.getX() + pw * (35 / 100.0);
		this.handY = playerModel.getY() + ph * (30 / 100.0);

		double centerX = playerModel.getX() + pw * (21.0 / 100.0);
		double centerY = playerModel.getY() + ph * (5 / 100.0);

		Image p;

		if (this.pistol.getLen() == 1) {
			p = this.pistol.get(0);
		} else {
			p = this.pistol.getNext();
			if (this.pistol.isDone()) {
				this.pistol = playerModel.getGunSprites(PlayerModel.GUN_IDLE);
			}
		}

		this.gc.save();
		Rotate r = new Rotate(ang, this.camera.getXMapRelative(this.handX), this.camera.getYMapRelative(this.handY));
		gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());

		this.camera.draw(this.gc, p, centerX, centerY, w, h);

		if (!this.flare.isDone()) {
			this.camera.draw(this.gc, this.flare.getNext(), centerX + w,
					centerY + (h * 0.3) - this.flare.getHeight(this.flare.getNFrame() - 1) / 2,
					(this.getPlayerAngle() < 90 || this.getPlayerAngle() > 270)
							? -this.flare.getWidth(this.flare.getNFrame() - 1)
							: this.flare.getWidth(this.flare.getNFrame() - 1),
					this.flare.getHeight(this.flare.getNFrame() - 1));
		}

		this.gc.restore();

		double pointX = this.handX + (w * 0.8);
		double pointY = this.handY - (h * 0.4);

		Point2D rotated = this.getRotated(ang, pointX, pointY, this.handX, this.handY);

		this.shootX = rotated.getX();
		this.shootY = rotated.getY();
	}

	public void doDodge() {
		if (this.getPlayerState() == PlayerModel.RUN && this.getVectorSum() > 0) {
			this.currentAngle = this.getPlayerAngle();
			this.setPlayerState(PlayerModel.DODGE);
			this.hideGun();
			this.currentVector = Arrays.copyOf(this.playerModel.getVectors(), this.playerModel.getVectors().length);
			this.sound.playRandomSfx(SoundController.SFX_PLAYER_LEAP_1, SoundController.SFX_PLAYER_LEAP_2, SoundController.SFX_PLAYER_LEAP_3);
			this.playerModel.setDodgeFrame(24);
		}
	}

	public int getDodgeDir() {
		int vX = this.getCVectorRight() - this.getCVectorLeft();
		int vY = this.getCVectorDown() - this.getCVectorUp();

		if (vX > 0 && vY > 0) {
			return PlayerModel.FRONT_RIGHT;
		} else if (vX < 0 && vY < 0) {
			return PlayerModel.BACK_LEFT;
		} else if (vX > 0 && vY < 0) {
			return PlayerModel.BACK_RIGHT;
		} else if (vX < 0 && vY > 0) {
			return PlayerModel.FRONT_LEFT;
		} else if (vX > 0 && vY == 0) {
			return PlayerModel.FRONT_RIGHT;
		} else if (vX < 0 && vY == 0) {
			return PlayerModel.FRONT_LEFT;
		} else if (vX == 0 && vY > 0) {
			return PlayerModel.FRONT;
		} else {
			return PlayerModel.BACK;
		}
	}

	public void updatePlayerFacing(double ang) {
		int f = 0;

		if (ang >= 20 && ang < 65) {
			f = PlayerModel.BACK_LEFT;
		} else if (ang >= 65 && ang < 115) {
			f = PlayerModel.BACK;
		} else if (ang >= 115 && ang < 160) {
			f = PlayerModel.BACK_RIGHT;
		} else if (ang >= 160 && ang < 245) {
			f = PlayerModel.FRONT_RIGHT;
		} else if (ang >= 245 && ang < 295) {
			f = PlayerModel.FRONT;
		} else {
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
			
			double dX = ((GameSceneView) this.scene).getMouseX() - this.camera.getXMapRelative(this.shootX);
			double dY = ((GameSceneView) this.scene).getMouseY() - this.camera.getYMapRelative(this.shootY);
			double dist = Math.sqrt(
					(
					(((GameSceneView) this.scene).getMouseX() - this.camera.getXMapRelative(this.getPlayerX())) * 
					(((GameSceneView) this.scene).getMouseX() - this.camera.getXMapRelative(this.getPlayerX()))
					) + (
					(((GameSceneView) this.scene).getMouseY() - this.camera.getYMapRelative(this.getPlayerY())) * 
					(((GameSceneView) this.scene).getMouseY() - this.camera.getYMapRelative(this.getPlayerY()))
					)
					);
			
			this.camera.shake(dX/dist, dY/dist);

			double ang = 0;
			if (dist < MainApplication.W * 0.1) {
				ang = this.getPlayerAngle();
			}
			else {
				ang = (Math.atan2(dY, dX) * 180 / Math.PI) + 180;			
			}

			double w = this.pistol.getWidth(0);
			double h = this.pistol.getHeight(0);

			this.pistol = playerModel.getGunSprites(PlayerModel.GUN_FIRE);
			this.pistol.reset();

			this.flare.reset();
			this.flare.setX(this.shootX);
			this.flare.setY(this.shootY);

			if (this.isFlip()) {
				w = -w;
			} else {
				ang -= 180;
			}

			double pointX = this.handX + w;
			double pointY = this.handY - h * 0.4;

			Point2D rresult = this.getRotated(ang, pointX, pointY, this.handX, this.handY);
			
			this.sound.playRandomSfx(SoundController.SFX_DEAGLE_SHOT_1, SoundController.SFX_DEAGLE_SHOT_2, SoundController.SFX_DEAGLE_SHOT_3);
			this.ppController.shootBullet(this.shootX, this.shootY, rresult.getX(), rresult.getY(), this.vectX, this.vectY);
		}
	}

	@Override
	public void move(double x, double y) {
		// TODO Auto-generated method stub
		if (this.hasDied) return;
		
		this.setPlayerX(x);
		this.setPlayerY(y);

		camera.setX((x - MainApplication.W / 2) + this.cameraOffsetX);
		camera.setY((y - MainApplication.H / 2) + this.cameraOffsetY);

		double dx = (this.lastX - this.getPlayerX()) * (this.lastX - this.getPlayerX());
		double dy = (this.lastY - this.getPlayerY()) * (this.lastY - this.getPlayerY());

		double d = Math.sqrt(dx + dy);

		if (d > 100 && this.getPlayerState() != PlayerModel.DODGE) {
			this.lastX = this.getPlayerX();
			this.lastY = this.getPlayerY();
			this.sound.playRandomSfx(SoundController.SFX_PLAYER_STEP_1, SoundController.SFX_PLAYER_STEP_2, SoundController.SFX_PLAYER_STEP_3);
			this.addVFX(PlayerModel.PATH_RUN_DUST, this.playerModel.getScale(), x,
					y + this.sprites.getHeight(0) * 0.425, 2);
		}
	}
	
	@Override
	public void renderShadow() {
		// TODO Auto-generated method stub
		this.drawShadow();
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		this.drawVFX();

		if (this.playerModel.isShowGun() && !this.hasDied) {
			this.drawPistol();
		}

		if (this.playerModel.getMagSize() == 0 && (this.globalTick / 16) % 2 == 0 && !this.hasDied) {
			this.gc.setFill(Color.WHITE);
			this.gc.fillText("reload", this.getPlayerX() - (this.sprites.getWidth(0) * 0.34) - this.camera.getX(),
					this.getPlayerY() - this.sprites.getHeight(0) * 0.35 - this.camera.getY());
		}

		this.drawPlayer();

		if (this.playerModel.isShowGun() && !this.hasDied) {
			this.drawHand();
		}
		

//		this.gc.setFill(Color.RED);
//		this.gc.fillOval(((GameSceneView) this.scene).getMouseX() - 5, ((GameSceneView) this.scene).getMouseY() - 5, 10, 10);

	}

	public int getVectorSum() {
		return Math.abs(this.getCVectorDown() - this.getCVectorUp())
				+ Math.abs(this.getCVectorLeft() - this.getCVectorRight());
	}

	public void doReload() {
		if (this.playerModel.getGunDownTime() > 0) return; 
		if (this.getPlayerState() == PlayerModel.DODGE) return;
		this.sound.playSfx(SoundController.SFX_DEAGLE_RELOAD);
		this.pistol = playerModel.getGunSprites(PlayerModel.GUN_RELOAD);
		this.pistol.reset();
		this.playerModel.setMagSize(this.playerModel.getMagCap());
		this.playerModel.setGunDownTime(36);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		this.globalTick++;
		this.drawTick++;
		double cX = this.playerModel.getX();
		double cY = this.playerModel.getY();

		if (this.hitFrame > 0)
			this.hitFrame -= 1;

		double dX = ((GameSceneView) this.scene).getMouseX() - this.camera.getXMapRelative(this.getPlayerX());
		double dY = ((GameSceneView) this.scene).getMouseY() - this.camera.getYMapRelative(this.getPlayerY());
		
		this.cameraOffsetX = dX / (MainApplication.W / 2) * (this.cameraOffset * MainApplication.W);
		this.cameraOffsetY = dY / (MainApplication.H / 2) * (this.cameraOffset * MainApplication.H);

		double ang = (Math.atan2(dY, dX) * 180 / Math.PI) + 180;

		if (!this.hasDied) {
			this.setPlayerAngle(ang);
			this.updatePlayerFacing(ang);
		}

		if (this.playerModel.getDodgeFrame() > 0) {
			this.playerModel.setDodgeFrame(this.playerModel.getDodgeFrame() - 1);
			
			if (this.playerModel.getDodgeFrame() == 8) {
				this.sound.playRandomSfx(SoundController.SFX_PLAYER_ROLL_1, SoundController.SFX_PLAYER_ROLL_2, SoundController.SFX_PLAYER_ROLL_3);
			}
			if (this.playerModel.getDodgeFrame() == 0) {
				this.setPlayerState(PlayerModel.IDLE);
				this.resetVector();
				this.unhideGun();
			}
		} else {
			if (this.getVectorSum() == 0) {
				this.setPlayerState(PlayerModel.IDLE);
			} else {
				this.setPlayerState(PlayerModel.RUN);
			}
		}

		double vX = (-this.getCVectorLeft()) + (this.getCVectorRight());

		double vY = (-this.getCVectorUp()) + (this.getCVectorDown());
		this.vectX = vX;
		this.vectY = vY;

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

		if (((GameSceneView) this.scene).ismPrimaryDown() && this.playerModel.getGunDownTime() == 0
				&& this.playerModel.getMagSize() > 0) {
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
		} else {
			return this.playerModel.getFacing();
		}
	}

	public void setPlayerAngle(double ang) {
		this.playerModel.setAngle(ang);
	}

	public double getPlayerAngle() {
		if (this.getPlayerState() == PlayerModel.DODGE) {
			return this.currentAngle;
		} else {
			return this.playerModel.getAngle();
		}
	}

	// [UP, LEFT, DOWN, RIGHT]
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
