package uml.models;

import junit.framework.*;
import uml.models.ClassBox;

import java.awt.*;

public class ClassBoxTest extends TestCase{
    ClassBox box1;
    ClassBox box2;

    protected void setUp()
    {
        box1 = new ClassBox();
        box2 = new ClassBox();
    }

    public void testConstruct()
    {
        assertEquals(box1.getOrigin(), new Point(20,20));
    }

    protected void tearDown(){

    }

}

