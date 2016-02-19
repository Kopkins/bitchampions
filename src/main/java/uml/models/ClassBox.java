package uml.models;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class ClassBox extends JPanel {

    private Point origin;
    private Point clickedPoint;

    public ClassBox() {
        init();
    }

    private void init() {
        origin = new Point();
        clickedPoint = new Point();
        setBackground(Color.gray);
        setSize(new Dimension(225, 270));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 25));
        textField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JTextArea textArea1 = new JTextArea();
        textArea1.setLineWrap(true);
        JScrollPane scrollPane1 = new JScrollPane(textArea1);
        scrollPane1.setPreferredSize(new Dimension(200, 100));
        scrollPane1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JTextArea textArea2 = new JTextArea();
        textArea2.setLineWrap(true);
        JScrollPane scrollPane2 = new JScrollPane(textArea2);
        scrollPane2.setPreferredSize(new Dimension(200, 100));
        scrollPane2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        add(textField);
        add(scrollPane1);
        add(scrollPane2);
        addMouseListener(new EventMouseListener());
        addMouseMotionListener(new EventMouseMotionListener());
    }

    /*
    TODO These should probably be abstracted out into ClassBoxManager or an Event Manager
     */
    class EventMouseListener extends MouseAdapter {

        public void mousePressed(MouseEvent event) {
            origin = getLocation();
            clickedPoint = event.getPoint();
            setBackground(Color.blue);
        }

        public void mouseReleased(MouseEvent event) {
            setBackground(Color.gray);
        }

        public void mouseClicked(MouseEvent event) {
        }
    }

    class EventMouseMotionListener extends MouseMotionAdapter {

        public void mouseDragged(MouseEvent event) {
            origin.x += event.getX() - clickedPoint.x;
            origin.y += event.getY() - clickedPoint.y;
            setLocation(origin);
        }
    }
}
