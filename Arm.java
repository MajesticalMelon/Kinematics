import java.awt.Graphics2D;

public class Arm {
    public Joint start;
    public Joint end;

    public double angle;

    public double length;

    public Arm(double x, double y, double length) {
        this(x, y, length, 0f);
    }

    public Arm(double x, double y, double length, double angle) {
        this.start = new Joint(x, y);
        this.end = new Joint(length, 0);
        this.length = length;
        this.angle = angle;
        calculateEndPoints();
    }

    // Draw a line with a point on either end
    public void draw(Graphics2D g2) {
        g2.fillRect((int)this.start.x - 2, (int)this.start.y - 2, 4, 4);
        g2.fillRect((int)this.end.x - 2, (int)this.end.y - 2, 4, 4);
        g2.drawLine((int)this.start.x, (int)this.start.y, (int)this.end.x, (int)this.end.y);
    }

    // Recalculates endpoints
    public void update() { 
        calculateEndPoints();
    }

    // Calculate end points based on angle
    private void calculateEndPoints() {
        this.end.x = this.start.x + this.length * Math.cos(this.angle);
        this.end.y = this.start.y + this.length * Math.sin(this.angle);
    }
}