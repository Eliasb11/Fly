package com.flappybird.model;

import com.flappybird.model.proxy.ProxyImage;
import com.flappybird.view.Game;

public class ItemRalentir extends Item {
	public ItemRalentir() {
		super(new ProxyImage("/assets/EtoileBleueRalentit.png"));
	}
	@Override
	public void appliquer(Game game) {
		game.getTubeColumn().setSpeed(3);
		this.applique = true;
		this.numTubePasse = game.getTubeColumn().getPoints();
	}
	
	
	@Override
	public void desactiver(Game game) {
		int vitesseActuelle = game.getTubeColumn().getSpeed();
		game.getTubeColumn().setSpeed(5);
		this.applique = false;
	}
}

