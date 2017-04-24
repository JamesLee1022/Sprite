package PaSkCode;
import java.util.ArrayList;

public class PSysModel {
    // ArrayList of particles
    // each particle: x, y, velX, velY, radius

    ArrayList <Particle> pList;
    
    PSysModel() {
		pList = new ArrayList<Particle>();
    }

    void add(int rad, int x, int y, int vx, int vy) {
		pList.add(new Particle(rad, x, y, vx, vy));
    }

    void update(int bw, int bh) {
		for (int i=0; i<pList.size(); i++) {
			pList.get(i).update(bw, bh);
		}
    }
}
