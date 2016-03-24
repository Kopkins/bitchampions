package uml.models;

import uml.Settings;
import uml.controls.EventManager;

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
        m_width = 120;
        m_height = 150;
        m_origin = new Point(20, 20);
        m_clickPoint = new Point();
        init();
    }

    /**
     * Build components for ClassBox
     */
    private void init() {

        //set the color, size, and border for the classbox 
        setBackground(Settings.Colors.DEFAULT.color);
        setSize(new Dimension(m_width, m_height));
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        //initialize the textfield for the classbox name and set it's size and border
        m_name = new JTextField();
        m_name.setFont(new Font(m_name.getFont().getName(), Font.BOLD, 16));
        m_name.setHorizontalAlignment(SwingConstants.CENTER);
        m_name.setPreferredSize(new Dimension(m_width - 6, (m_height - 18) / 7));
        m_name.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        //initialize the textarea for the classbox attributes and set it's size and border
        m_attributes = new JTextArea();
        m_attributes.setLineWrap(true);
        m_attributes.setPreferredSize(new Dimension(m_width - 6, (m_height - 18) * 3 / 7));
        m_attributes.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        //initialize the textarea for the classbox methods and set it's size and border
        m_operations = new JTextArea();
        m_operations.setLineWrap(true);
        m_operations.setPreferredSize(new Dimension(m_width - 6, (m_height - 18) * 3 / 7));
        m_operations.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        //add the name, attribute, and method fields to the classbox
        add(m_name);
        add(m_attributes);
        add(m_operations);

        addMouseListener(EventManager.getClassBoxListener());
        addMouseMotionListener(EventManager.getClassBoxListener());
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
     */
    public void setClickPoint(Point p) {
        m_clickPoint = p;
    }

    /**
     * Resize the class box
     */
    public void resize(Point p) {
        //Calculate the distance the mouse traveled
        int adjustedWidth = p.x - m_clickPoint.x;
        int adjustedHeight = p.y - m_clickPoint.y;
        //If the clickPoint is on the right side of the classbox then resize the width
        if (m_clickPoint.x > m_width * .5) {
            m_width += adjustedWidth;
        }
        //If the clickPoint is not on the upper part of the classBox then resize the height
        if (m_clickPoint.y > .2 * m_height) {
            m_height += adjustedHeight;
        }
        setSize(new Dimension(m_width, m_height));
        //resize the text fields based on size of the ClassBox
        m_name.setPreferredSize(new Dimension(m_width - 6, m_name.getHeight()));
        if (m_clickPoint.y > .8 * m_height) {
            //If the clickPoint is on bottom of classBox then reisze the height the second textArea
            m_attributes.setPreferredSize(new Dimension(m_width - 6, m_attributes.getHeight()));
            m_operations.setPreferredSize(new Dimension(m_width - 6, m_operations.getHeight() + adjustedHeight));

        } else if (m_clickPoint.y > .2 * m_height) {
            //If the clickPoint is on middle of classBox then reisze the height the first textArea
            m_attributes.setPreferredSize(new Dimension(m_width - 6, m_attributes.getHeight() + adjustedHeight));
            m_operations.setPreferredSize(new Dimension(m_width - 6, m_operations.getHeight()));
            //if the middle textArea's height changes then we need to move the last textArea's location 
            m_operations.setLocation(m_operations.getX(), m_operations.getY() + adjustedHeight);
        } else {
            m_attributes.setPreferredSize(new Dimension(m_width - 6, m_attributes.getHeight()));
            m_operations.setPreferredSize(new Dimension(m_width - 6, m_operations.getHeight()));
        }
    }
}
