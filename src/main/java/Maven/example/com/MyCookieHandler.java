package Maven.example.com;

import org.apache.commons.lang3.StringUtils;

import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MyCookieHandler {
    private List<HttpCookie> cookies = new ArrayList<>();

    public List<HttpCookie> getCookies() {
        return cookies;
    }

    public void addCustomCookie(HttpCookie cookie) {
        cookies.add(cookie);
    }

    public void updateCookies(HttpURLConnection connection) {
        String cookiesHandler = connection.getHeaderField("Set-Cookie");
        if (cookiesHandler != null) {
            cookies = HttpCookie.parse(cookiesHandler);
        }
    }

    public void checkAndAddUsernameCookie() {
        Optional<HttpCookie> usernameCookie = getCookies().stream()
                .findAny().filter(cookie -> cookie.getName().equals("username"));
        if (!usernameCookie.isPresent()) {
            addCustomCookie(new HttpCookie("username", "john"));
        }
    }

    public void setCookiesInRequestHeader(HttpURLConnection connection) {
        connection.setRequestProperty("Cookie", StringUtils.join(getCookies(), ":"));
    }


}