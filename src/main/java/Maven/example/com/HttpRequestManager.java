package Maven.example.com;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequestManager {
    private final MyCookieHandler myCookieHandler;

    public HttpRequestManager(MyCookieHandler myCookieHandler) {
        this.myCookieHandler = myCookieHandler;

    }

    public void sendGetRequest() {
        try {
            URL url = new URL("https://postman-echo.com/get");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");


            myCookieHandler.setCookiesInRequestHeader(httpURLConnection);

            httpURLConnection.setRequestProperty("Content-Type", "application/json");

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try (InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                     BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

                    String line = "";
                    StringBuilder response = new StringBuilder();
                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }
                    System.out.println("Response :" + response);
                }
            } else {
                System.out.println("GET request failed with response code: " + httpURLConnection.getResponseCode());
            }
            httpURLConnection.disconnect();
        } catch (IOException e) {
            System.out.println("Error in making GET request");
        }

    }

    public void sendPostRequest() {
        try {
            String postData = "key1=value1&key2=value2";
            URL url = new URL("https://postman-echo.com/post");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");

            myCookieHandler.setCookiesInRequestHeader(httpURLConnection);

            httpURLConnection.setRequestProperty("Content-Type", "application/json");

            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(postData.getBytes());
            outputStream.flush();
            outputStream.close();


            System.out.println("Response code " + httpURLConnection.getResponseCode());


            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try (InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                     BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                    String line = "";
                    StringBuilder response = new StringBuilder();
                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }
                    System.out.println("Response :" + response);
                }
            } else {
                System.out.println("POST request failed with response code: " + httpURLConnection.getResponseCode());
            }
            httpURLConnection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error in making POST request");
        }

    }

    public void parseJsonResponse() {
        try {
            URL url = new URL("https://jsonplaceholder.typicode.com/photos");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");


            myCookieHandler.setCookiesInRequestHeader(httpURLConnection);


            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try (InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                     BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

                    String line = "";
                    StringBuilder response = new StringBuilder();
                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }
                    System.out.println("Response :" + response);
                    JSONArray jsonArray = new JSONArray(response.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        System.out.println("Title : " + jsonArray.getJSONObject(i).getString("title"));
                        System.out.println("ID : " + jsonArray.getJSONObject(i).getInt("id"));
                        System.out.println("URL : " + jsonArray.getJSONObject(i).getString("url"));

                    }
                }
            } else {
                System.out.println("GET request failed with response code: " + httpURLConnection.getResponseCode());
            }

        } catch (IOException e) {
            System.out.println("Error in making GET request");
        }

    }
}
