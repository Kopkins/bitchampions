/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uml.controls;

import java.io.*;
import java.util.ArrayList;
import javax.swing.JLayeredPane;
import static uml.controls.CanvasManager.getSharedCanvas;
import uml.models.ClassBox;
import uml.models.Generics.Relationship;
import uml.views.EditorGUI;

/**
 *
 * @author Jared McAndrews
 */
public class SaveLoadManager {

    // Local Variables
    private ArrayList<Relationship> m_relationships;
    private ArrayList<ClassBox> m_classBoxes;
    private static SaveLoadManager m_saveLoadManager;
    private static String m_fileName;

    /**
     * Constructor
     */
    public SaveLoadManager() {
        m_fileName = "untitled.sav";
        m_relationships = new ArrayList<Relationship>();
        m_classBoxes = new ArrayList<ClassBox>();
    }

    /**
     * Singleton instance of SaveLoadManager
     *
     * @return
     */
    public static SaveLoadManager getInstance() {
        if (m_saveLoadManager == null) {
            m_saveLoadManager = new SaveLoadManager();
        }
        return m_saveLoadManager;
    }

    /**
     * Saves relationship and classbox arraylists to SaveObj.sav in root folder
     *
     * @param relationships, classBoxes
     */
    public void save(ArrayList<Relationship> relationships, ArrayList<ClassBox> classBoxes) {
        try {
                EditorGUI.updateFileName(m_fileName);
                FileOutputStream saveFile = new FileOutputStream(m_fileName);
                ObjectOutputStream save = new ObjectOutputStream(saveFile);
                save.writeObject(relationships);
                save.writeObject(classBoxes);
                save.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    /**
     * Loads relationship and classbox arraylists from SaveObj.sav in root
     * folder
     */
    public void load() {
        try {
                EditorGUI.updateFileName(m_fileName);
                FileInputStream loadFile = new FileInputStream(m_fileName);
                ObjectInputStream save = new ObjectInputStream(loadFile);
                m_relationships = (ArrayList<Relationship>) save.readObject();
                m_classBoxes = (ArrayList<ClassBox>) save.readObject();
                save.close();
                //add mouse actionListeners to each classbox
                for (ClassBox cb : m_classBoxes) {
                    cb.addMouseListener(EventManager.getClassBoxListener());
                    cb.addMouseMotionListener(EventManager.getClassBoxListener());
                }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    /**
     * gets the ArrayList of Relationships
     */
    public ArrayList<Relationship> getRelationships() {
        return m_relationships;
    }

    /**
     * gets the ArrayList of ClassBoxes
     */
    public ArrayList<ClassBox> getClassBoxes() {
        return m_classBoxes;
    }

    public String getFileName()
    {
        return m_fileName;
    }

    public void setFileName(String fileName)
    {
        m_fileName = fileName;
    }

    public static boolean isValidFileName(String fileName)
    {
        if (fileName.isEmpty())
        {
            return false;
        } else if (!fileName.endsWith(".sav") || fileName.length() <= 4)
        {
            return false;
        } else
        {
            return true;
        }
    }

    public static boolean isValidImageFile(String fileName)
    {
        if (fileName.isEmpty())
        {
            return false;
        } else if (!fileName.endsWith(".jpg") || fileName.length() <= 4)
        {
            return false;
        } else
        {
            return true;
        }
    }
}
