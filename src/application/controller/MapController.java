package application.controller;

import application.model.MapModel;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

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
		this.gc.setFill(Color.BLACK);

		this.gc.setGlobalAlpha(0.5);
		
		Image rightWall = this.map.getWall(MapModel.WALL_RIGHT_EDGE);
		Image leftWall = this.map.getWall(MapModel.WALL_LEFT_EDGE);
		Image rightTopCorner = this.map.getWall(MapModel.WALL_TOP_RIGHT_CORNER);
		Image leftTopCorner = this.map.getWall(MapModel.WALL_TOP_LEFT_CORNER);
		Image rightBotCorner = this.map.getWall(MapModel.WALL_BOT_RIGHT_CORNER);
		Image leftBotCorner = this.map.getWall(MapModel.WALL_BOT_LEFT_CORNER);
		Image botEdgeWall = this.map.getWall(MapModel.WALL_BOT_EDGE);
		
		Image redTileTopLeft = this.map.getWall(MapModel.RED_TILE_TOP_LEFT);
		Image redTileBotLeft = this.map.getWall(MapModel.RED_TILE_BOT_LEFT);
		Image redTileTopRight = this.map.getWall(MapModel.RED_TILE_TOP_RIGHT);
		Image redTileBotRight = this.map.getWall(MapModel.RED_TILE_BOT_RIGHT);
		
		Image redTileHor = this.map.getWall(MapModel.RED_TILE_HOR);
		Image redTileVerVar = this.map.getWall(MapModel.RED_TILE_VER_VAR);
		
		double tileSize = this.map.getTileSize();
		for (int i = 0; i < this.map.getNh(); i++) {
			for (int j = 0; j < this.map.getNw(); j++) {
				Image tile = this.map.getTile(this.tileMap[i][j]);
				
				camera.draw(this.gc, tile, j * this.map.getTileSize(), i * this.map.getTileSize(), this.map.getTileSize(), this.map.getTileSize());
			
				if (i > 0) continue;
				
				this.gc.setGlobalAlpha(1);
				this.gc.fillRect(this.camera.getXMapRelative(j * this.map.getTileSize()), this.camera.getYMapRelative(0), tileSize, tileSize / 2);
				this.gc.setGlobalAlpha(0.65);
				camera.draw(this.gc, redTileHor, 
						j * this.map.getTileSize(), 
						0, 
						tileSize,
						tileSize / 2);
				this.gc.setGlobalAlpha(0.5);
			}
		}
		
		for (int i = 0; i < this.map.getNw(); i++) {
			camera.draw(this.gc, botEdgeWall, i * this.map.getTileSize(), this.map.getNh() * this.map.getTileSize(), this.map.getTileSize(), this.map.getTileSize());
			this.gc.setGlobalAlpha(1);
			this.gc.fillRect(this.camera.getXMapRelative(i * this.map.getTileSize()), this.camera.getYMapRelative(this.map.getNh() * tileSize - (tileSize / 2)), tileSize, tileSize / 2);
			this.gc.setGlobalAlpha(0.65);
			camera.draw(this.gc, redTileHor, 
					i * this.map.getTileSize(), 
					this.map.getNh() * tileSize - (tileSize / 2), 
					tileSize, 
					tileSize / 2);
			this.gc.setGlobalAlpha(0.5);
		}
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < this.map.getNw(); j++) {
				Image wall = this.map.getWall(this.topwallMap[i][j]);
				
				camera.draw(this.gc, wall, j * this.map.getTileSize(), (i + 1) * -this.map.getTileSize(), this.map.getTileSize(), this.map.getTileSize());
			}
		}
		
		for(int i = 0; i < 2; i++) {
			for (int j = -3; j < this.map.getNh() + 1; j++) {
				double dx = i == 0 ? -tileSize : (this.map.getNw() * tileSize);
				double dy = j * tileSize;

				Image wall = leftWall;
				Image tile = redTileVerVar;
				double tileW = tileSize / 2;
						
				if (j == 0) {
					tile = i == 0? redTileTopLeft : redTileTopRight;
					tileW = tileSize;
				}
				else if (j == this.map.getNh() - 1) {
					tile = i == 0? redTileBotLeft : redTileBotRight;
					tileW = tileSize;
				}
				
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
						dx, 
						dy, 
						tileSize, 
						tileSize);
				
				if (j < 0 || j >= this.map.getNh()) continue;
				
				this.gc.setGlobalAlpha(1);
				this.gc.fillRect(this.camera.getXMapRelative(dx + (i == 0 ? tileSize : -tileSize / 2)), this.camera.getYMapRelative(dy), tileSize / 2, tileSize);
				this.gc.setGlobalAlpha(0.65);
				camera.draw(this.gc, tile, 
						dx + (i == 0 ? tileSize : -tileW), 
						dy, 
						tileW, 
						tileSize);
				this.gc.setGlobalAlpha(0.5);
			}
		}
		
		this.gc.setGlobalAlpha(0.25);
		this.gc.setFill(Color.BLACK);
		this.gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
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
