package uml.controls;

import uml.Settings;
import uml.models.Canvas;
import uml.models.ClassBox;
import uml.models.Generics.GenericRelationship;

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
    private int m_activeRelationshipIndex = -1;
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
        // Get shared instance of canvas.
        Canvas canvas = getSharedCanvas();

        // Prepare dimensions/preferences
        canvas.setPreferredSize(new Dimension(canvas.m_width, canvas.m_height));
        canvas.setBackground(Color.white);
        canvas.setOpaque(true);

        // Bind
        pane.add(new JScrollPane(canvas), BorderLayout.CENTER);
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
                //turn off delete mode if adding classBox
                m_isDeleteMode = false;
                ResetItemColor();

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
    public ActionListener getAddRelationshipListener(String type) {
        ActionListener listener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //turn off delete mode if adding Relationship
                m_isDeleteMode = false;
                ResetItemColor();
                GenericRelationship line = RelationshipFactory.getFromType(type);
                ArrayList<GenericRelationship> relationships = getSharedCanvas().getRelationships();
                addRelationship(line);
                int offset = relationships.size() * 8;
                line.translate(offset, offset);
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
                ArrayList<GenericRelationship> relationships = getSharedCanvas().getRelationships();
                if (!m_isDeleteMode) {
                    m_isDeleteMode = true;
                    //a way to show if we are in delete mode.
                    for (GenericRelationship r : getSharedCanvas().getRelationships()) {
                        r.setColor(Settings.Colors.DELETE.color);
                    }
                    for (ClassBox c : classBoxes) {
                        addColorToBox(c);
                    }
                    getSharedCanvas().revalidate();
                    getSharedCanvas().repaint();

                } else {
                    m_isDeleteMode = false;
                    ResetItemColor();
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
        getSharedCanvas().add(cb, JLayeredPane.DRAG_LAYER);
        getSharedCanvas().moveToFront(cb);
        addColorToBox(cb);
        cb.addMouseListener(new EventManager(m_canvasManager, cb));
        cb.addMouseMotionListener(new EventManager(m_canvasManager, cb));
    }

    /**
     * Add a Relationship to the Canvas
     *
     */
    public void addRelationship(GenericRelationship r) {
        ArrayList<GenericRelationship> relationships = getSharedCanvas().getRelationships();
        relationships.add(r);
    }

    /**
     * Delete a classBox from the Canvas
     *
     */
    public void deleteClassBox(ClassBox cb) {

        ArrayList<ClassBox> boxes = getSharedCanvas().getClassBoxes();
        boxes.remove(cb);
        getSharedCanvas().remove(cb);
    }

    /**
     * Delete a Relationship from the Canvas
     *
     */
    public void deleteRelationship(int index) {
        ArrayList<GenericRelationship> relationships = getSharedCanvas().getRelationships();
        relationships.remove(index);
    }

    /**
     * Clear everything from the Canvas
     *
     */
    public void clearCanvas() {
        ArrayList<ClassBox> classBoxes = getSharedCanvas().getClassBoxes();
        ArrayList<GenericRelationship> relationships = getSharedCanvas().getRelationships();
        classBoxes.clear();
        relationships.clear();
        getSharedCanvas().removeAll();
    }

    public void ResetItemColor() {

        for (GenericRelationship r : getSharedCanvas().getRelationships()) {
            r.setColor(Settings.Colors.DEFAULT.color);
        }

        for (ClassBox c : getSharedCanvas().getClassBoxes()) {
            c.setBackground(Settings.Colors.DEFAULT.color);
        }
        getSharedCanvas().repaint();
    }

    private void addColorToBox(ClassBox box) {
        Color color = m_isDeleteMode ? Settings.Colors.DELETE.color : Settings.Colors.DEFAULT.color;
        box.setBackground(color);
    }

}
