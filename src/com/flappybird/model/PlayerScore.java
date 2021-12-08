package com.flappybird.model;

/**
 * 
 * @author Alain Habyalimana
 *
 */
public class PlayerScore {

	private String pseudo;
	private int points;
	
	
	public PlayerScore(String pseudo, int points) {
		this.pseudo = pseudo;
		this.points = points;
	}
	
	public String getPseudo () {
		return this.pseudo;
	}
	
	public int getPoints () {
		return this.points;
	}
}
