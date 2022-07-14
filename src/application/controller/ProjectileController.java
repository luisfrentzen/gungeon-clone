package application.controller;

import java.util.Vector;

import application.model.ProjectileModel;

public abstract class ProjectileController extends GameObjectController{

	protected Vector<ProjectileModel> projectiles;
	protected int bulletIndex;
	protected int nBullets;
	protected BarrierController barrier;

	public abstract void initBullets(int n);
}
