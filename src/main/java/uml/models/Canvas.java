package uml.models;

import uml.Settings;
import uml.controls.EventManager;
import uml.models.Generics.Relationship;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Canvas extends JLayeredPane {

    // Local Variables
    public int m_width, m_height;
    private ArrayList<Relationship> m_relationships;
    private ArrayList<ClassBox> m_classBoxes;

    /**
     * Constructor
     */
    public Canvas() {
        m_width = Settings.getCanvasWidth();
        m_height = Settings.getCanvasHeight();
        m_relationships = new ArrayList<>();
        m_classBoxes = new ArrayList<>();
        bindListeners();
    }

    /**
     * Binds the mouse listeners to the canvas.
     */
    private void bindListeners() {
        addMouseListener(EventManager.getRelationshipListener());
        addMouseMotionListener(EventManager.getRelationshipListener());
    }

    /**
     * Add a classbox to the collection of boxes.
     * @param box
     */
    public void addClassBox(ClassBox box)
    {
        m_classBoxes.add(box);
        this.add(box);
    }

    /**
     * Remove a ClassBox from the collection of boxes.
     * @param box
     */
    public void removeClassBox(ClassBox box)
    {
        m_classBoxes.remove(box);
        this.remove(box);
    }

    /**
     * Add a relationship to the collection.
     * @param relationship
     */
    public void addRelationship(Relationship relationship)
    {
        m_relationships.add(relationship);
    }

    /**
     * Remove a relationship from the collection.
     * @param relationship
     */
    public void removeRelationship(Relationship relationship)
    {
        m_relationships.remove(relationship);
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
     */
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        //Antialiasing to smooth lines
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //draw each relationship
        for (Relationship r : m_relationships) {
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
