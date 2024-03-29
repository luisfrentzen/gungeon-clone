package application.model;

import application.MainApplication;

public class PlayerProjectileModel extends ProjectileModel{
	
	public static final String PATH_IMPACT = "/vfx/gun/impact/";
	
	public PlayerProjectileModel(double x, double y, double tX, double tY) {
		this.scale = 2.5;
		this.x = x;
		this.y = y;
		this.oriX = x;
		this.oriY = y;
		
		this.speed = 9 * this.scale * MainApplication.globalScale;
		
		this.targetX = tX;
		this.targetY = tY;
		this.spray = 0.15;
		
		calculateVectors();

		this.sprite = new SpriteModel("/projectile/marine/", this.scale);
		this.isActive = false;
	}
	
}
