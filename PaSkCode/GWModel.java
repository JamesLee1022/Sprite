package PaSkCode;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.security.spec.EllipticCurve;
import java.sql.Array;
import java.util.ArrayList;



public class GWModel extends PSysModel{
    Sprite player;
	ArrayList<Sprite> bots;
    ArrayList<Sprite> graveStones;
    Image graveStone;
    Image projectileImg;
    int playerPoints;

    public GWModel(){
        player = new Sprite();
        bots = new ArrayList<>();
        graveStones = new ArrayList<>();
        playerPoints = 0;
    }
	
	public void addPlayer(int rad, int x, int y, int velX, int velY, Image img){
        player.initSprite(rad, x, y, velX, velY, img);
	}

    public void addBot(int rad, int x, int y, int velX, int velY, Image img){
        Sprite s = new Sprite();
        Image sImg = img.getScaledInstance(rad*2, rad*2, Image.SCALE_DEFAULT);
        s.initSprite(rad, x, y, velX, velY, sImg);
		bots.add(s);
	}

    public void addGraveStone(Image gImg){
        graveStone = gImg;
    }

    public void addProjectileImg(Image pImg){
        projectileImg = pImg;
    }

    public void addProjectile(Sprite p1){
        pList.add(p1);
    }

    public void orientBots(){
        //if the player is dead then do nothing
        if(!player.alive)
            return;

        for(Sprite bot : bots){
            if(bot.alive){
                if(bot.x > player.x){
                    bot.velX = -1;
                }
                else if(bot.x < player.x){
                    bot.velX = 1;
                }
                else{
                    bot.velX = 0;
                }

                if(bot.y > player.y){
                    bot.velY = -1;
                }
                else if(bot.y < player.y){
                    bot.velY = 1;
                }
                else{
                    bot.velY = 0;
                }

                Sprite p1 = new Sprite();
                int vx = 0;
                int vy = 0;
                if(bot.velX > 0) vx = 1;
                else if(bot.velX < 0) vx = -1;

                if(bot.velY > 0) vy = 1;
                else if(bot.velY < 0) vy = -1;

                Image sImg = projectileImg.getScaledInstance(20, 20, Image.SCALE_DEFAULT);
                p1.initSprite(10, bot.x + vx * (bot.radius + 10), bot.y + vy * (bot.radius + 10), bot.velX*2, bot.velY*2, sImg);
                addProjectile(p1);
            }
        }

        

    }

    public void keyPressed(KeyEvent e){
        //if the player is not alive do not do anything
        if(!player.alive) return;

        switch(e.getKeyCode()){
            case KeyEvent.VK_LEFT:
                player.velX = -5;
                break;
            case KeyEvent.VK_RIGHT:
                player.velX = 5;
                break;
            case KeyEvent.VK_DOWN:
                player.velY = 5;
                break;
            case KeyEvent.VK_UP:
                player.velY = -5;
                break;
            case KeyEvent.VK_SPACE:
                Sprite p1 = new Sprite();
                int vx = 0;
                int vy = 0;
                if(player.velX > 0) vx = 1;
                else if(player.velX < 0) vx = -1;

                if(player.velY > 0) vy = 1;
                else if(player.velY < 0) vy = -1;

                Image sImg = projectileImg.getScaledInstance(20, 20, Image.SCALE_DEFAULT);
                p1.initSprite(10, player.x + vx * (player.radius + 10), player.y + vy * (player.radius + 10), player.velX*2, player.velY*2, sImg);
                addProjectile(p1);
                break;
        }
    }

    public void keyReleased(KeyEvent e){
        player.velX = 0;
        player.velY = 0;
    }

    public void checkCollide(){
        ArrayList<Particle> toRemoveProjectile = new ArrayList<>();
        for(Particle projectile : pList){
            //if projectile collide with player
            if(isOverlap(projectile, player)){
                Image sImg = graveStone.getScaledInstance(player.radius*2, player.radius*2, Image.SCALE_DEFAULT);
                player.initSprite(player.radius, player.x, player.y, 0,0,sImg);
                toRemoveProjectile.add(projectile);
                player.alive =  false;
            }

            for(Sprite bot : bots){
                //check if the projectile collide with a bot
                if(isOverlap(projectile, bot)){
                    Image sImg = graveStone.getScaledInstance(bot.radius*2, bot.radius*2, Image.SCALE_DEFAULT);                     
                    bot.sImg = sImg;
                    bot.velX= 0;
                    bot.velY = 0;
                    toRemoveProjectile.add(projectile);
                    
                    if(player.alive && bot.alive) playerPoints += 10;
                    bot.alive = false;
                }
            }

            //if projectile collide with another projectile
            for(Particle p : pList){
                if(p != projectile && isOverlap(p, projectile)){
                    toRemoveProjectile.add(p);
                    toRemoveProjectile.add(projectile);
                }
            }
			
        }

        for(Sprite bot : bots){
            //check if a bot collide with another bot;
            for(Sprite b : bots){
                if(b!=bot && isOverlap(b, bot)){
                    Image sImg = graveStone.getScaledInstance(bot.radius*2, bot.radius*2, Image.SCALE_DEFAULT);                     
                    bot.sImg = sImg;
                    bot.velX= 0;
                    bot.velY = 0;

                    b.sImg = sImg;
                    b.velX= 0;
                    b.velY = 0;
                    
                    if(player.alive && b.alive && bot.alive) playerPoints += 10;
                    else if(player.alive && (b.alive || bot.alive)) playerPoints += 5;
                    b.alive = false;
                }
            }

            //if bot collide with player
            if(isOverlap(bot, player)){
                Image sImg = graveStone.getScaledInstance(player.radius*2, player.radius*2, Image.SCALE_DEFAULT);
                player.initSprite(player.radius, player.x, player.y, 0,0,sImg);
                    
                bot.sImg = sImg;
                bot.velX= 0;
                bot.velY = 0;

                if(player.alive && bot.alive) playerPoints += 10;
                else if(player.alive) playerPoints+=5;
                bot.alive = false;
                player.alive =  false;
                bot.alive = false;
            }
        }

        pList.removeAll(toRemoveProjectile);
    }

    boolean isOverlap(Particle p1, Particle p2){
        int diffX = Math.abs(p1.x - p2.x);
        int diffY = Math.abs(p1.y - p2.y);
        if(diffX < p1.radius+p2.radius && diffY < p1.radius+p2.radius)
            return true;
        else 
            return false;
    }

    void update(int bw, int bh) {
        super.update(bw, bh);
		for (int i=0; i<bots.size(); i++) {
			bots.get(i).update(bw, bh);
		}
        player.update(bw, bh);
    }
}