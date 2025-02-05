package co.spribe.testing.listeners;

import lombok.extern.slf4j.Slf4j;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.xml.XmlSuite;

import static co.spribe.testing.ConfigurationProvider.*;

@Slf4j
public class SuiteListener implements ISuiteListener {
    @Override
    public void onStart(ISuite suite) {
        suite.getXmlSuite().setThreadCount(getProperty("thread-count", 1));
        suite.getXmlSuite().setDataProviderThreadCount(getProperty("thread-count", 1));
        suite.getXmlSuite().setParallel(XmlSuite.ParallelMode.getValidParallel(getProperty("parallel", "classes")));
    }

    @Override
    public void onFinish(ISuite suite) {
        log.debug("parallel set for this run  - {}", suite.getXmlSuite().getParallel());
        log.debug("thread-count of this run  - {}", suite.getXmlSuite().getThreadCount());
        log.debug("data-provider-thread-count of this run  - {}", suite.getXmlSuite().getDataProviderThreadCount());
    }
}
