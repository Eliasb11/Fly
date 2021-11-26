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

import javax.swing.JPanel;
import javax.swing.Timer;

import com.flappybird.controller.Controller;
import com.flappybird.model.Bird;
import com.flappybird.model.Debug;
import com.flappybird.model.Item;
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
    private Item bonus;
    private TableauScore tscore;
    private int points;
    private boolean bonusActif;

    public Game() {
        
        tscore = new TableauScore();
    	proxyImage1 = new ProxyImage("/assets/background.png");
        proxyImage2 = new ProxyImage("/assets/");
        accueil = proxyImage1.loadImage().getImage();
        background = proxyImage2.loadImage().getImage();
        setFocusable(true);
        setDoubleBuffered(false);
        addKeyListener(new GameKeyAdapter());
        Timer timer = new Timer(15, this);
        timer.start();
        debug = new Debug();
        /* Init compteur */
        points = 0;
        bonusActif = false;
        /* On crée le bonus*/
        bonus = new Item();
 
        
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Toolkit.getDefaultToolkit().sync();
        if (isRunning) {
            ////////////////////////////////
            bird.tick();
            tubeColumn.tick(); 
            checkColision();
            score++;
            
            if(this.tubeColumn.getPoints() - points  == 3) {
            	bonusActif = true;
            	points = this.tubeColumn.getPoints(); 
            }
            
            if (bonusActif) {
            	//Le bonus est actif on le met en mouvement car il est en dehors du background//
            	bonus.tick();
            }
            // Une fois le bonus en dehors du background 
            if(bonus.getX() < 0) {
            	// On désactive le bonus
            	bonusActif = false;
            	// On réinitialise le bonus 
            	bonus.reset();
            }
            
            
            ///////////////////////////////
        }

        repaint();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
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
            this.bonus.render(g2,this);
            
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
         *  et le cacher lorsque la aprtie est lancÃ©e
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
     * Appeler quand on tape F1 / Appel a la fonction FichierTxt qui permet de crÃ©er un fichier texte
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
     * Methode privÃ©e qui finit la partie, remet les points a zero, et ajoute le score dans un tableau 
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
     * Methode privÃ©e qui finit la partie, remet les points a zero, et ajoute le score dans un tableau 
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
     * @author BELABOU Elias
     * Creation d'un fichier texte avec le score et le nom du joueur
     */    
    private void FichierTxt(String nom, int score){
    	  try {

    		   String content = nom + score;

    		   File file = new File("test.txt");

    		   // crÃ©er le fichier s'il n'existe pas
    		   if (!file.exists()) {
    		    file.createNewFile();
    		   }

    		   FileWriter fw = new FileWriter(file.getAbsoluteFile());
    		   BufferedWriter bw = new BufferedWriter(fw);
    		   bw.write(content); /** Ajouter une fonction avec toutes les stats a la place du content pour avoir 10 lignes de texte**/
    		   bw.close();

    		   System.out.println("Modification terminÃ©e!");

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
}
