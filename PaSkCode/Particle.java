package PaSkCode;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Particle {
    int radius;
    int x, y, velX, velY;
    Color color = Color.RED;

    Particle() {
    }

    Particle(int rad, int nx, int ny, int vx, int vy) {
	initParticle(rad, nx, ny, vx, vy);
    }

    void initParticle(int rad, int nx, int ny, int vx, int vy) {
	radius = rad;
	x = nx;
	y = ny;
	velX = vx;
	velY = vy;
    }

    void setRadius(int r) {
	radius = r;
    }

    void update(int bw, int bh) {
	x += velX;
        y += velY;

        if (x >= bw-radius && velX > 0) {
            velX = -velX;
        }
        else if (x < radius && velX < 0) {
            velX = -velX;
        }

        if (y >= bh-radius && velY > 0) {
            velY = -velY;
        }
        else if (y < radius && velY < 0) {
            velY = -velY;
        }

    }

    void draw(Graphics2D g) {
	g.setColor(color);
	g.fillOval(x-radius, y-radius, radius*2, radius*2);
    }

}
