package controller;

import http.HttpRequest;
import http.HttpResponse;

public class ListUserController extends AbstractController{
    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        super.doGet(request, response);
    }

    public boolean isLogin(String cookieValue) {
        return false;
    }
}
