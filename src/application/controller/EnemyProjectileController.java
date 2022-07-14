package application.controller;

import java.util.Vector;

import application.model.EnemyProjectileModel;
import application.model.PlayerProjectileModel;
import application.model.ProjectileModel;
import application.model.SpriteModel;
import application.model.VFXModel;
import javafx.scene.canvas.Canvas;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;

public class EnemyProjectileController extends ProjectileController{
	
	public EnemyProjectileController(Canvas canvas, CameraController camera, BarrierController barrier) {
		// TODO Auto-generated constructor stub
		projectiles = new Vector<ProjectileModel>();
		this.canvas = canvas;
		this.gc = canvas.getGraphicsContext2D();
		this.barrier = barrier;
		
		this.nBullets = 10;
		this.camera = camera;
		this.vfxRender = new Vector<VFXModel>();
		
		initBullets(this.nBullets);
		this.bulletIndex = 0;
	}
	
	@Override
	public void initBullets(int n) {
		// TODO Auto-generated method stub
		for (int i = 0; i < n; i++) {
			this.projectiles.add(new EnemyProjectileModel(-1000, -1000 + i * 10, 0, 0));
		}
	}
	
	@Override
	public void render() {
		// TODO Auto-generated method stub
		this.drawVFX();

		for (ProjectileModel ppModel : projectiles) {
			SpriteModel s = ppModel.getSprite();

			double x = ppModel.getX() - s.getWidth(0) / 2;
			double y = ppModel.getY() - s.getWidth(0) / 2;

			ppModel.setW(s.getWidth(0) * 0.75);
			ppModel.setH(s.getHeight(0) * 0.75);
			ppModel.setBoundX(ppModel.getX() - ppModel.getW() / 2);
			ppModel.setBoundY(ppModel.getY() - ppModel.getH() / 2);

			this.gc.drawImage(s.get(0), this.camera.getXMapRelative(x), this.camera.getYMapRelative(y), s.getWidth(0),
					s.getHeight(0));

			this.gc.save();
			this.gc.setGlobalBlendMode(BlendMode.COLOR_DODGE);
			this.gc.setGlobalAlpha(0.7);
			this.gc.setFill(Color.RED);

			double glowOffset = 0;

			double glowX = ppModel.getX() - (s.getWidth(0) + glowOffset) / 2;
			double glowY = ppModel.getY() - (s.getWidth(0) + glowOffset) / 2;

			this.gc.fillOval(this.camera.getXMapRelative(glowX), this.camera.getYMapRelative(glowY),
					s.getWidth(0) + glowOffset, s.getHeight(0) + glowOffset);

			this.gc.restore();
			this.gc.strokeRect(this.camera.getXMapRelative(ppModel.getBoundX()),
					this.camera.getYMapRelative(ppModel.getBoundY()), ppModel.getW(), ppModel.getH());
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		for (ProjectileModel ppModel : projectiles) {
			if (!ppModel.isActive()) {
				continue;
			}

			double vX = ppModel.getVectorX();
			double vY = ppModel.getVectorY();

			vX *= ppModel.getSpeed();
//			vX *= 1;
			vY *= ppModel.getSpeed();
//			vY *= 1;

			double x = ppModel.getX();
			double y = ppModel.getY();

			ppModel.setX(ppModel.getX() + vX);
			ppModel.setY(ppModel.getY() + vY);

			if ((ppModel.getX() > this.barrier.getMaxX() || ppModel.getX() < this.barrier.getMinX())
					|| (ppModel.getY() > this.barrier.getMaxY() || ppModel.getY() < this.barrier.getMinY())) {
				ppModel.resetPosition();
//				System.out.println(ppModel.getScale());
				this.addVFX(EnemyProjectileModel.PATH_IMPACT, 3, x,
						y, 1);
			}

		}
	}

	@Override
	public void onCollide() {
		// TODO Auto-generated method stub
		
	}
}
