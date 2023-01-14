package webserver;

import java.io.InputStream;

public class HttpRequest {
    public HttpRequest(InputStream inputStream) {
    }

    String getMethod() {
        return "";
    }

    String getPath() {
        return "";
    }

    String getHeater(String key) {
        return "";
    }

    String getParameter(String key) {
        return "";
    }
}
