package uml;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Field;
import java.util.Properties;

public class Settings {

    // Member Variables
    private Properties properties;
    private String propertiesFile;
    private FileInputStream input;
    private FileOutputStream output;
    private static Settings m_instance;
    private int GRID_SIZE, CANVAS_WIDTH,
        CANVAS_HEIGHT, BOX_WIDTH, BOX_HEIGHT;

    // Properties to maintain
    private String gridSize;
    private String selectColor;
    private String defaultColor;
    private String deleteColor;
    private String maxCanvasWidth;
    private String maxCanvasHeight;
    private String defaultBoxWidth;
    private String defaultBoxHeight;

    private Settings()
    {
        properties = new Properties();
        propertiesFile = "app.properties";
        load();
    }

    public static Settings getInstance()
    {
        if (m_instance == null) {
            m_instance = new Settings();
        }
        return m_instance;
    }

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

    private void load()
    {
        try
        {
            input = new FileInputStream(propertiesFile);
            new BufferedInputStream(input);
            properties.load(input);
            loadValues(properties);
        }
        catch (IOException e)
        {
            System.out.println("Could not find file: " + propertiesFile);
            generateDefualts();
        }
        finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e)
                {
                    System.out.println("Could not close file " + input);
                }
            }
        }
    }

    private void loadValues(Properties properties)
    {
        GRID_SIZE = Integer.parseInt(properties.getProperty("gridSize"));
        CANVAS_WIDTH = Integer.parseInt(properties.getProperty("maxCanvasWidth"));
        CANVAS_HEIGHT = Integer.parseInt(properties.getProperty("maxCanvasHeight"));
        BOX_WIDTH = Integer.parseInt(properties.getProperty("defaultBoxWidth"));
        BOX_HEIGHT = Integer.parseInt(properties.getProperty("defaultBoxWidth"));
    }

    private void generateDefualts()
    {
        gridSize = "20";
        selectColor = "blue";
        deleteColor = "red";
        defaultColor = "gray";
        defaultBoxWidth = "120";
        defaultBoxHeight = "150";
        maxCanvasWidth = "1500";
        maxCanvasHeight = "1200";
        save();
    }

    // Map a string to a Color object.
    private Color mapColor(String colorName) {
        Color color;
        Color fallback = Color.black;
        try {
            Field field = Color.class.getField(colorName);
            color = (Color)field.get(null);
        } catch (Exception e) {
            color = fallback;
        }
        return color;
    }

    private String getProperty(String key)
    {
        return properties.getProperty(key);
    }

    public static int getCanvasWidth()
    {
        return getInstance().CANVAS_WIDTH;
    }

    public static int getCanvasHeight()
    {
        return getInstance().CANVAS_HEIGHT;
    }

    public static int getBoxWidth()
    {
        return getInstance().BOX_WIDTH;
    }

    public static int getBoxHeight()
    {
        return getInstance().BOX_HEIGHT;
    }

    public enum Colors {
        DEFAULT("defaultColor"), DELETE("deleteColor"), SELECT("selectColor");
        public Color color;

        Colors(String key)
        {
            Settings properties = Settings.getInstance();
            String value = properties.getProperty(key);
            this.color = properties.mapColor(value);
        }
    }
}
