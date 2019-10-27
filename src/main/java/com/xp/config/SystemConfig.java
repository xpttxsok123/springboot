package com.xp.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Configuration
@Component
public class SystemConfig {

    public void SystemConfig(){
        System.out.println("==================");
    }


    @Data
    @Component
    @ConfigurationProperties(prefix = "system.config.email")
    public class Email {
        private String username;
        private String password;
    }


}
