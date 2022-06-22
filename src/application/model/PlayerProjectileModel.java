package application.model;

public class PlayerProjectileModel extends ProjectileModel{
	
	public PlayerProjectileModel(double scale) {
		this.scale = scale;
		this.x = 100;
		this.y = 100;
		
		this.sprite = new SpriteModel("/projectile/marine/", this.scale);
	}
}
