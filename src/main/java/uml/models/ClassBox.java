package uml.models;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class ClassBox extends JPanel {

    int p, q;
    int p1, q1, p2, q2;

    public ClassBox() {
        init();
    }

    private void init()
    {
        //this.setLocation(5*count, 5*count);
        this.setBackground(Color.gray);
        this.setSize(new Dimension(225, 270));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

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

        this.add(textField);
        this.add(scrollPane1);
        this.add(scrollPane2);
        addMouseListener(new EventMouseListener());
        addMouseMotionListener(new EventMouseMotionListener());
    }

    class EventMouseListener extends MouseAdapter {

        public void mousePressed(MouseEvent event) {
            p = getX();
            q = getY();
            p1 = event.getX();
            q1 = event.getY();
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

            p2 = event.getX();
            q2 = event.getY();

            p = p + p2 - p1;
            q = q + q2 - q1;

            setLocation(p, q);
        }
    }
}
