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
import java.util.ArrayList;
import java.util.Map;
import uml.models.Generics.Relationship;

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
            m_canvasManager.setClickPoint(event.getPoint());
            //check if in deleteMode
            if (m_canvasManager.m_isDeleteMode) {
                // changed color to grey for undo/redo before deleteing 
                box.setBackground(Color.gray);
                m_canvasManager.deleteClassBox(box);
                m_canvasManager.toggleDeleteMode();
                // need to delete every anchor associated with the class box 
                Map anchors = box.getAnchors();
                for (Object key : anchors.keySet()) {
                    int i = Integer.parseInt(key.toString());
                    Relationship r = CanvasManager.getSharedCanvas().getRelationshipById(i);
                    r.setAnchoredCount(r.getAnchoredCount() - 1);
                }
                m_canvasManager.repaintCanvas();
                // add current state to undoRedoManager
                m_undoRedoManager.pushRelationshipsToUndo(CanvasManager.getSharedCanvas().getDeepCopyRelationships());
                m_undoRedoManager.pushClassBoxesToUndo(CanvasManager.getSharedCanvas().getDeepCopyClassBoxes());
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
            // add current state to undoRedoManager
            m_undoRedoManager.pushRelationshipsToUndo(CanvasManager.getSharedCanvas().getDeepCopyRelationships());
            m_undoRedoManager.pushClassBoxesToUndo(CanvasManager.getSharedCanvas().getDeepCopyClassBoxes());
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
            if (m_canvasManager.m_isAnchorMode) {
                // change color to blue to show classBox is active
                box.setBackground(Color.blue);
                //add the anchor to the classbox
                int index = m_canvasManager.getActiveRelationshipIndex();
                Relationship r = CanvasManager.getSharedCanvas().getRelationships().get(index);
                // if box already has this anchor dont add
                if (!box.getAnchors().containsKey(r.getId())) {
                    box.addAnchor(r.getId(), m_canvasManager.getPointType());
                    // set anchored to true for the active relationship
                    r.setAnchoredCount(r.getAnchoredCount() + 1);
                }
            }
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
        ClassBox box;
        try {
            box = (ClassBox) event.getSource();
            if (m_canvasManager.m_isAnchorMode) {
                //remove the anchor from the classbox 
                int index = m_canvasManager.getActiveRelationshipIndex();
                Relationship r = CanvasManager.getSharedCanvas().getRelationships().get(index);
                box.deleteAnchor(r.getId());
                // set anchored to false for the active relationship
                r.setAnchoredCount(r.getAnchoredCount() - 1);
            }
            if (!m_canvasManager.m_isDeleteMode) {
                // change color back to gray to show classBox is no longer active
                box.setBackground(Color.gray);
            }
        } catch (ClassCastException ex) {
            System.out.println(ex);
            throw ex;
        }

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
                // Get the distance the box was moved so the anchors can be moved the same distance
                int deltaX = newOrigin.x - box.getOrigin().x;
                int deltaY = newOrigin.y - box.getOrigin().y;
                box.setOrigin(newOrigin);
                box.setLocation(box.getOrigin());
                // need to move every anchor associated with the class box as the class box moves
                Map anchors = box.getAnchors();
                for (Object key : anchors.keySet()) {
                    int i = Integer.parseInt(key.toString());
                    Relationship r = CanvasManager.getSharedCanvas().getRelationshipById(i);
                    // determine whether to move the start point or end point of relationship
                    if (anchors.get(key).equals("start") && r != null) {
                        x = r.getStartPoint().x + deltaX;
                        y = r.getStartPoint().y + deltaY;
                        r.setStartPoint(new Point(x, y));
                        //m_canvasManager.setClickPoint(r.getStartPoint());
                    } else {
                        x = r.getEndPoint().x + deltaX;
                        y = r.getEndPoint().y + deltaY;
                        r.setEndPoint(new Point(x, y));
                    }
                    // rotate the angle for the relatioship and repaint
                    r.rotate();
                    m_canvasManager.repaintCanvas();
                }
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
     * 'Snaps' points to a grid. ClassBox origins will be translated to the
     * nearest point on the grid.
     *
     * @param x int, which is the horizontal component of the point to be
     * translated.
     * @param y int, which is the vertical component of the point to be
     * translated.
     * @return Point, which is the original point translated to it's nearest
     * point on the grid.
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
     * @param x int, which is the horizontal component of the point being
     * validated.
     * @param y int, which is the vertical component of the point being
     * validated.
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
