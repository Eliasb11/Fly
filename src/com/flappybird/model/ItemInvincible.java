package com.flappybird.model;

import com.flappybird.model.proxy.ProxyImage;
import com.flappybird.view.Game;

/**
 * 
 * @author Alexandre Pardon
 *
 */
public class ItemInvincible extends Item {
	
	public ItemInvincible() {
		super(new ProxyImage("/assets/EtoileInvincible.png"));
		numDureeTube = 5;
	}
	
	
	@Override
	public void appliquer(Game game) {
		game.setInvincible(true);
		this.applique = true;
		this.numTubePasse = game.getTubeColumn().getPoints();
	}
	
	
	@Override
	public void desactiver(Game game) {
		game.setInvincible(false);
		this.applique = false;
	}
	
	
}
