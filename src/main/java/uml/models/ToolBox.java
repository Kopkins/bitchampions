package uml.models;

import java.awt.*;
import javax.swing.*;
import java.util.*;

public class ToolBox extends JPanel {

    // Local Variables
    private Map<String, JButton> m_buttons;

    /**
     * Constructor
     */
    public ToolBox() {
        m_buttons = new HashMap();
        init();
    }

    /**
     * Build components for ToolBox
     */
    private void init() {
        JButton addClassBoxButton = new JButton("+ Class Box");
        addClassBoxButton.setPreferredSize(new Dimension(50, 25));
        add(addClassBoxButton);
        m_buttons.put("addClassBoxButton", addClassBoxButton);

        JButton addAssociationButton = new JButton("+ Association");
        addAssociationButton.setPreferredSize(new Dimension(25, 50));
        add(addAssociationButton);
        m_buttons.put("addAssociationButton", addAssociationButton);

        JButton addDirectedAssociationButton = new JButton("+ Directed Association");
        addDirectedAssociationButton.setPreferredSize(new Dimension(25, 50));
        add(addDirectedAssociationButton);
        m_buttons.put("addDirectedAssociationButton", addDirectedAssociationButton);

        JButton addDependencyButton = new JButton("+ Dependency");
        addDependencyButton.setPreferredSize(new Dimension(25, 50));
        add(addDependencyButton);
        m_buttons.put("addDependencyButton", addDependencyButton);

        JButton addGeneralizationButton = new JButton("+ Generalization");
        addGeneralizationButton.setPreferredSize(new Dimension(25, 50));
        add(addGeneralizationButton);
        m_buttons.put("addGeneralizationButton", addGeneralizationButton);

        JButton addAggregationButton = new JButton("+ Aggregation");
        addDependencyButton.setPreferredSize(new Dimension(25, 50));
        add(addAggregationButton);
        m_buttons.put("addAggregationButton", addAggregationButton);

        JButton addCompositionButton = new JButton("+ Composition");
        addDependencyButton.setPreferredSize(new Dimension(25, 50));
        add(addCompositionButton);
        m_buttons.put("addCompositionButton", addCompositionButton);

        JButton clearBoxButton = new JButton("Clear Canvas");
        clearBoxButton.setPreferredSize(new Dimension(25, 50));
        add(clearBoxButton);
        m_buttons.put("clearCanvasButton", clearBoxButton);

        JButton deleteSModeButton = new JButton("Delete Item");
        deleteSModeButton.setPreferredSize(new Dimension(25, 50));
        add(deleteSModeButton);
        m_buttons.put("deleteSModeButton", deleteSModeButton);
    }

    /**
     * Gets the Map of buttons.
     *
     * @return
     */
    public Map<String, JButton> getButtons() {
        return m_buttons;
    }
}
