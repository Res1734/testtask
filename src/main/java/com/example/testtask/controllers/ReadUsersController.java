package com.example.testtask.controllers;

import com.example.testtask.DataService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Resident on 05.07.2017.
 */
@Controller
public class ReadUsersController {
    private final DataService dataService;

    private class RequestSuccess {
        private boolean result = true;
    }

    private class RequestError {
        private boolean result = false;
        private String message;

        public RequestError(String message) {
            this.message = message;
        }
    }

    @Autowired
    public ReadUsersController(DataService dataService) {
        this.dataService = dataService;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseBody
    public void readAllUsers(HttpServletResponse response) throws IOException {
        String json;
        try {
            dataService.getAllUsers();
            json = new Gson().toJson(new RequestSuccess());
            response.setStatus(200);
        } catch (Exception e) {
            e.printStackTrace();
            json = new Gson().toJson(new RequestError(e.getLocalizedMessage()));
            response.setStatus(500);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}