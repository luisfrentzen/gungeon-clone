package application.model;

import javafx.scene.image.Image;

public class VFXModel extends SpriteModel{

	private boolean isDone;
	private int currentFrame;
	
	private int x;
	private int y;
	
	public VFXModel(String path, double scale, int x, int y) {
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
		Image im = get(currentFrame++);
		
		if (currentFrame == getLen()) {
			this.isDone = true;
		}
		
		return im;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
