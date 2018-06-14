package other;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {

    private static final Logger log = Logger.getLogger(PropertyReader.class);

    private final Properties properties = new Properties();

    public PropertyReader() {
        loadProperties();
    }

    /**
     * Loads the properties from an stream
     */
    private void loadProperties() {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("config.properties");
        try {
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            log.error("Error reading the properties input stream", e);
        }
    }

    public String readProperty(String key) {
        return properties.getProperty(key);
    }

    public Properties getProperties() {
        return properties;
    }

}
