import java.awt.Graphics2D;

public class Arm {
    public static double GRAVITY = 0.01;
    public Joint start;
    public Joint end;

    public double angle;
    public double length;

    public double worldAngle;

    public Arm child = null;

    public boolean fixed;

    public Joint velocity;

    public Arm(double x, double y, double length) {
        this(x, y, length, 0f);
    }

    public Arm(double x, double y, double length, double angle) {
        this.start = new Joint(x, y);
        this.end = new Joint(length, 0);
        this.length = length;
        this.angle = angle;
        this.velocity = new Joint(0, 0);
        this.fixed = false;
        calculateEndPoints();
    }

    // Draw a line with a point on either end
    public void draw(Graphics2D g2) {
        g2.drawLine((int) this.start.x, (int) this.start.y, (int) this.end.x, (int) this.end.y);
    }

    // Recalculates endpoints and constrains the arms rotation
    public void update() {
        calculateEndPoints();
    }

    // Calculate end points based on angle
    private void calculateEndPoints() {
        // Start the world angle calculation
        // this.angle = Math.max(Math.min(this.angle, Math.PI / 4), -Math.PI / 4);
        worldAngle = this.angle;

        // Move toward the root, and add onto the world angle
        for (Arm current = this.child; current != null; current = current.child) {
            worldAngle += current.angle;
        }

        // Use world angle to perfom arm rotation
        if (!this.fixed) {
            this.end.x = this.start.x + this.length * Math.cos(worldAngle);
            this.end.y = this.start.y + this.length * Math.sin(worldAngle);
        }
    }
}