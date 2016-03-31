package uml.models;

import uml.controls.CanvasManager;

import javax.swing.*;
import java.awt.*;

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

        this.setLayout(new GridLayout(13, 1));

        bindButtons();
    }

    /**
     * Bind all ActionListeners to their buttons
     */
    private void bindButtons() {
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
        addCompositionButton.addActionListener(CanvasManager.getAddRelationshipListener("Composition"));
        add(addCompositionButton);

        // Add Clear Canvas Button
        JButton clearCanvasButton = new JButton("Clear Canvas");
        clearCanvasButton.addActionListener(CanvasManager.getClearCanvasListener());
        add(clearCanvasButton);

        // Delete Item Button
        JButton deleteSModeButton = new JButton("Delete Item");
        deleteSModeButton.addActionListener(CanvasManager.getDeleteModeListener());
        add(deleteSModeButton);
        
        // Add Save button
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(CanvasManager.getSaveListener());
        add(saveButton);
        
        // Add Load button
        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(CanvasManager.getLoadListener());
        add(loadButton);
        
        // Add Undo button
        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(CanvasManager.getUndoListener());
        add(undoButton);
        
        // Add Redo button
        JButton redoButton = new JButton("Redo");
        redoButton.addActionListener(CanvasManager.getRedoListener());
        add(redoButton);
    }
}
