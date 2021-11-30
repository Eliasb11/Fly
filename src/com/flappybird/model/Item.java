package com.flappybird.model;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.Random;

import com.flappybird.model.proxy.ProxyImage;
import com.flappybird.view.Game;

/**
 * 
 * @author Alexandre Pardon
 *
 */
public class Item extends GameObject {

	protected ProxyImage proxyImage;
	
	/*Numero qui indique tous les combiens de tube (score) apparait l'item  */
	protected Integer numPop;
	
	/* Numero qui indique la durée d'activation d'un item en nombre de tube passé  */
	protected Integer numTubePasse;
	
	
	/* Indique si l'item est actif (affiché a l'ecran)*/
	protected boolean actif;
	
	/* Indique si le pouvoir de l'item est appliqué en jeux */
	protected boolean applique;
	
	/* Le score du dernier pop de l'item */
	protected int compteurScore;
	

	/* Durée du pouvoir - Par défaut il dure 2 tubes */
	protected int numDureeTube = 2;
	
	
	/* Position initiale des items sur l'axe X (caché en dehors de la fenetre) */
	public static final int initialX = 800;
	
	
	/**
	 * Constructeur grace a une image choisie
	 * @param proxyImage
	 */
	public Item(ProxyImage proxyImage) {
		this.image = proxyImage.loadImage().getImage();
		/* Le hitBox prend la taille de l'image */
		this.width = image.getWidth(null);
		this.height = image.getHeight(null);
		
		/* Position */
        this.x = initialX+getRandomPixel() ;
        this.y = getRandomPixel();
        
        /* Vitesse de l'item */
        this.dx = 5;
        
        /* On fait apparaitre l'item en Random */
        numPop = getRandomPop();
        
        /* Initialisation du compteur de tube passé.*/
        numTubePasse = 0;
	}
	
	
	
	
	
	/************* ACCESSEURS *******************/
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


	public int getNumDureeTube() {
		return numDureeTube;
	}


	public void setNumDureeTube(int numDureeTube) {
		this.numDureeTube = numDureeTube;
	}
	
	
	
	/************** Autres Fonctions *****************/
	
	/**
	 * Methode de base pour faire apparaitre un objet
	 */
	@Override
	public void render(Graphics2D g, ImageObserver obs) {
		g.drawImage(image, x, y, obs);
		
	}
	
	/**
	 * Methode de base pour mettre en mouvement l'objet
	 */
	@Override
	public void tick() {
		this.x -= dx;
	}
	
	
	/**
	 * Fonction qui retourne le rectangle (hitBox) correspondant a l'item
	 * @return
	 */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    
	
	/**
	 * Remet l'item en position initiale en dehors du background
	 *  et calcule une plage aléatoire pour sa prochaine apparition sur l'axe y
	 */
	public void reset() {
		this.x = initialX+getRandomPixel();
		this.y = getRandomPixel();
		System.out.println("reset a = "+ this.y);
		
		//A activer si on souhaite avoir un pop aleatoire a chaque item passé.
		//this.numPop = getRandomPop();
	}
	
	
	/**
	 * Génere un entier aléatoire dans la plage souhaité pour la position de l'item su l'axe Y
	 * @return int
	 */
	public static int getRandomPixel() {
		return new Random().nextInt(400 - 200 + 1) + 200;
	}
	
	
	/**
	 * Genere un entier aleatoire dans la plage souhaité pour géré le pop des items.
	 * @return
	 */
	protected int getRandomPop() {
		return new Random().nextInt(10 - 2 + 1) + 2;
	}

	
	/* Methode qui applique le pouvoir de l'item */
	/* On crée ici la methode vide, pour pouvoir l'override (réecrire) dans les classes filles */  
	public void appliquer(Game game) {
	}
	
	
	/* Methode qui desactive le pouvoir de l'item */
	/* On crée ici la methode vide, pour pouvoir l'override (réecrire) dans les classes filles */  
	public void desactiver(Game game) {
	}
	
	
	
	/**
	 * Methode appelée pour verifier si l'item est sortie de l'ecran.
	 * Si il est sorti, on le desactive et on le reset.
	 */
	public void checkOut() {
		if (this.x < 0) {
			this.actif = false;		
			this.reset();
		}		
		
	}



	
	
}
