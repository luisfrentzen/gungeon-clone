package application.model;

import application.MainApplication;

public class CameraModel {
	private double x;
	private double y;
	private double w;
	private double h;
	
	public CameraModel(double x, double y) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		
		this.w = MainApplication.W;
		this.h = MainApplication.H;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
}
