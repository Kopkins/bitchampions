package uml.models;

import javax.swing.*;
import java.awt.*;
import uml.controls.CanvasManager;

public class ClassBox extends JPanel {

// Local Variables
    private int m_width;
    private int m_height;
    private Point m_origin;
    private Point m_clickPoint;
    private CanvasManager m_canvasManager;

    /**
     * Constructor
     */
    public ClassBox(CanvasManager c) {
        m_canvasManager = c;
        m_width = 220;
        m_height = 270;
        m_origin = new Point(20, 20);
        m_clickPoint = new Point();
        init();
    }

    /**
     * Build components for ClassBox
     */
    private void init() {

        //set the color, size, and border for the classbox 
        setBackground(Color.gray);
        setSize(new Dimension(m_width, m_height));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        //initialize the textfield for the classbox name and set it's size and border
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 25));
        textField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        //initialize the textarea for the classbox attributes and set it's size and border
        JTextArea textArea1 = new JTextArea();
        textArea1.setLineWrap(true);
        JScrollPane scrollPane1 = new JScrollPane(textArea1);
        scrollPane1.setPreferredSize(new Dimension(200, 100));
        scrollPane1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        //initialize the textarea for the classbox methods and set it's size and border
        JTextArea textArea2 = new JTextArea();
        textArea2.setLineWrap(true);
        JScrollPane scrollPane2 = new JScrollPane(textArea2);
        scrollPane2.setPreferredSize(new Dimension(200, 100));
        scrollPane2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        //add the name, attribute, and method fields to the classbox
        add(textField);
        add(scrollPane1);
        add(scrollPane2);
    }

    /**
     * Gets the origin point.
     *
     * @return
     */
    public Point getOrigin() {
        return m_origin;
    }

    /**
     * Sets the origin point.
     *
     */
    public void setOrigin(Point p) {
        m_origin = p;
    }

    /**
     * Gets the point that was clicked on.
     *
     * @return
     */
    public Point getClickPoint() {
        return m_clickPoint;
    }

    /**
     * Sets the point that was clicked on.
     *
     */
    public void setClickPoint(Point p) {
        m_clickPoint = p;
    }
    
    /**
     * Gets the classBox's CanvasManager.
     *
     * @return
     */
    public CanvasManager getCanvasManager() {
        return m_canvasManager;
    }
}
