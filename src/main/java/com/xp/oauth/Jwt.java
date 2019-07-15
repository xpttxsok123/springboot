package com.xp.oauth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class Jwt {
    @Autowired
    ResourceLoader resourceLoader;

    //生成一个jwt令牌
    @Test
    public void testCreateJwt() {


    }

}
