package uml.controls;

import uml.Settings;
import uml.models.Canvas;
import uml.models.ClassBox;
import uml.models.Generics.Relationship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CanvasManager {

    // Local Variables
    private static int RADIUS = 5;
    private static Canvas m_canvas;
    private static CanvasManager m_canvasManager;
    public boolean m_isDeleteMode = false;
    private Point m_clickPoint;
    private int m_activeRelationshipIndex = -1;

    /**
     * Constructor
     */
    public CanvasManager() {
        init();
    }

    /**
     * Singleton instance of CanvasManager
     *
     * @return
     */
    public static CanvasManager getInstance() {
        if (m_canvasManager == null) {
            m_canvasManager = new CanvasManager();
        }
        return m_canvasManager;
    }

    /**
     * Attaches a canvas to the main window
     */
    public static void bindCanvas(Container pane) {
        // Get shared instance of canvas.
        Canvas canvas = getSharedCanvas();

        // Prepare dimensions/preferences
        canvas.setPreferredSize(
            new Dimension(canvas.m_width, canvas.m_height));

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
    public static Canvas getSharedCanvas() {
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
    public static ActionListener getAddBoxListener() {
        ActionListener listener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                getInstance().toggleDeleteMode();
                ResetItemColor();
                ClassBox classBox = new ClassBox();
                ArrayList<ClassBox> classBoxes = getSharedCanvas().getClassBoxes();
                int offset = classBoxes.size() + 1;
                Point origin = new Point(classBox.getOrigin().x * offset, classBox.getOrigin().y * offset);
                classBox.setOrigin(origin);
                classBox.setBounds(origin.x, origin.y, classBox.getWidth(), classBox.getHeight());
                getInstance().addClassBox(classBox);
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
    public static ActionListener getAddRelationshipListener(String type) {
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //turn off delete mode if adding Relationship
                ResetItemColor();

                Relationship line = RelationshipFactory.getFromType(type);
                ArrayList<Relationship> relationships = getSharedCanvas().getRelationships();

                getInstance().toggleDeleteMode();
                getInstance().addRelationship(line);

                int offset = relationships.size() * 8;
                line.translate(offset, offset);

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
    public static ActionListener getClearCanvasListener() {
        ActionListener listener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                getInstance().clearCanvas();
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
    public static ActionListener getDeleteModeListener() {
        ActionListener listener;
        listener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                CanvasManager canvasManager = CanvasManager.getInstance();
                boolean deleteMode = canvasManager.m_isDeleteMode;
                ArrayList<ClassBox> classBoxes = getSharedCanvas().getClassBoxes();
                if (!deleteMode) {
                    canvasManager.m_isDeleteMode = true;
                    //a way to show if we are in delete mode.
                    for (Relationship r : getSharedCanvas().getRelationships()) {
                        r.setColor(Settings.Colors.DELETE.color);
                    }
                    for (ClassBox c : classBoxes) {
                        canvasManager.addColorToBox(c);
                    }
                    getSharedCanvas().revalidate();
                    getSharedCanvas().repaint();

                } else {
                    canvasManager.m_isDeleteMode = false;
                    ResetItemColor();
                }
            }
        };
        return listener;
    }

    /**
     * Reset the color of each of the components in the canvas.
     */
    private static void ResetItemColor() {

        for (Relationship r : getSharedCanvas().getRelationships()) {
            r.setColor(Settings.Colors.DEFAULT.color);
        }

        for (ClassBox c : getSharedCanvas().getClassBoxes()) {
            c.setBackground(Settings.Colors.DEFAULT.color);
        }
        getSharedCanvas().repaint();
    }

    /**
     * Add MouseListeners to CanvasManager
     */
    private void init() {
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
     */
    public void setActiveRelationshipIndex(Integer index) {
        m_activeRelationshipIndex = index;
    }

    public Point getClickPoint() {
        return m_clickPoint;
    }

    /**
     * Sets the point that was clicked on.
     */
    public void setClickPoint(Point p) {
        m_clickPoint = p;
    }

    /**
     * Repaints the canvas
     */
    public void repaintCanvas() {
        getSharedCanvas().repaint();
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
     * Add a classBox to the Canvas
     */
    public void addClassBox(ClassBox cb) {
        ArrayList<ClassBox> classBoxes = getSharedCanvas().getClassBoxes();
        classBoxes.add(cb);
        getSharedCanvas().add(cb, JLayeredPane.DRAG_LAYER);
        getSharedCanvas().moveToFront(cb);
        addColorToBox(cb);
    }

    /**
     * Add a Relationship to the Canvas
     */
    public void addRelationship(Relationship r) {
        ArrayList<Relationship> relationships = getSharedCanvas().getRelationships();
        relationships.add(r);
    }

    /**
     * Delete a classBox from the Canvas
     */
    public void deleteClassBox(ClassBox cb) {

        ArrayList<ClassBox> boxes = getSharedCanvas().getClassBoxes();
        boxes.remove(cb);
        getSharedCanvas().remove(cb);
    }

    /**
     * Delete a Relationship from the Canvas
     */
    public void deleteRelationship(int index) {
        ArrayList<Relationship> relationships = getSharedCanvas().getRelationships();
        relationships.remove(index);
    }

    /**
     * Clear everything from the Canvas
     */
    private void clearCanvas() {
        ArrayList<ClassBox> classBoxes = getSharedCanvas().getClassBoxes();
        ArrayList<Relationship> relationships = getSharedCanvas().getRelationships();
        classBoxes.clear();
        relationships.clear();
        getSharedCanvas().removeAll();
    }

    /**
     * Add color to an individual classBox
     *
     * @param box, which is the box being colored.
     */
    private void addColorToBox(ClassBox box) {
        Color color = m_isDeleteMode ? Settings.Colors.DELETE.color : Settings.Colors.DEFAULT.color;
        box.setBackground(color);
    }

    /**
     * Toggle the deleteMode state on and off.
     */
    private void toggleDeleteMode() {
        if (m_isDeleteMode) {
            ResetItemColor();
            m_isDeleteMode = !m_isDeleteMode;
        }
    }
}
