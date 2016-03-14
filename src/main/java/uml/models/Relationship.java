package uml.models;

import java.awt.*;
import javax.swing.*;

public class Relationship extends JComponent {

// Local Variables
    private Point m_start, m_end;
    private Color m_color;

    /**
     * Constructor
     */
    public Relationship() {
        m_start = new Point(320, 20);
        m_end = new Point(440, 20);
        m_color = Color.gray;
    }

    /**
     * Gets the color for drawing the line.
     *
     * @return
     */
    public Color getColor() {
        return m_color;
    }

    /**
     * Sets the color for drawing the line.
     *
     */
    public void setColor(Color c) {
        m_color = c;
    }

    /**
     * Gets end point.
     *
     * @return
     */
    public Point getStartPoint() {
        return m_start;
    }

    /**
     * Sets start point.
     *
     */
    public void setStartPoint(Point p) {
        m_start = p;
    }

    /**
     * Gets end point.
     *
     * @return
     */
    public Point getEndPoint() {
        return m_end;
    }

    /**
     * Sets end point.
     *
     */
    public void setEndPoint(Point p) {
        m_end = p;
    }
}
