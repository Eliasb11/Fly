package com.flappybird.model;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.Random;

import com.flappybird.model.proxy.ProxyImage;
import com.flappybird.view.Window;

public class Item extends GameObject {

	private ProxyImage proxyImage;
	int randNumb = 0;
	int squareWidth = 25;
	int squareHeight = 25;
	int squareYLocation = -squareHeight;

	/* Position initiale de l'item. */
	public static final int initialX = 780;
	
	public Item() {
		this(initialX, getRandomInt()); 
	}
	
	public Item(int x, int y) {
		super(x, y);
		if (proxyImage == null) {
			proxyImage = new ProxyImage("/assets/avionGrand.gif");

		}
		this.image = proxyImage.loadImage().getImage();
		this.width = image.getWidth(null);
		this.height = image.getHeight(null);
		this.dx = 5;
	}
	@Override
	public void render(Graphics2D g, ImageObserver obs) {
		g.drawImage(image, x, y, obs);
		
	}
	@Override
	public void tick() {
		// TODO Stub de la méthode généré automatiquement
		this.x -= dx;
	}
	
	
	/**
	 * Remet l'item en position initiale en dehors du background
	 *  et calcule une plage aléatoire pour sa prochaine apparition sur l'axe y
	 */
	public void reset() {
		this.x = initialX;
		this.y = getRandomInt();
	}
	/**
	 * Génere un entier aléatoire 
	 * @return int
	 */
	public static int getRandomInt() {
		return new Random().nextInt(400 - 200 + 1) + 200;
	}

	/*@Override
	    public void tick() {
	        this.x -= dx;
	    }

	    public void generateRandomNumber() {
	        Random rand = new Random();
	        randNumb = rand.nextInt(Window.WIDTH - squareWidth);
	        numberCreated = true;
	    }

	    //paints a black screen, then paints a rectangle on top of the black screen
	    public void paint(Graphics g) {
	        g.setColor(Color.black);
	        g.fillRect(0, 0, windowWidth, windowHeight);
	        g.setColor(Color.BLUE);
	        g.fillRect(randNumb, squareYLocation, squareWidth, squareHeight);
	    }

	    public void update() {

	        //calls the generateRandomNumber() method which gives the square a random x value inside the screen
	        if (!numberCreated) {
	            generateRandomNumber();
	        }
	        //moves the squares y coordinate towards the bottom of the screen and stops once it hits the bottom
	        if (squareYLocation <= windowHeight) {
	            squapublic void generateRandomNumber() {
	                Random rand = new Random();
	                randNumb = rand.nextInt(windowWidth - squareWidth);
	                numberCreated = true;
	            }

	            //paints a black screen, then paints a rectangle on top of the black screen
	            public void paint(Graphics g) {
	                g.setColor(Color.black);
	                g.fillRect(0, 0, windowWidth, windowHeight);
	                g.setColor(Color.BLUE);
	                g.fillRect(randNumb, squareYLocation, squareWidth, squareHeight);
	            }

	            public void update() {

	                //calls the generateRandomNumber() method which gives the square a random x value inside the screen
	                if (!numberCreated) {
	                    generateRandomNumber();
	                }
	                //moves the squares y coordinate towards the bottom of the screen and stops once it hits the bottom
	                if (squareYLocation <= windowHeight) {
	                	public void generateRandomNumber() {
	                        Random rand = new Random();
	                        randNumb = rand.nextInt(windowWidth - squareWidth);
	                        numberCreated = true;
	                    }

	                    //paints a black screen, then paints a rectangle on top of the black screen
	                    public void paint(Graphics g) {
	                        g.setColor(Color.black);
	                        g.fillRect(0, 0, windowWidth, windowHeight);
	                        g.setColor(Color.BLUE);
	                        g.fillRect(randNumb, squareYLocation, squareWidth, squareHeight);
	                    }

	                    public void update() {

	                        //calls the generateRandomNumber() method which gives the square a random x value inside the screen
	                        if (!numberCreated) {
	                            generateRandomNumber();
	                        }
	                        //moves the squares y coordinate towards the bottom of the screen and stops once it hits the bottom
	                        if (squareYLocation <= windowHeight) {
	                            squareYLocation++;

	                            //resets the x and y location to a new position
	                        } else {
	                            numberCreated = false;
	                            squareYLocation = -squareHeight;
	                        }
	                    }

	                    //sets the while loop to true to start the game
	                    public void start() {
	                        gameRunning = true;
	                    }     squareYLocation++;

	                    //resets the x and y location to a new position
	                } else {
	                    numberCreated = false;
	                    squareYLocation = -squareHeight;
	                }
	            }

	            //sets the while loop to true to start the game
	            public void start() {
	                gameRunning = true;
	            }reYLocation++;

	            //resets the x and y location to a new position
	        } else {
	            numberCreated = false;
	            squareYLocation = -squareHeight;
	        }
	    }

	    //sets the while loop to true to start the game
	    public void start() {
	        gameRunning = true;
	    }
	    @Override
	    public void render(Graphics2D g, ImageObserver obs) {
	        g.drawImage(image, x, y, obs);

	    }


	    public Rectangle getBounds() {
	        return new Rectangle(x, y, width, height);
	    }*/
}
