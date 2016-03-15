/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uml.controls;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import uml.models.ClassBox;
import uml.models.Relationship;

/**
 * @author Vincent Smith
 * @author Jared M.
 * @author Jesse Platts
 * @author Kyle Hopkins
 */
public class EventManager implements MouseMotionListener,
        MouseListener {

    // Local Variables
    private static int RADIUS = 5;
    private ClassBox m_classBox;
    private CanvasManager m_canvasManager;
    private boolean m_isClassBox;

    /**
     * Constructor that takes a ClassBoxManager
     */
    public EventManager(CanvasManager canvasManager, ClassBox classBox) {
        m_canvasManager = canvasManager;
        m_classBox = classBox;
        m_isClassBox = true;
    }

    /**
     * Constructor that takes a CanvasManager
     */
    public EventManager(CanvasManager canvasManager) {
        m_canvasManager = canvasManager;
        m_isClassBox = false;
    }

    @Override
    public void mousePressed(MouseEvent event) {
        if (m_isClassBox) {
            // get point the mouse is pressed on
            m_classBox.setClickPoint(event.getPoint());
            //check if in deleteMode
            if (m_canvasManager.m_isDeleteMode) {
                m_canvasManager.deleteClassBox(m_classBox);
                m_canvasManager.repaintCanvas();
            } else {
                // changed color to blue to show classBox is active
                m_classBox.setBackground(Color.blue);
            }

        } else {
            // get the point the mouse is pressed on
            m_canvasManager.setClickPoint(event.getPoint());
            // loop through relationships arraylist and see if click point is within a 5 point radius of any of the relationships origin point
            ArrayList<Relationship> relationships = m_canvasManager.getSharedCanvas().getRelationships();
            for (int i = 0; i < relationships.size(); i++) {
                if (relationships.get(i).getStartPoint().distance(event.getPoint()) <= RADIUS) {
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
    }

    @Override
    public void mouseDragged(MouseEvent event) {
        if (m_isClassBox) {
            //calculate the distance of mouse drag event, update the origin to this point and move the location of the classbox
            int x = m_classBox.getOrigin().x + event.getX() - m_classBox.getClickPoint().x;
            int y = m_classBox.getOrigin().y + event.getY() - m_classBox.getClickPoint().y;
            // snap to grid logic
            // Should gridSize be GLOBAL?
            /*
            int gridSize = 20;
            int offset = x % gridSize;
            x = (offset > gridSize / 2) ? x + gridSize - offset : x - offset;
            offset = y % gridSize;
            y = (offset > gridSize / 2) ? y + gridSize - offset : y - offset;
            */
            /////////////////////
            m_classBox.setOrigin(new Point(x, y));
            m_classBox.setLocation(m_classBox.getOrigin());
        } else {
            int activeIndex = m_canvasManager.getActiveRelationshipIndex();
            if (activeIndex != -1) {
                // get the active relationship
                Relationship activeRelationship = m_canvasManager.getSharedCanvas().getRelationships().get(activeIndex);
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
                // update the click point to where the active relationship's origin point was moved to
                m_canvasManager.setClickPoint(activeRelationship.getStartPoint());
                m_canvasManager.repaintCanvas();
            }

        }
    }

    @Override
    public void mouseMoved(MouseEvent event) {

    }

    @Override
    public void mouseClicked(MouseEvent event) {
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        if (m_isClassBox) {
            // change color back to gray to show classBox is no longer active
            m_classBox.setBackground(Color.gray);
        } else {
            // deactivate the active relationship
            int activeIndex = m_canvasManager.getActiveRelationshipIndex();
            if (activeIndex != -1) {
                // change relationship's color back to gray to show it is no longer active and repaint
                ArrayList<Relationship> relationships = m_canvasManager.getSharedCanvas().getRelationships();
                relationships.get(activeIndex).setColor(Color.gray);
                m_canvasManager.repaintCanvas();
                m_canvasManager.setActiveRelationshipIndex(-1);
            }
        }

    }

    @Override
    public void mouseEntered(MouseEvent event) {

    }

    @Override
    public void mouseExited(MouseEvent event) {

    }

}
