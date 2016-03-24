package uml.models.Relationships;

import uml.models.Generics.Relationship;

import java.awt.*;

public class Association extends Relationship {

    /**
     * Constructor
     */
    public Association() {
        init();
        setType("Association");
        setSymbol(new Polygon());
    }
}
