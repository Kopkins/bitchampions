package uml.controls;

import uml.views.EditorGUI;

import javax.swing.*;

public class DialogManager {

    public DialogManager() {
    }

    public static void confirmTermination(JFrame frame) {
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

    public static void showAbout(JFrame frame) {
        String message = "Kyle Hopkins, Jared McAndrews,\n"
            + "Jesse Platts, Vincent Smith, Vince Viggiano";
        String title = "bit_Champions: UML Editor";
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.PLAIN_MESSAGE);
    }

    public static void showNotImplemented(JFrame frame) {
        String message = "Sorry. This feature is not yet implemented";
        String title = "Oh no!";
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.PLAIN_MESSAGE);
    }
}
