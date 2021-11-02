import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.MouseInfo;
import java.awt.Point;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Canvas extends JPanel implements ActionListener, MouseListener {
    Point mouseLoc = new Point();
    Point prevMouseLoc = new Point();
    Kinematics kinematics = new Kinematics();
    Arm testArm = new Arm(400, 400, 50);

    Arm selectedArm;

    Timer gameTime = new Timer(1000 / 144, this);

    private boolean mousePressed = false;

    Canvas() {
        setBackground(Color.gray);
        setFocusable(true);

        addMouseListener(this);

        // Only two for now
        kinematics.AddArm(testArm);
        kinematics.AddArm(50, 0);
        kinematics.AddArm(50, 0);

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
        // Get mouse's location relative to the top left corner of the canvas
        mouseLoc = MouseInfo.getPointerInfo().getLocation();
        mouseLoc.translate(-this.getLocationOnScreen().x, -this.getLocationOnScreen().y);

        // Grab a joint
        // if (selectedArm != null) {
        //     selectedArm.axis.x = (mouseLoc.getX() - selectedArm.start.x) / mouseLoc.distance(selectedArm.start.x, selectedArm.start.y);
        //     selectedArm.axis.y = (mouseLoc.getY() - selectedArm.start.y) / mouseLoc.distance(selectedArm.start.x, selectedArm.start.y);
        // }

        if (mousePressed && mouseLoc != prevMouseLoc) {
            kinematics.setTarget(mouseLoc.getX(), mouseLoc.getY());
        }

        kinematics.update();

        prevMouseLoc = mouseLoc;

        repaint();
    }

    // Mouse events
    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        mousePressed = true;

        for (Arm selected : kinematics.arms) {
            if (mouseLoc.distance(selected.end.x, selected.end.y) < 10) {
                selectedArm = selected;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        mousePressed = false;
        selectedArm = null;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }
}