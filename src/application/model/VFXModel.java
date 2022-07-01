package application.model;

import javafx.scene.image.Image;

public class VFXModel extends SpriteModel{
	
	private double x;
	private double y;
	
	public VFXModel(String path, double scale, double x, double y) {
		// TODO Auto-generated constructor stub
		super(path, scale);
		
		this.x = x;
		this.y = y;
		
		this.isDone = false;
		this.currentFrame = 0;
	}
	
	public VFXModel(String path, double scale, double x, double y, int frameLength) {
		// TODO Auto-generated constructor stub
		this(path, scale, x, y);
		this.frameLength = frameLength;
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
