package application.controller;

import application.model.MapModel;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class MapController extends GameObjectController{
	
	private MapModel map;
	private CameraController camera;
	private Canvas canvas;
	private GraphicsContext gc;
	private int[][] tileMap;
	private int[][] topwallMap;
	
	public MapController(double scale, CameraController camera, Canvas canvas) {
		// TODO Auto-generated constructor stub
		this.map = new MapModel(scale);
		this.camera = camera;
	
		this.canvas = canvas;
		this.gc = canvas.getGraphicsContext2D();
		
		this.generateMap();
	}
	
	private void generateMap() {
		this.tileMap = new int[this.map.getNh()][this.map.getNw()];
		
		for (int i = 0; i < this.map.getNh(); i++) {
			for (int j = 0; j < this.map.getNw(); j++) {
				double chance = Math.random();
				int id = 6;
				
				if (chance > 0.95) {
					id = 2;
				}
				else if (chance > 0.5) {
					id = 4;
				}
				
				
				this.tileMap[i][j] = id;
			}
		}
		
		this.topwallMap = new int[3][this.map.getNw()];
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < this.map.getNw(); j++) {
				double chance = Math.random();
				int id = i == 1 ? MapModel.WALL_LIGHT : MapModel.WALL_DARK;
				
				if (chance > 0.8) {
					id = i == 1 ? MapModel.WALL_LIGHT_VAR : MapModel.WALL_DARK_VAR;
				}
				
				this.topwallMap[i][j] = i == 2 ? MapModel.WALL_TOP_EDGE : id;
			}
		}
	}
	
	public double getMapWidth() {
		return this.map.getTileSize() * this.map.getNw();
	}
	
	public double getMapHeight() {
		return this.map.getTileSize() * this.map.getNh();
	}
	
	@Override
	public void render() {
		// TODO Auto-generated method stub
		this.gc.save();
		this.gc.setGlobalAlpha(0.6);
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < this.map.getNw(); j++) {
				Image wall = this.map.getWall(this.topwallMap[i][j]);
				
				camera.draw(this.gc, wall, j * this.map.getTileSize(), (i + 1) * -this.map.getTileSize(), this.map.getTileSize(), this.map.getTileSize());
			}
		}
		
		Image rightWall = this.map.getWall(MapModel.WALL_RIGHT_EDGE);
		Image leftWall = this.map.getWall(MapModel.WALL_LEFT_EDGE);
		Image rightTopCorner = this.map.getWall(MapModel.WALL_TOP_RIGHT_CORNER);
		Image leftTopCorner = this.map.getWall(MapModel.WALL_TOP_LEFT_CORNER);
		Image rightBotCorner = this.map.getWall(MapModel.WALL_BOT_RIGHT_CORNER);
		Image leftBotCorner = this.map.getWall(MapModel.WALL_BOT_LEFT_CORNER);
		Image botEdgeWall = this.map.getWall(MapModel.WALL_BOT_EDGE);
		
		for(int i = 0; i < 2; i++) {
			for (int j = -3; j < this.map.getNh() + 1; j++) {
				Image wall = leftWall;

				if (j == -3) {
					wall = i == 0? leftTopCorner : rightTopCorner;
				}
				else if (j == this.map.getNh()) {
					wall = i == 0? leftBotCorner : rightBotCorner;
				}
				else {
					wall = i == 0? leftWall : rightWall;
				}
				
				camera.draw(this.gc, wall, 
						i == 0? -this.map.getTileSize() : this.map.getNw() * this.map.getTileSize(), 
						j * this.map.getTileSize(), 
						this.map.getTileSize(), 
						this.map.getTileSize());
			}
		}
		
		for (int i = 0; i < this.map.getNw(); i++) {
			camera.draw(this.gc, botEdgeWall, i * this.map.getTileSize(), this.map.getNh() * this.map.getTileSize(), this.map.getTileSize(), this.map.getTileSize());
		}
	
		this.gc.setGlobalAlpha(0.5);
		
		for (int i = 0; i < this.map.getNh(); i++) {
			for (int j = 0; j < this.map.getNw(); j++) {
				Image tile = this.map.getTile(this.tileMap[i][j]);
				
				camera.draw(this.gc, tile, j * this.map.getTileSize(), i * this.map.getTileSize(), this.map.getTileSize(), this.map.getTileSize());
			}
		}
		this.gc.restore();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCollide() {
		// TODO Auto-generated method stub
		
	}

}
