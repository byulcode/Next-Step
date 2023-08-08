package next.controller;

import core.mvc.Controller;
import next.view.JsonView;
import next.view.JspView;
import next.view.ModelAndView;

public abstract class AbstractController implements Controller {

    //jsp 모델앤뷰 생성
    protected ModelAndView jspView(String forwardUrl) {
        return new ModelAndView(new JspView(forwardUrl));
    }

    //json 모델앤뷰 생성
    protected ModelAndView jsonView() {
        return new ModelAndView(new JsonView());
    }
}
