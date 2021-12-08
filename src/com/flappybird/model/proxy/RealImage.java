
package com.flappybird.model.proxy;

import javax.swing.ImageIcon;

/**
 *
 * @author FlyTeam
 */
public class RealImage implements IImage {

    private final String src;
    private ImageIcon imageIcon;
    
    /**
     * @param src
     */
    public RealImage(String src) {
        this.src = src;
    }
    //@alain Methode permettant d'attribuer le type ImageIcon à une image chargée a partir d'une URL 
    @Override
    public ImageIcon loadImage() {
        if(imageIcon == null) {
            this.imageIcon = new ImageIcon(getClass().getResource(src));
        }
        return imageIcon;
    }
    
}
