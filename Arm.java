import java.awt.Graphics2D;

public class Arm {
    public Joint start;
    public Joint end;

    public double angle;
    public double length;

    public double worldAngle;

    public Arm child = null;

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
        g2.drawLine((int) this.start.x, (int) this.start.y, (int) this.end.x, (int) this.end.y);
    }

    // Recalculates endpoints ye
    public void update() {
        // Constrain angle
        this.angle = Math.max(Math.min(this.angle, Math.PI / 2), -Math.PI / 2);
        calculateEndPoints();
    }

    // Calculate end points based on angle
    private void calculateEndPoints() {
        worldAngle = this.angle;

        for (Arm current = this.child; current != null; current = current.child) {
            worldAngle += current.angle;
        }

        this.end.x = this.start.x + this.length * Math.cos(worldAngle);
        this.end.y = this.start.y + this.length * Math.sin(worldAngle);
    }
}