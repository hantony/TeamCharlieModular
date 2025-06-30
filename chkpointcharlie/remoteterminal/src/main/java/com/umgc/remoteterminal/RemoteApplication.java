

//spring-web-modules/spring-resttemplate/src/main/java/com/baeldung/sampleapp/config

package com.umgc.remoteterminal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableAutoConfiguration
@ComponentScan("com.umgc.remoteterminal")
public class RemoteApplication implements WebMvcConfigurer {

    public static void main(final String[] args) {
        SpringApplication.run(RemoteApplication.class, args);
    }
}