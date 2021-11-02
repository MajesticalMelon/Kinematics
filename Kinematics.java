import java.util.ArrayList;

import java.awt.*;

public class Kinematics {
    public ArrayList<Arm> arms;

    private Arm first = new Arm(0, 0, 0);
    private Arm last = new Arm(0, 0, 0);

    // Target points
    private Joint target;

    public Kinematics() {
        this(new ArrayList<>());
    }

    public Kinematics(ArrayList<Arm> arms) {
        this.arms = arms;

        // Only grab variables if the list has items
        if (this.arms.size() > 0) {
            last = this.arms.get(this.arms.size() - 1);
            first = this.arms.get(0);

            target = new Joint(last.end.x, last.end.y);
        }
    }

    public void update() {
        for (int i = 0; i < this.arms.size(); i++) {
            // Make sure the arms stay together
            if (i != 0) {
                this.arms.get(i).start = this.arms.get(i - 1).end;
            }

            // Recalcualte endpoints based on any angle changes
            this.arms.get(i).update();
        }
    }

    // Renders all arms of this kinematic object to the screen
    public void draw(Graphics2D g2) {
        for (int i = 0; i < this.arms.size(); i++) {
            // Changes stroke weight based on position in array
            g2.setStroke(new BasicStroke((this.arms.size() - i) * 2));
            this.arms.get(i).draw(g2);
        }
    }

    // Add an arm to this kinematic object
    public void AddArm(Arm arm) {
        // Perform some different assignments for the first arm
        if (this.arms.size() == 0) {
            this.arms.add(arm);
            first = arm;
            last = arm;
            
            target = new Joint(last.end.x, last.end.y);
        } else {
            this.AddArm(arm.length, arm.angle);
        }
    }

    // Mostly used for arms added after the first
    public void AddArm(double length, double angle) {
        Arm arm = new Arm(last.end.x, last.end.y, length, angle);

        this.arms.add(arm);
        last = arm;
        
        target = new Joint(last.end.x, last.end.y);
    }

    // Recursively performs inverse kinematics
    public void inverse(int index) {
        // Break out if there are no more arms left
        if (index < 0) {
            return;
        }

        // Grab the passed in arm
        Arm current = this.arms.get(index);

        // Line from the start joint of current arm to the target point
        double startToEndX = target.x - current.start.x;
        double startToEndY = target.y - current.start.y;

        // Line from the start joint of current arm to the end of the kinematic
        double startToLastX = last.end.x - current.start.x;
        double startToLastY = last.end.y - current.start.y;

        // Angle between the two aforementioned lines
        double angleBetween = Math.atan2(startToEndY, startToEndX) - Math.atan2(startToLastY, startToLastX);

        // Adjust the current arm's angle
        current.angle += angleBetween;

        // Recurse with the arm before the current
        inverse(index - 1);
    }

    // Moves the kinematic chain towards (x, y)
    public void moveToTarget(double x, double y) {
        target.x = x;
        target.y = y;

        // Inverse kinematics
        inverse(this.arms.size() - 1);
    }
}
