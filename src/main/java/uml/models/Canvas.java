package uml.models;

import uml.models.Generics.GenericRelationship;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Canvas extends JLayeredPane {

    // Local Variables
    public int m_width, m_height;
    private ArrayList<GenericRelationship> m_relationships;
    private ArrayList<ClassBox> m_classBoxes;

    /**
     * Constructor
     */
    public Canvas() {
        m_width = 1500;
        m_height = 1500;
        m_relationships = new ArrayList<GenericRelationship>();
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
    public ArrayList<GenericRelationship> getRelationships() {
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

        //draw each relationship
        for (GenericRelationship r : m_relationships) {
            if (r.getType() == "Dependency") {
                g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
            } else {
                g2d.setStroke(new BasicStroke(2.0f));
            }
            int x1 = Math.round(r.getStartPoint().x);
            int y1 = Math.round(r.getStartPoint().y);
            int x2 = Math.round(r.getEndPoint().x);
            int y2 = Math.round(r.getEndPoint().y);
            g2d.setColor(r.getColor());
            g2d.drawLine(x1, y1, x2, y2);
            g2d.rotate(r.getAngle(), r.getEndPoint().x, r.getEndPoint().y);
            if (r.getType() == "Dependency") {
                g2d.setStroke(new BasicStroke(2.0f));
            }
            if (r.getType() == "Dependency" || r.getType() == "DirectedAssociation") {
                Polygon symbol = r.getSymbol();
                g2d.drawLine(symbol.xpoints[0], symbol.ypoints[0], symbol.xpoints[1], symbol.ypoints[1]);
                g2d.drawLine(symbol.xpoints[0], symbol.ypoints[0], symbol.xpoints[2], symbol.ypoints[2]);
            } else if (r.getType() == "Composition") {
                g2d.fillPolygon(r.getSymbol());
            } else {
                g2d.setColor(this.getBackground());
                //fill with color of canvas to cover the end of the line going through the symbol
                g2d.fillPolygon(r.getSymbol());
                g2d.setColor(r.getColor());
                g2d.drawPolygon(r.getSymbol());
            }
            g2d.rotate(-r.getAngle(), r.getEndPoint().x, r.getEndPoint().y);
        }
    }
}
