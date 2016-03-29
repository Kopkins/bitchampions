package uml.controls.listeners;

import uml.Settings;
import uml.controls.CanvasManager;
import uml.models.*;
import uml.models.Generics.Relationship;
import uml.controls.UndoRedoManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class RelationshipListener implements MouseMotionListener, MouseListener {

    // Local Variables
    private final int RADIUS = 5;
    private CanvasManager m_canvasManager;
    private UndoRedoManager m_undoRedoManager;

    /**
     * Constructor
     */
    public RelationshipListener() {
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
     * When mouse is clicked on the canvas, query a collection of relationships
     * to see if any endpoints reside at that location. If so, color it and
     * select it as the current relationship.
     *
     * @param event
     */
    @Override
    public void mousePressed(MouseEvent event) {
        m_canvasManager.toggleAnchorMode();
        // get the point the mouse is pressed on
        m_canvasManager.setClickPoint(event.getPoint());
        // loop through relationships arraylist and see if click point is within a 5 point radius of
        // any of the relationships origin point
        ArrayList<Relationship> relationships = CanvasManager.getSharedCanvas().getRelationships();
        for (int i = 0; i < relationships.size(); i++) {
            if (relationships.get(i).getStartPoint().distance(event.getPoint()) <= RADIUS
                    || relationships.get(i).getEndPoint().distance(event.getPoint()) <= RADIUS) {
                //check if in delete mode
                if (m_canvasManager.m_isDeleteMode) {
                    // changed color to grey for undo/redo before deleteing 
                    relationships.get(i).setColor(Color.gray);
                    m_canvasManager.deleteRelationship(i);
                    m_canvasManager.setActiveRelationshipIndex(-1);
                    m_canvasManager.toggleDeleteMode();
                    m_canvasManager.repaintCanvas();
                    // add current state to undoRedoManager
                    m_undoRedoManager.pushRelationshipsToUndo(CanvasManager.getSharedCanvas().getDeepCopyRelationships());
                    m_undoRedoManager.pushClassBoxesToUndo(CanvasManager.getSharedCanvas().getDeepCopyClassBoxes());
                } else //get the index of the active relationship
                {
                    m_canvasManager.setActiveRelationshipIndex(i);
                    // if clickpoint is within 5 point radius of relationship's origin point,
                    // set the relationship to active
                    relationships.get(i).setColor(Settings.Colors.SELECT.color);
                    // repaint the canvas so the active relationship's color is blue
                    m_canvasManager.repaintCanvas();
                }
            }
        }
    }

    /**
     * When mouse is released, recolor the line.
     *
     * @param event
     */
    @Override
    public void mouseReleased(MouseEvent event) {
        int activeIndex = m_canvasManager.getActiveRelationshipIndex();
        m_canvasManager.toggleAnchorMode();
        if (activeIndex != -1) {
            // change relationship's color back to gray to show it is no longer active and repaint
            ArrayList<Relationship> relationships = CanvasManager.getSharedCanvas().getRelationships();
            relationships.get(activeIndex).setColor(Settings.Colors.DEFAULT.color);
            m_canvasManager.repaintCanvas();
            m_canvasManager.setActiveRelationshipIndex(-1);
            // add current state to undoRedoManager
            m_undoRedoManager.pushRelationshipsToUndo(CanvasManager.getSharedCanvas().getDeepCopyRelationships());
            m_undoRedoManager.pushClassBoxesToUndo(CanvasManager.getSharedCanvas().getDeepCopyClassBoxes());
        }
    }

    /**
     * Mandatory override for implementing MouseListener.
     *
     * @param event
     */
    @Override
    public void mouseEntered(MouseEvent event) {

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
     * Logic for moving and rotating relationship lines on the canvas.
     *
     * @param event
     */
    @Override
    public void mouseDragged(MouseEvent event) {
        int activeIndex = m_canvasManager.getActiveRelationshipIndex();
        if (activeIndex != -1) {
            // get the active relationship
            Relationship activeRelationship = CanvasManager.getSharedCanvas().getRelationships().get(activeIndex);
            if (SwingUtilities.isLeftMouseButton(event) && activeRelationship.getAnchoredCount() < 1) {
                if (activeRelationship.getStartPoint().distance(m_canvasManager.getClickPoint()) <= RADIUS) {
                    // set the point type to start
                    m_canvasManager.setPointType("start");
                    // get the distance the origin point is moved
                    int x = activeRelationship.getStartPoint().x - event.getX();
                    int y = activeRelationship.getStartPoint().y - event.getY();
                    // set the active relationship's origin point to the point where the mouse is dragged
                    activeRelationship.setStartPoint(event.getPoint());
                    // calculate the point to move the active relationship's second point to, based on the
                    // distance it's origin point is moved
                    x = activeRelationship.getEndPoint().x - x;
                    y = activeRelationship.getEndPoint().y - y;
                    // move the active relationship's second point to the point calculated above
                    activeRelationship.setEndPoint(new Point(x, y));
                    activeRelationship.refreshSymbol();
                    // update the click point to where the active relationship's origin point was moved to
                    m_canvasManager.setClickPoint(activeRelationship.getStartPoint());
                } else {
                    // set the point type to end
                    m_canvasManager.setPointType("end");
                    // get the distance the end point is moved
                    int x = activeRelationship.getEndPoint().x - event.getX();
                    int y = activeRelationship.getEndPoint().y - event.getY();
                    // set the active relationship's end point to the point where the mouse is dragged
                    activeRelationship.setEndPoint(event.getPoint());
                    // calculate the point to move the active relationship's origin point to, based on the
                    // distance it's endpoint point is moved
                    x = activeRelationship.getStartPoint().x - x;
                    y = activeRelationship.getStartPoint().y - y;
                    // move the active relationship's second point to the point calculated above
                    activeRelationship.setStartPoint(new Point(x, y));
                    activeRelationship.refreshSymbol();
                    // update the click point to where the active relationship's end point was moved to
                    m_canvasManager.setClickPoint(activeRelationship.getEndPoint());

                }
            } else if (SwingUtilities.isRightMouseButton(event)) {
                if (activeRelationship.getStartPoint().distance(m_canvasManager.getClickPoint()) <= RADIUS) {
                    // set the point type to start
                    m_canvasManager.setPointType("start");
                    // set the active relationship's origin point to the point where the mouse is dragged
                    activeRelationship.setStartPoint(event.getPoint());
                    // update the click point to where the active relationship's origin point was moved to
                    m_canvasManager.setClickPoint(activeRelationship.getStartPoint());
                    activeRelationship.rotate();
                } else {
                    // set the point type to end
                    m_canvasManager.setPointType("end");
                    // set the active relationship's end point to the point where the mouse is dragged
                    activeRelationship.setEndPoint(event.getPoint());
                    // update the click point to where the active relationship's end point was moved to
                    m_canvasManager.setClickPoint(activeRelationship.getEndPoint());
                    activeRelationship.rotate();
                }
            }
            m_canvasManager.repaintCanvas();
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
}
