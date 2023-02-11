package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.exception.CannotDeleteException;
import next.controller.UserSessionUtils;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DeleteQuestionController extends AbstractController {

    private QuestionDao questionDao = QuestionDao.getInstance();
    private AnswerDao answerDao = AnswerDao.getInstance();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!UserSessionUtils.isLogined(request.getSession())) {
            return jspView("redirect:/users/loginForm");
        }

        long questionId = Long.parseLong(request.getParameter("questionId"));
        Question question = questionDao.findById(questionId);

        //질문이 없는 경우
        if (question == null) {
            throw new CannotDeleteException("존재하지 않는 질문입니다.");
        }

        //질문자와 로그인된 사용자가 다른 경우
        if (!question.isSameUser(UserSessionUtils.getUserFromSession(request.getSession()))) {
            createModelAndView(question, answerDao.findAllByQuestionId(questionId), "다른 사용자의 글을 삭제할 수 없습니다.");
        }

        List<Answer> answers = answerDao.findAllByQuestionId(questionId);
        if (answers.isEmpty()) {
            questionDao.delete(questionId);
            return jspView("redirect:/");
        }

        // 질문자와 답변자가 다른 답변이 있는 경우
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
            return jspView("redirect:/");
        }
        return createModelAndView(question, answers, "다른 사용자가 추가한 댓글이 존재해 삭제할 수 없습니다.");
    }

    private ModelAndView createModelAndView(Question question, List<Answer> answers, String errorMessage) {
        return jspView("show.jsp")
                .addObject("question", question)
                .addObject("answers", answers)
                .addObject("errorMessage", errorMessage);
    }

}
