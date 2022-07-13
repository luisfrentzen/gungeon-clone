package application.controller;

import java.util.Vector;

import application.model.GameObjectModel;
import application.model.VFXModel;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public abstract class GameObjectController {
	protected CameraController camera;
	protected Vector<VFXModel> vfxRender;
	protected GraphicsContext gc;
	protected Canvas canvas;
	
	public abstract void render();
	
	public abstract void update();
	
	public abstract void onCollide();
	
	public boolean isColliding(GameObjectModel a, GameObjectModel b) {
		boolean right = a.getBoundX() < b.getBoundX() + b.getW();
		boolean left = a.getBoundX() + a.getW() > b.getBoundX();
		boolean bot = a.getBoundY() < b.getBoundY() + b.getH();
		boolean top= a.getBoundY() + a.getH() > b.getBoundY();
		
		return left && right && top && bot;
	}
	
	public void addVFX(String vfx, double scale, double x, double y, int frameLength) {
		vfxRender.add(new VFXModel(vfx, scale, x, y, frameLength));
	}
	
	public void drawVFX() {
		for (VFXModel fx : this.vfxRender) {
			if (!fx.isDone()) {
				double w = fx.getWidth(fx.getNFrame() - 1);
				double h = fx.getHeight(fx.getNFrame() - 1);
				this.camera.draw(this.gc, fx.getNext(), fx.getX() - w / 2, fx.getY() - h / 2, w, h);
				
			}
		}
	}

}
