package application.model;

import java.util.HashMap;
import java.util.Vector;

public class PlayerModel extends CharacterModel{
	
	public static final int IDLE = 0;
	public static final int RUN = 1;
	public static final int DODGE = 2;
	public static final int HAND = 3;
	
	public static final int FRONT = 0;
	public static final int FRONT_RIGHT = 1;
	public static final int BACK_RIGHT = 2;
	public static final int BACK = 3;
	public static final int BACK_LEFT = 4;
	public static final int FRONT_LEFT = 5;
	
	public static final int NO_DIR = 0;
	
	public static final int GUN_IDLE = 0;
	public static final int GUN_RELOAD = 1;
	public static final int GUN_FIRE = 2;
	
	public static final String PATH_FLARE = "/vfx/gun/flare/";
	public static final String PATH_RUN_DUST = "/vfx/player/run/";
	
	private int dodgeFrame;
	private int maxHp;

	public PlayerModel(double x, double y, double scale) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		this.w = 50;
		this.h = 50;
		this.boundX = this.x - this.w / 2;
		this.boundY = this.y - this.h / 2;
		this.scale = scale;
		this.speed = 3;
		this.gunDownTime = 0;
		
		this.magCap = 9;
		this.magSize = 9;
		
		this.hp = 6;
		this.maxHp = 6;
		this.state = PlayerModel.IDLE;
		this.angle = 0;
		
		this.showGun = true;
		this.dodgeFrame = 0;
		
		this.loadSprites();
	}
	
	public int getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}

	@Override
	public double getBoundX() {
		// TODO Auto-generated method stub
		return this.x - this.getW() / 2;
	}
	
	@Override
	public double getBoundY() {
		// TODO Auto-generated method stub
		return this.y - this.getH() * 0.15;
	}

	public int getDodgeFrame() {
		return dodgeFrame;
	}

	public void setDodgeFrame(int dodgeFrame) {
		this.dodgeFrame = dodgeFrame;
	}
	
	@Override
	public double getSpeed() {
		// TODO Auto-generated method stub
		switch (this.state) {
		case PlayerModel.DODGE:
			return super.getSpeed() * 1.15;

		default:
			return super.getSpeed();
		}
	}
	
	public void loadSprites() {
		//this.getClass().getResource("/Marine/Idle/Front/marine_idle_front_001.png").toExternalForm()	
		this.sprites = new HashMap<Integer, HashMap<Integer,SpriteModel>>();
		
		HashMap<Integer, SpriteModel> idle = new HashMap<Integer, SpriteModel>();
		idle.put(PlayerModel.FRONT, new SpriteModel("/marine/idle/front/onehand/", this.scale));
		idle.put(PlayerModel.FRONT_RIGHT, new SpriteModel("/marine/idle/frontright/onehand/", this.scale));
		idle.put(PlayerModel.FRONT_LEFT, new SpriteModel("/marine/idle/frontright/onehand/", this.scale));
		idle.put(PlayerModel.BACK, new SpriteModel("/marine/idle/back/onehand/", this.scale));
		idle.put(PlayerModel.BACK_RIGHT, new SpriteModel("/marine/idle/backright/nohand/", this.scale));
		idle.put(PlayerModel.BACK_LEFT, new SpriteModel("/marine/idle/backright/nohand/", this.scale));
		
		HashMap<Integer, SpriteModel> run = new HashMap<Integer, SpriteModel>();
		run.put(PlayerModel.FRONT, new SpriteModel("/marine/run/front/nohand/", this.scale));
		run.put(PlayerModel.FRONT_RIGHT, new SpriteModel("/marine/run/frontright/nohand/", this.scale));
		run.put(PlayerModel.FRONT_LEFT, new SpriteModel("/marine/run/frontright/nohand/", this.scale));
		run.put(PlayerModel.BACK, new SpriteModel("/marine/run/back/nohand/", this.scale));
		run.put(PlayerModel.BACK_RIGHT, new SpriteModel("/marine/run/backright/nohand/", this.scale));
		run.put(PlayerModel.BACK_LEFT, new SpriteModel("/marine/run/backright/nohand/", this.scale));
		
		HashMap<Integer, SpriteModel> dodge = new HashMap<Integer, SpriteModel>();
		dodge.put(PlayerModel.FRONT, new SpriteModel("/marine/dodge/front/", this.scale));
		dodge.put(PlayerModel.FRONT_RIGHT, new SpriteModel("/marine/dodge/frontright/", this.scale));
		dodge.put(PlayerModel.FRONT_LEFT, new SpriteModel("/marine/dodge/frontright/", this.scale));
		dodge.put(PlayerModel.BACK, new SpriteModel("/marine/dodge/back/", this.scale));
		dodge.put(PlayerModel.BACK_RIGHT, new SpriteModel("/marine/dodge/backright/", this.scale));
		dodge.put(PlayerModel.BACK_LEFT, new SpriteModel("/marine/dodge/backright/", this.scale));
		
		this.sprites.put(PlayerModel.IDLE, idle);
		this.sprites.put(PlayerModel.RUN, run);
		this.sprites.put(PlayerModel.DODGE, dodge);
		
		this.gunSprites = new HashMap<Integer, SpriteModel>();
		
		this.gunSprites.put(PlayerModel.GUN_IDLE, new SpriteModel("/gun/idle/", this.scale));
		this.gunSprites.put(PlayerModel.GUN_FIRE, new SpriteModel("/gun/fire/", this.scale, 3));
		this.gunSprites.put(PlayerModel.GUN_RELOAD, new SpriteModel("/gun/reload/", this.scale, 3));
		
		HashMap<Integer, Vector<VFXModel>> gunFlare = new HashMap<Integer, Vector<VFXModel>>();
		gunFlare.put(PlayerModel.NO_DIR, new Vector<VFXModel>());
		
		this.hand = new SpriteModel("/marine/hand/", this.scale);
		
	}

}
