package uml.models;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Relationship extends JComponent {

    private Point point1, point2, clickPoint;

    public Relationship() {
        init();
    }

    private void init() {
        point1 = new Point(20, 20);
        point2 = new Point(220, 20);
        clickPoint = new Point();
        setForeground(Color.gray);
        addMouseListener(new EventMouseListener());
        addMouseMotionListener(new EventMouseMotionListener());
        setSize(new Dimension(280, 40));
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2.0f));
        int x1 = (int) Math.round(point1.x);
        int y1 = (int) Math.round(point1.y);
        int x2 = (int) Math.round(point2.x);
        int y2 = (int) Math.round(point2.y);
        g2d.drawLine(x1, y1, x2, y2);
    }

    class EventMouseListener extends MouseAdapter {

        public void mousePressed(MouseEvent event) {
            clickPoint = event.getPoint();
            setForeground(Color.BLUE);
        }

        public void mouseReleased(MouseEvent event) {
            setForeground(Color.gray);
        }

        public void mouseClicked(MouseEvent event) {

        }
    }

    class EventMouseMotionListener extends MouseMotionAdapter {

        public void mouseDragged(MouseEvent event) {
            int x = (int) Math.round(getLocation().getX()) + event.getX() - clickPoint.x;
            int y = (int) Math.round(getLocation().getY()) + event.getY() - clickPoint.y;
            setLocation(x, y);
        }
    }
}
