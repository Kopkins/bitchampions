package uml.models;
import java.awt.*;
import javax.swing.*;
import java.util.*;


public class ToolBox extends JPanel {
    
    // Local Variables
    private Map<String, JButton> m_buttons;
    /**
     * Constructor
     */
    public ToolBox() {
        m_buttons = new HashMap();
        init();
    }

    /**
     * Build components for ToolBox
     */
    private void init() {
        JButton addClassBoxButton = new JButton("+ Add a Class Box");
        addClassBoxButton.setPreferredSize(new Dimension(50, 25));
        add(addClassBoxButton);
        m_buttons.put("addClassBoxButton", addClassBoxButton);
        
        JButton addRelationshipButton = new JButton("+ Add a Line");
        addRelationshipButton.setPreferredSize(new Dimension(25, 50));
        add(addRelationshipButton);
        m_buttons.put("addRelationshipButton", addRelationshipButton);
        
        JButton clearBoxButton = new JButton("Clear Canvas");
        clearBoxButton.setPreferredSize(new Dimension(25, 50));
        add(clearBoxButton);
        m_buttons.put("clearCanvasButton", clearBoxButton);
        
        JButton deleteSModeButton = new JButton("Delete Item");
        deleteSModeButton.setPreferredSize(new Dimension(25, 50));
        add(deleteSModeButton);
        m_buttons.put("deleteSModeButton", deleteSModeButton);
        
    }
    
    /**
     * Gets the Map of buttons.
     *
     * @return
     */
    public  Map<String, JButton> getButtons(){
        return m_buttons;
    }
}