package co.spribe.testing.allure;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Properties;
import java.util.stream.Collectors;

import static co.spribe.testing.Constants.LEVEL;

@Slf4j(topic = "REPORTER")
public class ReporterUtil {

    private static final String ALLURE_RESULTS = System.getProperty("allure.resultsDir", "target" + File.separator + "allure-results");
    private static final String ALLURE_ENV_PROPERTIES = ALLURE_RESULTS + File.separator + "environment.properties";

    public static void registerReporterAppender(String pattern) {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = getRootLogger();

        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(lc);
        encoder.setPattern(pattern);
        encoder.setOutputPatternAsHeader(true);
        encoder.start();

        LogToReporterAppender<ILoggingEvent> appender = new LogToReporterAppender<>();
        appender.setContext(lc);
        appender.setEncoder(encoder);
        appender.start();

        rootLogger.addAppender(appender);
    }

    private static Logger getRootLogger() {
        Logger rootLogger = (Logger) LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(LEVEL);

        return rootLogger;
    }

    public static void addEnvironmentInfo(String key, String value) {
        addEnvironmentInfo(key + "=" + value);
    }

    public static void addEnvironmentInfo(Properties properties) {
        addEnvironmentInfo(getPropertiesAsString(properties));
    }

    private static void addEnvironmentInfo(String string) {
        string = string + "\n";
        try {
            Path path = Paths.get(ALLURE_ENV_PROPERTIES);
            Path parentDir = path.getParent();
            if (!Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
            }

            Files.write(path, string.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            log.error("Unable to add record to environment.properties", e);
        }
    }

    private static String getPropertiesAsString(Properties prop) {
        return prop.entrySet()
                .stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("\n"));
    }
}