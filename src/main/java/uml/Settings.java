package uml;

import java.awt.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

public class Settings {

    private static Settings m_instance;

    // Member Variables
    private Properties properties;
    private final String OUTPUT_FILE = "app.properties";
    private FileInputStream input;
    private FileOutputStream output;
    private int GRID_SIZE, CANVAS_WIDTH,
        CANVAS_HEIGHT, BOX_WIDTH, BOX_HEIGHT;

    // Properties identifiers to maintain in app.properties
    private String gridSize;
    private String selectColor;
    private String defaultColor;
    private String deleteColor;
    private String maxCanvasWidth;
    private String maxCanvasHeight;
    private String defaultBoxWidth;
    private String defaultBoxHeight;

    /**
     * Constructor
     */
    private Settings() {
        properties = new Properties();
        load();
    }

    /**
     * Get a Singleton instance of Settings
     *
     * @return Settings
     */
    private static Settings getInstance() {
        if (m_instance == null) {
            m_instance = new Settings();
        }
        return m_instance;
    }

    /**
     * Get the stored width of the canvas
     * @return int
     */
    public static int getCanvasWidth() {
        return getInstance().CANVAS_WIDTH;
    }

    /**
     * Get the stored height of the canvas
     * @return int
     */
    public static int getCanvasHeight() {
        return getInstance().CANVAS_HEIGHT;
    }

    /**
     * Get the stored default box width
     * @return int
     */
    public static int getBoxWidth() {
        return getInstance().BOX_WIDTH;
    }

    /**
     * Get the stored default box height
     * @return
     */
    public static int getBoxHeight() {
        return getInstance().BOX_HEIGHT;
    }

    /**
     * get the stored default grid size
     * @return
     */
    public static int getGridSize() {
        return getInstance().GRID_SIZE;
    }

    /**
     * Save the properties
     */
    public void save() {
        try {
            output = new FileOutputStream("app.properties");
            properties.setProperty("gridSize", gridSize);
            properties.setProperty("selectColor", selectColor);
            properties.setProperty("deleteColor", deleteColor);
            properties.setProperty("defaultColor", defaultColor);
            properties.setProperty("maxCanvasWidth", maxCanvasWidth);
            properties.setProperty("maxCanvasHeight", maxCanvasHeight);
            properties.setProperty("defaultBoxWidth", defaultBoxWidth);
            properties.setProperty("defaultBoxHeight", defaultBoxHeight);

            // save properties to project root folder
            properties.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Load properties from file
     */
    private void load() {
        try {
            input = new FileInputStream(OUTPUT_FILE);
            new BufferedInputStream(input);
            properties.load(input);
            loadValues(properties);
        } catch (IOException e) {
            System.out.println("Could not find file: " + OUTPUT_FILE);
            generateDefualts();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    System.out.println("Could not close file " + input);
                }
            }
        }
    }

    /**
     * Map property values to member variables.
     * @param properties
     */
    private void loadValues(Properties properties) {
        GRID_SIZE = Integer.parseInt(properties.getProperty("gridSize"));
        CANVAS_WIDTH = Integer.parseInt(properties.getProperty("maxCanvasWidth"));
        CANVAS_HEIGHT = Integer.parseInt(properties.getProperty("maxCanvasHeight"));
        BOX_WIDTH = Integer.parseInt(properties.getProperty("defaultBoxWidth"));
        BOX_HEIGHT = Integer.parseInt(properties.getProperty("defaultBoxWidth"));
    }

    /**
     * Generate default values and save them out to the file.
     */
    private void generateDefualts() {
        gridSize = "10";
        selectColor = "blue";
        deleteColor = "red";
        defaultColor = "gray";
        defaultBoxWidth = "120";
        defaultBoxHeight = "150";
        maxCanvasWidth = "1500";
        maxCanvasHeight = "1200";
        save();
        load();
    }

    /**
     * Map string identifiers to Color objects
     * @param colorName
     * @return
     */
    private Color mapColor(String colorName) {
        Color color;
        Color fallback = Color.black;
        try {
            Field field = Color.class.getField(colorName);
            color = (Color) field.get(null);
        } catch (Exception e) {
            color = fallback;
        }
        return color;
    }

    /**
     * Get a specific property
     * @param key, which identifies the property
     * @return String, which is the value of the property
     */
    private String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Enumeration of Colors
     */
    public enum Colors {
        DEFAULT("defaultColor"), DELETE("deleteColor"), SELECT("selectColor");
        public Color color;

        Colors(String key) {
            Settings properties = Settings.getInstance();
            String value = properties.getProperty(key);
            this.color = properties.mapColor(value);
        }
    }
}
