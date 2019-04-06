package com.xp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xupan
 */
@Controller
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


    /**
     * @param index 下标
     * @return 返回字符
     */
    @RequestMapping(value = "/index1")
    public String indexq(String index) {
        return "index";
    }
}



