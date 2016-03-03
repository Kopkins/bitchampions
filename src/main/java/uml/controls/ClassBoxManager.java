package uml.controls;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import uml.models.ClassBox;
import java.awt.*;
import uml.controls.EventManager;

public class ClassBoxManager {

    // Local Variables
    private ClassBox m_classBox;
    private CanvasManager m_canvasManager;

    /**
     * Constructor
     * @param c
     */
    public ClassBoxManager(CanvasManager c) {
        m_canvasManager = c;
        init();
    }

    /**
     * Add MouseListeners to ClassBoxManager
     */
    private void init() {
        getSharedClassBox().addMouseListener(new EventManager(this));
        getSharedClassBox().addMouseMotionListener(new EventManager(this));
    }

    /**
     * Gets a ClassBox.
     *
     * @return
     */
    public ClassBox getSharedClassBox() {
        if (m_classBox == null) {
            m_classBox = new ClassBox(m_canvasManager);
        }
        return m_classBox;
    }

}
