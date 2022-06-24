package application.controller;

import java.util.Vector;

import application.MainApplication;
import application.model.PlayerProjectileModel;
import application.model.SpriteModel;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class PlayerProjectileController extends ProjectileController{
	
	private GraphicsContext gc;
	private Canvas canvas;
	private Vector<PlayerProjectileModel> projectiles;
	private int bulletIndex;
	private int nBullets;
	
	public PlayerProjectileController(Canvas canvas) {
		projectiles = new Vector<PlayerProjectileModel>();
		this.canvas = canvas;
		this.gc = canvas.getGraphicsContext2D();
		
		this.nBullets = 100;
		
		initBullets(this.nBullets);
		this.bulletIndex = 0;
	}
	
	public void initBullets(int n) {
		for (int i = 0; i < n; i ++) {
			this.projectiles.add(new PlayerProjectileModel(100, 100 + i * 10, 0, 0));
		}
	}
	
	public void shootBullet(double oX, double oY, double tX, double tY) {
		PlayerProjectileModel pp = this.projectiles.get(this.bulletIndex++ % this.nBullets);
		
		pp.setActive(true);
		pp.setX(oX);
		pp.setY(oY);
		pp.setTargetX(tX);
		pp.setTargetY(tY);
		
		pp.calculateVectors();
	}
	
	@Override
	public void render() {
		// TODO Auto-generated method stub
		for (PlayerProjectileModel ppModel : projectiles) {
			SpriteModel s = ppModel.getSprite();
			
			double x = ppModel.getX() - s.getWidth(0) / 2;
			double y = ppModel.getY() - s.getWidth(0) / 2;
			
			this.gc.drawImage(s.get(0), x, y, s.getWidth(0), s.getHeight(0));
		
			this.gc.save();
			this.gc.setEffect(new GaussianBlur(30.0));
			this.gc.setGlobalAlpha(0.7);
			this.gc.setFill(Color.YELLOW);
			
			double glowOffset = 0;
			
			double glowX = ppModel.getX() - (s.getWidth(0) + glowOffset) / 2;
			double glowY = ppModel.getY() - (s.getWidth(0) + glowOffset) / 2;
			
			this.gc.fillOval(glowX, glowY, s.getWidth(0) + glowOffset, s.getHeight(0) + glowOffset);
			
			this.gc.restore();
		}
	}
	
	public void addProjectile(double oX, double oY, double tX, double tY) {
		this.projectiles.add(new PlayerProjectileModel(oX, oY, tX, tY));
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		for (PlayerProjectileModel ppModel : projectiles) {
			if (!ppModel.isActive()) {
				continue;
			}
			
			double vX = ppModel.getVectorX();
			double vY = ppModel.getVectorY();
			
			vX *= ppModel.getSpeed();
			vY *= ppModel.getSpeed();
			
			ppModel.setX(ppModel.getX() + vX);
			ppModel.setY(ppModel.getY() + vY);
			
			if ((ppModel.getX() > MainApplication.W || ppModel.getX() < 0) || ppModel.getY() > MainApplication.H || ppModel.getY() < 0) {
				ppModel.resetPosition();
			}
		}
	}

	@Override
	public void onCollide() {
		// TODO Auto-generated method stub
		
	}

}
