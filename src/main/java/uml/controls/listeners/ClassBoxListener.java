package uml.controls.listeners;

import uml.Settings;
import uml.controls.CanvasManager;
import uml.models.ClassBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class ClassBoxListener implements MouseListener, MouseMotionListener
{
    CanvasManager m_canvasManager;
    private boolean m_snapToGrid = false;
    public ClassBoxListener()
    {
        m_canvasManager = CanvasManager.getInstance();
    }

    @Override
    public void mouseClicked(MouseEvent event) {

    }

    @Override
    public void mousePressed(MouseEvent event) {
        ClassBox box;

        try {
            box = (ClassBox) event.getSource();
            m_canvasManager.getSharedCanvas().moveToFront(box);

            // get point the mouse is pressed on
            box.setClickPoint(event.getPoint());
            //check if in deleteMode
            if (m_canvasManager.m_isDeleteMode) {
                m_canvasManager.deleteClassBox(box);
                m_canvasManager.repaintCanvas();
            } else {
                // changed color to blue to show classBox is active
                box.setBackground(Color.blue);
            }
        } catch (ClassCastException exception)
        {
            System.out.println(exception);
        }
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        ClassBox box;
        try {
            box = (ClassBox) event.getSource();
            box.setBackground(Color.gray);
        } catch (ClassCastException ex)
        {
            System.out.println(ex);
        }
    }

    @Override
    public void mouseEntered(MouseEvent event) {
        ClassBox box;
        try {
            box = (ClassBox)event.getSource();
            box.setCursor(new Cursor(Cursor.HAND_CURSOR));
        } catch (ClassCastException ex)
        {
            System.out.println(ex);
            throw ex;
        }
    }

    @Override
    public void mouseExited(MouseEvent event) {

    }

    @Override
    public void mouseDragged(MouseEvent event) {
        ClassBox box;
        try {
            box = (ClassBox)event.getSource();

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
            m_canvasManager.getSharedCanvas().revalidate();
            m_canvasManager.getSharedCanvas().repaint();
            box.setClickPoint(event.getPoint());
        }
        } catch (ClassCastException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void mouseMoved(MouseEvent event) {

    }

    private Point snapToGrid(int x, int y)
    {
        int gridSize = 20;
        int offset = x % gridSize;
        int x2, y2;
        x2 = (offset > gridSize / 2) ? x + gridSize - offset : x - offset;
        offset = y % gridSize;
        y2 = (offset > gridSize / 2) ? y + gridSize - offset : y - offset;

        return new Point(x2, y2);
    }

    private Point validateMovement(int x, int y)
    {
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
        }
        else {
            return new Point(x2, y2);
        }
    }
}
