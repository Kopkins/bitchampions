package uml.views;

import uml.controls.DialogManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//TODO Write tests for this class

/**
 * The main view and driver class for the UML app.
 *
 * @author Vincent Smith
 *         2/4/16
 */
public class EditorGUI {

    // Global Variables
    final private static int WIDTH = 800;
    final private static int HEIGHT = 600;
    final private static String TITLE = "UML - Editor";

    public JFrame _window;
    private DialogManager _dm;
    private static EditorGUI _sharedApp;

    /**
     * Constructor
     */
    private EditorGUI() {
        _window = new JFrame();
        _dm = new DialogManager(_window);
        initialize();
    }

    /**
     * Sets up the EditorGUI and  and positions it within
     */
    private void initialize() {
        // Center Frame in middle of this
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        _window.setSize(WIDTH, HEIGHT);
        int xPos = (dimension.width / 2) - (_window.getWidth() / 2);
        int yPos = (dimension.height / 2) - (_window.getHeight() / 2);
        _window.setTitle(TITLE);
        this.attachMenuBar();
        _window.setLocation(xPos, yPos);
        this.setExitOnWindowClose();
        _window.setVisible(true);
    }

    /**
     * Ensures that only one instance of this class is in
     * use at a time.
     *
     * @return EditorGUI sharedApp
     */
    public static EditorGUI getSharedApp() {
        if (_sharedApp == null) {
            _sharedApp = new EditorGUI();
        }
        return _sharedApp;
    }

    /**
     * Binds a JMenuBar to our current this.
     */
    private void attachMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu, aboutMenu;
        fileMenu = new JMenu("File");
        aboutMenu = new JMenu("About");

        // Buttons
        JMenuItem newDiagram = new JMenuItem("New Diagram...");
        JMenuItem exit = new JMenuItem("Exit");
        JMenuItem about = new JMenuItem("About Us...");

        // Bind Events to buttons
        exit.addActionListener(e -> _dm.confirmTermination());
        about.addActionListener(e -> _dm.showAbout());
        newDiagram.addActionListener(e -> _dm.showNotImplemented());

        // Bind Buttons to Menu
        fileMenu.add(newDiagram);
        fileMenu.add(exit);
        aboutMenu.add(about);

        menuBar.add(fileMenu);
        menuBar.add(aboutMenu);

        _window.setJMenuBar(menuBar);
    }

    /**
     * Sets the program to terminate when the this is closed
     */
    private void setExitOnWindowClose() {
        _window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        _window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                _dm.confirmTermination();
            }
        });
    }

    /**
     * The main driver for the UML Editor App.
     *
     * @param args which are command line arguments
     */
    public static void main(String[] args) {
        getSharedApp();
    }

}
