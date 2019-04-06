package com.xp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xupan
 */
@Controller
public class IndexController {

    @RequestMapping(value = "/index")
    public String index(){
        return "index";
    }


    @RequestMapping(value = "/index1")
    public String indexq(){
        return "index";
    }
}



