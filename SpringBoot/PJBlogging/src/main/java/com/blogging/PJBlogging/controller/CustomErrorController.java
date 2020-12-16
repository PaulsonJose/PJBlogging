package com.blogging.PJBlogging.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {
    private static final String PATH = "/error_Disable";
    @RequestMapping(value = PATH)
    public String error() {
        return "errorCustom";
    }
    @RequestMapping(value = "/report")
    public String reportError(){
        return "submitResponse";
    }
    @Override
    public String getErrorPath() {
        return PATH;
    }
}
