package application.model;

public class PlayerModel extends CharacterModel{

	public PlayerModel(double x, double y) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		this.w = 50;
		this.h = 50;
		this.speed = 15;
//		this.damage = damage;
		
		this.hp = 3;
	}


}
