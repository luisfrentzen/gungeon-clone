package application.model;

import application.MainApplication;

public class EnemyProjectileModel extends ProjectileModel{

	public static final String PATH_IMPACT = "/vfx/gun/impact";
	
	public EnemyProjectileModel(double x, double y, double tX, double tY) {
		this.scale = 2.5;
		this.x = x;
		this.y = y;
		this.oriX = x;
		this.oriY = y;
		
		this.speed = 1 * this.scale * MainApplication.globalScale;
		
		this.targetX = tX;
		this.targetY = tY;
		this.spray = 0.2;
		
		calculateVectors();

		this.sprite = new SpriteModel("/projectile/bulletkin/", this.scale);
		this.isActive = false;
	}
	
}
