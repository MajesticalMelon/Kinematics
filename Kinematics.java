import java.util.ArrayList;

import java.awt.Graphics2D;
import java.text.BreakIterator;

public class Kinematics {
    public ArrayList<Arm> arms;

    private int currentIndex = 0;

    private Arm first = new Arm(0, 0, 0);
    private Arm last = new Arm(0, 0, 0);

    private double endX = 0;
    private double endY = 0;

    private double maxLength = 0;

    public Kinematics() {
        this(new ArrayList<>());
    }

    public Kinematics(ArrayList<Arm> arms) {
        this.arms = arms;

        if (this.arms.size() > 0) {

            endX = (last.end.x - first.start.x);
            endY = (last.end.y - first.start.y);

            last = this.arms.get(this.arms.size() - 1);
            first = this.arms.get(0);
        }
    }

    public void update() {
        for (int i = 0; i < this.arms.size(); i++) {
            // if (i != this.arms.size() - 1) {
            //     this.arms.get(i).end = this.arms.get(i + 1).start;
            // }

            if (i != 0) {
                this.arms.get(i).start = this.arms.get(i - 1).end;
            }

            this.arms.get(i).update();
        }
    }

    public void draw(Graphics2D g2) {
        for (Arm arm : this.arms) {
            arm.draw(g2);
        }
    }

    public void AddArm(Arm arm) {
        if (this.arms.size() == 0) {
            this.arms.add(arm);
            first = arm;
            last = arm;
            endX = last.end.x;
            endY = last.end.y;
        } else {
            this.AddArm(arm.length, arm.start.angle);
        }
    }

    public void AddArm(double length, double angle) {
        Arm arm = new Arm(last.end.x, last.end.y, length, angle);

        this.arms.add(arm);
        last = arm;
        endX = last.end.x;
        endY = last.end.y;

        currentIndex = this.arms.size() - 1;
    }

    public void forward() {
        // Perform forward kinematics
        for (int i = this.arms.size() - 1; i > 0; i--) {
            Arm current = this.arms.get(i);
            Arm prev = this.arms.get(i - 1);

            double cos = Math.cos(current.start.angle);
            double sin = Math.sin(current.start.angle);

            double xnew = prev.axis.x * cos - prev.axis.y * sin;
            double ynew = prev.axis.x * sin + prev.axis.y * cos;

            prev.axis.x = xnew;
            prev.axis.y = ynew;
        }
    }

    public void inverse(int index) {
        if (index < 0) {
            return;
        }

        // Perform inverse kinematics
        Arm current = this.arms.get(index);

        double startToEndX = endX - current.start.x;
        double startToEndY = endY - current.start.y;

        double startToLastX = last.end.x - current.start.x;
        double startToLastY = last.end.y - current.start.y;

        double angleBetween = Math.atan2(startToEndY, startToEndX) - Math.atan2(startToLastY, startToLastX);
        double currentAngle = Math.atan2(current.axis.y, current.axis.x);

        current.axis.x = Math.cos(angleBetween + currentAngle);
        current.axis.y = Math.sin(angleBetween + currentAngle);

        inverse(index - 1);
    }

    public void setTarget(double x, double y) {
        endX = x;
        endY = y;

        inverse(this.arms.size() - 1);
    }
}
