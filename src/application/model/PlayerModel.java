package application.model;

public class PlayerModel extends CharacterModel{

	private SpriteModel frontIdleSprites;
	private SpriteModel backIdleSprites;
	private SpriteModel frontRightIdleSprites;
	private SpriteModel backRightIdleSprites;
	
	private int facing;
	
	public static final int FRONT = 0;
	public static final int FRONT_RIGHT = 1;
	public static final int BACK_RIGHT = 2;
	public static final int BACK = 3;
	public static final int BACK_LEFT = 4;
	public static final int FRONT_LEFT = 5;
	
	private double angle = 0;

	public PlayerModel(double x, double y, double scale) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		this.w = 50;
		this.h = 50;
		this.speed = 15;
		this.scale = scale;
//		this.damage = damage;
		
		this.hp = 3;
		
		this.facing = PlayerModel.FRONT;
		
		this.loadSprites();
	}
	
	public int getFacing() {
		return this.facing;
	}
	
	public void setFacing(int f) {
		this.facing = f;
	}
	
	public void loadSprites() {
		//this.getClass().getResource("/Marine/Idle/Front/marine_idle_front_001.png").toExternalForm()
		frontIdleSprites = new SpriteModel("/marine/idle/front/twohand/", this.scale);
		backIdleSprites = new SpriteModel("/marine/idle/back/twohand/", this.scale);
		frontRightIdleSprites = new SpriteModel("/marine/idle/frontright/twohand/", this.scale);
		backRightIdleSprites = new SpriteModel("/marine/idle/backright/twohand/", this.scale);
	}
	
	public SpriteModel getFrontIdleSprites() {
		return frontIdleSprites;
	}
	
	public SpriteModel getFrontRightIdleSprites() {
		return frontRightIdleSprites;
	}
	
	public SpriteModel getBackIdleSprites() {
		return backIdleSprites;
	}
	
	public SpriteModel getBackRightIdleSprites() {
		return backRightIdleSprites;
	}

	public void setAngle(double a) {
		this.angle = a;
	}
	
	public double getAngle() {
		return this.angle;
	}
}
