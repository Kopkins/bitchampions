package uml.models;

import javax.swing.*;
import java.awt.*;

public class ClassBox extends JPanel {

// Local Variables
    private int m_width, m_height;
    private Point m_origin, m_clickPoint;
    private JTextField m_name;
    private JTextArea m_attributes, m_operations;

    /**
     * Constructor
     */
    public ClassBox() {
        m_width = 200;
        m_height = 240;
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
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        //initialize the textfield for the classbox name and set it's size and border
        m_name = new JTextField();
        m_name.setPreferredSize(new Dimension(194, 20));
        m_name.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        //initialize the textarea for the classbox attributes and set it's size and border
        m_attributes = new JTextArea();
        m_attributes.setLineWrap(true);
        m_attributes.setPreferredSize(new Dimension(194, 100));
        m_attributes.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        //initialize the textarea for the classbox methods and set it's size and border
        m_operations = new JTextArea();
        m_operations.setLineWrap(true);
        m_operations.setPreferredSize(new Dimension(194, 100));
        m_operations.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        //add the name, attribute, and method fields to the classbox
        add(m_name);
        add(m_attributes);
        add(m_operations);
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
     * Resize the class box
     *
     */
    public void resize(Point p) {
        //Calculate the distance the mouse traveled
        int adjustedWidth = p.x - m_clickPoint.x;
        int adjustedHeight = p.y - m_clickPoint.y;
        //Add the distance the mouse traveled to the height and width of the ClassBox and resize it
        m_width += adjustedWidth;
        m_height += adjustedHeight;
        setSize(new Dimension(m_width, m_height));
        //resize the text fields based on size of the ClassBox
        m_name.setPreferredSize(new Dimension(m_width - 6, m_name.getHeight()));
        if (m_clickPoint.y > .8 * m_height) {
            m_attributes.setPreferredSize(new Dimension(m_width - 6, m_attributes.getHeight()));
            m_operations.setPreferredSize(new Dimension(m_width - 6, m_operations.getHeight() + adjustedHeight));

        } else {
            m_attributes.setPreferredSize(new Dimension(m_width - 6, m_attributes.getHeight() + adjustedHeight));            //m_attributes.getComponent(0).setSize(new Dimension(m_width - 6, m_attributes.getHeight() + adjustedHeight));
            m_operations.setPreferredSize(new Dimension(m_width - 6, m_operations.getHeight()));
            //if the middle textArea's height changes then we need to move the last textArea's location 
            m_operations.setLocation(m_operations.getX(), m_operations.getY() + adjustedHeight);
        }
    }
}
