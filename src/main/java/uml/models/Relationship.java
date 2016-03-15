package uml.models;

import java.awt.*;
import javax.swing.*;

public class Relationship extends JComponent {

// Local Variables
    private Point m_start, m_end;
    private Color m_color;
    private Polygon m_symbol;
    private String m_type;
    private double m_angle = 0.0;

    /**
     * Constructor
     */
    public Relationship(String type) {
        m_start = new Point(320, 20);
        m_end = new Point(440, 20);
        m_color = Color.gray;
        m_type = type;
        init();
    }

    /**
     * Build features for Relationship
     */
    public void init() {
        setSymbol();
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
        setSymbol();
    }

    /**
     * Gets the symbol.
     *
     * @return
     */
    public Polygon getSymbol() {
        return m_symbol;
    }

    /**
     * Gets the type of relationship.
     *
     * @return
     */
    public String getType() {
        return m_type;
    }

    /**
     * Gets the angle of rotation for the symbol.
     *
     * @return
     */
    public double getAngle() {
        return m_angle;
    }

    /**
     * Rotates the angle for the symbol.
     *
     */
    public void rotate() {
        m_angle = Math.atan2(m_end.getY() - m_start.getY(), m_end.getX() - m_start.getX());
    }

    /**
     * Builds the symbol for the relationship.
     *
     */
    public void setSymbol() {
        switch (m_type) {
            case "DirectedAssociation":
                m_symbol = new Polygon(new int[]{m_end.x, m_end.x - 14, m_end.x - 14}, new int[]{m_end.y, m_end.y - 7, m_end.y + 7}, 3);
                break;
            case "Dependency":
                m_symbol = new Polygon(new int[]{m_end.x, m_end.x - 14, m_end.x - 14}, new int[]{m_end.y, m_end.y - 7, m_end.y + 7}, 3);
                break;
            case "Generalization":
                m_symbol = new Polygon(new int[]{m_end.x, m_end.x - 14, m_end.x - 14}, new int[]{m_end.y, m_end.y - 7, m_end.y + 7}, 3);
                break;
            case "Aggregation":
                m_symbol = new Polygon(new int[]{m_end.x - 20, m_end.x - 9, m_end.x + 2, m_end.x - 9}, new int[]{m_end.y, m_end.y - 5, m_end.y, m_end.y + 5}, 4);
                break;
            case "Composition":
                m_symbol = new Polygon(new int[]{m_end.x - 23, m_end.x - 10, m_end.x + 3, m_end.x - 10}, new int[]{m_end.y, m_end.y - 6, m_end.y, m_end.y + 6}, 4);
                break;
            default:
                m_symbol = new Polygon();
        }
    }
}
