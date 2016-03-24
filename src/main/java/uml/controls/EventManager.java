package uml.controls;

import uml.controls.listeners.ClassBoxListener;
import uml.controls.listeners.RelationshipListener;

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

    /**
     * Get the singleton instance of EventManager
     *
     * @return EventManager
     */
    private static EventManager getInstance() {
        if (m_eventManager == null) {
            m_eventManager = new EventManager();
        }
        return m_eventManager;
    }

    /**
     * Get the ClassBoxListener
     *
     * @return ClassBoxListener
     */
    public static ClassBoxListener getClassBoxListener() {
        return getInstance().m_classBoxListener;
    }

    /**
     * Get the RelationshipListener
     *
     * @return RelationshipListener
     */
    public static RelationshipListener getRelationshipListener() {
        return getInstance().m_relationshipListener;
    }
}
