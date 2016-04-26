package uml.models;

import junit.framework.TestCase;
import uml.controls.CanvasManager;
import uml.controls.RelationshipFactory;
import uml.controls.UndoRedoManager;
import uml.models.Generics.Relationship;

import java.util.ArrayList;
import java.util.Random;

public class UndoRedoTest extends TestCase
{
    UndoRedoManager urm;
    CanvasManager cm;
    private final int MAX_STATE_SIZE = 20;

    @Override
    public void setUp() throws Exception {
        urm = UndoRedoManager.getInstance();
    }

    public void testPushPop() throws Exception {
        int testNum = 10;
        ArrayList<ArrayList<ClassBox>> cb_states = new ArrayList<ArrayList<ClassBox>>(testNum);
        ArrayList<ArrayList<Relationship>> r_states = new ArrayList<ArrayList<Relationship>>(testNum);

        cb_states.forEach(c -> {
            c = getRandomClassBoxState();
            urm.pushClassBoxesToUndo(c);
        });

        r_states.forEach(r -> {
            r = getRandomRelationshipState();
            urm.pushRelationshipsToUndo(r);
        });

        // Test swap
        cb_states.forEach(c -> {
            ArrayList<ClassBox> temp = urm.popClassBoxesFromUndo();
            urm.pushClassBoxesToRedo(temp);
            assertEquals(urm.popClassBoxesFromRedo(), temp);
            urm.pushClassBoxesToRedo(temp);
        });

        cb_states.forEach(r -> {
            ArrayList<Relationship> temp = urm.popRelationshipsFromUndo();
            urm.pushRelationshipsToRedo(temp);
            assertEquals(urm.popRelationshipsFromRedo(), temp);
            urm.pushRelationshipsToRedo(temp);
        });
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }


    // Helpers
    private ArrayList<ClassBox> getRandomClassBoxState()
    {
        Random random = new Random();
        int stateSize = random.nextInt(MAX_STATE_SIZE + 1);

        ArrayList<ClassBox> classBoxes = new ArrayList<ClassBox>(stateSize);
        classBoxes.forEach(c -> c = new ClassBox());

        return classBoxes;
    }

    private ArrayList<Relationship> getRandomRelationshipState()
    {
        Random random = new Random();
        int stateSize = random.nextInt(MAX_STATE_SIZE + 1);
        String[] relationshipTypes = {
            "Aggregation", "Composition",
            "Dependency", "DirectedAssociation",
            "Generalization", "Association"
        };

        ArrayList<Relationship> relationships = new ArrayList<Relationship>(stateSize);

        relationships.forEach(r -> {
                int index = random.nextInt(relationshipTypes.length + 1);
                r = RelationshipFactory.getFromType(relationshipTypes[index]);
            }
        );

        return relationships;
    }
}
