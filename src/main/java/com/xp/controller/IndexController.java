package com.xp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xupan
 */
@RestController
public class IndexController {

    /**
     * 首页跳转
     *
     * @return 返回字符串
     */
    @RequestMapping(value = "/index")
    public String index() {
        return "index";
    }



    @RequestMapping(value = "/string")
    public String string(String index) {
        return "index";
    }


}



