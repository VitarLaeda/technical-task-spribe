package co.spribe.testing.allure;

import lombok.extern.slf4j.Slf4j;
import org.testng.ITestContext;
import org.testng.TestListenerAdapter;

@Slf4j(topic = "REPORTER")
public class AppenderListener extends TestListenerAdapter {

    private static final String pattern = "%d{HH:mm:ss.SSS} %-5level %logger{0} - %msg%n";

    @Override
    public void onStart(ITestContext testContext) {
        super.onStart(testContext);
        ReporterUtil.registerReporterAppender(pattern);
    }
}