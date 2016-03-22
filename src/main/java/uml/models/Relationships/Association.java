package uml.models.Relationships;

import uml.models.Generics.Relationship;

import java.awt.*;

public class Association extends Relationship {
    public Association()
    {
        init();
        setType("Association");
        setSymbol(new Polygon());
    }
}
