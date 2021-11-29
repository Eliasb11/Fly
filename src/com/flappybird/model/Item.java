package com.flappybird.model;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.Random;
import com.flappybird.model.proxy.ProxyImage;
import com.flappybird.view.Game;


public class Item extends GameObject {

	protected ProxyImage proxyImage;
	/*Numero qui indique tous les combiens de tube (score) apparait l'item  */
	protected Integer numPop;
	/* Numero qui la durée d'activation d'un item en nombre de tube passé  */
	protected Integer numTubePasse;
	
	
	/* Indique si l'item est actif */
	protected boolean actif;
	
	protected boolean applique;
	
	/* Le score du dernier pop */
	protected int compteurScore;
	
	/* Position initiale de l'item. */
	public static final int initialX = 780;
	/* Par défaut un bonus dure 2 tubes */
	public static final int numDureeTube = 2;
	
	public Item(ProxyImage proxyImage) {
		this.image = proxyImage.loadImage().getImage();
		this.width = image.getWidth(null);
		this.height = image.getHeight(null);
        this.x = initialX;
        this.y = getRandomHeight();
        this.dx = 5;
        numPop = 1;
        numPop = getRandomPop();
        numTubePasse = 0;
	}
	
	
	@Override
	public void render(Graphics2D g, ImageObserver obs) {
		g.drawImage(image, x, y, obs);
		
	}
	@Override
	public void tick() {
		// TODO Stub de la m�thode g�n�r� automatiquement
		this.x -= dx;
	}
	
	
	/**
	 * Fonction qui retourne le rectangle correspondant a l'item
	 * @return
	 */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    
	
	/**
	 * Remet l'item en position initiale en dehors du background
	 *  et calcule une plage al�atoire pour sa prochaine apparition sur l'axe y
	 */
	public void reset() {
		this.x = initialX;
		this.y = getRandomHeight();
		System.out.println("reset a = "+ this.y);
		
		//A activer si on souhaite avoir un pop aleatoire a chaque item pass�.
		//this.numPop = getRandomPop();
	}
	/**
	 * G�nere un entier al�atoire pour la position de l'item su l'axe Y
	 * @return int
	 */
	public static int getRandomHeight() {
		return new Random().nextInt(400 - 200 + 1) + 200;
	}
	
	
	/**
	 * Genere un entier aleatoire pour g�r� le pop des items.
	 * @return
	 */
	protected int getRandomPop() {
		return new Random().nextInt(3 - 1 + 1) + 1;
	}

	
	/* On crée ici la methode vide, pour pouvoir l'override (réecrire) dans les classe fille */  
	public void appliquer(Game game) {
	}
	
	
	public void desactiver(Game game) {
	}
	
	
	public Integer getNumPop() {
		return numPop;
	}


	public void setNumPop(Integer numPop) {
		this.numPop = numPop;
	}


	public boolean isActif() {
		return actif;
	}


	public void setActif(boolean actif) {
		this.actif = actif;
	}


	public int getCompteurScore() {
		return compteurScore;
	}


	public void setCompteurScore(int compteurScore) {
		this.compteurScore = compteurScore;
	}
	
	/**
	 * Methode appel pour verifier si l'item est sortie de l'ecran.
	 * Si il est sorti, on le desactive et on le reset.
	 */
	public void checkOut() {
		if (this.x < 0) {
			this.actif = false;		
			this.reset();
		}		
		
	}


	public Integer getNumTubePasse() {
		return numTubePasse;
	}


	public void setNumTubePasse(Integer numTubePasse) {
		this.numTubePasse = numTubePasse;
	}


	public boolean isApplique() {
		return applique;
	}


	public void setApplique(boolean applique) {
		this.applique = applique;
	}
	
	
	
	
}
