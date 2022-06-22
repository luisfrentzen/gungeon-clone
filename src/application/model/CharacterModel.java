package application.model;

import application.MainApplication;
import javafx.scene.image.Image;

public abstract class CharacterModel extends GameObjectModel {
	
	protected int hp;
	protected double speed;
	protected int state;
	
	protected int[] vectors = {0, 0, 0, 0};

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public double getSpeed() {
		return speed * this.scale * MainApplication.globalScale;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
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