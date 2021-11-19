package com.flappybird.model;

import java.util.ArrayList;

public class TableauScore {
	
	private ArrayList<PlayerScore> scores; 

	public TableauScore() {
		this.scores = new ArrayList<PlayerScore>();
	}
	
	public void addScore (PlayerScore s) {
		scores.add(s);
	}
	
	public void afficherScore() {
		System.out.println("Tableau de score :");
		for(int i =0; i < this.scores.size(); i++) {
			System.out.println("Pseudo : " +this.scores.get(i).getPseudo() + " Score : " + this.scores.get(i).getPoints());
		}
		
	}
}
