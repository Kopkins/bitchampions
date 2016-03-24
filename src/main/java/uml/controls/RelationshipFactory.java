package uml.controls;

import uml.models.Generics.Relationship;
import uml.models.Relationships.*;

public class RelationshipFactory {

    /**
     * Get a relationship object based on a String identifier.
     *
     * @param type, which is a String identifier of the type of relationship to generate.
     * @return Relationship
     */
    public static Relationship getFromType(String type) {
        switch (type) {
            case "Aggregation":
                return new Aggregation();
            case "Composition":
                return new Composition();
            case "Dependency":
                return new Dependency();
            case "DirectedAssociation":
                return new DirectedAssociation();
            case "Generalization":
                return new Generalization();
            default:
                return new Association();
        }
    }
}
