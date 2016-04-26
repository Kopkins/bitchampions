package uml.models;

import junit.framework.*;
import uml.models.ClassBox;

import java.awt.*;

public class ClassBoxTest extends TestCase{
    ClassBox box1;
    ClassBox box2;

    // SETUP
    protected void setUp()
    {
        box1 = new ClassBox();
        box2 = new ClassBox();
    }

    /**
     * Test of Default constructor
     */
    public void testConstruct()
    {
        assertEquals(box1.getOrigin(), new Point(20,20));
    }

    public void testSetClickPoint(){
        Point p = new Point(100,100);
        box1.setClickPoint(p);

        assertEquals(p, box1.getClickPoint());
        assertTrue(box1.getClickPoint() == p);
    }

    public void testSetOrigin()
    {
        Point p = new Point(100,100);
        box1.setOrigin(p);
        assertEquals(p, box1.getOrigin());
        assertTrue(box1.getOrigin() == p);
    }

    // TEARDOWN
    protected void tearDown(){
        box1 = null;
        box2 = null;
    }

}

