import Maven.example.com.MyCookieHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;


public class MyCookieHandlerTest {
    MyCookieHandler myCookieHandler = new MyCookieHandler();


    @Test
    public void shouldBeEmptyTest() {
        Assertions.assertTrue(myCookieHandler.getCookies().isEmpty());
        System.out.println("Cookies before adding testCookie: " + myCookieHandler.getCookies());
    }

    @Test
    public void shouldAddAndUpdateCustomCookieTest() {
        HttpCookie testCookie = new HttpCookie("test", "value");
        myCookieHandler.addCustomCookie(testCookie);
        System.out.println("Cookies after adding testCookie: " + myCookieHandler.getCookies());
        Assertions.assertTrue(myCookieHandler.getCookies().contains(testCookie));

        HttpURLConnection testConnection = createTestConnection();
        myCookieHandler.updateCookies(testConnection);
        System.out.println("Cookies after updating: " + myCookieHandler.getCookies());
        Assertions.assertFalse(myCookieHandler.getCookies().isEmpty());
    }

    @Test
    public void checkAndAddUsernameCookieTest() {
        myCookieHandler.checkAndAddUsernameCookie();
        System.out.println("Cookies after checking and adding usernameCookie: " + myCookieHandler.getCookies());
        Assertions.assertTrue(myCookieHandler.getCookies().stream().anyMatch(cookie -> cookie.getName().equals("username")));

    }

    @Test
    public void shouldSetCookiesInRequestHeader() {
        HttpURLConnection requestHeaderConnection = createTestConnection();
        myCookieHandler.setCookiesInRequestHeader(requestHeaderConnection);
        System.out.println("Cookies after setting in request header: " + myCookieHandler.getCookies());
        Assertions.assertNotNull(requestHeaderConnection.getRequestProperty("Cookie"));
    }


    private HttpURLConnection createTestConnection() {
        try {
            URL url = new URL("https://jsonplaceholder.typicode.com/posts");
            HttpURLConnection connection =  (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Set-Cookie", "test=value; Path=/");
            return connection;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}