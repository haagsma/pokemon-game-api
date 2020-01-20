package com.poke.boot;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(value = "com.poke")
@SpringBootApplication
public class RunApplication {

    public static void main(String[] args) {
        BasicConfigurator.configure();
        SpringApplication.run(RunApplication.class, args);
    }

}
