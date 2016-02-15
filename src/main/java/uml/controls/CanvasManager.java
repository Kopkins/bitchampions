package uml.controls;

import uml.models.Canvas;
import uml.views.EditorGUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;

public class CanvasManager {

    // Local Variables
    private Canvas _canvas;

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
        CompoundBorder line = new CompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10),
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
}
