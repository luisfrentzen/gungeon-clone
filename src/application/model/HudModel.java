package application.model;

import java.util.HashMap;

public class HudModel extends GameObjectModel{

	private SpriteModel weapon;
	private SpriteModel ammo;
	private SpriteModel ammoEmpty;
	private SpriteModel ammoBorder;
	private SpriteModel ammoCount;
	private HashMap<Integer, SpriteModel> heart;
	
	public static final int HEART_IDLE_BIG = 0;
	public static final int HEART_IDLE_SMALL = 1;
	public static final int HEART_IDLE_EMPTY = 2;
	public static final String PATH_HEART_SHATTER = "/hud/heart/burst/";
	
	public HudModel() {
		// TODO Auto-generated constructor stub
		this.scale = 1;
		this.loadSprites();
	}
	
	private void loadSprites() {
		weapon = new SpriteModel("/hud/weapon/", this.scale);
		ammo = new SpriteModel("/hud/ammo/fill/", this.scale);
		ammoEmpty = new SpriteModel("/hud/ammo/empty/", this.scale);
		ammoBorder = new SpriteModel("/hud/ammo/border/", this.scale);
		ammoCount = new SpriteModel("/hud/ammo/count/", this.scale);
		
		heart = new HashMap<Integer, SpriteModel>();
		heart.put(HudModel.HEART_IDLE_BIG, new SpriteModel("/hud/heart/big/", 1));
		heart.put(HudModel.HEART_IDLE_SMALL, new SpriteModel("/hud/heart/small/", 1));
		heart.put(HudModel.HEART_IDLE_EMPTY, new SpriteModel("/hud/heart/empty/", 1));
	}
	
	public SpriteModel getHeartSprite(int i) {
		return heart.get(i);
	}

	public SpriteModel getWeapon() {
		return weapon;
	}

	public void setWeapon(SpriteModel weapon) {
		this.weapon = weapon;
	}

	public SpriteModel getAmmo() {
		return ammo;
	}

	public void setAmmo(SpriteModel ammo) {
		this.ammo = ammo;
	}

	public SpriteModel getAmmoEmpty() {
		return ammoEmpty;
	}

	public void setAmmoEmpty(SpriteModel ammoEmpty) {
		this.ammoEmpty = ammoEmpty;
	}

	public SpriteModel getAmmoBorder() {
		return ammoBorder;
	}

	public void setAmmoBorder(SpriteModel ammoBorder) {
		this.ammoBorder = ammoBorder;
	}

	public SpriteModel getAmmoCount() {
		return ammoCount;
	}

	public void setAmmoCount(SpriteModel ammoCount) {
		this.ammoCount = ammoCount;
	}

}
