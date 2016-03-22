package uml.controls;

import uml.models.Generics.Relationship;
import uml.models.Relationships.*;

public class RelationshipFactory {
    private static RelationshipFactory m_factory;

    public RelationshipFactory()
    {

    }

    public static RelationshipFactory getInstance()
    {
        if (m_factory == null)
        {
            m_factory = new RelationshipFactory();
        }
        return m_factory;
    }

    public static Relationship getFromType(String type)
    {
        switch(type)
        {
            case "Aggregation":
                return generateAggregation();
            case "Composition":
                return generateComposition();
            case "Dependency":
                return generateDependency();
            case "DirectedAssociation":
                return generateDirectedAssociation();
            case "Generalization":
                return generateGeneralizaion();
            default:
                return generateAssociation();
        }
    }

    public static Relationship generateAggregation()
    {
        return new Aggregation();
    }

    public static Relationship generateAssociation()
    {
        return new Association();
    }

    public static Relationship generateComposition()
    {
        return new Composition();
    }

    public static Relationship generateDependency()
    {
        return new Dependency();
    }

    public static Relationship generateDirectedAssociation()
    {
        return new DirectedAssociation();
    }

    public static Relationship generateGeneralizaion()
    {
        return new Generalization();
    }
}
