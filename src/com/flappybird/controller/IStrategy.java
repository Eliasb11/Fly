
package com.flappybird.controller;

import java.awt.event.KeyEvent;

import com.flappybird.model.Bird;

/**
 *
 * @author FlyTeam
 */
//interface des controlleurs
public interface IStrategy {
    
    public void controller(Bird bird, KeyEvent kevent);
    public void controllerReleased(Bird bird, KeyEvent kevent);
}
