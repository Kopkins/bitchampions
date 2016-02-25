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

public class CanvasManager {

    // Local Variables
    private static int RADIUS = 5;
    private Canvas m_canvas;
    private Point m_clickPoint;
    private int m_activeIndex;
    private ArrayList<ClassBox> m_classBoxes;
    private ArrayList<Relationship> m_relationships;

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
        getSharedCanvas().addMouseListener(new EventMouseListener());
        getSharedCanvas().addMouseMotionListener(new EventMouseMotionListener());
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
    private Canvas getSharedCanvas() {
        if (m_canvas == null) {
            m_canvas = new Canvas();
        }
        return m_canvas;
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
                ClassBoxManager classBoxManager = new ClassBoxManager();
                ClassBox classBox = classBoxManager.getSharedClassBox();
                int offset = m_classBoxes.size() + 1;
                classBox.setBounds(classBox.getOrigin().x * offset, classBox.getOrigin().y * offset, classBox.getWidth(), classBox.getHeight());
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
     * EventMouseListener for detecting when mouse is pressed and released
     *
     */
    public class EventMouseListener extends MouseAdapter {

        public void mousePressed(MouseEvent event) {

            // get the point the mouse is pressed on
            m_clickPoint = event.getPoint();
            // loop through relationships arraylist and see if click point is within a 5 point radius of any of the relationships origin point
            for (int i = 0; i < m_relationships.size(); i++) {
                if (m_relationships.get(i).getStartPoint().distance(m_clickPoint) <= RADIUS) {
                    //get the index of the active relationship
                    m_activeIndex = i;
                    // if clickpoint is within 5 point radius of relationship's origin point, set the relationship to active
                    m_relationships.get(i).setColor(Color.blue);
                    // repaint the canvas so the active relationship's color is blue
                    getSharedCanvas().repaint();
                }
            }
        }

        public void mouseReleased(MouseEvent event) {
            // deactivate the active relationship
            if (m_activeIndex != -1) {
                // change relationship's color back to gray to show it is no longer active and repaint
                m_relationships.get(m_activeIndex).setColor(Color.gray);
                getSharedCanvas().repaint();
                m_activeIndex = -1;
            }
        }
    }

    /**
     * EventMouseListener for detecting when mouse is dragged
     *
     */
    public class EventMouseMotionListener extends MouseMotionAdapter {

        public void mouseDragged(MouseEvent event) {
            if (m_activeIndex != -1) {
                // get the active relationship
                Relationship activeRelationship = m_relationships.get(m_activeIndex);
                // get the distance the origin point is moved
                int x = activeRelationship.getStartPoint().x - event.getX();
                int y = activeRelationship.getStartPoint().y - event.getY();
                // set the active relationship's origin point to the point where the mouse is dragged
                activeRelationship.setStartPoint(event.getPoint());
                // calculate the point to move the active relationship's second point to, based on the
                // distance it's origin point is moved
                x = activeRelationship.getEndPoint().x - x;
                y = activeRelationship.getEndPoint().y - y;
                // move the active relationship's second point to the point calculated above
                activeRelationship.setEndPoint(new Point(x, y));
                // update the click point to where the active relationship's origin point was moved to
                m_clickPoint = activeRelationship.getStartPoint();
                getSharedCanvas().repaint();
            }
        }
    }
}
