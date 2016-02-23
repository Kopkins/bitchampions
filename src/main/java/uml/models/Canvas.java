package uml.models;

import uml.controls.CanvasManager;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.*;

public class Canvas extends JPanel {

    private ArrayList<ClassBox> m_boxes;
    private ArrayList<Relationship> m_relationships;

    public Canvas() {
        init();
    }

    private void init() {
        // Set to Null so Boxes and Lines can be placed freely
        this.setLayout(null);
        m_boxes = new ArrayList<ClassBox>();
        m_relationships = new ArrayList<Relationship>();
    }

    public void addBox(ClassBox box) {
        m_boxes.add(box);
    }

    public void addRelationship(Relationship relationship) {
        m_relationships.add(relationship);
    }

    public void addRelationship(Integer index, Relationship relationship) {
        m_relationships.add(index, relationship);
    }

    public ArrayList<ClassBox> getBoxes() {
        return m_boxes;
    }

    public ArrayList<Relationship> getRelationships() {
        return m_relationships;
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(2.0f));

        for (Relationship r : m_relationships) {
            int x1 = (int) Math.round(r.getPoint1().x);
            int y1 = (int) Math.round(r.getPoint1().y);
            int x2 = (int) Math.round(r.getPoint2().x);
            int y2 = (int) Math.round(r.getPoint2().y);
            g2d.setColor(r.getColor());
            g2d.drawLine(x1, y1, x2, y2);
        }
    }

}
