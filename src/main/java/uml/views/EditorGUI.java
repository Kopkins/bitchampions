package uml.views;

import uml.controls.CanvasManager;
import uml.controls.DialogManager;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
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
    private CanvasManager _cm;
    private static EditorGUI _sharedApp;


    /**
     * Constructor
     */
    private EditorGUI() {
        _window = new JFrame();
        _dm = new DialogManager(_window);
        _cm = new CanvasManager();
        initialize();
    }

    /**
     * Sets up the EditorGUI and  and positions it within
     */
    private void initialize() {

        Container pane = _window.getContentPane();

        // Add toolbox to Pane
        JPanel toolbox = new JPanel();
        toolbox.setLayout(new BoxLayout(toolbox, BoxLayout.PAGE_AXIS));
        CompoundBorder line = new CompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
            BorderFactory.createLineBorder(Color.black));

        Border toolboxBorder = BorderFactory.createTitledBorder(line, "Toolbox");
        toolbox.setBorder(toolboxBorder);
        toolbox.setPreferredSize(new Dimension(150, _window.getHeight()));

        //Bind Buttons to Toolbox
        JButton button = new JButton("+ Add a Class Box");
        button.addActionListener(_cm.getAddBoxListener());
        button.setPreferredSize(new Dimension(50, 25));
        toolbox.add(button);
        JButton button2 = new JButton("+ Add a Line");
        button2.addActionListener(_cm.getAddRelationshipListener());
        button2.setPreferredSize(new Dimension(25, 50));
        toolbox.add(button2);
        pane.add(toolbox, BorderLayout.LINE_START);
        _cm.bindCanvas(_window.getContentPane());

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
