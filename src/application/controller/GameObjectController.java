package application.controller;

import application.model.GameObjectModel;

public abstract class GameObjectController {
	protected CameraController camera;
	
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

}
