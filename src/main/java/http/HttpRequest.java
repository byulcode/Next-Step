package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import session.HttpSession;
import session.HttpSessions;
import util.HttpRequestUtils;
import util.IOUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private Map<String, String> header = new HashMap<>();
    private Map<String, String> params = new HashMap<>();
    private RequestLine requestLine;


    public HttpRequest(InputStream in) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            //요청 라인 읽기
            String line = br.readLine();
            log.debug("request line : {}", line);

            //null인 경우 예외처리
            if (line == null){
                throw new IllegalStateException();
            }

            //GET 방식, POST 방식 구분
            requestLine = new RequestLine(line);

            // 헤더 정보 가져오기
            while (!"".equals(line = br.readLine())) {
                log.debug("header : {}", line);
                String[] info = line.split(": ");
                header.put(info[0], info[1]);
            }

            //POST 방식인 경우 body정보 가져오기
            if ("POST".equals(getMethod())) {
                String body = IOUtils.readData(br, Integer.parseInt(header.get("Content-Length")));
                params = HttpRequestUtils.parseQueryString(body);
            }else{
                //GET 방식인 경우 requestLine에 저장된 파라미터정보 가져오기
                params = requestLine.getParams();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public String getHeader(String key) {
        return header.get(key);
    }

    public String getParameter(String key) {
        return params.get(key);
    }

    public String getMethod() {
        return requestLine.getMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public HttpCookie getCookies() {
        return new HttpCookie(getHeader("Cookie"));
    }

    public HttpSession getSession() {
        return HttpSessions.getSession(getCookies().getCookie("JSESSIONID"));
    }
}
