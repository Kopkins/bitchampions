package uml.models;

import junit.framework.TestCase;
import uml.models.Generics.Relationship;
import uml.models.Relationships.Association;

import java.awt.*;

public class RelationshipTest extends TestCase{
    Relationship rel;

    @Override
    protected void setUp() throws Exception {
        rel = new Association();
    }

    public void testConstruct()
    {

        assertEquals(rel.getType(), "Association");
    }

    public void testSetType()
    {
        String typeName = "testname";
        rel.setType(typeName);
        assertEquals(typeName, rel.getType());
    }

    public void testSetColor()
    {
        rel.setColor(Color.orange);
        assertEquals(rel.getColor(), Color.orange);
    }

    public void testSetPoints()
    {
        Point point = new Point(100,100);
        Point point2 = new Point(200,200);
        rel.setStartPoint(point);
        rel.setEndPoint(point2);
        assertEquals(point, rel.getStartPoint());
        assertEquals(point2, rel.getEndPoint());
    }

    public void testTranslate()
    {
        Point start = rel.getStartPoint();
        Point end = rel.getEndPoint();
        int offset = 50;

        start.translate(offset, offset);
        end.translate(offset, offset);
        rel.translate(offset,offset);

        assertEquals(rel.getStartPoint(), start);
        assertEquals(rel.getEndPoint(), end);
    }

    public void testRotate()
    {
        rel.setEndPoint(new Point( 200, 300));
        rel.setStartPoint(new Point(300, 300));
        rel.rotate();

        assertEquals(rel.getAngle(), Math.PI);

    }

    @Override
    protected void tearDown() throws Exception {
        rel = null;
    }
}
