package application.model;

import java.util.HashMap;
import java.util.Vector;

import application.MainApplication;

public class EnemyModel extends CharacterModel{
	
	public static final int IDLE = 0;
	public static final int RUN = 1;
	public static final int HIT = 2;
	public static final int DEATH = 3;
	
	public static final int FRONT_RIGHT = 0;
	public static final int FRONT_LEFT = 1;
	public static final int BACK_RIGHT = 2;
	public static final int BACK_LEFT = 3;
	
	public static final int NO_DIR = 0;
	
	public static final int GUN_IDLE = 0;
	public static final int GUN_FIRE = 1;
	
	public static final String PATH_FLARE = "/vfx/gun/flare/";
	public static final String PATHS_SPAWN = "/vfx/enemy/spawn/crosshair/";
	
	private SpriteModel spawn;
	
	private double noticeRadius;
	private int fireCooldown;
	
	private double knockbackSpeed;
	
	public EnemyModel(double x, double y, double scale) {
		this.x = x;
		this.y = y;
		this.w = 50;
		this.h = 50;
		this.boundX = this.x - this.w / 2;
		this.boundY = this.y - this.y / 2;
		this.scale = scale;
		this.speed = 0.5;
		this.damage = 1;
		this.knockbackSpeed = 0.7;
		
		this.hp = 4;
		this.angle = 0;
		
		this.loadSprites();
		
		this.magCap = 9;
		this.magSize = 9;
		this.fireCooldown = 75;
		this.gunDownTime = 0;
		this.noticeRadius = 100 * this.scale * MainApplication.globalScale;
	}

	public double getKnockbackSpeed() {
		return knockbackSpeed * this.scale * MainApplication.globalScale;
	}

	public int getFireCooldown() {
		return fireCooldown;
	}

	public double getNoticeRadius() {
		return noticeRadius;
	}
	
	@Override
	public void loadSprites() {
		// TODO Auto-generated method stub
		this.sprites = new HashMap<Integer, HashMap<Integer,SpriteModel>>();
		
		HashMap<Integer, SpriteModel> idle = new HashMap<Integer, SpriteModel>();
		idle.put(EnemyModel.FRONT_RIGHT, new SpriteModel("/bulletkin/idle/right/", this.scale));
		idle.put(EnemyModel.FRONT_LEFT, new SpriteModel("/bulletkin/idle/left/", this.scale));
		idle.put(EnemyModel.BACK_RIGHT, new SpriteModel("/bulletkin/idle/back/", this.scale));
		idle.put(EnemyModel.BACK_LEFT, new SpriteModel("/bulletkin/idle/back/", this.scale));

		HashMap<Integer, SpriteModel> run = new HashMap<Integer, SpriteModel>();
		run.put(EnemyModel.FRONT_RIGHT, new SpriteModel("/bulletkin/run/right/", this.scale));
		run.put(EnemyModel.FRONT_LEFT, new SpriteModel("/bulletkin/run/left/", this.scale));
		run.put(EnemyModel.BACK_RIGHT, new SpriteModel("/bulletkin/run/rightback/", this.scale));
		run.put(EnemyModel.BACK_LEFT, new SpriteModel("/bulletkin/run/leftback/", this.scale));

		HashMap<Integer, SpriteModel> hit = new HashMap<Integer, SpriteModel>();
		hit.put(EnemyModel.FRONT_RIGHT, new SpriteModel("/bulletkin/hit/right/", this.scale, 3));
		hit.put(EnemyModel.FRONT_LEFT, new SpriteModel("/bulletkin/hit/left/", this.scale, 3));
		hit.put(EnemyModel.BACK_RIGHT, new SpriteModel("/bulletkin/hit/rightback/", this.scale, 3));
		hit.put(EnemyModel.BACK_LEFT, new SpriteModel("/bulletkin/hit/leftback/", this.scale, 3));
		
		HashMap<Integer, SpriteModel> death = new HashMap<Integer, SpriteModel>();
		death.put(EnemyModel.FRONT_RIGHT, new SpriteModel("/bulletkin/death/rightfront/", this.scale, 3));
		death.put(EnemyModel.FRONT_LEFT, new SpriteModel("/bulletkin/death/leftfront/", this.scale, 3));
		death.put(EnemyModel.BACK_RIGHT, new SpriteModel("/bulletkin/death/rightback/", this.scale, 3));
		death.put(EnemyModel.BACK_LEFT, new SpriteModel("/bulletkin/death/leftback/", this.scale, 3));

		this.sprites.put(EnemyModel.IDLE, idle);
		this.sprites.put(EnemyModel.RUN, run);
		this.sprites.put(EnemyModel.HIT, hit);
		this.sprites.put(EnemyModel.DEATH, death);

		this.gunSprites = new HashMap<Integer, SpriteModel>();

		this.gunSprites.put(EnemyModel.GUN_IDLE, new SpriteModel("/magnum/idle/", this.scale));
		this.gunSprites.put(EnemyModel.GUN_FIRE, new SpriteModel("/magnum/fire/", this.scale, 2));

		HashMap<Integer, Vector<VFXModel>> gunFlare = new HashMap<Integer, Vector<VFXModel>>();
		gunFlare.put(EnemyModel.NO_DIR, new Vector<VFXModel>());

		this.hand = new SpriteModel("/bulletkin/hand/", this.scale);
		this.spawn = new SpriteModel("/bulletkin/spawn/", this.scale, 16);
	}
	
	public SpriteModel getSpawnSprite() {
		return this.spawn;
	}
	
	@Override
	public double getBoundX() {
		// TODO Auto-generated method stub
		return this.x - this.getW() / 2;
	}
	
	@Override
	public double getBoundY() {
		// TODO Auto-generated method stub
		return this.y - this.getH() * 0.45;
	}
}
