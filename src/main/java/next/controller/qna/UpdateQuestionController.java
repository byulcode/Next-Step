package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.controller.user.UpdateUserController;
import next.dao.QuestionDao;
import next.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateQuestionController extends AbstractController {

    private QuestionDao questionDao = new QuestionDao();
    private static final Logger log = LoggerFactory.getLogger(UpdateQuestionController.class);

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
            throw new IllegalStateException("다른 사용자가 쓴 글을 수정할 수 없습니다.");
        }

        Question updateQuestion = new Question(question.getWriter(), request.getParameter("title"), request.getParameter("contents"));
        log.debug("Update Question : {}", question);

        question.update(updateQuestion);
        questionDao.update(question);

        return jspView("redirect:/");
    }
}
