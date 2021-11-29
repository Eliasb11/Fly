/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flappybird.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import com.flappybird.controller.Controller;
import com.flappybird.model.Bird;
import com.flappybird.model.Debug;
import com.flappybird.model.Item;
import com.flappybird.model.ItemAccelerer;
import com.flappybird.model.ItemAgrandir;
import com.flappybird.model.ItemRalentir;
import com.flappybird.model.ItemRetrecir;
import com.flappybird.model.PlayerScore;
import com.flappybird.model.TableauScore;
import com.flappybird.model.Tube;
import com.flappybird.model.TubeColumn;
import com.flappybird.model.proxy.ProxyImage;

/**
 *
 * @author derickfelix
 */
public class Game extends JPanel implements ActionListener {

    private boolean isRunning = false;
    private ProxyImage proxyImage1;
    private ProxyImage proxyImage2;
    private Image accueil;
    private Image background;
    private Bird bird;
    private TubeColumn tubeColumn;
    private int score;
    private int highScore;
    private Debug debug;
    private List<Item> items;
    private TableauScore tscore;
    private Graphics2D g2;
    private boolean invinsible;

    public Game() {
        
        tscore = new TableauScore();
    	proxyImage1 = new ProxyImage("/assets/background.png");
        proxyImage2 = new ProxyImage("/assets/");
        accueil = proxyImage1.loadImage().getImage();
        background = proxyImage2.loadImage().getImage();
        setFocusable(true);
        setDoubleBuffered(false);
        addKeyListener(new GameKeyAdapter());
        Timer timer = new Timer(5, this);
        timer.start();
        debug = new Debug();

     
        /* On cr�e les bonus*/
        /* D'abord on initialise la liste qui va contenir les items */
        items = new ArrayList<>();
        /* On ajoute les items choisis a la liste */
//       items.add(new ItemAgrandir());
//       items.add(new ItemRetrecir());
        items.add(new ItemRalentir());
        items.add(new ItemAccelerer());

        
        
    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        Toolkit.getDefaultToolkit().sync();
        if (isRunning) {
            ////////////////////////////////
            bird.tick();
            tubeColumn.tick(); 
            if (!invinsible) {
            	 checkColision();
            }
           
            checkColisionBonusMalus();
            score++;
            //System.out.println(timer.get);
            
            /////////////////////////////// GESTION DES ITEMS ////////////////
            for (Item item : this.items) {
            	
            	 if(this.tubeColumn.getPoints() - item.getCompteurScore()  == item.getNumPop()) {
            		 /* on active l'item */
            		 item.setActif(true);
            		 item.setCompteurScore(this.tubeColumn.getPoints());
            	 }
            	 
            	 if (item.isActif()) {
            		 item.tick();
            		 item.checkOut();
            	 }           
            	 
            	 /* SI le item est appliqué et qu'on a passé X tube on desactive.*/
            	 if(item.isApplique() && this.tubeColumn.getPoints() - item.getNumTubePasse() >= Item.numDureeTube) {
         			item.desactiver(this);
        		 }
            	 
            }
 
            /////////////////////////////// ///////////////  ////////////////
   
        }

        repaint();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        this.g2 = g2;
        g2.drawImage(accueil, 0, 0, null);
        //d'abord l'accueil s'affiche, et quand on met la variable isRunning a true, le background modifiable s'active
        if (isRunning) {
            ///////////////////////////////
        	g2.drawImage(background, 0, 0, null);
            this.bird.render(g2, this);
            this.tubeColumn.render(g2, this);
            g2.setColor(Color.black);
            g.setFont(new Font("Arial", 1, 20));
            g2.drawString("Votre score : " + this.tubeColumn.getPoints(), 10, 20);
            g2.drawString("Niveau : " +this.tubeColumn.getNiveau(), Window.WIDTH / 2 - 150, 20);
            /* On parcoure les items et on les materialisent (en dehors de la fenetre dans un premir temps)*/
            for(Item item : items) {
            	item.render(g2, this);
            }
            
            ///////////////////////////////
        } else {
            g2.setColor(Color.black);
             g.setFont(new Font("Arial", 1, 20));
            g2.drawString("Appuyez sur la touche 'Entrée' pour jouer", Window.WIDTH / 2 - 150, Window.HEIGHT / 2);
            g2.setColor(Color.black);
            g.setFont(new Font("Arial", 1, 15));
            g2.drawString("Powered by LaFlyTeam", Window.WIDTH - 200, Window.HEIGHT - 50);
        }
        g2.setColor(Color.black);
        g.setFont(new Font("Arial", 1, 20));
        g2.drawString("High Score: " + highScore, Window.WIDTH - 160, 20);
        
        
        /**
         *  @author Belabou Elias
         *  Afficher la focntion debug sur la page d'accueil
         *  et le cacher lorsque la aprtie est lancée
         */
        if (!isRunning) {
        /** @author PARDON Alexandre
        /* Mettre ici tous les elements du debug */
        g2.drawString(this.debug.getIntitule(), 20, 20);
        g.dispose();
        }
    }
    
