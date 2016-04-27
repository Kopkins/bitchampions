package uml.models;

import junit.framework.*;
import uml.controls.CanvasManager;
import uml.models.Canvas;
import uml.models.ClassBox;
import uml.models.Generics.Relationship;
import uml.models.Relationships.Association;

import java.awt.*;
import java.util.ArrayList;


public class CanvasTest extends TestCase {

    final int BOX_OFFSET = 10;
    CanvasManager cm;
    Canvas canvas;

    protected void setUp() {
        canvas = new Canvas();
        cm = new CanvasManager();
    }

    /**
     * Constructor Test
     */
    public void testConstructor()
    {
        assertTrue(canvas != null);
    }

    /**
     * Add ClassBox test. Make sure that ClassBoxes are being maintained in a collection.
     */
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

    /**
     * Test removal of a ClassBox from an already empty canvas.
     */
    public void testEmptyRemoveClassBox()
    {
        ClassBox box = new ClassBox();
        canvas.removeClassBox(box);
        assertTrue(canvas.getClassBoxes().size() == 0);
    }

    /**
     * Test removal of numerous ClassBoxes
     */
    public void testRemoveClassBox(){
        for (int i = 0; i < 10; i++)
        {
            canvas.addClassBox(new ClassBox());
        }

        // Make a clone of ClassBoxes since we can't
        // iterate and modify at the same time.
        ArrayList<ClassBox> tempClassBoxes = (ArrayList<ClassBox>)canvas.getClassBoxes().clone();
        int count = tempClassBoxes.size();
        for (ClassBox box : tempClassBoxes)
        {
            assertTrue(canvas.getClassBoxes().size() == count);
            assertTrue(canvas.getComponentCount() == count);
            canvas.removeClassBox(box);
            assertTrue(canvas.getComponentCount() == count - 1);
            assertTrue(canvas.getClassBoxes().size() == count-1);
            count--;
        }
    }

    /**
     * Test adding relationships to canvas
     */
    public void testAddRelationship(){
        for (int i = 0; i < 10; i++)
        {
            canvas.addRelationship(new Association());
        }

        assertTrue(canvas.getRelationships().size() == 10);
    }

    /**
     * Test removing relationships from an empty collection
     */
    public void testEmptyRemoveRelationship()
    {
        canvas.removeRelationship(new Association());
        assertTrue(canvas.getRelationships().size() == 0);

    }

    /**
     * Test removing relationships from a collection
     */
    public void testRemoveRelationship()
    {
        for (int i = 0; i < 10; i++)
        {
            canvas.addRelationship(new Association());
        }
        // Make a clone of Relationships since we can't
        // iterate and modify at the same time.
        ArrayList<Relationship> temp = (ArrayList<Relationship>)canvas.getRelationships().clone();
        int count = temp.size();
        for (Relationship r : temp)
        {
            assertTrue(canvas.getRelationships().size() == count);
            canvas.removeRelationship(r);
            assertTrue(canvas.getRelationships().size() == count-1);
            count--;
        }
    }

    protected void tearDown(){
        cm = null;
        canvas = null;
    }

}
