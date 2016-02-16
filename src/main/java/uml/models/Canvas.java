package uml.models;

import javax.swing.*;
import java.util.ArrayList;

public class Canvas extends JPanel {
    private ArrayList<ClassBox> _boxes;

    public Canvas()
    {
        _boxes = new ArrayList<ClassBox>();
        init();
    }

    private void init()
    {
        // Set to Null so Boxes and Lines can be placed freely
        this.setLayout(null);
    }

    public void addBox(ClassBox box)
    {
        _boxes.add(box);
    }
}