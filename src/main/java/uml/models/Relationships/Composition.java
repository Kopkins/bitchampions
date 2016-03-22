package uml.models.Relationships;

import uml.models.Generics.Relationship;

import java.awt.*;

public class Composition extends Relationship {
    public Composition() {
        init();
        setType("Composition");
        refreshSymbol();
    }

    @Override
    public void refreshSymbol()
    {
        super.setSymbol(new Polygon(
            new int[]{m_end.x - 23, m_end.x - 10, m_end.x + 3, m_end.x - 10},
            new int[]{m_end.y, m_end.y - 6, m_end.y, m_end.y + 6}, 4));
    }
}
