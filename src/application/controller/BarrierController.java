package application.controller;

import java.util.Vector;

import application.model.BarrierModel;

public class BarrierController extends GameObjectController{

	private Vector<BarrierModel> barriers;
	
	public BarrierController() {
		// TODO Auto-generated constructor stub
		this.barriers = new Vector<BarrierModel>();
	}
	
	public void addBarrier(double x, double y, double h, double w) {
		this.barriers.add(new BarrierModel(x, y, w, h));
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
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
