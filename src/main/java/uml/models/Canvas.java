package uml.models;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Canvas extends JPanel {

    // Local Variables
    private int m_minWidth, m_minHeight;
    private ArrayList<Relationship> m_relationships;

    /**
     * Constructor
     */
    public Canvas() {
        m_minWidth = 800;
        m_minHeight = 800;
        m_relationships = new ArrayList<Relationship>();
        init();
    }

    private void init() {
        // Set to Null so Boxes and Lines can be placed freely
        this.setLayout(null);
    }

    /**
     * Sets the ArrayList of Relationships for paint
     */
    public void setRelationships(ArrayList<Relationship> r) {
        m_relationships = r;
    }

    /**
     * Override paint method to draw relationships on canvas
     *
     */
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        //Antialiasing to smooth lines
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(2.0f));
        //draw each relationship
        for (Relationship r : m_relationships) {
            int x1 = (int) Math.round(r.getStartPoint().x);
            int y1 = (int) Math.round(r.getStartPoint().y);
            int x2 = (int) Math.round(r.getEndPoint().x);
            int y2 = (int) Math.round(r.getEndPoint().y);
            g2d.setColor(r.getColor());
            g2d.drawLine(x1, y1, x2, y2);
        }
    }
}
