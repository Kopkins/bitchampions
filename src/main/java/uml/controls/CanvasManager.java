package uml.controls;

import uml.models.Canvas;
import uml.models.ClassBox;
import uml.views.EditorGUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CanvasManager {

    // Local Variables
    private Canvas _canvas;
    ArrayList<ClassBox> _boxes;

    /**
     * Constructor
     */
    public CanvasManager()
    {
        init();
    }

    /**
     * Attaches a canvas to the main window
     */
    public void bindCanvas(Container pane)
    {
        Canvas canvas = getSharedCanvas();

        // Setup border
        CompoundBorder line = new CompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10),
            BorderFactory.createLineBorder(Color.black));
        Border canvasBorder = BorderFactory.createTitledBorder(line, "Canvas");
        canvas.setBorder(canvasBorder);

        // Bind canvas
        pane.add(canvas, BorderLayout.CENTER);
    }

    /**
     * Bind a new classBox to the canvas
     */
    public void addClassBox()
    {
        _boxes.add(new ClassBox());
    }

    /**
     * Gets a singleton instance of canvas for the editors window.
     * @return
     */
    private Canvas getSharedCanvas()
    {
        if (_canvas == null)
        {
            _canvas =  new Canvas();
        }
        return _canvas;
    }

    private void init()
    {
        Canvas canvas = getSharedCanvas();
        JMenuBar menuBar = new JMenuBar();
        menuBar.setSize(new Dimension(100, 25));
        JButton button = new JButton("+ Add a Class Box");
        button.setPreferredSize(new Dimension(50, 25));
        menuBar.add(button);

//        JButton button2 = new JButton("+ Add a Line");
//        button2.setPreferredSize(new Dimension(50, 25));
//        menuBar.add(button2);
        canvas.add(menuBar);

        button.addActionListener(new ActionListener() {
            int counter = 0;
            public void actionPerformed(ActionEvent e) {
                counter++;
                ClassBox classBox = new ClassBox();
                classBox.setBounds(10 * counter, 10 * counter, classBox.getWidth(), classBox.getHeight());
                canvas.add(classBox, 0);
                canvas.revalidate();
                canvas.repaint();
            }
        });

//        button2.addActionListener(new ActionListener() {
//
//            public void actionPerformed(ActionEvent e) {
//
//                canvas.revalidate();
//                canvas.repaint();
//            }
//        });
    }
}
