/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uml.controls.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextArea;
import uml.controls.CanvasManager;
import uml.controls.UndoRedoManager;

public class TextAreaKeyListener implements KeyListener {

    //Local Variables
    UndoRedoManager m_undoRedoManager;

    /**
     * Constructor
     */
    public TextAreaKeyListener() {
        m_undoRedoManager = UndoRedoManager.getInstance();
    }

    /**
     * Mandatory override for implementing KeyListener.
     *
     * @param event
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Method to save the canvas state to undo/red manager when enter is pressed
     *
     * @param event
     */
    @Override
    public void keyPressed(KeyEvent e) {
     JTextArea area;
        try {
            area = (JTextArea) e.getSource();
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                // add current state to undoRedoManager
                m_undoRedoManager.pushRelationshipsToUndo(CanvasManager.getSharedCanvas().getDeepCopyRelationships());
                m_undoRedoManager.pushClassBoxesToUndo(CanvasManager.getSharedCanvas().getDeepCopyClassBoxes());
            }

        } catch (ClassCastException ex) {
            System.out.println(ex);
            throw ex;
        }
    }

    /**
     * Mandatory override for implementing KeyListener.
     *
     * @param event
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }
}
