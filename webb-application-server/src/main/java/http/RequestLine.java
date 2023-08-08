package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

import java.util.HashMap;
import java.util.Map;

public class RequestLine {
    private HttpMethod method;
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);
    private String path;
    private Map<String, String> params = new HashMap<>();

    public RequestLine(String requestLine) {
        log.debug("request line : {}", requestLine);
        String[] token = requestLine.split(" ");

        //POST 방식인 경우 body 정보 읽기
        method = method.valueOf(token[0]);
        if (method.isPost()) {
            path = token[1];
            return;
        }

        //GET 방식인 경우 쿼리스트링을 통해 사용자정보 저장
        int index = token[1].indexOf("?");
        if (index == -1) {
            path = token[1];
        } else {
            path = token[1].substring(0, index);
            params = HttpRequestUtils.parseQueryString(token[1].substring(index + 1)); //쿼리스트링
        }
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public String getMethod() {
        return method.toString();
    }
}
