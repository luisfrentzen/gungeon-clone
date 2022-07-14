package application.controller;

import java.util.Vector;

import application.model.PlayerProjectileModel;
import application.model.ProjectileModel;
import application.model.VFXModel;
import javafx.scene.canvas.Canvas;

public class EnemyProjectileController extends ProjectileController{
	
	public EnemyProjectileController(Canvas canvas, CameraController camera, BarrierController barrier) {
		// TODO Auto-generated constructor stub
		projectiles = new Vector<ProjectileModel>();
		this.canvas = canvas;
		this.gc = canvas.getGraphicsContext2D();
		this.barrier = barrier;
		
		this.nBullets = 10;
		this.camera = camera;
		this.vfxRender = new Vector<VFXModel>();
		
		initBullets(this.nBullets);
		this.bulletIndex = 0;
	}
	
	@Override
	public void initBullets(int n) {
		// TODO Auto-generated method stub
		for (int i = 0; i < n; i++) {
			this.projectiles.add(new PlayerProjectileModel(-1000, -1000 + i * 10, 0, 0));
		}
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
