package com.xp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class Application {

    public static void main(String[] args){
        Executors.newFixedThreadPool(6);
        SpringApplication.run(Application.class,args);
    }

}
