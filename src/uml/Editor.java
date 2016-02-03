package uml;

import uml.controls.MenuBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Editor extends JFrame {

    // Global Vars
    private final String TITLE = "bit_Champions UML";
    private final int WIDTH = 700;
    private final int HEIGHT = 500;

    public static void main(String[] args)
    {
        new Editor();
    }

    public Editor()
    {
       init(WIDTH, HEIGHT);
    }

    private void init(int width, int height)
    {
        this.setSize(width, height);
        this.setTitle(TITLE);
        this.setJMenuBar(new MenuBar());

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        int xPos = (dimension.width/2) - (this.getWidth() / 2);
        int yPos = (dimension.height/2) - (this.getHeight() / 2);
        this.setLocation(xPos, yPos);

        // Program should exit when window closes
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        this.setVisible(true);
    }
}
