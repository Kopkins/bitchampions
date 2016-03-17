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
    public static int GRIDSIZE;

    // Properties to maintain
    String gridSize;
    String selectColor;
    String defaultColor;
    String deleteColor;
    String maxCanvasWidth;
    String maxCanvasHeight;

    public Settings()
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
        GRIDSIZE = Integer.parseInt(properties.getProperty("gridSize"));
    }

    private void generateDefualts()
    {
        gridSize = "10";
        selectColor = "blue";
        deleteColor = "red";
        defaultColor = "gray";
        maxCanvasWidth = "600";
        maxCanvasHeight = "400";
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
