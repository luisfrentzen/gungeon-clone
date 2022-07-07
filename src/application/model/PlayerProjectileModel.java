package application.model;

import application.MainApplication;

public class PlayerProjectileModel extends ProjectileModel{
	
	public PlayerProjectileModel(double x, double y, double tX, double tY) {
		this.scale = 2;
		this.x = x;
		this.y = y;
		this.oriX = x;
		this.oriY = y;
		
		this.speed = 13 * this.scale * MainApplication.globalScale;
		
		this.targetX = tX;
		this.targetY = tY;
		this.spray = 0.4;
		
		calculateVectors();

		this.sprite = new SpriteModel("/projectile/marine/", this.scale);
		this.isActive = false;
	}
	
	public void calculateVectors() {
		double dY = this.targetY - this.y;
		double dX = this.targetX - this.x;
		
		double mag = Math.sqrt(dX * dX + dY * dY);
		
		this.vectorX = dX / mag;
		this.vectorY = dY / mag;
	}
}
