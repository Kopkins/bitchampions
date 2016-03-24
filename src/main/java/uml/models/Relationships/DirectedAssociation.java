package uml.models.Relationships;

import uml.models.Generics.Relationship;

import java.awt.*;

public class DirectedAssociation extends Relationship {

    /**
     * Constructor
     */
    public DirectedAssociation() {
        init();
        setType("DirectedAssociation");
        refreshSymbol();
    }


    /**
     * Set the symbol to be a plain open arrow.
     */
    @Override
    public void refreshSymbol() {
        super.setSymbol(new Polygon(
            new int[]{m_end.x, m_end.x - 14, m_end.x - 14},
            new int[]{m_end.y, m_end.y - 7, m_end.y + 7}, 3));
    }
}
