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
	protected int frameLength;
	protected int currentFrameL;
	
	public SpriteModel(String path, double scale) {
		// TODO Auto-generated constructor stub
		this.scale = scale;
		this.isDone = false;
		this.currentFrame = 0;
		
		this.frameLength = 1;
		this.currentFrameL = 0;
		
		final File dir = new File(this.getClass().getResource(path).toString().substring(5));
		len = dir.list().length;
		
		sprites = new Image[len + 1];
		int i = 0;
		
		for (final File f : dir.listFiles()) {
			sprites[i++] = new Image(f.toURI().toString());
		}
	}
	
	public SpriteModel(String path, double scale, int frameLength) {
		// TODO Auto-generated constructor stub
		this(path, scale);
		this.frameLength = frameLength;
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
		Image im;
		
		if (this.currentFrameL == this.frameLength) {
			im = get(this.currentFrame++);
			this.currentFrameL = 0;
		}
		else {
			im = get(this.currentFrame);
			this.currentFrameL += 1;
		}
		
		if (this.currentFrame == getLen() - 1 && this.currentFrameL == this.frameLength) {
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
		if (idx < 0 || idx > this.sprites.length - 1) {
			idx = 0;
		}
		
		return sprites[idx].getWidth() * this.scale * MainApplication.globalScale;
	}
	
	public double getHeight(int idx) {
		if (idx < 0 || idx > this.sprites.length - 1) {
			idx = 0;
		}
		
		return sprites[idx].getHeight() * this.scale * MainApplication.globalScale;
	}

}
