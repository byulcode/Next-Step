package controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

import java.util.Collection;
import java.util.Map;

public class ListUserController extends AbstractController{
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
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

    public boolean isLogin(String cookieValue) {
        Map<String, String> cookies = HttpRequestUtils.parseCookies(cookieValue);
        String value = cookies.get("logined");
        if (value == null) {
            return false;
        }
        return Boolean.parseBoolean(value);
    }
}
