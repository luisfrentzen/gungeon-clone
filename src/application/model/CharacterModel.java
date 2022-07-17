package application.model;

import java.util.HashMap;
import application.MainApplication;

public abstract class CharacterModel extends GameObjectModel {
	
	protected HashMap<Integer, HashMap<Integer, SpriteModel>> sprites;
	protected HashMap<Integer, SpriteModel> gunSprites;
	protected SpriteModel hand;

	protected int facing;
	
	protected int hp;
	protected double speed;
	protected int state;
	protected double damage;

	protected double angle;
	
	protected int gunDownTime;
	
	protected int magCap;
	protected int magSize;
	
	protected boolean showGun;
	protected boolean showHand;
	
	protected int[] vectors = {0, 0, 0, 0};
	
	public abstract void loadSprites();

	public boolean isShowGun() {
		return showGun;
	}

	public void setShowGun(boolean showGun) {
		this.showGun = showGun;
	}

	public boolean isShowHand() {
		return showHand;
	}

	public void setShowHand(boolean showHand) {
		this.showHand = showHand;
	}

	public int getMagCap() {
		return magCap;
	}

	public void setMagCap(int magCap) {
		this.magCap = magCap;
	}

	public int getMagSize() {
		return magSize;
	}

	public void setMagSize(int magSize) {
		this.magSize = magSize;
	}

	public int getGunDownTime() {
		return gunDownTime;
	}

	public void setGunDownTime(int gunDownTime) {
		this.gunDownTime = gunDownTime;
	}
	
	public int getFacing() {
		return facing;
	}

	public void setFacing(int facing) {
		this.facing = facing;
	}
	
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
		this.vectors[0] = v;
	}
	
	public void setVectorLeft(int v) {
		this.vectors[1] = v;
	}
	
	public void setVectorDown(int v) {
		this.vectors[2] = v;
	}
	
	public void setVectorRight(int v) {
		this.vectors[3] = v;
	}

	public SpriteModel getHand() {
		return hand;
	}

	public void setHand(SpriteModel hand) {
		this.hand = hand;
	}

	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public void setVectors(int[] vectors) {
		this.vectors = vectors;
	}
	
	public SpriteModel getSprites(int state, int dir) {
		return this.sprites.get(state).get(dir);
	}
	
	public SpriteModel getGunSprites(int state) {
		return this.gunSprites.get(state);
	}
	
}
