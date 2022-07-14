package application.model;

import java.util.HashMap;
import java.util.Vector;

public class EnemyModel extends CharacterModel{
	
	public static final int IDLE = 0;
	public static final int RUN = 1;
	
	public static final int FRONT_RIGHT = 0;
	public static final int FRONT_LEFT = 1;
	public static final int BACK_RIGHT = 2;
	public static final int BACK_LEFT = 3;
	
	public static final int NO_DIR = 0;
	
	public static final int GUN_IDLE = 0;
	public static final int GUN_FIRE = 1;
	
	public static final String PATH_FLARE = "/vfx/gun/flare/";
	
	protected SpriteModel spawn;
	
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
		
		this.hp = 3;
		this.angle = 0;
		
		this.loadSprites();
		
		this.magCap = 9;
		this.magSize = 9;
	}

	@Override
	public void loadSprites() {
		// TODO Auto-generated method stub
		this.sprites = new HashMap<Integer, HashMap<Integer,SpriteModel>>();
		
		HashMap<Integer, SpriteModel> idle = new HashMap<Integer, SpriteModel>();
		idle.put(EnemyModel.FRONT_RIGHT, new SpriteModel("/bulletkin/idle/right/", this.scale));
		idle.put(EnemyModel.FRONT_LEFT, new SpriteModel("/bulletkin/idle/right/", this.scale));
		idle.put(EnemyModel.BACK_RIGHT, new SpriteModel("/bulletkin/idle/back/", this.scale));
		idle.put(EnemyModel.BACK_LEFT, new SpriteModel("/bulletkin/idle/back/", this.scale));

		HashMap<Integer, SpriteModel> run = new HashMap<Integer, SpriteModel>();
		run.put(EnemyModel.FRONT_RIGHT, new SpriteModel("/bulletkin/run/right/", this.scale));
		run.put(EnemyModel.FRONT_LEFT, new SpriteModel("/bulletkin/run/right/", this.scale));
		run.put(EnemyModel.BACK_RIGHT, new SpriteModel("/bulletkin/run/rightback/", this.scale));
		run.put(EnemyModel.BACK_LEFT, new SpriteModel("/bulletkin/run/rightback/", this.scale));

		HashMap<Integer, SpriteModel> hit = new HashMap<Integer, SpriteModel>();
		hit.put(EnemyModel.FRONT_RIGHT, new SpriteModel("/bulletkin/hit/right/", this.scale));
		hit.put(EnemyModel.FRONT_LEFT, new SpriteModel("/bulletkin/hit/right/", this.scale));
		hit.put(EnemyModel.BACK_RIGHT, new SpriteModel("/bulletkin/hit/rightback/", this.scale));
		hit.put(EnemyModel.BACK_LEFT, new SpriteModel("/bulletkin/hit/rightback/", this.scale));

		this.sprites.put(EnemyModel.IDLE, idle);
		this.sprites.put(EnemyModel.RUN, run);

		this.gunSprites = new HashMap<Integer, SpriteModel>();

		this.gunSprites.put(EnemyModel.GUN_IDLE, new SpriteModel("/magnum/idle/", this.scale));
		this.gunSprites.put(EnemyModel.GUN_FIRE, new SpriteModel("/magnum/fire/", this.scale, 3));

		HashMap<Integer, Vector<VFXModel>> gunFlare = new HashMap<Integer, Vector<VFXModel>>();
		gunFlare.put(EnemyModel.NO_DIR, new Vector<VFXModel>());

		this.hand = new SpriteModel("/bulletkin/hand/", this.scale);
		this.spawn = new SpriteModel("/bulletkin/spawn/", this.scale);
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
}
