package application.controller;

public abstract class GameObjectController {
	protected CameraController camera;
	
	public abstract void render();
	
	public abstract void update();
	
	public abstract void onCollide();

}
