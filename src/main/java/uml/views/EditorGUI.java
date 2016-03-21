package uml.views;

import uml.controls.CanvasManager;
import uml.controls.DialogManager;
import uml.models.ToolBox;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;

/**
 * The main view and driver class for the UML app.
 *
 * @author Vincent Smith 2/4/16
 */
public class EditorGUI {

    // Global Variables
    final private static int WIDTH = 800;
    final private static int HEIGHT = 600;
    final private static String TITLE = "UML - Editor";

    public JFrame m_window;
    private DialogManager m_dialogManager;
    private CanvasManager m_canvasManager;
    private static EditorGUI _sharedApp;

    /**
     * Constructor
     */
    private EditorGUI() {
        m_window = new JFrame();
        m_dialogManager = new DialogManager(m_window);
        m_canvasManager = CanvasManager.getInstance();
        initialize();
    }

    /**
     * Sets up the EditorGUI and and positions it within
     */
    private void initialize() {

        Container pane = m_window.getContentPane();

        // Add toolbox to Pane
        ToolBox toolbox = new ToolBox();
        toolbox.setLayout(new BoxLayout(toolbox, BoxLayout.PAGE_AXIS));
        CompoundBorder line = new CompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createLineBorder(Color.black));

        Border toolboxBorder = BorderFactory.createTitledBorder(line, "Toolbox");
        toolbox.setBorder(toolboxBorder);
        toolbox.setPreferredSize(new Dimension(150, m_window.getHeight()));

        //Add ActionListeners to toolbox buttons
        Map<String, JButton> buttons = toolbox.getButtons();
        buttons.get("addClassBoxButton").addActionListener(CanvasManager.getAddBoxListener());
        buttons.get("addAssociationButton").addActionListener(m_canvasManager.getAddRelationshipListener("Association"));
        buttons.get("addDirectedAssociationButton").addActionListener(m_canvasManager.getAddRelationshipListener("DirectedAssociation"));
        buttons.get("addDependencyButton").addActionListener(m_canvasManager.getAddRelationshipListener("Dependency"));
        buttons.get("addGeneralizationButton").addActionListener(m_canvasManager.getAddRelationshipListener("Generalization"));
        buttons.get("addAggregationButton").addActionListener(m_canvasManager.getAddRelationshipListener("Aggregation"));
        buttons.get("addCompositionButton").addActionListener(m_canvasManager.getAddRelationshipListener("Composition"));
        buttons.get("clearCanvasButton").addActionListener(CanvasManager.getClearCanvasListener());
        buttons.get("deleteSModeButton").addActionListener(m_canvasManager.getDeleteModeListener());

        //Add the toolbox to the pane and bind the canvas
        pane.setLayout(new BorderLayout());
        pane.add(toolbox, BorderLayout.LINE_START);
        CanvasManager.bindCanvas(m_window.getContentPane());

        // Center Frame in middle of this
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        m_window.setSize(WIDTH, HEIGHT);
        int xPos = (dimension.width / 2) - (m_window.getWidth() / 2);
        int yPos = (dimension.height / 2) - (m_window.getHeight() / 2);
        m_window.setTitle(TITLE);
        this.attachMenuBar();
        m_window.setLocation(xPos, yPos);
        this.setExitOnWindowClose();
        m_window.setVisible(true);
    }

    /**
     * Ensures that only one instance of this class is in use at a time.
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
        exit.addActionListener(e -> m_dialogManager.confirmTermination());
        about.addActionListener(e -> m_dialogManager.showAbout());
        newDiagram.addActionListener(e -> m_dialogManager.showNotImplemented());

        // Bind Buttons to Menu
        fileMenu.add(newDiagram);
        fileMenu.add(exit);
        aboutMenu.add(about);

        menuBar.add(fileMenu);
        menuBar.add(aboutMenu);

        m_window.setJMenuBar(menuBar);
    }

    /**
     * Sets the program to terminate when the this is closed
     */
    private void setExitOnWindowClose() {
        m_window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        m_window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                m_dialogManager.confirmTermination();
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
