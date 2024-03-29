package uml.models.Relationships;

import uml.models.Generics.Relationship;

import java.awt.*;

public class Generalization extends Relationship {

    /**
     * Constructor
     */
    public Generalization() {
        init();
        setType("Generalization");
        refreshSymbol();
    }

    /**
     * Set the symbol to be a closed triangle.
     */
    @Override
    public void refreshSymbol() {
        super.setSymbol(new Polygon(
            new int[]{m_end.x, m_end.x - 14, m_end.x - 14},
            new int[]{m_end.y, m_end.y - 7, m_end.y + 7}, 3));
    }
}
