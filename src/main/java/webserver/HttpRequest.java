package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);
    private String method;
    private String path;
    private Map<String, String> header = new HashMap<>();
    private Map<String, String> parameter = new HashMap<>();


    HttpRequest(InputStream in) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            //요청 라인 읽기
            String line = br.readLine();
            log.debug("request : {}", line);

            //null인 경우 예외처리
            if (line == null) {
                return;
            }

            //GET 방식, POST 방식 구분
            httpMethod(line);

            // 헤더 정보 가져오기
            while (!"".equals(line = br.readLine())) {
                String[] info = line.split(": ");
                header.put(info[0], info[1]);
                log.debug("key : {}, value : {}", info[0], info[1]);
            }

            //POST 방식인 경우 body정보 가져오기
            if (method.equals("POST")) {
                saveParameterUseBody(br, Integer.parseInt(header.get("Content-Length")));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void httpMethod(String line) {
        String[] token = line.split(" ");
        method = token[0];

        //POST 방식인 경우 body 정보 읽기
        if (method.equals("POST")) {
            path = token[1];
            return;
        }

        //GET 방식인 경우 쿼리스트링을 통해 사용자정보 저장
        String url = token[1];
        int index = url.indexOf("?");
        path = url.substring(0, index);
        String queryString = url.substring(index + 1);
        parameter = HttpRequestUtils.parseQueryString(queryString);
    }

    private void saveParameterUseBody(BufferedReader br, int contentLength) throws IOException {
        String body = IOUtils.readData(br, contentLength);
        parameter = HttpRequestUtils.parseQueryString(body);
    }
    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getHeader(String key) {
        return header.get(key);
    }

    public String getParameter(String key) {
        return parameter.get(key);
    }
}
