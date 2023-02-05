package next.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import next.controller.UserSessionUtils;
import next.dao.UserDao;
import next.view.JspView;
import next.view.View;

public class ListUserController implements Controller {
    @Override
    public View execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return new JspView("redirect:/users/loginForm");
        }

        UserDao userDao = new UserDao();
        req.setAttribute("users", userDao.findAll());
        return new JspView("/user/list.jsp");
    }
}
