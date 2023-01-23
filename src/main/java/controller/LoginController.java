package controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;
import session.HttpSession;

public class LoginController extends AbstractController {

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        User user = DataBase.findUserById(request.getParameter("userId"));

        // 해당 아이디가 존재하는 경우
        if (user != null) {
            if (user.login(request.getParameter("password"))) {//비밀번호 맞을 경우
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                response.sendRedirect("/index.html");
            } else {// 비밀번호가 틀릴 경우
                response.sendRedirect("/user/login_failed.html");
            }
        } else {// 아이디가 존재하지 않는 경우
            response.sendRedirect("/user/login_failed.html");
        }
    }
}
