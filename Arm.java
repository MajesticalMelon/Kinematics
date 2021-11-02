import java.awt.Graphics2D;

public class Arm {
    public Joint start;
    public Joint end;

    public Joint axis;

    public double length;

    public Arm(double x, double y, double length) {
        this(x, y, length, 0f);
    }

    public Arm(double x, double y, double length, double angle) {
        start = new Joint(x, y, angle);
        end = new Joint(length, 0, 0);
        this.length = length;
        axis = new Joint(1, 0, 0);
        calculateEndPoints();
    }

    public void draw(Graphics2D g2) {
        g2.fillRect((int)this.start.x - 2, (int)this.start.y - 2, 4, 4);
        g2.fillRect((int)this.end.x - 2, (int)this.end.y - 2, 4, 4);
        g2.drawLine((int)this.start.x, (int)this.start.y, (int)this.end.x, (int)this.end.y);
    }

    public void update() { 
        calculateEndPoints();
    }

    private void calculateEndPoints() {
        // Create a point directly to the right (0 rotation) of the origin
        this.end.x = this.start.x + axis.x * this.length;
        this.end.y = this.start.y + axis.y * this.length;

        this.axis.x = (this.end.x - this.start.x) / this.length;
        this.axis.y = (this.end.y - this.start.y) / this.length;
    }
}