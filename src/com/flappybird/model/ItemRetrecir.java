package com.flappybird.model;

import com.flappybird.model.proxy.ProxyImage;
import com.flappybird.view.Game;

public class ItemRetrecir extends Item {
	
	
	public ItemRetrecir() {
		super(new ProxyImage("/assets/avionGrand.gif"));
	}
	
	
	@Override
	public void appliquer(Game game) {	
		Bird bird = game.getBird();
		game.getBird().setProxyImage(new ProxyImage("/assets/birdGrand.gif"));
		bird.setImage(game.getBird().getProxyImage().loadImage().getImage());
		bird.setWidth(bird.getImage().getWidth(null));
		bird.setHeight(bird.getImage().getHeight(null));
		bird.render(game.getG2(), game);
		this.numTubePasse = game.getTubeColumn().getPoints();
	}
	
	
	
	public void  desactiver(Game game) {
		Bird bird = game.getBird();
		bird.setProxyImage(new ProxyImage("/assets/DracaufeuPetit.gif"));
		bird.setImage(game.getBird().getProxyImage().loadImage().getImage());
		bird.setWidth(bird.getImage().getWidth(null));
		bird.setHeight(bird.getImage().getHeight(null));
		bird.render(game.getG2(), game);
		this.applique = false;
	}
}
