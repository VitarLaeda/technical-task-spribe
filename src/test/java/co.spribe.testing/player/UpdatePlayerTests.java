package co.spribe.testing.player;

import co.spribe.testing.TestBase;
import co.spribe.testing.dto.CreatePlayerDTO;
import io.qameta.allure.Issue;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Issue("Update player doesn't work at all")
public class UpdatePlayerTests extends TestBase {

    @Test
    public void testUpdatePlayer() {
        List<CreatePlayerDTO> players = given()
                .contentType(ContentType.JSON)
                .when().get("/get/all").body().jsonPath().getList("players", CreatePlayerDTO.class);

        players.stream().filter(p -> p.getScreenName().equals("testSupervisor")).forEach(player -> {
            player.setAge(player.getAge() + 1);
            player.setLogin(player.getLogin() + "_updated");
            Response response = given()
                    .contentType(ContentType.JSON)
                    .pathParam("editor", "admin")
                    .pathParam("id", player.getId())
                    .body(player)
                    .when().patch("/update/{editor}/{id}");
            assertEquals(response.statusCode(), 200, "Invalid response code");
            assertEquals(player, response.body().as(CreatePlayerDTO.class), "Invalid response body");
        });
    }

    @Test
    public void testUpdatePlayerWithoutEditor() {
        List<CreatePlayerDTO> players = given()
                .contentType(ContentType.JSON)
                .when().get("/get/all").body().jsonPath().getList("players", CreatePlayerDTO.class);

        players.stream().filter(p -> p.getScreenName().equals("testSupervisor")).forEach(player -> {
            player.setAge(player.getAge() + 1);
            player.setLogin(player.getLogin() + "_updated");
            Response response = given()
                    .contentType(ContentType.JSON)
                    .pathParam("editor", "")
                    .pathParam("id", player.getId())
                    .body(player)
                    .when().patch("/update/{editor}/{id}");
            assertEquals(response.statusCode(), 404, "Invalid response code");
        });
    }

    @Test(dataProviderClass = PlayerDataProvider.class, dataProvider = "getInvalidPlayerId")
    public void testUpdatePlayerWithoutId(int id) {
        List<CreatePlayerDTO> players = given()
                .contentType(ContentType.JSON)
                .when().get("/get/all").body().jsonPath().getList("players", CreatePlayerDTO.class);

        players.stream().filter(p -> p.getScreenName().equals("testSupervisor")).forEach(player -> {
            player.setAge(player.getAge() + 1);
            player.setLogin(player.getLogin() + "_updated");
            Response response = given()
                    .contentType(ContentType.JSON)
                    .pathParam("editor", "supervisor")
                    .pathParam("id", id)
                    .body(player)
                    .when().patch("/update/{editor}/{id}");
            assertEquals(response.statusCode(), 404, "Invalid response code");
        });
    }

    @Test
    public void testUpdatePlayerWithInvalidData() {
        List<CreatePlayerDTO> players = given()
                .contentType(ContentType.JSON)
                .when().get("/get/all").body().jsonPath().getList("players", CreatePlayerDTO.class);

        players.stream().filter(p -> p.getScreenName().equals("testSupervisor")).forEach(player -> {
            player.setAge(player.getAge() + 1);
            player.setLogin(player.getLogin() + "_updated");
            Response response = given()
                    .contentType(ContentType.JSON)
                    .pathParam("editor", "admin")
                    .pathParam("id", player.getId())
                    .when().patch("/update/{editor}/{id}");
            assertEquals(response.statusCode(), 400, "Invalid response code");
        });
    }
}
