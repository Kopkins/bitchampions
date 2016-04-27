package uml.views;

import uml.controls.*;
import uml.models.ToolBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The main view and driver class for the UML app.
 */
public class EditorGUI {

    // Global Variables
    final private static int WIDTH = 800;
    final private static int HEIGHT = 600;
    final private static String TITLE = "UML";
    private static EditorGUI _sharedApp;
    public JFrame m_window;
    private DialogManager m_dialogManager;
    private static String m_fileName;
    private static int primaryMask = ActionEvent.CTRL_MASK;

    /**
     * Constructor
     */
    private EditorGUI() {
        m_window = new JFrame();
        m_fileName = SaveLoadManager.getInstance().getFileName();
        m_dialogManager = new DialogManager(m_window);
        initialize();
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
     * The main driver for the UML Editor App.
     *
     * @param args which are command line arguments
     */
    public static void main(String[] args) {
        if(System.getProperty("os.name").equals("Mac OS X")){
            System.setProperty("apple.laf.useScreenMenuBar","true");
            primaryMask = ActionEvent.META_MASK;
        }
        getSharedApp();
    }

    /**
     * Sets up the EditorGUI and and positions it within
     */
    private void initialize() {

        Container pane = m_window.getContentPane();

        //Add the toolbox to the pane and bind the canvas
        pane.setLayout(new BorderLayout());
        pane.add(new ToolBox(), BorderLayout.LINE_START);
        CanvasManager.bindCanvas(m_window.getContentPane());

        // Center Frame in middle of this
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        m_window.setSize(WIDTH, HEIGHT);
        int xPos = (dimension.width / 2) - (m_window.getWidth() / 2);
        int yPos = (dimension.height / 2) - (m_window.getHeight() / 2);
        m_window.setTitle(m_fileName + " - " + TITLE);
        this.attachMenuBar();
        m_window.setLocation(xPos, yPos);
        this.setExitOnWindowClose();
        m_window.setVisible(true);
    }

    /**
     * Binds a JMenuBar to our current this.
     */
    private void attachMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu, aboutMenu, editMenu;
        fileMenu = new JMenu("File");
        aboutMenu = new JMenu("About");
        editMenu = new JMenu("Edit");

        // Buttons
        JMenuItem newDiagram = new JMenuItem("New");
        JMenuItem close = new JMenuItem("Close");
        JMenuItem about = new JMenuItem("About Us...");
        JMenuItem export = new JMenuItem("Export as .jpg");
        JMenuItem saveAs = new JMenuItem("Save As...");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem open = new JMenuItem("Open...");
        JMenuItem undo = new JMenuItem("Undo");
        JMenuItem redo = new JMenuItem("Redo");

        // Bind Accelerators and Mnemonics
        newDiagram.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, primaryMask));
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, primaryMask));
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, primaryMask));
        saveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, primaryMask + ActionEvent.SHIFT_MASK));
        close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, primaryMask));
        export.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, primaryMask));
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, primaryMask));
        redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, primaryMask + ActionEvent.SHIFT_MASK));


        // Bind Events to buttons
        close.addActionListener(e -> m_dialogManager.confirmTermination());
        about.addActionListener(e -> m_dialogManager.showAbout());
        newDiagram.addActionListener(e -> m_dialogManager.showNotImplemented());
        export.addActionListener(CanvasManager.getExportListener());
        open.addActionListener(CanvasManager.getLoadListener());
        saveAs.addActionListener(CanvasManager.getSaveListener(false));
        save.addActionListener(CanvasManager.getSaveListener(true));
        undo.addActionListener(CanvasManager.getUndoListener());
        redo.addActionListener(CanvasManager.getRedoListener());

        // Bind Buttons to Menu
        fileMenu.add(newDiagram);
        fileMenu.add(open);
        fileMenu.add(new JSeparator());
        fileMenu.add(close);
        fileMenu.add(saveAs);
        fileMenu.add(save);
        fileMenu.add(export);
        editMenu.add(undo);
        editMenu.add(redo);

        aboutMenu.add(about);
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
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

    public static void updateFileName(String fileName)
    {
        m_fileName = fileName;
        getSharedApp().m_window.setTitle(m_fileName + " - " + TITLE);
    }

}
