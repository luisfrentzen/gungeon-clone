package application.controller;

import application.model.SpriteModel;
import application.model.VFXModel;
import application.view.SceneView;
import javafx.geometry.Point2D;
import javafx.scene.effect.ColorAdjust;

public abstract class CharacterController extends GameObjectController{
	protected SceneView scene;
	
	protected int drawTick;
	protected int globalTick;
	
	protected SpriteModel sprites;
	protected SpriteModel hand;
	protected SpriteModel pistol;
	protected VFXModel flare;
	
	protected int[] currentVector;
	protected double currentAngle;

	protected double shootX;
	protected double shootY;
	
	protected double handX;
	protected double handY;
	
	protected BarrierController barrier;
	
	public abstract void move(double x, double y);
	
	protected ColorAdjust colorHit;
	protected ColorAdjust colorDead;
	
	public Point2D getRotated(double ang, double pointX, double pointY, double pivotX, double pivotY) {
		double newX = pivotX + (pointX - pivotX) * Math.cos(Math.toRadians(ang)) - (pointY - pivotY)* Math.sin(Math.toRadians(ang));
		double newY = pivotY + (pointX - pivotX) * Math.sin(Math.toRadians(ang)) + (pointY - pivotY)* Math.cos(Math.toRadians(ang));
		return new Point2D(newX, newY);
	}

}
