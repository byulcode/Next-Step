package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.QuestionDao;
import next.model.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateFormQuestionController extends AbstractController {

    private QuestionDao questionDao = new QuestionDao();
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //로그인하지 않은 경우
        if (!UserSessionUtils.isLogined(request.getSession())) {
            return jspView("redirect:/users/loginForm");
        }

        long questionId = Long.parseLong(request.getParameter("questionId"));
        Question question = questionDao.findById(questionId);

        //글쓴이와 로그인한 유저가 서로 다를 경우
        if (!question.isSameUser(UserSessionUtils.getUserFromSession(request.getSession()))) {
            throw new IllegalStateException("다른 사용자가 쓴 글은 수정할 수 없습니다.");
        }
        return jspView("/qna/update.jsp").addObject("question", question);
    }
}
