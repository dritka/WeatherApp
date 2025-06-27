package test;

import api.*;
import java.io.*;
import java.util.*;
import java.nio.file.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class ApiHandlerTest {

    public static int testNum = 1;

    @org.junit.jupiter.api.BeforeEach
    public void setUp() {
        System.out.printf("Running test %d...%n", testNum);
    }

    @Test
    public void readAllDataFirst() throws IOException {
        ApiHandler.readAllData("Tirana");
        Path p = Paths.get("data_Tirana.json");
        assertTrue(Files.exists(p));
    }

    @Test
    public void readAllDataSecond() throws IOException {
        assertThrows(IOException.class, () -> ApiHandler.readAllData("Imaginary"));
    }

    @Test
    public void readAllDataThird() throws IOException {
        ApiHandler.readAllData("Jakarta");
        Path p2 = Paths.get("data_Jakarta.json");
        assertTrue(Files.exists(p2));
    }

    @Test
    public void fetchWeatherDataFirst() throws IOException {
        ApiHandler.readAllData("Tirana");
        Map<Enum, String> data = ApiHandler.fetchWeatherData("data_Tirana.json");
        for (String value : data.values())
            assertNotNull(value);
    }

    @Test
    public void fetchWeatherDataSecond() throws IOException {
        ApiHandler.readAllData("Barcelona");
        Map<Enum, String> data = ApiHandler.fetchWeatherData("data_Barcelona.json");
        for (String value : data.values())
            assertNotNull(value);
    }

    @Test
    public void fetchWeatherDataThird() {
        assertThrows(IOException.class, () -> ApiHandler.fetchWeatherData("Imaginary"));
    }

    @Test
    public void downloadIconFirst() {
        assertThrows(IOException.class, () -> ApiHandler.downloadIcon("htps://image.png"));
    }

    @Test
    public void downloadIconSecond() {
        assertThrows(IOException.class, () -> ApiHandler.downloadIcon("httttps:???;////gibberish"));
    }

    @Test
    public void downloadIconThird() throws IOException {
        ApiHandler.readAllData("Oslo");
        Map<Enum, String> data = ApiHandler.fetchWeatherData("data_Oslo.json");
        ApiHandler.downloadIcon(data.get(Attributes.condition_json.icon));
        Path p = Paths.get("icon.png");
        assertTrue(Files.exists(p));
    }

    @Test
    public void isApiKeySetCorreclty() {
        String actual = System.getenv("API_KEY");
        assertEquals(actual, "14e81ae19442478db80164223252606");
    }

    @org.junit.jupiter.api.AfterEach
    public void tearDown() {
        testNum++;
    }
}