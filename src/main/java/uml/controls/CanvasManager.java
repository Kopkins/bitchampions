package uml.controls;

import uml.models.Canvas;
import uml.models.ClassBox;
import uml.models.Relationship;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import uml.controls.EventManager;
public class CanvasManager {

    // Local Variables
    private static int RADIUS = 5;
    private Canvas m_canvas;
    private Point m_clickPoint;
    private int m_activeRelationshipIndex;
    private ArrayList<ClassBox> m_classBoxes;
    private ArrayList<Relationship> m_relationships;
    public boolean m_isDeleteMode = false;
    private CanvasManager m_canvasManager = this;
 

    /**
     * Constructor
     */
    public CanvasManager() {
        m_classBoxes = new ArrayList<ClassBox>();
        m_relationships = new ArrayList<Relationship>();
        init();
    }

    /**
     * Add MouseListeners to CanvasManager
     */
    private void init() {
        getSharedCanvas().addMouseListener(new EventManager(this));
        getSharedCanvas().addMouseMotionListener(new EventManager(this));
    }
    
    /**
     * Attaches a canvas to the main window
     */
    public void bindCanvas(Container pane) {
        Canvas canvas = getSharedCanvas();
        // Setup border
        CompoundBorder line = new CompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createLineBorder(Color.black));
        Border canvasBorder = BorderFactory.createTitledBorder(line, "Canvas");
        canvas.setBorder(canvasBorder);

        // Bind canvas
        pane.add(canvas, BorderLayout.CENTER);
    }

    /**
     * Gets a singleton instance of canvas for the editors window.
     *
     * @return
     */
    public Canvas getSharedCanvas() {
        if (m_canvas == null) {
            m_canvas = new Canvas();
        }
        return m_canvas;
    }
    
    /**
     * Gets the index of the activeRelationship.
     *
     * @return
     */
    public int getActiveRelationshipIndex() {
        return m_activeRelationshipIndex;
    }
    
    /**
     * Sets the index of the activeRelationship.
     *
     */
    public void setActiveRelationshipIndex(Integer index) {
        m_activeRelationshipIndex = index;
    }
    
     /**
     * Gets the ArrayList of Relationships.
     *
     * @return
     */
    public ArrayList<Relationship> getRelationships() {
        return m_relationships;
    }
    
     /**
     * Gets the ArrayList of ClassBoxes.
     *
     * @return
     */
    public ArrayList<ClassBox> getClassBoxes() {
        return m_classBoxes;
    }
    
    /**
     * Gets the point that was clicked on.
     *
     * @return
     */
    public Point getClickPoint() {
        return m_clickPoint;
    }

    /**
     * Sets the point that was clicked on.
     *
     */
    public void setClickPoint(Point p) {
        m_clickPoint = p;
    }
    
     /**
     * Repaints the canvas
     *
     */
    public void repaintCanvas() {
        getSharedCanvas().repaint();
    }
    
    /**
     * Get an ActionListener that will add new CanvasBoxes to the canvas.
     *
     * @return
     */
    public ActionListener getAddBoxListener() {
        ActionListener listener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ClassBoxManager classBoxManager = new ClassBoxManager(m_canvasManager);
                ClassBox classBox = classBoxManager.getSharedClassBox();
                int offset = m_classBoxes.size() + 1;
                Point origin = new Point(classBox.getOrigin().x * offset, classBox.getOrigin().y * offset);
                classBox.setOrigin(origin);
                classBox.setBounds(origin.x, origin.y, classBox.getWidth(), classBox.getHeight());
                getSharedCanvas().add(classBox, 0);
                m_classBoxes.add(classBox);
                getSharedCanvas().revalidate();
                getSharedCanvas().repaint();
            }
        };
        return listener;
    }

    /**
     * Get an ActionListener that will add new Relationship to the canvas.
     *
     * @return
     */
    public ActionListener getAddRelationshipListener() {
        ActionListener listener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Relationship line = new Relationship();
                m_relationships.add(line);
                int offset = m_relationships.size() * 8;
                Point startPoint = new Point(line.getStartPoint().x + offset, line.getStartPoint().y + offset);
                Point endPoint = new Point(line.getEndPoint().x + offset, line.getEndPoint().y + offset);
                line.setStartPoint(startPoint);
                line.setEndPoint(endPoint);
                getSharedCanvas().setRelationships(m_relationships);
                getSharedCanvas().repaint();
            }
        };
        return listener;
    }

    /**
     * Get an ActionListener for revalidating and repainting the canvas panel
     *
     * @return listener
     */
    public ActionListener getRevalidateListener() {
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getSharedCanvas().revalidate();
                getSharedCanvas().repaint();
            }
        };
        return listener;
    }

    /**
     * Get an ActionListener that will add new Relationship to the canvas.
     *
     * @return
     */
    public ActionListener getClearCanvasListener() {
        ActionListener listener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                m_classBoxes.clear();
                m_relationships.clear();
                getSharedCanvas().removeAll();
                getSharedCanvas().revalidate();
                getSharedCanvas().repaint();
            }
        };
        return listener;
        
        
    }
    
 /**
     * Get an ActionListener that will put canvas in delete mode.
     *
     * @return
     */
    public ActionListener getDeleteModeListener() {
        ActionListener listener;
        listener = new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!m_isDeleteMode){
                    //a way to show if we are in delete mode.
                    //this doesnt have to stay or we could use a different color like red
                    for (Relationship r : getRelationships()){
                        r.setColor(Color.blue);
                    }
                     for (ClassBox c : m_classBoxes){
                        c.setBackground(Color.blue);
                    }
                    getSharedCanvas().revalidate();
                    getSharedCanvas().repaint();
                    
                    m_isDeleteMode = true;
                }else{
                     for (Relationship r : getRelationships()){
                        r.setColor(Color.gray);
                    }
                     for (ClassBox c : m_classBoxes){
                        c.setBackground(Color.gray);
                    }
                    m_isDeleteMode = false;
                }
                
                
            }
        };
        return listener;
        
        
    }
}
