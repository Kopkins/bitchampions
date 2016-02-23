package uml.models;

import java.awt.*;
import javax.swing.*;

public class Relationship extends JComponent {

// Local Variables
    private Point m_point1, m_point2;
    private Color m_color;

    /**
     * Constructor
     */
    public Relationship() {
        init();
    }

    /**
     * Initialize data members
     *
     */
    private void init() {
        m_point1 = new Point(20, 20);
        m_point2 = new Point(220, 20);
    }

    /**
     * Gets the color for drawing the line.
     *
     * @return color
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
     * Gets point1.
     *
     * @return point
     */
    public Point getPoint1() {
        return m_point1;
    }

    /**
     * Sets point1.
     *
     */
    public void setPoint1(Point p) {
        m_point1 = p;
    }

    /**
     * Gets point2.
     *
     * @return point
     */
    public Point getPoint2() {
        return m_point2;
    }

    /**
     * Sets point2.
     *
     */
    public void setPoint2(Point p) {
        m_point2 = p;
    }
}
