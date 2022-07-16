package application.controller;

import application.MainApplication;
import application.model.HudModel;
import application.model.SpriteModel;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class HudController extends GameObjectController {

	private CameraController camera;
	private Canvas canvas;
	private GraphicsContext gc;
	private PlayerController player;

	private HudModel hud;
	
	private int pAmmo;
	private int pMaxAmmo;
	
	private int pHeart;
	private int pMaxHeart;

	public HudController(CameraController camera, Canvas canvas, PlayerController player) {
		// TODO Auto-generated constructor stub
		this.hud = new HudModel();

		this.camera = camera;
		this.canvas = canvas;
		this.gc = canvas.getGraphicsContext2D();
		this.player = player;
		
		this.pAmmo = player.getAmmo();
		this.pMaxAmmo = player.getAmmoCap();
		
		this.pHeart = player.getPlayerHp();
		this.pMaxHeart = player.getPlayerMaxHp();
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		double xratio = this.hud.getWeapon().getWidth(0);
		double yratio = this.hud.getWeapon().getHeight(0);
		
		this.gc.drawImage(this.hud.getWeapon().get(0), 
				MainApplication.W - xratio * 1.35,
				MainApplication.H - this.hud.getWeapon().getHeight(0) * 1.1, 
				xratio,
				yratio);

		this.gc.drawImage(this.hud.getAmmoCount().get(0), 
				MainApplication.W - this.hud.getAmmoCount().getWidth(0) - (xratio * 0.35),
				MainApplication.H - (yratio * 1.2) - this.hud.getAmmoCount().getHeight(0), 
				this.hud.getAmmoCount().getWidth(0),
				this.hud.getAmmoCount().getHeight(0));
		
		double bulY = MainApplication.H - (yratio * 0.1) - this.hud.getAmmo().getHeight(0);
		double bulYOffset = this.hud.getAmmo().getHeight(0);
		Image bulIm = this.hud.getAmmo().get(0);
		
		for (int i = 0; i < this.pMaxAmmo + 2; i++) {
			if (i == 0 || i == this.pMaxAmmo + 1) {
				bulIm = this.hud.getAmmoBorder().get(0);
			}
			else if (i > this.pAmmo){
				bulIm = this.hud.getAmmoEmpty().get(0);
			}
			else {
				bulIm = this.hud.getAmmo().get(0);
			}
			
			this.gc.drawImage(bulIm,
					MainApplication.W - (xratio * 0.1) - this.hud.getAmmo().getWidth(0),
					bulY,
					this.hud.getAmmo().getWidth(0),
					i == this.pMaxAmmo + 1? -this.hud.getAmmo().getHeight(0):this.hud.getAmmo().getHeight(0));
			
			if (i < this.pMaxAmmo) {
				bulY -= bulYOffset;
			}
		}
		
		int tlHp = this.pHeart / 2;
		int remHp = this.pHeart % 2;
		
		SpriteModel hBig = this.hud.getHeartSprite(HudModel.HEART_IDLE_BIG);
		SpriteModel hEmp = this.hud.getHeartSprite(HudModel.HEART_IDLE_EMPTY);
		SpriteModel hHalf = this.hud.getHeartSprite(HudModel.HEART_IDLE_SMALL);
		double hrtX = 0;
		double hrtXOffset = hBig.getWidth(0) + xratio * 0.05;
		
		for (int i = 0; i < this.pMaxHeart / 2; i++) {
			this.gc.drawImage(hEmp.get(0),
					(xratio * 0.1) + hrtX,
					(yratio * 0.1),
					hBig.getWidth(0),
					hBig.getHeight(0));
			
			if (i < tlHp) {
				this.gc.drawImage(hBig.get(0),
						(xratio * 0.1) + hrtX,
						(yratio * 0.1),
						hBig.getWidth(0),
						hBig.getHeight(0));
			}
			else if (remHp > 0) {
				this.gc.drawImage(hHalf.get(0),
						(xratio * 0.1) + hrtX,
						(yratio * 0.1),
						hBig.getWidth(0),
						hBig.getHeight(0));
				
				remHp -= 1;
			}
			
			hrtX += hrtXOffset;
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		this.pAmmo = this.player.getAmmo();
		this.pHeart = this.player.getPlayerHp();
	}

	@Override
	public void onCollide() {
		// TODO Auto-generated method stub

	}

}
