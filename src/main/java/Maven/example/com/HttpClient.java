package Maven.example.com;

public class HttpClient {

    public static void main(String[] args) {

        MyCookieHandler myCookieHandler = new MyCookieHandler();
        HttpRequestManager httpRequestManager = new HttpRequestManager(myCookieHandler);

        System.out.println("GET Request:");
        httpRequestManager.sendGetRequest();
        myCookieHandler.checkAndAddUsernameCookie();

        System.out.println("POST Request:");
        httpRequestManager.sendPostRequest();

        System.out.println();

        httpRequestManager.parseJsonResponse();


    }
}