package uml.models.Relationships;

import uml.models.Generics.GenericRelationship;

import java.awt.*;

public class Composition extends GenericRelationship{
    public Composition() {
        init();
        setType("Composition");
        setSymbol(new Polygon(
            new int[]{m_end.x - 23, m_end.x - 10, m_end.x + 3, m_end.x - 10},
            new int[]{m_end.y, m_end.y - 6, m_end.y, m_end.y + 6}, 4));
    }
}
