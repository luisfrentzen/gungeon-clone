package application.controller;

import application.model.CameraModel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class CameraController {
	private CameraModel camera;
	
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
		gc.drawImage(im, getXMapRelative(x), getYMapRelative(y), w, h);
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
