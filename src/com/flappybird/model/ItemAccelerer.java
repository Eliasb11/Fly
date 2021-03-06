package com.flappybird.model;

import com.flappybird.model.proxy.ProxyImage;
import com.flappybird.view.Game;


/**
 * 
 * @author Alexandre Pardon
 *
 */
public class ItemAccelerer extends Item {
	public ItemAccelerer() {
		super(new ProxyImage("/assets/EtoileJauneAccelere.png"));
	}
	
	
	@Override
	public void appliquer(Game game) {
		game.getTubeColumn().setSpeed(7);
		/* Quand on active le tube on recup les points */
		this.numTubePasse = game.getTubeColumn().getPoints();
		this.applique = true;
	}
	
	
	@Override
	public void desactiver(Game game) {
		game.getTubeColumn().setSpeed(5);
		this.applique = false;
	}
}
