package co.spribe.testing;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.config.LogConfig;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.SSLConfig;
import io.restassured.parsing.Parser;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

import java.nio.charset.StandardCharsets;

import static com.fasterxml.jackson.annotation.JsonInclude.*;
import static io.restassured.config.RestAssuredConfig.newConfig;
import static io.restassured.config.SSLConfig.sslConfig;

public class TestBase {

    @BeforeTest
    public void setUp() {
        SSLConfig sslConfig = sslConfig().allowAllHostnames();
        EncoderConfig encoderConfig = EncoderConfig.encoderConfig().defaultContentCharset(StandardCharsets.UTF_8);
        LogConfig config = LogConfig.logConfig();
        config.enableLoggingOfRequestAndResponseIfValidationFails();
        config.enablePrettyPrinting(true);
        RestAssured.filters(new AllureRestAssured());
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.config = newConfig()
                .sslConfig(sslConfig)
                .logConfig(config)
                .encoderConfig(encoderConfig)
                .objectMapperConfig(new ObjectMapperConfig().jackson2ObjectMapperFactory((cls, charSet) -> {
                    ObjectMapper om = new ObjectMapper().findAndRegisterModules();
                    om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                    om.setSerializationInclusion(Include.NON_NULL);
                    om.disable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
                    return om;
                }));
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = ConfigurationProvider.getURL();
        RestAssured.basePath = "/player";
    }
}
