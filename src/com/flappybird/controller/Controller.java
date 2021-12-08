
package com.flappybird.controller;

import java.awt.event.KeyEvent;

import com.flappybird.model.Bird;

/**
 *
 * @author FlyTeam
 */
public class Controller implements IStrategy {

    @Override
    public void controller(Bird bird, KeyEvent kevent) {
    }

    //a chaque appui sur la barre d'espace, l'oiseau saute
    @Override
    public void controllerReleased(Bird bird, KeyEvent kevent) {
        if(kevent.getKeyCode() == KeyEvent.VK_SPACE) {
            bird.jump();
        }
    }
    
}
