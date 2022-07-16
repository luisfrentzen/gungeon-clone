package application.controller;

import java.util.Vector;

import application.model.PlayerProjectileModel;
import application.model.ProjectileModel;

public abstract class ProjectileController extends GameObjectController{

	protected Vector<ProjectileModel> projectiles;
	protected int bulletIndex;
	protected int nBullets;
	protected BarrierController barrier;

	public abstract void initBullets(int n);
	
	public void shootBullet(double oX, double oY, double tX, double tY) {
		ProjectileModel pp = this.projectiles.get(this.bulletIndex++ % this.nBullets);

		tX += (Math.random() * pp.getSpray()) - (pp.getSpray() / 2);
		tY += (Math.random() * pp.getSpray()) - (pp.getSpray() / 2);

		pp.setActive(true);
		pp.setX(oX);
		pp.setY(oY);
		pp.setTargetX(tX);
		pp.setTargetY(tY);

		pp.calculateVectors();
	}
	
	public void bulletHit(ProjectileModel p) {
		this.addVFX(PlayerProjectileModel.PATH_IMPACT, 3, p.getX(),
				p.getY(), 1);
		p.resetPosition();
	}
}
