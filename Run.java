import java.awt.EventQueue;

import javax.swing.JFrame;

public class Run extends JFrame {
    // Window size
    public static int Width = 800;
    public static int Height = 800;

    Run() {
        pack();
        add(new Canvas());

        setTitle("Forward and Inverse Kinematics");
        setSize(Width, Height);
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable(){
            public void run() {
                JFrame f = new Run();
                f.setVisible(true);
            }
        });
    }
}