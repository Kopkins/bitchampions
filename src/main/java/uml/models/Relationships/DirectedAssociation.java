package uml.models.Relationships;
import uml.models.Generics.GenericRelationship;

import java.awt.*;

public class DirectedAssociation extends GenericRelationship {
    public DirectedAssociation()
    {
        init();
        setType("DirectedAssociation");
        refreshSymbol();
    }

    @Override
    public void refreshSymbol()
    {
        super.setSymbol(new Polygon(
            new int[]{m_end.x, m_end.x - 14, m_end.x - 14},
            new int[]{m_end.y, m_end.y - 7, m_end.y + 7}, 3));
    }
}