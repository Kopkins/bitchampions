package uml.models;

import uml.controls.CanvasManager;

import java.awt.*;
import javax.swing.*;
import java.util.*;

public class ToolBox extends JPanel {

    /**
     * Constructor
     */
    public ToolBox() {
        init();
    }

    /**
     * Build components for ToolBox
     */
    private void init() {

        this.setLayout(new GridLayout(9,1));

        bindButtons();
    }

    private void bindButtons()
    {
        // Add ClassBox Button
        JButton addClassBoxButton = new JButton("+ Class Box");
        addClassBoxButton.addActionListener(CanvasManager.getAddBoxListener());
        add(addClassBoxButton);

        // Add Association Button
        JButton addAssociationButton = new JButton("+ Association");
        addAssociationButton.addActionListener(CanvasManager.getAddRelationshipListener("Association"));
        add(addAssociationButton);

        // Add Directed Association Button
        JButton addDirectedAssociationButton = new JButton("+ Directed Association");
        addDirectedAssociationButton.addActionListener(CanvasManager.getAddRelationshipListener("DirectedAssociation"));
        add(addDirectedAssociationButton);

        // Add Dependency Button
        JButton addDependencyButton = new JButton("+ Dependency");
        addDependencyButton.addActionListener(CanvasManager.getAddRelationshipListener("Dependency"));
        add(addDependencyButton);

        // Add Generalization Button
        JButton addGeneralizationButton = new JButton("+ Generalization");
        addGeneralizationButton.addActionListener(CanvasManager.getAddRelationshipListener("Generalization"));
        add(addGeneralizationButton);

        // Add Aggregation Button
        JButton addAggregationButton = new JButton("+ Aggregation");
        addAggregationButton.addActionListener(CanvasManager.getAddRelationshipListener("Aggregation"));
        add(addAggregationButton);

        // Add Composition Button
        JButton addCompositionButton = new JButton("+ Composition");
        addDependencyButton.setPreferredSize(new Dimension(25, 50));
        add(addCompositionButton);

        // Add Clear Canvas Button
        JButton clearCanvasButton = new JButton("Clear Canvas");
        clearCanvasButton.addActionListener(CanvasManager.getClearCanvasListener());
        add(clearCanvasButton);

        // Delete Item Button
        JButton deleteSModeButton = new JButton("Delete Item");
        deleteSModeButton.addActionListener(CanvasManager.getDeleteModeListener());
        add(deleteSModeButton);
    }
}
