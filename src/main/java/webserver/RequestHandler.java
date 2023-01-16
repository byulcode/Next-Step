package webserver;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.Map;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            HttpRequest request = new HttpRequest(in);
            HttpResponse response = new HttpResponse(out);
            String path = getDefaultPath(request.getPath());

            // 회원가입
            if ("/user/create".equals(path)) {
                // 읽어온 회원 정보를 담은 user 객체 생성
                createUser(request, response);
            }  // 로그인
            else if ("/user/login".equals(path)) {
                login(request, response);
            } //사용자 목록 출력
            else if ("/user/list".equals(path)) {
                listUser(request, response);
            } else {
                response.forward(path);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void createUser(HttpRequest request, HttpResponse response) {
        User user = new User(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email")
        );
        log.debug("User : {}", user);

        DataBase.addUser(user);
        log.debug("Database : {}", DataBase.findUserById(user.getUserId()));

        response.sendRedirect("/index.html");
    }

    private void login(HttpRequest request, HttpResponse response) {
        User user = DataBase.findUserById(request.getParameter("userId"));

        // 해당 아이디가 존재하는 경우
        if (user != null) {
            if (user.login(request.getParameter("password"))) {//비밀번호 맞을 경우
                response.addHeader("Set-Cookie", "logined=true");
                response.sendRedirect("/index.html");
            } else {// 비밀번호가 틀릴 경우
                response.sendRedirect("/user/login_failed.html");
            }
        } else {// 아이디가 존재하지 않는 경우
            response.sendRedirect("/user/login_failed.html");
        }
    }

    private void listUser(HttpRequest request, HttpResponse response) {
        if (!isLogin(request.getHeader("Cookie"))) {//로그인하지 않은 경우
            response.sendRedirect("/user/login.html");
            return;
        }

        //로그인 한 경우
        StringBuilder sb = new StringBuilder();
        Collection<User> users = DataBase.findAll();
        log.debug("database.user : {}", users);
        sb.append("<table border=\"1\">");
        String prefix = "<td>";
        String postfix = "</td>";
        for (User user : users) {
            sb.append("<tr>");
            sb.append(prefix + user.getUserId() + postfix);
            sb.append(prefix + user.getName() + postfix);
            sb.append(prefix + user.getEmail() + postfix);
            sb.append("</tr>");
        }
        sb.append("</table>");
        response.forwardBody(sb.toString());
    }

    private String getDefaultPath(String path) {
        if (path.equals("/")) {
            return "/index.html";
        }
        return path;
    }

    private boolean isLogin(String cookieValue) {
        Map<String, String> cookies = HttpRequestUtils.parseCookies(cookieValue);
        String value = cookies.get("logined");
        if (value == null) {
            return false;
        }
        return Boolean.parseBoolean(value);
    }
}
