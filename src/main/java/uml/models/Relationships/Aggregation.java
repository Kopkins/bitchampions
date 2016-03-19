package uml.models.Relationships;

import uml.models.Generics.GenericRelationship;

import java.awt.*;

public class Aggregation extends GenericRelationship {
    public Aggregation() {
        init();
        setType("Aggregation");
        refreshSymbol();
    }

    @Override
    public void refreshSymbol()
    {
        super.setSymbol(new Polygon(
            new int[]{m_end.x - 20, m_end.x - 9, m_end.x + 2, m_end.x - 9},
            new int[]{m_end.y, m_end.y - 5, m_end.y, m_end.y + 5}, 4));
    }
}
