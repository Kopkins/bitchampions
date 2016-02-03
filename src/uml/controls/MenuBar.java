package uml.controls;

import javax.swing.*;
import java.awt.*;

public class MenuBar extends JMenuBar {
    public MenuBar()
    {
        JMenu fileMenu, helpMenu;
        fileMenu = new JMenu("File");
        helpMenu = new JMenu("Help");

        // New Diagram Button
        JMenuItem newMenu = new JMenuItem("New Diagram...");

        // Open
        JMenuItem openMenu = new JMenuItem("Open...");

        // Save Button
        JMenuItem saveMenu = new JMenuItem("Save");

        fileMenu.add(newMenu);
        fileMenu.add(openMenu);
        fileMenu.add(saveMenu);

        this.add(fileMenu);
        this.add(helpMenu);
    }

}
