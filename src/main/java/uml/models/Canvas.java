package uml.models;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Canvas extends JLayeredPane {

    // Local Variables
    private int m_minWidth, m_minHeight;
    private ArrayList<Relationship> m_relationships;
    private ArrayList<ClassBox> m_classBoxes;

    /**
     * Constructor
     */
    public Canvas() {
        m_minWidth = 800;
        m_minHeight = 800;
        m_relationships = new ArrayList<Relationship>();
        m_classBoxes = new ArrayList<ClassBox>();
        init();
    }

    private void init() {
        // Set to Null so Boxes and Lines can be placed freely
        this.setLayout(null);
    }

    /**
     * gets the ArrayList of Relationships
     */
    public ArrayList<Relationship> getRelationships() {
        return m_relationships;
    }

    /**
     * gets the ArrayList of ClassBoxes
     */
    public ArrayList<ClassBox> getClassBoxes() {
        return m_classBoxes;
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
