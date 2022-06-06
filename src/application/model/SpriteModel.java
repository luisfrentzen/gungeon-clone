package application.model;

import java.io.File;

import application.MainApplication;
import javafx.scene.image.Image;

public class SpriteModel {

	private Image[] sprites;
	private int len;
	private double scale;
	
	public SpriteModel(String path, double scale) {
		// TODO Auto-generated constructor stub
		this.scale = scale;
		
		final File dir = new File(this.getClass().getResource(path).toString().substring(5));
//		System.out.println(path);
//		System.out.println(dir.exists());
//		System.out.println(dir.isDirectory());
//		System.out.println(dir.isFile());
		len = dir.list().length;
		
		sprites = new Image[len + 1];
		int i = 0;
		
		for (final File f : dir.listFiles()) {
			sprites[i++] = new Image(f.toURI().toString());
		}
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
