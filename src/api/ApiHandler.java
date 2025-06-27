package api;

import java.io.*;
import java.net.*;
import java.sql.SQLOutput;
import java.util.*;
import java.nio.file.*;

import org.json.*;
import javax.json.*;

public class ApiHandler {

    private static final String website = "http://api.weatherapi.com/v1";
    private static final String apiKey = System.getenv("API_KEY");

    public static void main(String[] args) throws IOException {
        
    }

    /*
    This functions fetches all the data
    from the api and writes it inside
    a file with the name data_City.json.
    This way it is more efficient because
    we get the data from the API only once and
    then work with the local json file.
     */
    public static void readAllData(String city) throws IOException {
        BufferedReader in = null;

        try {
            String urlString = website + "/current.json?key=" + apiKey + "&q=" + city;
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            String line;
            StringBuilder data = new StringBuilder();
            while ((line = in.readLine()) != null) {
                data.append(line);
            }

            String output = prettyPrintJson(data.toString());
            Path path = Paths.get("data_" + city + ".json");
            Files.writeString(path, output);
        } finally {
            try {
                if (in != null) in.close();
            } catch (IOException ioe) {
                System.out.println("There was a problem while trying to close the reader: " + ioe.getMessage());
            }
        }
    }

    /*
    This function gets the data that is going
    to be displayed in the weather app. Here I only
    get the temperature, the feels like temperature,
    the sky condition, the wind speed, the visibility
    and also the location of an icon that will be helpful
    when creating the GUI.
    */
    public static Map<Enum, String> fetchWeatherData(String fileName) throws IOException {
        Map<Enum, String> data = new HashMap<>();

        Path path = null;
        JsonReader reader = null;

        try {
            path = Paths.get(fileName);

            // Reading the content of the file
            reader = Json.createReader(
                    new InputStreamReader(new FileInputStream(path.toString()))
            );

            // Creating a json object from the content
            // and getting the nested json objects where
            // the data that we need to fetch is located
            JsonObject jsonObject = reader.readObject();
            JsonObject current = jsonObject.getJsonObject(String.valueOf(Attributes.current));
            JsonObject condition = current.getJsonObject(String.valueOf(Attributes.condition));

            // Extracting the data
            int temp_c = current.getInt(String.valueOf(Attributes.current_json.temp_c));
            int feelslike_c = current.getInt(String.valueOf(Attributes.current_json.feelslike_c));
            int wind_kph = current.getInt(String.valueOf(Attributes.current_json.wind_kph));
            int vis_km = current.getInt(String.valueOf(Attributes.current_json.wind_kph));
            String text = condition.getString(String.valueOf(Attributes.condition_json.text));
            String iconLocation = "http:" + condition.getString(String.valueOf(Attributes.condition_json.icon));

            // Populating the map with the
            // data attribute name as the key
            // and the data as the value
            data.put(Attributes.current_json.temp_c, String.valueOf(temp_c));
            data.put(Attributes.current_json.feelslike_c, String.valueOf(feelslike_c));
            data.put(Attributes.current_json.wind_kph, String.valueOf(wind_kph));
            data.put(Attributes.current_json.vis_km, String.valueOf(vis_km));
            data.put(Attributes.condition_json.text, text);
            data.put(Attributes.condition_json.icon, iconLocation);
            downloadIcon(iconLocation);
        } finally {
            if (reader != null) reader.close();
        }

        return data;
    }

    public static void downloadIcon(String iconLocation) throws IOException {
        InputStream in = null;
        FileOutputStream out = null;

        try {
            URL url = new URL(iconLocation);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            in = conn.getInputStream();
            out = new FileOutputStream("icon.png");

            int data;
            while ((data = in.read()) != -1) {
                out.write(data);
            }
        } finally {
            try {
                if (out != null) out.close();
            } catch (IOException ioe) {
                System.out.println("There was a problem while trying to close the writer: " + ioe.getMessage());
            }

            try {
                if (in != null) in.close();
            } catch (IOException ioe) {
                System.out.println("There was a problem while trying to close the reader: " + ioe.getMessage());
            }
        }
    }

    /*
     This function isn't necessary, it just
     makes the content of the json file more
     readable by adding an indentation with
     4 spaces.
     */
    public static String prettyPrintJson(String uglyJson) {
        JSONObject jsonObject = new JSONObject(uglyJson);
        return jsonObject.toString(4);
    }
}