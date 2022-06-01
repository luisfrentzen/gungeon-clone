package application.model;

public class PlayerModel extends CharacterModel{

	public PlayerModel(int x, int y, int speed, int damage) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		this.w = 50;
		this.h = 50;
		this.speed = speed;
		this.damage = damage;
		
		this.hp = 3;
	}

}
