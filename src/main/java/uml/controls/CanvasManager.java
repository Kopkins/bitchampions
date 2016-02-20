package uml.controls;

import uml.models.Canvas;
import uml.models.ClassBox;
import uml.models.Relationship;
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
    private static final int OFFSET = 20;
    /**
     * Constructor
     */
    public CanvasManager()
    {
    }

    /**
     * Attaches a canvas to the main window
     */
    public void bindCanvas(Container pane)
    {
        Canvas canvas = getSharedCanvas();

        // Setup border
        CompoundBorder line = new CompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
            BorderFactory.createLineBorder(Color.black));
        Border canvasBorder = BorderFactory.createTitledBorder(line, "Canvas");
        canvas.setBorder(canvasBorder);

        // Bind canvas
        pane.add(canvas, BorderLayout.CENTER);
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

    /**
     * Get an ActionListener that will add new CanvasBoxes to the canvas.
     * @return
     */
    public ActionListener getAddBoxListener()
    {
        ActionListener listener = new ActionListener() {
            int counter = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                counter++;
                ClassBox classBox = new ClassBox();
                classBox.setBounds(OFFSET * counter, OFFSET * counter, classBox.getWidth(), classBox.getHeight());
                getSharedCanvas().add(classBox, 0);
                getSharedCanvas().revalidate();
                getSharedCanvas().repaint();
                getSharedCanvas().addBox(classBox);
                
            }
            
        };
        return listener;
    }

     /**
     * Get an ActionListener that will add new Relationship to the canvas.
     * @return
     */
    public ActionListener getAddRelationshipListener()
    {
        ActionListener listener = new ActionListener() {
            int counter = 0;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                Relationship line = new Relationship();
                line.setBounds(OFFSET  * counter, OFFSET * counter, line.getWidth(), line.getHeight());
                getSharedCanvas().add(line, 0);
                getSharedCanvas().revalidate();
                getSharedCanvas().repaint();
                getSharedCanvas().addRelationship(line);
            }
        };
        return listener;
    }

    /**
     * Get an ActionListener for revalidating and repainting the canvas panel
     * @return listener
     */
    public ActionListener getRevalidateListener()
    {
        ActionListener listener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                getSharedCanvas().revalidate();
                getSharedCanvas().repaint();
            }
        };
        return listener;
    }

}
