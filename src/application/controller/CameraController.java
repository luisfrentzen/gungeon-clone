package application.controller;

import application.MainApplication;
import application.model.CameraModel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class CameraController {
	private CameraModel camera;
	private int shakeFrame;
	private double offX;
	private double offY;
	private double vX;
	private double vY;
	
	public CameraController() {
		// TODO Auto-generated constructor stub
		this.camera = new CameraModel(0, 0);
	}
	
	public double getXCamRelative(double x) {
		return x + this.camera.getX();
	}
	
	public double getYCamRelative(double y) {
		return y + this.camera.getY();
	}
	
	public double getXMapRelative(double x) {
		return x - this.camera.getX();
	}
	
	public double getYMapRelative(double y) {
		return y - this.camera.getY();
	}
	
	public void draw(GraphicsContext gc, Image im, double x, double y, double w, double h) {
		double offsetX = this.camera.getW() * 0.15;
		double offsetY = this.camera.getH() * 0.15;
		if (x < this.camera.getX() - offsetX || y < this.camera.getY() - offsetY || x > this.camera.getX() + this.camera.getW() + offsetX || y > this.camera.getY() + this.camera.getH() + offsetY) {
			return;
		}
		
		gc.drawImage(im, getXMapRelative(x), getYMapRelative(y), w, h);
	}
	
	public void update() {
		if (this.shakeFrame > 0) {
			this.shakeFrame -= 1;
			offX = (Math.random() * 1) * MainApplication.W * 0.005 * (vX == 0? (Math.random() * 2 - 1) : -vX);
			offY = (Math.random() * 1) * MainApplication.W * 0.005 * (vY == 0? (Math.random() * 2 - 1) : -vY);
		} else {
			offX = 0;
			offY = 0;
		}
		
		this.camera.setX(this.camera.getX() + offX);
		this.camera.setY(this.camera.getY() + offY);
	}
	
	public void shake(double vX, double vY) {
		if (!MainApplication.IS_SHAKE) return;
		
		this.shakeFrame += (int)(MainApplication.FPS / 15);
		this.vX = vX;
		this.vY = vY;
	}
	
	public double getX() {
		return this.camera.getX();
	}

	public void setX(double x) {
		this.camera.setX(x);
	}

	public double getY() {
		return this.camera.getY();
	}

	public void setY(double y) {
		this.camera.setY(y);
	}
	
}
