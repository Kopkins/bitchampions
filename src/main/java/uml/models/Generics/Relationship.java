package uml.models.Generics;

import uml.Settings;

import javax.swing.*;
import java.awt.*;
import uml.models.ClassBox;

public abstract class Relationship extends JComponent implements Cloneable {

    // Local Variables
    protected Point m_start, m_end;
    protected Color m_color;
    protected Polygon m_symbol;
    protected String m_type;
    protected double m_angle;
    private int m_anchoredCount;
    private int m_id;

    /**
     * Initialize the main components of the Relationship
     */
    public void init() {
        m_angle = 0.0;
        m_start = new Point(320, 20);
        m_end = new Point(440, 20);
        m_color = Settings.Colors.DEFAULT.color;
        m_anchoredCount = 0;
        m_id = 0;
    }

    /**
     * Getter for type
     *
     * @return String, which identifies the type of the string.
     */
    public String getType() {
        return m_type;
    }

    /**
     * Set the type identifier.
     *
     * @param type, String which identifies the type of the string.
     */
    public void setType(String type) {
        m_type = type;
    }

    /**
     * Getter for Color
     *
     * @return Color, which is the color of this Relationship
     */
    public Color getColor() {
        return m_color;
    }

    /**
     * Setter for Color
     *
     * @param c Color, which is the new Color for this Relationship
     */
    public void setColor(Color c) {
        m_color = c;
    }

    /**
     * Getter for start point
     *
     * @return Point, which is the start point of this Relationship
     */
    public Point getStartPoint() {
        return m_start;
    }

    /**
     * Setter for start point
     *
     * @param p, Point whick is the start point of this Relationship
     */
    public void setStartPoint(Point p) {
        m_start = p;
    }

    /**
     * Getter for end point
     *
     * @return Point which is the end point
     */
    public Point getEndPoint() {
        return m_end;
    }

    /**
     * Setter for the end point
     *
     * @param p, Point which is the end point fo this Relationship
     */
    public void setEndPoint(Point p) {
        m_end = p;
    }

    /**
     * Getter for the angle
     *
     * @return double, which is the angle of the current Relationship
     */
    public double getAngle() {
        return m_angle;
    }

    /**
     * Sets anchored.
     *
     * @param int
     */
    public void setAnchoredCount(int i) {
        // the relationship can be anchored to atmost 2 classboxes, ensure that this is always the case
        if (i > 2) {
            m_anchoredCount = 2;
        } else {
            m_anchoredCount = i;
        }
    }

    /**
     * Gets the value of anchored.
     *
     * @return
     */
    public int getAnchoredCount() {
        return m_anchoredCount;
    }

    /**
     * Sets id.
     *
     * @param int
     */
    public void setId(int i) {
        m_id = i;
    }

    /**
     * Gets the id.
     *
     * @return
     */
    public int getId() {
        return m_id;
    }

    /**
     * Translate the current relationship.
     *
     * @param dx, which is the change in the horizontal component of the point.
     * @param dy, which is the change in the vertical component of the point.
     */
    public void translate(int dx, int dy) {
        m_start.translate(dx, dy);
        m_end.translate(dx, dy);
        refreshSymbol();
    }

    /**
     * Redraw this Relationships symbol based on the endpoint
     */
    public void refreshSymbol() {
        // Implemented by subclasses
    }

    /**
     * Rotate the relationship
     */
    public void rotate() {
        m_angle = Math.atan2(m_end.getY() - m_start.getY(), m_end.getX() - m_start.getX());
        refreshSymbol();
    }

    /**
     * Get the Polygon the makes up the endpoints symbol.
     *
     * @return Polygon, which is the Symbol
     */
    public Polygon getSymbol() {
        return m_symbol;
    }

    /**
     * Set the Polygon symbol for this Relationship
     *
     * @param symbol Polygon
     */
    public void setSymbol(Polygon symbol) {
        m_symbol = symbol;
    }

    /**
     * Override clone method
     *
     * @return clone of relationship
     */
    @Override
    public Relationship clone() {
        final Relationship copy;

        try {
            copy = (Relationship) super.clone();
        } catch (CloneNotSupportedException e) {

            e.printStackTrace();
            return null;
        }
        return copy;
    }

}
