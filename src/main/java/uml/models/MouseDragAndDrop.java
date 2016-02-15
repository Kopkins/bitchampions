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
        JMenuBar menuBar = new JMenuBar();
        menuBar.setSize(new Dimension(100, 25));
        JButton button = new JButton("+ Add a Class Box");
        button.setPreferredSize(new Dimension(50, 25));
        menuBar.add(button);
        window.add(menuBar, BorderLayout.NORTH);

        JButton button2 = new JButton("+ Add a Line");
        button2.setPreferredSize(new Dimension(50, 25));
        menuBar.add(button2);

        canvas = new JPanel();
        canvas.setLayout(null);
        window.add(canvas);
        button.addActionListener(new ActionListener() {
            int counter = 0;
            public void actionPerformed(ActionEvent e) {
                counter++;
                ClassBox classBox = new ClassBox(counter);
                classBox.setBounds(10 * counter, 10 * counter, classBox.getWidth(), classBox.getHeight());
                canvas.add(classBox, 0);
                canvas.revalidate();
                canvas.repaint();
            }
        });

        button2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                canvas.revalidate();
                canvas.repaint();
            }
        });

        setSize(900, 900);
        setVisible(true);
    }

    public static void main(String arg[]) {
        new MouseDragAndDrop();
    }

    class ClassBox extends JPanel {
        int p, q;
        int p1, q1, p2, q2;
        int id;
        public ClassBox(int count) {
            id = count;
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
}
