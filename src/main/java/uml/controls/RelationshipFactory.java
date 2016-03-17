package uml.controls;

import uml.models.Generics.GenericRelationship;
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

    public static GenericRelationship getFromType(String type)
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

    public static GenericRelationship generateAggregation()
    {
        return new Aggregation();
    }

    public static GenericRelationship generateAssociation()
    {
        return new Association();
    }

    public static GenericRelationship generateComposition()
    {
        return new Composition();
    }

    public static GenericRelationship generateDependency()
    {
        return new Dependency();
    }

    public static GenericRelationship generateDirectedAssociation()
    {
        return new DirectedAssociation();
    }

    public static GenericRelationship generateGeneralizaion()
    {
        return new Generalization();
    }
}
