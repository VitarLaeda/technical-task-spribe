package co.spribe.testing.player;

import co.spribe.testing.TestBase;
import co.spribe.testing.dto.CreatePlayerDTO;
import co.spribe.testing.dto.DeletePlayerRequestDTO;
import io.qameta.allure.Issue;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Issue("Delete player can't be checked, blocked by Create player API")
public class DeletePlayerTests extends TestBase {

    @Test(dataProviderClass = PlayerDataProvider.class, dataProvider = "getValidIdAndEditor")
    public void testDeletePlayerWithValidParameters(int id, String editor) {
        DeletePlayerRequestDTO player = new DeletePlayerRequestDTO();
        player.setPlayerId(id);
        Response response = given()
                .pathParam("editor", editor)
                .when()
                .body(player)
                .delete("/delete/{editor}");
        assertEquals(response.statusCode(), 200);
        assertTrue(response.body().asString().contains("playerId"));
    }

    @Test(dataProviderClass = PlayerDataProvider.class, dataProvider = "getInvalidEditor")
    public void testDeletePlayerWithInvalidEditor(int id, String editor) {
        DeletePlayerRequestDTO player = new DeletePlayerRequestDTO();
        player.setPlayerId(id);
        Response response = given()
                .pathParam("editor", editor)
                .when()
                .body(player)
                .delete("/delete/{editor}");
        assertEquals(response.statusCode(), 404);
    }

    @Test(dataProviderClass = PlayerDataProvider.class, dataProvider = "getInvalidPlayerId")
    public void testDeletePlayerWithInvalidId(int id) {
        DeletePlayerRequestDTO player = new DeletePlayerRequestDTO();
        player.setPlayerId(id);
        Response response = given()
                .pathParam("editor", "admin")
                .when()
                .body(player)
                .delete("/delete/{editor}");
        assertEquals(response.statusCode(), 400);
    }
}
