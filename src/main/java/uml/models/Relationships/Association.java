package uml.models.Relationships;

import uml.models.Generics.GenericRelationship;

import java.awt.*;

public class Association extends GenericRelationship {
    public Association()
    {
        init();
        setType("Association");
        setSymbol(new Polygon());
    }
}
