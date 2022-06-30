package application.model;

import java.io.File;

import application.MainApplication;
import javafx.scene.image.Image;

public class SpriteModel {

	protected Image[] sprites;
	protected int len;
	protected double scale;
	protected int currentFrame;
	protected boolean isDone;
	
	public SpriteModel(String path, double scale) {
		// TODO Auto-generated constructor stub
		this.scale = scale;
		this.isDone = false;
		this.currentFrame = 0;
		
		final File dir = new File(this.getClass().getResource(path).toString().substring(5));
		len = dir.list().length;
		
		sprites = new Image[len + 1];
		int i = 0;
		
		for (final File f : dir.listFiles()) {
			sprites[i++] = new Image(f.toURI().toString());
		}
	}
	
	public void reset() {
		this.isDone = false;
		this.currentFrame = 0;
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
	
	public Image getNext() {
		Image im = get(this.currentFrame++);
		
		if (currentFrame == getLen()) {
			this.isDone = true;
		}
		
		return im;
	}
	
	public int getLen() {
		return this.len;
	}
	
	public Image get(int idx) {
		return sprites[idx];
	}
	
	public double getWidth(int idx) {
		return sprites[idx].getWidth() * this.scale * MainApplication.globalScale;
	}
	
	public double getHeight(int idx) {
		return sprites[idx].getHeight() * this.scale * MainApplication.globalScale;
	}

}
