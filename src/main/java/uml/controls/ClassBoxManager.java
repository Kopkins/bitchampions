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

    /**
     * Constructor
     */
    public ClassBoxManager() {
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
            m_classBox = new ClassBox();
        }
        return m_classBox;
    }

}
