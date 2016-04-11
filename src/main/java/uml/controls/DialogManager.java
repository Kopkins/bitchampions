package uml.controls;

import uml.views.EditorGUI;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/**
 * Manages dialog boxes and modals
 */
public class DialogManager {

    // Instance variables
    private static JFrame frame;

    /**
     * Constructor
     *
     * @param parentFrame which is the JFrame on which a dialog will act.
     */
    public DialogManager(JFrame parentFrame) {
        frame = parentFrame;
    }

    /**
     * Displays a modal confirming application termination.
     */
    public void confirmTermination() {
        String message = "Are you sure you want to leave?";
        String title = "Exit";
        Object[] options = {"Yes", "No, I should stay...",};

        int result = JOptionPane.showOptionDialog(frame, message, title,
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null, options, options[1]);

        if (result == 0) {
            System.exit(0);
        }
    }

    /**
     * Displays a modal displaying info about the project and
     * its contributors.
     */
    public void showAbout() {
        String message = "Kyle Hopkins, Jared McAndrews,\n"
            + "Jesse Platts, Vincent Smith, Vince Viggiano";
        String title = "bit_Champions: UML Editor";
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Displays a dialog stating that a feature is not yet implemented.
     */
    public void showNotImplemented() {
        String message = "Sorry. This feature is not yet implemented";
        String title = "Oh no!";
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Gives a file explorer for saving and loading a file.
     */
    public String getOpenFileFromDialog()
    {
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setCurrentDirectory(new File("."));
        fileChooser.setDialogTitle("Open UML file");
        fileChooser.setFileFilter(new FileNameExtensionFilter("UML Files (*.sav)", "sav"));
        fileChooser.setFileHidingEnabled(true);
        String fileName = "";
        int i = fileChooser.showOpenDialog(frame);
        if(i == JFileChooser.APPROVE_OPTION) {
            fileName = fileChooser.getSelectedFile().getName();
            if (!fileName.endsWith(".sav")) {
                displayError("Invalid file type. Must end in .sav");
            }
        }
        return fileName;
    }

    /**
     * Gives a file explorer for saving and loading a file.
     */
    public String getSaveFileFromDialog()
    {
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setCurrentDirectory(new File("."));
        fileChooser.setDialogTitle("Save UML file");
        fileChooser.setFileFilter(new FileNameExtensionFilter("UML Files (*.sav)", "sav"));
        fileChooser.setFileHidingEnabled(true);
        String fileName = "";
        int i = fileChooser.showSaveDialog(frame);
        if(i == JFileChooser.APPROVE_OPTION) {
            fileName = fileChooser.getSelectedFile().getName();
            if (!fileName.endsWith(".sav")) {
                displayError("Invalid file type. Must end in .sav");
            }
        }
        return fileName;
    }

    /**
     * Display an error to user.
     */
    public void displayError(String error)
    {
        String title = "Warning!";
        JOptionPane.showMessageDialog(frame, error, title, JOptionPane.OK_OPTION);
    }

    /**
     * Get a suitable filename for exporting a .jpg
     */

    public String getExportFileFromDialog()
    {
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setCurrentDirectory(new File("."));
        fileChooser.setDialogTitle("Save UML file");
        fileChooser.setFileFilter(new FileNameExtensionFilter("JPG (*.jpg)", "jpg"));
        fileChooser.setFileHidingEnabled(true);
        String fileName = SaveLoadManager.getInstance().getFileName().replace(".sav", ".jpg");
        File file = new File(fileName);
        fileChooser.setSelectedFile(file);

        int i = fileChooser.showSaveDialog(frame);
        if(i == JFileChooser.APPROVE_OPTION) {
            fileName = fileChooser.getSelectedFile().getName();
            if (!fileName.endsWith(".jpg")) {
                displayError("Invalid file type. Must end in .jpg");
            }
        }
        return fileName;
    }

    /**
     * Confirm whether or not to overwrite an existing file
     * @return boolean which is the users decision
     */
    public boolean confirmSave(String fileName)
    {
        SaveLoadManager slm = SaveLoadManager.getInstance();
        String message = "Are you sure you want to overwrite " + fileName + "?";
        String title = "Quick Save";
        Object[] options = {"Yes", "No",};

        int result = JOptionPane.showOptionDialog(frame, message, title,
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null, options, options[1]);

        return result == 0;
    }

}
