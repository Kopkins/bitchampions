package uml.models.Generics;

import uml.Settings;

import javax.swing.*;
import java.awt.*;

public abstract class Relationship extends JComponent {
    protected Point m_start, m_end;
    protected Color m_color;
    protected Polygon m_symbol;
    protected String m_type;
    protected double m_angle;

    public void init() {
        m_angle = 0.0;
        m_start = new Point(320, 20);
        m_end = new Point(440, 20);
        m_color = Settings.Colors.DEFAULT.color;
    }

    public void setType(String type) {
        m_type = type;
    }

    public String getType() {
        return m_type;
    }

    public Color getColor() {
        return m_color;
    }

    public void setColor(Color c) {
        m_color = c;
    }

    public Point getStartPoint() {
        return m_start;
    }

    public void setStartPoint(Point p) {
        m_start = p;
    }

    public Point getEndPoint() {
        return m_end;
    }

    public void setEndPoint(Point p) {
        m_end = p;
    }

    public double getAngle() {
        return m_angle;
    }

    public void translate(int dx, int dy) {
        m_start.translate(dx, dy);
        m_end.translate(dx, dy);
        refreshSymbol();
    }

    public void refreshSymbol() {
        // Implemented by subclasses
    }

    public void rotate() {
        m_angle = Math.atan2(m_end.getY() - m_start.getY(), m_end.getX() - m_start.getX());
        refreshSymbol();
    }

    public Polygon getSymbol() {
        return m_symbol;
    }

    public void setSymbol(Polygon symbol) {
        m_symbol = symbol;
    }

}