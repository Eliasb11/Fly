package com.flappybird.model;

import com.flappybird.model.proxy.ProxyImage;
import com.flappybird.view.Game;

public class ItemAgrandir extends Item {

	
	public ItemAgrandir() {
		super(new ProxyImage("/assets/EtoileRoseAgrandit.png"));
	}
	
	
	@Override
	public void appliquer(Game game) {
		Bird bird = game.getBird();
		bird.setProxyImage(new ProxyImage("/assets/ArtikodinGrand.gif"));
		bird.setImage(game.getBird().getProxyImage().loadImage().getImage());
		bird.setWidth(bird.getImage().getWidth(null));
		bird.setHeight(bird.getImage().getHeight(null));
		bird.render(game.getG2(), game);
		this.applique = true;
		this.numTubePasse = game.getTubeColumn().getPoints();
		
	}
	
	
	public void  desactiver(Game game) {
		Bird bird = game.getBird();
		bird.setProxyImage(new ProxyImage("/assets/DracaufeuNormal.gif"));
		bird.setImage(game.getBird().getProxyImage().loadImage().getImage());
		bird.setWidth(bird.getImage().getWidth(null));
		bird.setHeight(bird.getImage().getHeight(null));
		bird.render(game.getG2(), game);
		this.applique = false;
	}

}
