package application.model;

import javafx.scene.image.Image;

public class VFXModel extends SpriteModel{

	private boolean isDone;
	private int currentFrame;
	
	private double x;
	private double y;
	
	public VFXModel(String path, double scale, double x, double y) {
		// TODO Auto-generated constructor stub
		super(path, scale);
		
		this.isDone = false;
		this.currentFrame = 0;
	}
	
	public void reset() {
		this.isDone = false;
		this.currentFrame = 0;
	}
	
	public Image getNext() {
		Image im = get(this.currentFrame++);
		
		if (currentFrame == getLen()) {
			this.isDone = true;
		}
		
		return im;
	}
	
	public boolean isDone() {
		return isDone;
	}

	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}

	public int getNFrame() {
		return this.currentFrame;
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
