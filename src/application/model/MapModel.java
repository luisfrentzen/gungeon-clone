package application.model;

import javafx.scene.image.Image;

public class MapModel extends GameObjectModel{
	private SpriteModel tiles;
	private SpriteModel walls;
	private int nw;
	private int nh;
	
	public static int WALL_LIGHT = 0;
	public static int WALL_LIGHT_VAR = 1;
	public static int WALL_DARK = 2;
	public static int WALL_DARK_VAR = 3;
	
	public static int WALL_BOT_EDGE = 4;
	public static int WALL_RIGHT_EDGE = 5;
	public static int WALL_LEFT_EDGE = 6;
	public static int WALL_TOP_EDGE = 7;
	
	public static int WALL_TOP_LEFT_CORNER = 8;
	public static int WALL_TOP_RIGHT_CORNER = 9;
	public static int WALL_BOT_LEFT_CORNER = 10;
	public static int WALL_BOT_RIGHT_CORNER = 11;
	
	public static int RED_TILE_TOP_LEFT = 12;
	public static int RED_TILE_TOP_RIGHT = 13;
	public static int RED_TILE_BOT_LEFT = 14;
	public static int RED_TILE_BOT_RIGHT = 15;
	
	public static int RED_TILE_HOR = 16;
	public static int RED_TILE_VER = 17;
	public static int RED_TILE_VER_VAR = 18;
	public static int RED_TILE_HOR_VAR = 19;
	
	public MapModel(double scale) {
		// TODO Auto-generated constructor stub
		this.x = 0;
		this.y = 0;
		this.nw = 50;
		this.nh = 25;
		this.scale = scale;
	
		this.loadSprites();
	}
	
	public int getNw() {
		return nw;
	}

	public void setNw(int nw) {
		this.nw = nw;
	}

	public int getNh() {
		return nh;
	}

	public void setNh(int nh) {
		this.nh = nh;
	}

	private void loadSprites() {
		tiles = new SpriteModel("/tileset/", this.scale);
		walls = new SpriteModel("/wall/", this.scale);
	}
	
	public Image getTile(int id) {
		return tiles.get(id);
	}
	
	public Image getWall(int id) {
		return walls.get(id);
	}
	
	public double getTileSize() {
		return tiles.getHeight(0);
	}
}
