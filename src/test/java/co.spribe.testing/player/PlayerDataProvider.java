package co.spribe.testing.player;

import co.spribe.testing.dto.CreatePlayerDTO;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

@Slf4j
public class PlayerDataProvider {

    @DataProvider
    public static Iterator<Object> getPlayers() {
        ArrayList<Object> temp = new ArrayList<>();
        temp.add(new CreatePlayerDTO("supervisor", (40), "Female", "test", "password", "supervisor", "superTest"));
        temp.add(new CreatePlayerDTO("supervisor", (10), "Male", "testAdmin", "password", "admin", "adminTest"));
        temp.add(new CreatePlayerDTO("admin", (70), "Male", "testAdmin", "password", "admin", "adminTest"));
        temp.add(new CreatePlayerDTO("admin", (100), "Female", "test", "password", "supervisor", "superTest"));
        return Arrays.stream(temp.toArray()).iterator();
    }

    @DataProvider
    public static Iterator<Object> getInvalidEditors() {
        ArrayList<Object> temp = new ArrayList<>();
        temp.add(new CreatePlayerDTO("", 25, "Male",
                "test", "password", "supervisor", "superTest")
        );

        return Arrays.stream(temp.toArray()).iterator();
    }

    @DataProvider
    public static Iterator<Object> getInvalidPlayers() {
        ArrayList<Object> temp = new ArrayList<>();
        temp.add(new CreatePlayerDTO("supervisor", 200, "Male", "test", "password", "supervisor", ""));
        temp.add(new CreatePlayerDTO("supervisor", -21, "Male", "test", "password", "admin", "superTest"));
        temp.add(new CreatePlayerDTO("supervisor", 22, "Male", "test", "password", "", "superTest"));
        temp.add(new CreatePlayerDTO("supervisor", 2, "Male", "", "password", "supervisor", "superTest"));
        temp.add(new CreatePlayerDTO("supervisor", 0, "", "test", "password", "supervisor", "superTest"));

        return Arrays.stream(temp.toArray()).iterator();
    }

    @DataProvider
    public static Object[][] getValidIdAndEditor() {
        return new Object[][]{
                {2, "admin"},
                {2, "supervisor"},
                {1, "admin"},
                {1, "supervisor"},
        };
    }

    @DataProvider
    public static Object[][] getInvalidEditor() {
        return new Object[][]{
                {1, ""},
                {2, ""},
                {0, ""},
        };
    }

    @DataProvider
    public static Object[] getInvalidPlayerId() {
        return new Object[]{0, 00, -1, Integer.MAX_VALUE, Integer.MIN_VALUE};
    }
}
