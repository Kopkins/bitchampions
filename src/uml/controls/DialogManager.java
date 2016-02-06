package uml.controls;

import javax.swing.*;

/**
 * Manages dialog boxes and modals
 */
public class DialogManager {

    // Instance variables
    private static JFrame frame;

    /**
     * Constructor
     * @param parentFrame which is the JFrame on which a dialog will act.
     */
    public DialogManager(JFrame parentFrame)
    {
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
}
