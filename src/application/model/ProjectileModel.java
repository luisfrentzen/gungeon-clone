package application.model;

import application.MainApplication;

public abstract class ProjectileModel extends GameObjectModel{
	protected SpriteModel sprite;
	protected double speed;
	protected double vectorX;
	protected double vectorY;
	protected double targetX;
	protected double targetY;
	protected double damage;
	protected double oriX;
	protected double oriY;
	protected boolean isActive;
	protected double spray;
	
	public void calculateVectors() {
		double dY = this.targetY - this.y;
		double dX = this.targetX - this.x;
		
		double mag = Math.sqrt(dX * dX + dY * dY);
		
		this.vectorX = dX / mag;
		this.vectorY = dY / mag;
	}
	
	public void calculateVectors(double vectX, double vectY) {
		// TODO Auto-generated method stub
		double dY = this.targetY - this.y;
		double dX = this.targetX - this.x;
		
		double mag = Math.sqrt(dX * dX + dY * dY);
		
		this.vectorX = dX / mag;
		this.vectorY = dY / mag;
	}
	
	public void resetPosition() {
		this.x = this.oriX;
		this.y = this.oriY;
		this.isActive = false;
		
	}
	
	public double getSpray() {
		return spray * this.scale * MainApplication.globalScale;
	}

	public void setSpray(double spray) {
		this.spray = spray;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public double getOriX() {
		return this.oriX;
	}
	
	public void setOriX(double ox) {
		this.oriX = ox;
	}
	
	public double getOriY() {
		return this.oriY;
	}
	
	public void setOriY(double oy) {
		this.oriY = oy;
	}
	
	public SpriteModel getSprite() {
		return sprite;
	}
	
	public void setSprite(SpriteModel sprite) {
		this.sprite = sprite;
	}
	
	public double getSpeed() {
		return speed * this.scale * MainApplication.globalScale;
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
	
	public double getVectorX() {
		return vectorX;
	}
	
	public void setVectorX(double vectorX) {
		this.vectorX = vectorX;
	}
	
	public double getVectorY() {
		return vectorY;
	}
	
	public void setVectorY(double vectorY) {
		this.vectorY = vectorY;
	}
	
	public double getTargetX() {
		return targetX;
	}
	
	public void setTargetX(double targetX) {
		this.targetX = targetX;
	}
	
	public double getTargetY() {
		return targetY;
	}
	
	public void setTargetY(double targetY) {
		this.targetY = targetY;
	}
	
}
