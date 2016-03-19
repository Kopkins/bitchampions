package uml.controls;

import uml.controls.listeners.ClassBoxListener;
import uml.controls.listeners.RelationshipListener;
import uml.models.ClassBox;

import java.awt.event.ActionListener;

public class EventManager {

    // Local Variables
    private static EventManager m_eventManager;
    private ClassBoxListener m_classBoxListener;
    private RelationshipListener m_relationshipListener;

    /**
     * Constructor that takes a ClassBoxManager
     */
    private EventManager() {
        m_classBoxListener = new ClassBoxListener();
        m_relationshipListener = new RelationshipListener();
    }

    private static EventManager getInstance() {
        if (m_eventManager == null)
        {
            m_eventManager = new EventManager();
        }
        return m_eventManager;
    }

    public static ClassBoxListener getClassBoxListener() {
        return getInstance().m_classBoxListener;
    }

    public static RelationshipListener getRelationshipListener() {
        return getInstance().m_relationshipListener;
    }

}
