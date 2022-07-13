package application.model;

public abstract class GameObjectModel {
	protected double x;
	protected double y;
	
	protected double w;
	protected double h;
	
	protected double boundX;
	protected double boundY;
	
	protected double scale;
	
	public double getBoundX() {
		return boundX;
	}
	
	public void setBoundX(double boundX) {
		this.boundX = boundX;
	}
	
	public double getBoundY() {
		return boundY;
	}
	
	public void setBoundY(double boundY) {
		this.boundY = boundY;
	}
	
	public double getScale() {
		return scale;
	}
	
	public void setScale(double scale) {
		this.scale = scale;
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
	
	public double getW() {
		return w * scale;
	}
	
	public void setW(double w) {
		this.w = w / scale;
	}
	
	public double getH() {
		return h * scale;
	}
	
	public void setH(double h) {
		this.h = h / scale;
	}
	
	
}
