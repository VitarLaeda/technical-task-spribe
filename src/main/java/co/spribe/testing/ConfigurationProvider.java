package co.spribe.testing;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

@Slf4j
public class ConfigurationProvider {
    static Logger allureLogger = (Logger) LoggerFactory.getLogger("io.qameta.allure.AllureLifecycle");
    static Logger testngLogger = (Logger) LoggerFactory.getLogger(" co.spribe.testing.listeners.TestNgLifecycleListener");
    private static final Level level = Level.toLevel(getProperty("logLevel", "error").toUpperCase());
    private static final String PROPERTIES = "config.properties";
    private static final String BUILD_ENV = "envURL";
    private static final String TEST_ENV = "envType";

    static {
        allureLogger.setLevel(level);
        testngLogger.setLevel(level);
    }

    public static String getURL() {
        return getProperty(BUILD_ENV, "http://localhost:8080");
    }

    public static String getTestEnv() {
        return getProperty(TEST_ENV, "test");
    }

    public static String getProperty(String key) {
        return loadConfigProperties().getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return loadConfigProperties().getProperty(key, defaultValue);
    }

    public static int getProperty(String key, int defaultValue) {
        return Integer.parseInt(loadConfigProperties().getProperty(key, String.valueOf(defaultValue)));
    }

    public static Properties loadConfigProperties() {
        final Properties properties = new Properties();
        if (Objects.nonNull(ClassLoader.getSystemResource(PROPERTIES))) {
            try (InputStream stream = ClassLoader.getSystemResourceAsStream(PROPERTIES)) {
                properties.load(stream);
            } catch (IOException e) {
                log.error("Error while reading configuration.properties file from classpath: {}", e.getMessage());
            }
        }
        properties.putAll(System.getProperties());
        return properties;
    }
}
