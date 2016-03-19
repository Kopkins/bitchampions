package uml.controls.listeners;

import uml.controls.CanvasManager;
import uml.models.Generics.GenericRelationship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class RelationshipListener implements MouseMotionListener, MouseListener {

    private CanvasManager m_canvasManager;
    private final int RADIUS = 5;

    public RelationshipListener()
    {
        m_canvasManager = CanvasManager.getInstance();
    }

    @Override
    public void mouseClicked(MouseEvent event) {

    }

    @Override
    public void mousePressed(MouseEvent event) {
        // get the point the mouse is pressed on
        m_canvasManager.setClickPoint(event.getPoint());
        // loop through relationships arraylist and see if click point is within a 5 point radius of any of the relationships origin point
        ArrayList<GenericRelationship> relationships = m_canvasManager.getSharedCanvas().getRelationships();
        for (int i = 0; i < relationships.size(); i++) {
            if (relationships.get(i).getStartPoint().distance(event.getPoint()) <= RADIUS
                || relationships.get(i).getEndPoint().distance(event.getPoint()) <= RADIUS) {
                //check if in delete mode
                if (m_canvasManager.m_isDeleteMode) {
                    m_canvasManager.deleteRelationship(i);
                    m_canvasManager.setActiveRelationshipIndex(-1);
                    m_canvasManager.repaintCanvas();
                } else //get the index of the active relationship
                {
                    m_canvasManager.setActiveRelationshipIndex(i);
                    // if clickpoint is within 5 point radius of relationship's origin point, set the relationship to active
                    relationships.get(i).setColor(Color.blue);
                    // repaint the canvas so the active relationship's color is blue
                    m_canvasManager.repaintCanvas();
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        int activeIndex = m_canvasManager.getActiveRelationshipIndex();
        if (activeIndex != -1) {
            // change relationship's color back to gray to show it is no longer active and repaint
            ArrayList<GenericRelationship> relationships = m_canvasManager.getSharedCanvas().getRelationships();
            relationships.get(activeIndex).setColor(Color.gray);
            m_canvasManager.repaintCanvas();
            m_canvasManager.setActiveRelationshipIndex(-1);
        }
    }

    @Override
    public void mouseEntered(MouseEvent event) {

    }

    @Override
    public void mouseExited(MouseEvent event) {

    }

    @Override
    public void mouseDragged(MouseEvent event) {
        int activeIndex = m_canvasManager.getActiveRelationshipIndex();
        if (activeIndex != -1) {
            // get the active relationship
            GenericRelationship activeRelationship = m_canvasManager.getSharedCanvas().getRelationships().get(activeIndex);
            if (SwingUtilities.isLeftMouseButton(event)) {
                if (activeRelationship.getStartPoint().distance(m_canvasManager.getClickPoint()) <= RADIUS) {
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
                    // set the active relationship's origin point to the point where the mouse is dragged
                    activeRelationship.setStartPoint(event.getPoint());
                    // update the click point to where the active relationship's origin point was moved to
                    m_canvasManager.setClickPoint(activeRelationship.getStartPoint());
                    activeRelationship.rotate();
                } else {
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

    @Override
    public void mouseMoved(MouseEvent event) {
    }
}
