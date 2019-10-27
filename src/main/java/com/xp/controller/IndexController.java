package com.xp.controller;

import com.xp.annotation.Log;
import com.xp.config.SystemConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xupan
 */
@RestController
public class IndexController {

    @Autowired
    private SystemConfig systemConfig;
    /**
     * 首页跳转
     *
     * @return 返回字符串
     */
    @RequestMapping(value = "/index")
    @Log
    public String index() {
        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "index";
    }



    @RequestMapping(value = "/string")
    public String string(String index) {
        return "index";
    }


}



