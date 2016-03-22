package uml.models;

import junit.framework.*;
import uml.controls.CanvasManager;
import uml.models.Canvas;
import uml.models.ClassBox;
import java.awt.*;


public class CanvasTest extends TestCase {

    final int BOX_OFFSET = 10;
    CanvasManager cm;
    Canvas canvas;

    protected void setUp() {
        canvas = CanvasManager.getSharedCanvas();
    }

    public void testConstructor()
    {
        assertTrue(canvas != null);
    }

    public void testAddClassBox()
    {
        for (int i = 0; i < 10; i++)
        {
            canvas.addClassBox(new ClassBox());
        }

        for (ClassBox box : canvas.getClassBoxes())
        {
            assertEquals(box.getOrigin(), new Point(BOX_OFFSET * 2, BOX_OFFSET * 2));
        }

        assertTrue(canvas.getClassBoxes().size() == 10);
        assertTrue(canvas.getRelationships() != null);
        assertTrue(canvas.getRelationships().size() == 0);
    }

    public void testEmptyRemoveClassBox()
    {
        ClassBox box = new ClassBox();
        canvas.removeClassBox(box);
        assertTrue(canvas.getClassBoxes().size() == 0);
    }

    public void testRemoveClassBox(){
        // Add some boxes
        // Remove one
        // Assert it's not there anymore
    }

    public void testAddRelationship(){
        // Add some boxes
        // Remove one
        // Assert it's not there anymore
    }

    public void testEmptyRemoveRelationship()
    {

    }

    public void testRemoveRelationship()
    {

    }

    public void testCanvasSizeFromSettings()
    {

    }


//    public void testAddBoxWithManager(){
//        for (int i = 0; i < 10; i++)
//        {
//            cm.addClassBox(new ClassBox());
//        }
//
//        for (ClassBox box : canvas.getClassBoxes())
//        {
//            assertEquals(box.getOrigin(), new Point(BOX_OFFSET * 2, BOX_OFFSET * 2));
//        }
//
//        assertTrue(canvas.getClassBoxes().size() == 10);
//        assertTrue(canvas.getRelationships() != null);
//        assertTrue(canvas.getRelationships().size() == 0);
//    }


    protected void tearDown(){
        cm = null;
        canvas = null;
    }

}
