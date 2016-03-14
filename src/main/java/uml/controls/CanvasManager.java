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
import java.util.ArrayList;

public class CanvasManager {

    // Local Variables
    private static int RADIUS = 5;
    private Canvas m_canvas;
    private Point m_clickPoint;
    private int m_activeRelationshipIndex;
    public boolean m_isDeleteMode = false;
    private CanvasManager m_canvasManager = this;

    /**
     * Constructor
     */
    public CanvasManager() {
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
    	getSharedCanvas().revalidate();
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
                ClassBox classBox = new ClassBox();

                ArrayList<ClassBox> classBoxes = getSharedCanvas().getClassBoxes();
                int offset = classBoxes.size() + 1;
                Point origin = new Point(classBox.getOrigin().x * offset, classBox.getOrigin().y * offset);
                classBox.setOrigin(origin);
                classBox.setBounds(origin.x, origin.y, classBox.getWidth(), classBox.getHeight());
                addClassBox(classBox);
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
                ArrayList<Relationship> relationships = getSharedCanvas().getRelationships();
                addRelationship(line);
                int offset = relationships.size() * 8;
                Point startPoint = new Point(line.getStartPoint().x + offset, line.getStartPoint().y + offset);
                Point endPoint = new Point(line.getEndPoint().x + offset, line.getEndPoint().y + offset);
                line.setStartPoint(startPoint);
                line.setEndPoint(endPoint);
                //getSharedCanvas().setRelationships(relationships);
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
                clearCanvas();
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
                ArrayList<ClassBox> classBoxes = getSharedCanvas().getClassBoxes();
                ArrayList<Relationship> relationships = getSharedCanvas().getRelationships();
                if (!m_isDeleteMode) {
                    //a way to show if we are in delete mode.
                    for (Relationship r : getSharedCanvas().getRelationships()) {
                        r.setColor(Color.red);
                    }
                    for (ClassBox c : classBoxes) {
                        c.setBackground(Color.red);
                    }
                    getSharedCanvas().revalidate();
                    getSharedCanvas().repaint();

                    m_isDeleteMode = true;
                } else {
                    for (Relationship r : getSharedCanvas().getRelationships()) {
                        r.setColor(Color.gray);
                    }
                    for (ClassBox c : classBoxes) {
                        c.setBackground(Color.gray);
                    }
                    getSharedCanvas().revalidate();
                    getSharedCanvas().repaint();
                    m_isDeleteMode = false;
                }

            }
        };
        return listener;

    }

    /**
     * Add a classBox to the Canvas
     *
     */
    public void addClassBox(ClassBox cb) {
        ArrayList<ClassBox> classBoxes = getSharedCanvas().getClassBoxes();
        classBoxes.add(cb);
        getSharedCanvas().add(cb, 0);
        cb.addMouseListener(new EventManager(m_canvasManager, cb));
        cb.addMouseMotionListener(new EventManager(m_canvasManager, cb));

    }

    /**
     * Add a Relationship to the Canvas
     *
     */
    public void addRelationship(Relationship r) {
        ArrayList<Relationship> relationships = getSharedCanvas().getRelationships();
        relationships.add(r);
    }

    /**
     * Delete a classBox from the Canvas
     *
     */
    public void deleteClassBox(ClassBox cb) {
        getSharedCanvas().remove(cb);
    }

    /**
     * Delete a Relationship from the Canvas
     *
     */
    public void deleteRelationship(int index) {
        ArrayList<Relationship> relationships = getSharedCanvas().getRelationships();
        relationships.remove(index);
    }

    /**
     * Clear everything from the Canvas
     *
     */
    public void clearCanvas() {
        ArrayList<ClassBox> classBoxes = getSharedCanvas().getClassBoxes();
        ArrayList<Relationship> relationships = getSharedCanvas().getRelationships();
        classBoxes.clear();
        relationships.clear();
        getSharedCanvas().removeAll();
    }

}
