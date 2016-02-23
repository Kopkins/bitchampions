package uml.controls;

import uml.models.Canvas;
import uml.models.ClassBox;
import uml.models.Relationship;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.Graphics.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

public class CanvasManager {

    // Local Variables
    private Canvas m_canvas;
    private static final int OFFSET = 20;
    private Point m_clickPoint;
    private int m_activeIndex;

    /**
     * Constructor
     */
    public CanvasManager() {
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
            int counter = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                counter++;
                ClassBox classBox = new ClassBox();
                classBox.setBounds(OFFSET * counter, OFFSET * counter, classBox.getWidth(), classBox.getHeight());
                getSharedCanvas().add(classBox, 0);
                getSharedCanvas().revalidate();
                getSharedCanvas().repaint();
                getSharedCanvas().addBox(classBox);

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
            int counter = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                Relationship line = new Relationship();
                getSharedCanvas().revalidate();
                getSharedCanvas().repaint();
                getSharedCanvas().addRelationship(line);
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

    public class EventMouseListener extends MouseAdapter {

        ArrayList<Relationship> relationships = getSharedCanvas().getRelationships();

        public void mousePressed(MouseEvent event) {

            m_clickPoint = event.getPoint();
            for (int i = 0; i < relationships.size(); i++) {
                if (relationships.get(i).getPoint1().distance(m_clickPoint) <= 5) {
                    m_activeIndex = i;
                    relationships.get(i).setColor(Color.blue);
                    getSharedCanvas().revalidate();
                    getSharedCanvas().repaint();
                }
            }
        }

        public void mouseReleased(MouseEvent event) {
            if (m_activeIndex != -1) {
                relationships.get(m_activeIndex).setColor(Color.gray);
                getSharedCanvas().revalidate();
                getSharedCanvas().repaint();
                m_activeIndex = -1;
            }
        }
    }

    public class EventMouseMotionListener extends MouseMotionAdapter {

        public void mouseDragged(MouseEvent event) {
            if (m_activeIndex != -1) {
                Relationship activeRelationship = getSharedCanvas().getRelationships().get(m_activeIndex);
                int x = activeRelationship.getPoint1().x - event.getX();
                int y = activeRelationship.getPoint1().y - event.getY();
                activeRelationship.setPoint1(event.getPoint());
                x = activeRelationship.getPoint2().x - x;
                y = activeRelationship.getPoint2().y - y;
                activeRelationship.setPoint2(new Point(x, y));
                m_clickPoint = activeRelationship.getPoint1();
                getSharedCanvas().addRelationship(m_activeIndex, activeRelationship);
                getSharedCanvas().revalidate();
                getSharedCanvas().repaint();
            }
        }

    }

}
