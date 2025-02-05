package co.spribe.testing.player;

import co.spribe.testing.TestBase;
import co.spribe.testing.dto.CreatePlayerDTO;
import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
@Issue("Doesn't work. In my opinion here should be POST request with authorization")
public class CreatePlayerTests extends TestBase {

    @Test(dataProviderClass = PlayerDataProvider.class, dataProvider = "getPlayers")
    @Description("In my opinion here should be POST request")
    public void testCreatePlayerValidEditor(CreatePlayerDTO player) {

        RequestSpecification request = given().auth().preemptive().basic("Test", "testSupervisor")
                .contentType(ContentType.JSON)
                .pathParam("editor", player.getEditor())
                .queryParam("login", player.getLogin())
                .queryParam("password", player.getPassword())
                .queryParam("age", player.getAge())
                .queryParam("gender", player.getGender())
                .queryParam("screenName", player.getScreenName())
                .queryParam("role", player.getRole())
                .when();

        Response response = request.get("/create/{editor}");
        assertEquals(response.statusCode(), 200, "Invalid response code");
        assertTrue(response.body().as(CreatePlayerDTO.class).equals(player), "Invalid response body");
    }

    @Test(dataProviderClass = PlayerDataProvider.class, dataProvider = "getInvalidEditors")
    public void testCreatePlayerInvalidEditor(CreatePlayerDTO player) {

        RequestSpecification request = given()
                .contentType(ContentType.JSON)
                .pathParam("editor", player.getEditor())
                .queryParam("login", player.getLogin())
                .queryParam("password", player.getPassword())
                .queryParam("age", player.getAge())
                .queryParam("gender", player.getGender())
                .queryParam("screenName", player.getScreenName())
                .queryParam("role", player.getRole())
                .when();

        Response response = request.get("/create{editor}");
        assertEquals(response.statusCode(), 404, "Invalid response code");
    }

    @Test(dataProviderClass = PlayerDataProvider.class, dataProvider = "getInvalidPlayers")
    public void testCreatePlayerWithoutRequiredParams(CreatePlayerDTO player) {

        RequestSpecification request = given()
                .contentType(ContentType.JSON)
                .pathParam("editor", player.getEditor())
                .queryParam("login", player.getLogin())
                .queryParam("password", player.getPassword())
                .queryParam("age", player.getAge())
                .queryParam("gender", player.getGender())
                .queryParam("screenName", player.getScreenName())
                .queryParam("role", player.getRole())
                .when();

        Response response = request.get("/create/{editor}");
        assertEquals(response.statusCode(), 403, "Invalid response code");
    }
}
