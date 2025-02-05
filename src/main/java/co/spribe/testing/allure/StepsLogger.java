package co.spribe.testing.allure;

import io.qameta.allure.listener.StepLifecycleListener;
import io.qameta.allure.model.StepResult;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;

@SuppressWarnings("JavadocType")
@Slf4j(topic = "STEP")
public class StepsLogger implements StepLifecycleListener {

    @Override
    public void beforeStepStart(final StepResult result) {
        log.debug("{}{}", result.getName(), result.getParameters().isEmpty() ? "" : " - [ " + getParameters(result) + "]");
    }

    private String getParameters(StepResult result) {
        return result.getParameters().stream().map(it -> it.getName() + "=" + it.getValue()).collect(Collectors.joining(", "));
    }
}