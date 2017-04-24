package PaSkCode;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

public class PSysView {
    
    PSysView() {
    }

    void draw(PSysModel psm, Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.RED);
		for (int i=0; i<psm.pList.size(); i++) {
			Particle p = psm.pList.get(i);
			g2d.fillOval(p.x-p.radius, p.y-p.radius, p.radius*2, p.radius*2);
		}
    }

    void dump(PSysModel psm, int lc) {
		System.out.println("Frame " + lc);
		for (int i=0; i<psm.pList.size(); i++) {
			Particle p = psm.pList.get(i);
			System.out.println(p.radius + " " + p.x + " " + p.y + " " + p.velX + " " + p.velY);
		}

    }
}
