package application.model;

import java.util.HashMap;
import java.util.Vector;

public class PlayerModel extends CharacterModel{
	
	private HashMap<Integer, HashMap<Integer, SpriteModel>> sprites;
	private HashMap<Integer, SpriteModel> gunSprites;
	private HashMap<Integer, HashMap<Integer, Vector<VFXModel>>> vfxs;
	private SpriteModel hand;
	
	private int facing;
	
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
	
	public static final int VFX_FLARE = 0;
	
	public static final String PATH_FLARE = "/vfx/gun/flare/";
	
	private double angle;
	
	private boolean showGun;
	private boolean showHand;
	
	private int dodgeFrame;
	private int gunDownTime;
	
	private int magCap;
	private int magSize;

	public PlayerModel(double x, double y, double scale) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		this.w = 50;
		this.h = 50;
		this.scale = scale;
		this.speed = 3;
		this.gunDownTime = 0;
//		this.damage = damage;
		
		this.magCap = 9;
		this.magSize = 9;
		
		this.hp = 3;
		this.state = PlayerModel.IDLE;
		this.angle = 0;
		
		this.facing = PlayerModel.FRONT;
		
		this.showGun = true;
		this.dodgeFrame = 0;
		
		this.loadSprites();
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

	public int getDodgeFrame() {
		return dodgeFrame;
	}

	public void setDodgeFrame(int dodgeFrame) {
		this.dodgeFrame = dodgeFrame;
	}

	public boolean isShowHand() {
		return showHand;
	}

	public void setShowHand(boolean showHand) {
		this.showHand = showHand;
	}

	public boolean isShowGun() {
		return showGun;
	}

	public void setShowGun(boolean showGun) {
		this.showGun = showGun;
	}

	public int getFacing() {
		return this.facing;
	}
	
	public void setFacing(int f) {
		this.facing = f;
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
		this.gunSprites.put(PlayerModel.GUN_FIRE, new SpriteModel("/gun/fire/", this.scale));
		this.gunSprites.put(PlayerModel.GUN_RELOAD, new SpriteModel("/gun/reload/", this.scale));
		
		this.vfxs = new HashMap<Integer, HashMap<Integer, Vector<VFXModel>>>();
		
		HashMap<Integer, Vector<VFXModel>> gunFlare = new HashMap<Integer, Vector<VFXModel>>();
		gunFlare.put(PlayerModel.NO_DIR, new Vector<VFXModel>());
		
		this.vfxs.put(PlayerModel.VFX_FLARE, gunFlare);
		
		this.hand = new SpriteModel("/marine/hand/", this.scale);
		
	}
	
	public SpriteModel getHand() {
		return this.hand;
	}
	
	public int getState() {
		return this.state;
	}
	
	public void setState(int s) {
		this.state = s;
	}
	
	public SpriteModel getSprites(int state, int dir) {
		return this.sprites.get(state).get(dir);
	}
	
	public SpriteModel getGunSprites(int state) {
		return this.gunSprites.get(state);
	}

//	public VFXModel getVFXSprites(int vfx, int dir) {
//		// TODO Auto-generated method stub
//		Vector<VFXModel> v = this.vfxs.get(vfx).get(dir);
//	}

	public void setAngle(double a) {
		this.angle = a;
	}
	
	public double getAngle() {
		return this.angle;
	}

}
