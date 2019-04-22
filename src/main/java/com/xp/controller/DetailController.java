package com.xp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.annotation.WebServlet;

/**
 * @author xupan
 */
@RestController
@WebServlet
public class DetailController {


    @RequestMapping(value = "/detail")
    public String detail(Long id) throws InterruptedException {
        long i = 0;
        String a = "";
        while ( i<90000000l){
            i++;
            a+="";
        }
        return "detail";
    }

}
