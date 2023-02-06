package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import core.web.filter.ResourceFilter;
import next.dao.QuestionDao;
import next.dao.UserDao;
import next.model.Question;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateQuestionController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(CreateQuestionController.class);
    private QuestionDao questionDao = new QuestionDao();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Question question = new Question(request.getParameter("writer"), request.getParameter("title"), request.getParameter("contents"));
        logger.debug("Question : {}", question);
        questionDao.insert(question);
        return jspView("redirect:/");
    }
}
