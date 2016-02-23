package uml.models;

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
    private int m_activeIndex;
    private Point m_clickPoint;

    public Canvas() {
        init();
    }

    private void init() {
        // Set to Null so Boxes and Lines can be placed freely
   
        this.setLayout(null);
        m_boxes = new ArrayList<ClassBox>();
        m_relationships = new ArrayList<Relationship>();
        m_activeIndex = -1;
        addMouseListener(new Canvas.EventMouseListener());
        addMouseMotionListener(new Canvas.EventMouseMotionListener());
    }

    public void addBox(ClassBox box) {
        m_boxes.add(box);
    }

    public void addRelationship(Relationship relationship) {
        m_relationships.add(relationship);
    }

    class EventMouseListener extends MouseAdapter {

        public void mousePressed(MouseEvent event) {

            m_clickPoint = event.getPoint();
            for (int i = 0; i < m_relationships.size(); i++) {
                if (m_relationships.get(i).getPoint1().distance(m_clickPoint) <= 5) {
                    m_activeIndex = i;
                    m_relationships.get(i).setColor(Color.blue);
                    repaint();
                }
            }
        }

        public void mouseReleased(MouseEvent event) {
            if (m_activeIndex != -1) {
                m_relationships.get(m_activeIndex).setColor(Color.gray);
                repaint();
                m_activeIndex = -1;
            }
        }
    }

    class EventMouseMotionListener extends MouseMotionAdapter {

        public void mouseDragged(MouseEvent event) {
            if (m_activeIndex != -1) {
                Relationship activeRelationship = m_relationships.get(m_activeIndex);
                int x = activeRelationship.getPoint1().x - event.getX();
                int y = activeRelationship.getPoint1().y - event.getY();
                activeRelationship.setPoint1(event.getPoint());
                x = activeRelationship.getPoint2().x - x;
                y = activeRelationship.getPoint2().y - y;
                activeRelationship.setPoint2(new Point(x, y));
                m_clickPoint = activeRelationship.getPoint1();
                m_relationships.add(m_activeIndex, activeRelationship);
                repaint();
            }
        }

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
