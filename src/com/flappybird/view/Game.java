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

import javax.swing.JPanel;
import javax.swing.Timer;

import com.flappybird.controller.Controller;
import com.flappybird.model.Bird;
import com.flappybird.model.Debug;
import com.flappybird.model.Item;
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
    private int niveau;
    private Debug debug;
    private Item bonus;

    public Game() {
        
    	proxyImage1 = new ProxyImage("/assets/background.png");
        proxyImage2 = new ProxyImage("/assets/test.gif");
        accueil = proxyImage1.loadImage().getImage();
        background = proxyImage2.loadImage().getImage();
        setFocusable(true);
        setDoubleBuffered(false);
        addKeyListener(new GameKeyAdapter());
        Timer timer = new Timer(15, this);
        timer.start();
        debug = new Debug();
        bonus = new Item(400,300);
        bonus.setDx(5);
        bonus.tick();
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
           //La boucle permet d'augmenter le niveau. Bug au niveau de l'iteration 
            while((this.tubeColumn.getPoints()%5) == 0 && this.tubeColumn.getPoints() > 0) {
            	
             niveau = niveau + 1;
             break;
            }
            g2.drawString("Niveau : " +niveau, Window.WIDTH / 2 - 150, 20);
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

        /* Mettre ici tous les elements du debug */
        g2.drawString(this.debug.getIntitule(), 20, 20);
        this.bonus.render(g2,this);
        g.dispose();
    }
    
    /**
     * @author PARDON Alexandre
     * Appeler quand on tape F1
     */
    private void ouvrirDebug() {
    	if (!debug.isActiv()) {
    		debug.setActiv(true);
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

    private void endGame() {
        this.isRunning = false;
        if (this.tubeColumn.getPoints() > highScore) {
            this.highScore = this.tubeColumn.getPoints();
        }
        this.tubeColumn.setPoints(0);
        this.niveau = 0;

    }

    private void checkColision() {
        Rectangle rectBird = this.bird.getBounds();
        Rectangle rectTube;

        for (int i = 0; i < this.tubeColumn.getTubes().size(); i++) {
            Tube tempTube = this.tubeColumn.getTubes().get(i);
            rectTube = tempTube.getBounds();
            if (rectBird.intersects(rectTube)) {
                endGame();
            }
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
