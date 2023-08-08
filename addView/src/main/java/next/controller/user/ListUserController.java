package next.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import next.controller.AbstractController;
import next.controller.UserSessionUtils;
import next.dao.UserDao;
import next.view.JspView;
import next.view.ModelAndView;
import next.view.View;

public class ListUserController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return jspView("redirect:/users/loginForm");
        }

        UserDao userDao = new UserDao();
        req.setAttribute("users", userDao.findAll());
        return jspView("/user/list.jsp");
    }
}
