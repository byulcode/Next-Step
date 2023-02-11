package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ApiDeleteQuestionController extends AbstractController {
    private QuestionDao questionDao = QuestionDao.getInstance();
    private AnswerDao answerDao = AnswerDao.getInstance();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!UserSessionUtils.isLogined(request.getSession())) {
            return jsonView().addObject("result", Result.fail("Login is required"));
        }

        long questionId = Long.parseLong(request.getParameter("questionId"));
        Question question = questionDao.findById(questionId);

        //질문이 없는 경우
        if (question == null) {
            return jsonView().addObject("result", Result.fail("존재하지 않는 질문입니다."));
        }

        //질문자와 로그인된 사용자가 다른 경우
        if (!question.isSameUser(UserSessionUtils.getUserFromSession(request.getSession()))) {
            return jsonView().addObject("result", Result.fail("다른 사용자의 글을 삭제할 수 없습니다."));
        }

        List<Answer> answers = answerDao.findAllByQuestionId(questionId);
        if (answers.isEmpty()) {
            questionDao.delete(questionId);
            return jsonView().addObject("result", Result.ok());
        }

        boolean canDelete = true;
        for (Answer answer : answers) {
            String writer = question.getWriter();
            if (!writer.equals(answer.getWriter())) {
                canDelete = false;
                break;
            }
        }

        if (canDelete) {
            questionDao.delete(questionId);
            return jsonView().addObject("result", Result.ok());
        } else {
            return jsonView().addObject("result", Result.fail("다른 사용자가 추가한 댓글이 존재해 삭제할 수 없습니다."));
        }
    }
}
