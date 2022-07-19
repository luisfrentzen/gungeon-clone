package application.controller;


import application.model.BarrierModel;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class BarrierController extends GameObjectController{

	private BarrierModel barrier;
	
	public BarrierController(Canvas canvas, CameraController camera, double x, double y, double w, double h) {
		// TODO Auto-generated constructor stub
		this.barrier = new BarrierModel(x, y, w, h);
		this.canvas = canvas;
		this.camera = camera;
		this.gc = this.canvas.getGraphicsContext2D();
	}
	
	public BarrierModel getBarrier() {
		return this.barrier;
	}
	
	public double getMaxX() {
		return this.barrier.getX() + this.barrier.getW();
	}

	public double getMaxY() {
		return this.barrier.getY() + this.barrier.getH();
	}
	
	public double getMinX() {
		return this.barrier.getX();
	}
	
	public double getMinY() {
		return this.barrier.getY();
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
//		this.gc.setStroke(Color.RED);
//		this.gc.strokeRect(this.camera.getXMapRelative(barrier.getX()), this.camera.getYMapRelative(barrier.getY()), barrier.getW(), barrier.getH());
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCollide() {
		// TODO Auto-generated method stub
		
	}

}
