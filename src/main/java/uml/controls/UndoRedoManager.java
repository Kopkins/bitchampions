/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uml.controls;

import java.util.ArrayList;
import uml.models.ClassBox;
import uml.models.Generics.Relationship;

/**
 *
 * @author Jared McAdrews
 */
public class UndoRedoManager {
    // Local Variables
    private ArrayList<ArrayList<Relationship>> m_undoRelationships;
    private ArrayList<ArrayList<ClassBox>> m_undoClassBoxes;
    private ArrayList<ArrayList<Relationship>> m_redoRelationships;
    private ArrayList<ArrayList<ClassBox>> m_redoClassBoxes;
    private static UndoRedoManager m_undoRedoManager;
    
    /**
     * Constructor
     */
    public UndoRedoManager() {
        m_undoRelationships = new ArrayList<ArrayList<Relationship>>();
        m_undoClassBoxes = new ArrayList<ArrayList<ClassBox>>();
        m_redoRelationships = new ArrayList<ArrayList<Relationship>>();
        m_redoClassBoxes = new ArrayList<ArrayList<ClassBox>>();
    }
    
    /**
     * Singleton instance of SaveLoadManager
     *
     * @return
     */
    public static UndoRedoManager getInstance() {
        if (m_undoRedoManager == null) {
            m_undoRedoManager = new UndoRedoManager();
        }
        return m_undoRedoManager;
    }

    /**
     * Pushes relationship array list to relationships undo stack
     */
    public void pushRelationshipsToUndo(ArrayList<Relationship> retionships) {
        m_undoRelationships.add(retionships);
    }

    /**
     * Pops relationship array list from relationship undo stack, adds popped
     * value to relationship redo stack, and returns the next value in
     * relationships undo stack
     *
     * @return
     */
    public ArrayList<Relationship> popRelationshipsFromUndo() {
        ArrayList<Relationship> returnVal = new ArrayList<Relationship>();
        if (m_undoRelationships.size() > 0) {
            pushRelationshipsToRedo(m_undoRelationships.get(m_undoRelationships.size() - 1));
            m_undoRelationships.remove(m_undoRelationships.size() - 1);
            if (m_undoRelationships.size() > 0) {
                returnVal = m_undoRelationships.get(m_undoRelationships.size() - 1);
            }
        }
        return returnVal;
    }

    /**
     * Pushes classbox arraylist to classBox undo stack
     */
    public void pushClassBoxesToUndo(ArrayList<ClassBox> classBoxes) {
        m_undoClassBoxes.add(classBoxes);
    }

    /**
     * Pops classbox arraylist from classbox undo stack, adds popped value to
     * classbox redo stack, and returns the next value in classbox undo stack
     *
     * @return
     */
    public ArrayList<ClassBox> popClassBoxesFromUndo() {
        ArrayList<ClassBox> returnVal = new ArrayList<ClassBox>();
        if (m_undoClassBoxes.size() > 0) {
            pushClassBoxesToRedo(m_undoClassBoxes.get(m_undoClassBoxes.size() - 1));
            m_undoClassBoxes.remove(m_undoClassBoxes.size() - 1);
            if (m_undoClassBoxes.size() > 0) {
                returnVal = m_undoClassBoxes.get(m_undoClassBoxes.size() - 1);
            }
        }
        return returnVal;
    }

    /**
     * Pushes relationship array list to relationship redo stack
     */
    public void pushRelationshipsToRedo(ArrayList<Relationship> retionships) {
        m_redoRelationships.add(retionships);    
    }

    /**
     * Pops relationship array list from relationships redo stack, adds popped
     * value to relationships undo stack, and returns the popped value. 
     *
     * @return
     */
    public ArrayList<Relationship> popRelationshipsFromRedo() {
        ArrayList<Relationship> returnVal = new ArrayList<Relationship>();
        if (m_redoRelationships.size() > 0) {
            returnVal = m_redoRelationships.get(m_redoRelationships.size() - 1);
            pushRelationshipsToUndo(returnVal);
            m_redoRelationships.remove(m_redoRelationships.size() - 1);
        }
        //If relationships redo stack is empty, return the current arrayList from undo stack
        else if(m_undoRelationships.size() > 0){
            returnVal = m_undoRelationships.get(m_undoRelationships.size() - 1);
        }
        return returnVal;
    }

    /**
     * Pushes classbox arraylist to classBox redo stack
     */
    public void pushClassBoxesToRedo(ArrayList<ClassBox> classBoxes) {
        m_redoClassBoxes.add(classBoxes);
    }

    /**
     * Pops classbox arraylist from classBox redo stack, adds popped value to
     * classBox undo stack, and returns the popped value
     *
     * @return
     */
    public ArrayList<ClassBox> popClassBoxesFromRedo() {
        ArrayList<ClassBox> returnVal = new ArrayList<ClassBox>();
        if (m_redoClassBoxes.size() > 0) {
            returnVal = m_redoClassBoxes.get(m_redoClassBoxes.size() - 1);
            pushClassBoxesToUndo(returnVal);
            m_redoClassBoxes.remove(m_redoClassBoxes.size() - 1);
        }
        //If classBoxes redo stack is empty, return the current arrayList from undo stack
        else if(m_undoClassBoxes.size() > 0){
            returnVal = m_undoClassBoxes.get(m_undoClassBoxes.size() - 1);
        }
        return returnVal;
    }

    /**
     * Clear the undo redo stacks
     */
    public void clear()
    {
        m_redoClassBoxes.clear();
        m_redoRelationships.clear();
        m_undoClassBoxes.clear();
        m_undoRelationships.clear();
    }
}
