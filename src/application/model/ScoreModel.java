package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ScoreModel {
	private final StringProperty name = new SimpleStringProperty();
	private final IntegerProperty score = new SimpleIntegerProperty();
	
	public ScoreModel(String name, int score) {
		// TODO Auto-generated constructor stub
		this.name.set(name);
		this.score.set(score);;
	}

	public String getName() {
		return name.get();
	}

	public int getScore() {
		return score.get();
	}
	
	public void setName(String name) {
		this.name.set(name);
	}
	
	public void setScore(int score) {
		this.score.set(score);
	}
}
