package PaSkCode;

import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GWView extends PSysView{
	void draw(GWModel gw, Graphics g){
		Graphics2D g2 =(Graphics2D)g;

		//draw player
		gw.player.draw(g2);

		g2.setColor(Color.WHITE);
		g2.drawString("Player Points : " + gw.playerPoints, 10, 10);

		//draw all bots
		for(Sprite bot:gw.bots){
			bot.draw(g2);
		}

		//draw all sprites
		for(Particle projectile:gw.pList){
			projectile.draw(g2);
		}

		//draw all gravestones
		for(Sprite graveStone:gw.graveStones){
			graveStone.draw(g2);
		}
	}
}