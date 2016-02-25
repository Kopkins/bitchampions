package uml.controls;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import uml.models.ClassBox;
import java.awt.*;

public class ClassBoxManager {

    // Local Variables
    private Point m_origin;
    private Point m_clickedPoint;
    private ClassBox m_classBox;

    /**
     * Constructor
     */
    public ClassBoxManager() {
        init();
    }

    /**
     * Add MouseListeners to ClassBoxManager
     */
    private void init() {
        getSharedClassBox().addMouseListener(new EventMouseListener());
        getSharedClassBox().addMouseMotionListener(new EventMouseMotionListener());
    }

    /**
     * Gets a singleton instance of ClassBox.
     *
     * @return
     */
    public ClassBox getSharedClassBox() {
        if (m_classBox == null) {
            m_classBox = new ClassBox();
        }
        return m_classBox;
    }

    /**
     * EventMouseListener for detecting when mouse is pressed and released
     *
     */
    class EventMouseListener extends MouseAdapter {

        public void mousePressed(MouseEvent event) {
            // get initial location of classBox
            m_origin = getSharedClassBox().getLocation();
            // get point the mouse is pressed on
            m_clickedPoint = event.getPoint();
            // changed color to blus to show classBox is active
            getSharedClassBox().setBackground(Color.blue);
        }

        public void mouseReleased(MouseEvent event) {
            // change color back to gray to show classBox is no longer active
            getSharedClassBox().setBackground(Color.gray);
        }
    }

    /**
     * EventMouseMotionListener for detecting when mouse is dragged
     *
     */
    class EventMouseMotionListener extends MouseMotionAdapter {

        //calculate the distance of mouse drag event and move the location of the classbox
        public void mouseDragged(MouseEvent event) {
            m_origin.x += event.getX() - m_clickedPoint.x;
            m_origin.y += event.getY() - m_clickedPoint.y;
            getSharedClassBox().setLocation(m_origin);
        }
    }
}
