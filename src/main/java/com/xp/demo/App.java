package com.xp.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

    private int a = 8;  // pre app = null      res app = new App() ;    count = 1;

    public static void main(String[] args)  {
        SpringApplication.run(App.class, args);
    }
}
