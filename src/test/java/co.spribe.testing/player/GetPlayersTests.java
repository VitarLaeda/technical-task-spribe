package co.spribe.testing.player;

import co.spribe.testing.TestBase;
import co.spribe.testing.dto.CreatePlayerDTO;
import co.spribe.testing.dto.DeletePlayerRequestDTO;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

public class GetPlayersTests extends TestBase {

    @Test
    public void testGetAllPlayers() {
        Response response = given()
                .contentType(ContentType.JSON)
                .when().get("/get/all");


        assertEquals(response.getStatusCode(), 200, "Response code: should be 200");

        response.body().jsonPath().getList("players", CreatePlayerDTO.class)
                .forEach(p -> {
                    assertNotNull(p.getScreenName(), "Screen Name should be present");
                    assertNotNull(p.getId(), "Id should be not null");
                });
    }

    @Test
    public void testGetPlayersById() {
        DeletePlayerRequestDTO player = new DeletePlayerRequestDTO();
        player.setPlayerId(1);
        Response response = given()
                .contentType(ContentType.JSON)
                .body(player)
                .when().post("/get");


        assertEquals(response.getStatusCode(), 200, "Response code: should be 200");

        response.body().jsonPath().getList("players", CreatePlayerDTO.class)
                .forEach(p -> {
                    assertNotNull(p.getScreenName(), "Screen Name should be present");
                    assertNotNull(p.getId(), "Id should be not null");
                });
    }

    @Test(dataProviderClass = PlayerDataProvider.class, dataProvider = "getInvalidPlayerId")
    public void testGetPlayersByInvalidId(int id) {
        DeletePlayerRequestDTO player = new DeletePlayerRequestDTO();
        player.setPlayerId(id);
        Response response = given()
                .contentType(ContentType.JSON)
                .body(player)
                .when().post("/get");

        assertEquals(response.getStatusCode(), 403, "Response code: should be 403");
    }

    @Test
    public void testGetAllPlayersWithoutContentType() {
        Response response = given()
                .when().get("/get/all");

        assertEquals(response.getStatusCode(), 200, "Response code: should be 200");

        response.body().jsonPath().getList("players", CreatePlayerDTO.class)
                .forEach(p -> {
                    assertNotNull(p.getScreenName(), "Screen Name should be present");
                    assertNotNull(p.getId(), "Id should be not null");
                });
    }

    @Test
    public void testGetAllPlayersWithBadRequest() {
        Response response = given()
                .when().get("/get/all.");

        assertEquals(response.getStatusCode(), 404, "Response code: should be 404");
    }
}
