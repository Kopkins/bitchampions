package uml.models;

    import java.awt.*;
    import javax.swing.*;
    import java.awt.event.*;
    import java.awt.geom.*;

public class MouseDragAndDrop extends JFrame {
    JPanel canvas;
    public MouseDragAndDrop() {
        Container window = getContentPane();
        //window.setLayout(null);

        setSize(900, 900);
        setVisible(true);
    }

    public static void main(String arg[]) {
        new MouseDragAndDrop();
    }

    class ClassBox extends JPanel {
        int p, q;
        int p1, q1, p2, q2;

        public ClassBox() {
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
        }
    }
}