    /**
     * @author PARDON Alexandre / Belabou Elias
     * Appeler quand on tape F1 / Appel a la fonction FichierTxt qui permet de créer un fichier texte
     */
    private void ouvrirDebug() {
    	if (!debug.isActiv()) {
    		debug.setActiv(true);
    		this.FichierTxt("michel ", highScore);
    		debug.setIntitule(debug.getIntituleCalcule());
    	}else {
    		debug.setActiv(false);
    		debug.setIntitule(debug.getIntituleCalcule());
    	}
    }

    private void restartGame() {
        if (!isRunning) {
        	this.debug.setActiv(false);
        	this.debug.setIntitule(debug.getIntituleCalcule());
            this.isRunning = true;
            this.bird = new Bird(Window.WIDTH / 2, Window.HEIGHT / 2);
            this.tubeColumn = new TubeColumn();
        }
    }

    
   /**
     * Methode privée qui finit la partie, remet les points a zero, et ajoute le score dans un tableau 
     */
    private void endGame() {
    	
        int myScore = this.tubeColumn.getPoints();
        this.tscore.addScore(new PlayerScore("michel",myScore));
        this.tscore.afficherScore();
       
    	
    	this.isRunning = false;
        if (this.tubeColumn.getPoints() > highScore) {
            this.highScore = this.tubeColumn.getPoints();
        }

        this.tubeColumn.setPoints(0);
        
    }

    /**
     * Methode privée qui finit la partie, remet les points a zero, et ajoute le score dans un tableau 
     */
    private void checkColision() {
        Rectangle rectBird = this.bird.getBounds();
        Rectangle rectTube;

        for (int i = 0; i < this.tubeColumn.getTubes().size(); i++) {
            Tube tempTube = this.tubeColumn.getTubes().get(i);
            rectTube = tempTube.getBounds();
            if (rectBird.intersects(rectTube)) {
              if(this.isRunning) {
                	endGame();                	
                }
            }
        }
    }
    
    
    
    /**
     * Methode pour verifier si on passe sur le bonus.
     */
    private void checkColisionBonusMalus() {
    	/* Le recangle correspodant ou */ 
    	Rectangle rectBird = this.bird.getBounds();
    	
    	for (Item item : items) {
	    	if (rectBird.intersects(item.getBounds())){
	    		System.out.println("colision bonus");
	    		item.setActif(false);
	    		item.reset();
	    		item.appliquer(this);
	    		this.bird.render(g2, this);
	    	}
    	}
    }
    
    

    
    /**
     * @author BELABOU Elias
     * Creation d'un fichier texte avec le score et le nom du joueur
     */    
    private void FichierTxt(String nom, int score){
    	try {

    		   String content = nom + score;

    		   File file = new File("test.txt");

    		   // créer le fichier s'il n'existe pas
    		   if (!file.exists()) {
    		    file.createNewFile();
    		   }

    		   FileWriter fw = new FileWriter(file.getAbsoluteFile());
    		   BufferedWriter bw = new BufferedWriter(fw);
    		   bw.write(content); /** Ajouter une fonction avec toutes les stats a la place du content pour avoir 10 lignes de texte**/
    		   bw.close();

    		   System.out.println("Modification terminée!");

    		  } catch (IOException e) {
    		   e.printStackTrace();
    	}
    }
    	


    

    

    // Key
    private class GameKeyAdapter extends KeyAdapter {

        private final Controller controller;

        public GameKeyAdapter() {
            controller = new Controller();
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                restartGame();
            }
        }

        
        @Override
        public void keyReleased(KeyEvent e) {
            if (isRunning) {
                controller.controllerReleased(bird, e);
            }else {
            	 if (e.getKeyCode() == KeyEvent.VK_F1) {
                 	ouvrirDebug();
                 }
            }
    }
    }




	public Bird getBird() {
		return bird;
	}



	public void setBird(Bird bird) {
		this.bird = bird;
	}



	public Graphics2D getG2() {
		return g2;
	}



	public void setG2(Graphics2D g2) {
		this.g2 = g2;
	}



	public TubeColumn getTubeColumn() {
		return tubeColumn;
	}



	public void setTubeColumn(TubeColumn tubeColumn) {
		this.tubeColumn = tubeColumn;
	}



	public boolean isInvinsible() {
		return invinsible;
	}



	public void setInvinsible(boolean invinsible) {
		this.invinsible = invinsible;
	}
    
	
	
    
}
