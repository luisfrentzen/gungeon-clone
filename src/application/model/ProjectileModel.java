package application.model;

public abstract class ProjectileModel extends GameObjectModel{
	protected SpriteModel sprite;
	protected double speed;
	protected double damage;
	
	public SpriteModel getSprite() {
		return sprite;
	}
	public void setSprite(SpriteModel sprite) {
		this.sprite = sprite;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public double getDamage() {
		return damage;
	}
	public void setDamage(double damage) {
		this.damage = damage;
	}
	
}
