package co.spribe.testing.listeners;

import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import lombok.extern.slf4j.Slf4j;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static co.spribe.testing.ConfigurationProvider.getTestEnv;
import static co.spribe.testing.Constants.*;

@Slf4j
public class TestNgLifecycleListener extends TestListenerAdapter {
    private final ByteArrayOutputStream request = new ByteArrayOutputStream();
    private final ByteArrayOutputStream response = new ByteArrayOutputStream();
    private final PrintStream requestVar = new PrintStream(request, true);
    private final PrintStream responseVar = new PrintStream(response, true);

    @Override
    public void onTestStart(ITestResult result) {
        log.info("{}Test {} started{}", LOG_COLOR_GREEN, result.getName(), LOG_COLOR_END_TAG);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        log.info(LOG_COLOR_GREEN + "{} - finished successfully. Time duration :{}ms" + LOG_COLOR_END_TAG, methodName, result.getEndMillis() - result.getStartMillis());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        log.info(LOG_COLOR_RED + "Failed: {}" + LOG_COLOR_END_TAG, methodName);
        log.info(LOG_COLOR_RED + "Time wasted: {}ms" + LOG_COLOR_END_TAG, result.getEndMillis() - result.getStartMillis());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.info("{} - skipped because alwaysRun set as {}", result.getMethod().getMethodName(), result.getMethod().isAlwaysRun());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Not useful for us
    }

    @Override
    public void onStart(ITestContext context) {
        if (context.getSuite().getXmlSuite().getName().equals("Default Suite")) {
            context.getSuite().getXmlSuite().setName("One-time run from IDE");
            context.getCurrentXmlTest()
                    .setName(Arrays.stream(context.getAllTestMethods())
                            .findFirst().orElseThrow().getMethodName());
        } else {
            context.getSuite().getXmlSuite().setName(getTestEnv());
        }
        RestAssured.filters(
                new ResponseLoggingFilter(LogDetail.ALL, responseVar),
                new RequestLoggingFilter(LogDetail.ALL, requestVar)
        );
    }

    @Override
    public void onFinish(ITestContext context) {
        log.info("Finished : {}", context.getName());
    }
}
