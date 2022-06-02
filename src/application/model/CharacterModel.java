package application.model;

public abstract class CharacterModel extends GameObjectModel {
	
	protected int hp;
	protected double speed;
	protected double damage;

	protected int[] vectors = {0, 0, 0, 0};

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
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

	public int[] getVectors() {
		return vectors;
	}

	public void setVectorUp(int v) {
		// TODO Auto-generated method stub
		this.vectors[0] = v;
	}
	
	public void setVectorLeft(int v) {
		// TODO Auto-generated method stub
		this.vectors[1] = v;
	}
	
	public void setVectorDown(int v) {
		// TODO Auto-generated method stub
		this.vectors[2] = v;
	}
	
	public void setVectorRight(int v) {
		// TODO Auto-generated method stub
		this.vectors[3] = v;
	}
	
	
}
