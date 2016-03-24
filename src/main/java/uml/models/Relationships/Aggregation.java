package uml.models.Relationships;

import uml.models.Generics.Relationship;

import java.awt.*;

public class Aggregation extends Relationship {

    /**
     * Constructor
     */
    public Aggregation() {
        init();
        setType("Aggregation");
        refreshSymbol();
    }

    /**
     * Set the symbol to be an diamond.
     */
    @Override
    public void refreshSymbol() {
        super.setSymbol(new Polygon(
            new int[]{m_end.x - 20, m_end.x - 9, m_end.x + 2, m_end.x - 9},
            new int[]{m_end.y, m_end.y - 5, m_end.y, m_end.y + 5}, 4));
    }
}
