package uml.controls.listeners;

import uml.Settings;
import uml.controls.CanvasManager;
import uml.models.ClassBox;
import uml.controls.UndoRedoManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class ClassBoxListener implements MouseListener, MouseMotionListener {

    //Local Variables
    CanvasManager m_canvasManager;
    UndoRedoManager m_undoRedoManager;
    private boolean m_snapToGrid = true;
    
    /**
     * Constructor
     */
    public ClassBoxListener() {
        m_canvasManager = CanvasManager.getInstance();
        m_undoRedoManager = UndoRedoManager.getInstance();
    }

    /**
     * Mandatory override for implementing MouseListener.
     *
     * @param event
     */
    @Override
    public void mouseClicked(MouseEvent event) {

    }

    /**
     * Alter the color and z-index of a ClassBox when mouse is pressed.
     *
     * @param event
     */
    @Override
    public void mousePressed(MouseEvent event) {

        ClassBox box;
        try {
            box = (ClassBox) event.getSource();
            CanvasManager.getSharedCanvas().moveToFront(box);

            // get point the mouse is pressed on
            box.setClickPoint(event.getPoint());
            //check if in deleteMode
            if (m_canvasManager.m_isDeleteMode) {
                // changed color to grey for undo/redo before deleteing 
                box.setBackground(Color.gray);
                m_canvasManager.deleteClassBox(box);
                m_canvasManager.toggleDeleteMode();
                m_canvasManager.repaintCanvas();
                // add current state to undoRedoManager
                m_undoRedoManager.pushRelationshipsToUndo(CanvasManager.getSharedCanvas().getRelationships());
                m_undoRedoManager.pushClassBoxesToUndo(CanvasManager.getSharedCanvas().getClassBoxes());
            } else {
                // changed color to blue to show classBox is active
                box.setBackground(Color.blue);
            }
        } catch (ClassCastException exception) {
            System.out.println(exception);
        }
    }

    /**
     * Alter the color of a ClassBox when mouse is released
     *
     * @param event
     */
    @Override
    public void mouseReleased(MouseEvent event) {
        ClassBox box;
        try {
            box = (ClassBox) event.getSource();
            box.setBackground(Color.gray);
        } catch (ClassCastException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Change the cursor when entering a ClassBox
     *
     * @param event
     */
    @Override
    public void mouseEntered(MouseEvent event) {
        ClassBox box;
        try {
            box = (ClassBox) event.getSource();
            box.setCursor(new Cursor(Cursor.HAND_CURSOR));
        } catch (ClassCastException ex) {
            System.out.println(ex);
            throw ex;
        }
    }

    /**
     * Mandatory override for implementing MouseListener.
     *
     * @param event
     */
    @Override
    public void mouseExited(MouseEvent event) {

    }

    /**
     * ClassBox drag logic.
     *
     * @param event
     */
    @Override
    public void mouseDragged(MouseEvent event) {
        ClassBox box;
        try {
            box = (ClassBox) event.getSource();

            if (SwingUtilities.isLeftMouseButton(event)) {
                //calculate the distance of mouse drag event, update the origin to this point and move the location of the classbox
                int x = box.getOrigin().x + event.getX() - box.getClickPoint().x;
                int y = box.getOrigin().y + event.getY() - box.getClickPoint().y;

                // Keep boxes within canvas
                Point newOrigin = validateMovement(x, y);
                box.setOrigin(newOrigin);
                box.setLocation(box.getOrigin());
            } else if (SwingUtilities.isRightMouseButton(event)) {
                box.resize(event.getPoint());
                CanvasManager.getSharedCanvas().revalidate();
                CanvasManager.getSharedCanvas().repaint();
                box.setClickPoint(event.getPoint());
            }
        } catch (ClassCastException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Mandatory override for implementing MouseMotionListener.
     *
     * @param event
     */
    @Override
    public void mouseMoved(MouseEvent event) {

    }

    /**
     * 'Snaps' points to a grid. ClassBox origins will be translated to the nearest point on the grid.
     *
     * @param x int, which is the horizontal component of the point to be translated.
     * @param y int, which is the vertical component of the point to be translated.
     * @return Point, which is the original point translated to it's nearest point on the grid.
     */
    private Point snapToGrid(int x, int y) {
        int gridSize = Settings.getGridSize();
        int offset = x % gridSize;
        int x2, y2;
        x2 = (offset > gridSize / 2) ? x + gridSize - offset : x - offset;
        offset = y % gridSize;
        y2 = (offset > gridSize / 2) ? y + gridSize - offset : y - offset;

        return new Point(x2, y2);
    }

    /**
     * Checks to make sure a mouse movement is valid.
     *
     * @param x int, which is the horizontal component of the point being validated.
     * @param y int, which is the vertical component of the point being validated.
     * @return Point, which is a valid classBox origin on the canvas.
     */
    private Point validateMovement(int x, int y) {
        int x2, y2;
        int xMin = 0;
        int xMax = Settings.getCanvasWidth() - Settings.getBoxWidth();
        int yMin = 0;
        int yMax = Settings.getCanvasHeight() - Settings.getBoxHeight();

        x2 = x < xMin ? xMin : x;
        x2 = x > xMax ? xMax : x2;
        y2 = y < yMin ? yMin : y;
        y2 = y > yMax ? yMax : y2;

        if (m_snapToGrid) {
            return snapToGrid(x2, y2);
        } else {
            return new Point(x2, y2);
        }
    }
}
