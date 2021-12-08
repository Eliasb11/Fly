
package com.flappybird.model.proxy;

import com.flappybird.model.proxy.RealImage;
import javax.swing.ImageIcon;

/**
 *
 * @author FlyTeam
 */

public class ProxyImage implements IImage {

    private final String src;
    private RealImage realImage;
    
    /**
     * @param src
     */
    public ProxyImage(String src) {
        this.src = src;
    }
    //@alain methode permettant de verifier si l'objet realimage contient bien une valeur, un sorte de proxy
    @Override
    public ImageIcon loadImage() {
        if(realImage == null) {
            this.realImage = new RealImage(src);
        }
        
        return this.realImage.loadImage();
    }
    
}
