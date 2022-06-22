package application.controller;

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
	private PlayerProjectileModel ppModel;
	
	public PlayerProjectileController(Canvas canvas) {
		// TODO Auto-generated constructor stub
		ppModel = new PlayerProjectileModel(1.75);
		this.canvas = canvas;
		this.gc = canvas.getGraphicsContext2D();
	}
	
	@Override
	public void render() {
		// TODO Auto-generated method stub
		SpriteModel s = this.ppModel.getSprite();
		
		double x = this.ppModel.getX() - s.getWidth(0) / 2;
		double y = this.ppModel.getY() - s.getWidth(0) / 2;
		
		this.gc.drawImage(s.get(0), x, y, s.getWidth(0), s.getHeight(0));
	
		this.gc.save();
		this.gc.setEffect(new GaussianBlur(30.0));
		this.gc.setGlobalAlpha(0.7);
		this.gc.setFill(Color.YELLOW);
		
		double glowOffset = 0;
		
		double glowX = this.ppModel.getX() - (s.getWidth(0) + glowOffset) / 2;
		double glowY = this.ppModel.getY() - (s.getWidth(0) + glowOffset) / 2;
		
		this.gc.fillOval(glowX, glowY, s.getWidth(0) + glowOffset, s.getHeight(0) + glowOffset);
		
		this.gc.restore();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCollide() {
		// TODO Auto-generated method stub
		
	}

}
