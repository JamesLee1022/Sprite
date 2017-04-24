package PaSkCode;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

class Sprite extends Particle{
    Image sImg;     //image of the sprite
    boolean alive;

    public Sprite(){
        sImg = null;
        radius = 0;
        x = 0;
        y = 0;
        velX = 0;
        velY = 0;
        alive = true;
    }

    public void initSprite(int r, int x, int y, int velX, int velY, Image img){
        this.sImg = img;
        this.radius = r;
        this.x = x;
        this.y = y;
        this.velX = velX;
        this.velY = velY;
    }
	
    public void draw(Graphics2D g){
        g.drawImage(sImg, new AffineTransform(1f,0f,0f,1f,x,y), null);
    }

}