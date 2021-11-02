import java.awt.*;
import java.awt.event.*;

import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.MouseInputListener;

public class Canvas extends JPanel implements ActionListener, MouseInputListener {
    private Point mouseLoc = new Point();
    private Kinematics kinematics = new Kinematics();
    private Arm rootArm = new Arm(400, 400, 50);

    private Arm selectedArm;

    private Timer gameTime = new Timer(1000 / 144, this);

    private boolean mousePressed = false;

    Canvas() {
        // Some setup
        setBackground(Color.gray);
        setFocusable(true);

        // Allow the canvas to look out for mouse presses and movement
        addMouseListener(this);
        addMouseMotionListener(this);

        // Create a kinematic chain
        kinematics.AddArm(rootArm);
        kinematics.AddArm(50, Math.PI / 4);
        kinematics.AddArm(50, 2 * Math.PI / 4);
        kinematics.AddArm(50, 3 * Math.PI / 4);
        kinematics.AddArm(50, 4 * Math.PI / 4);

        // Start game loop
        gameTime.start();
    }

    // Main draw loop
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        this.draw(g2);
    }

    private void draw(Graphics2D g2) {
        kinematics.draw(g2);
    }

    // Main game loop
    @Override
    public void actionPerformed(ActionEvent e) {

        // Grab a joint or perform inverse kinematics
        if (selectedArm != null) {
            // Calculate angle relative to joint
            selectedArm.angle = Math.atan2(mouseLoc.getY() - selectedArm.start.y, mouseLoc.getX() - selectedArm.start.x);
        } else if (mousePressed) {
            // Set the target destination for inverse kinematics
            kinematics.moveToTarget(mouseLoc.getX(), mouseLoc.getY());
        }

        kinematics.update();

        repaint();
    }

    // Mouse events
    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Capture a continuos mouse press
        mousePressed = true;

        // Grab which arm was selected at the time of press
        for (Arm selected : kinematics.arms) {
            if (e.getPoint().distance(selected.end.x, selected.end.y) < 10) {
                selectedArm = selected;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Reset continuous mouse press
        mousePressed = false;
        selectedArm = null;
    }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mouseDragged(MouseEvent e) {
        // We only use the mouse's location when the mouse is also pressed
        mouseLoc = e.getPoint();
    }

    @Override
    public void mouseMoved(MouseEvent e) { }
}