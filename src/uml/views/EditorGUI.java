package uml.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//TODO Write tests for this class
/**
 * The main view and driver class for the UML app.
 *
 * @author Vincent Smith
 * 2/4/16
 */
public class EditorGUI extends JFrame {

    // Global Variables
    final private static int WIDTH = 800;
    final private static int HEIGTH = 600;
    final private static String TITLE = "UML - Editor";
    private static EditorGUI sharedApp;

    /**
     * Constructor
     */
    private EditorGUI()
    {
        initialize();
    }

    /**
     * Sets up the EditorGUI and  and positions it within
     */
    private void initialize()
    {
        // Center Frame in middle of window
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setSize(WIDTH, HEIGTH);
        int xPos = (dimension.width/2) - (this.getWidth() / 2);
        int yPos = (dimension.height/2) - (this.getHeight() / 2);
        this.setTitle(TITLE);
        this.attachMenuBar();
        this.setLocation(xPos, yPos);
        this.setExitOnWindowClose();
        this.setVisible(true);
    }

    /**
     * Ensures that only one instance of this class is in
     * use at a time.
     *
     * @return EditorGUI sharedApp
     */
    public static EditorGUI getSharedApp()
    {
        if (sharedApp == null)
        {
            sharedApp = new EditorGUI();
        }
        return sharedApp;
    }

    /**
     * Binds a JMenuBar to our current window.
     */
    private void attachMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu, helpMenu;
        fileMenu = new JMenu("File");
        helpMenu = new JMenu("About");

        // New Diagram Button
        JMenuItem newMenu = new JMenuItem("New Diagram...");

        fileMenu.add(newMenu);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        this.setJMenuBar(menuBar);
    }

    /**
     * Sets the program to terminate when the window is closed
     */
    private void setExitOnWindowClose()
    {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    /**
     * The main driver for the UML Editor App.
     *
     * @param args which are command line arguments
     */
    public static void main(String[] args)
    {
        getSharedApp();
    }

}
