package net.xmmpp.uc.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class},
        scanBasePackages = {
                "net.xmmpp.uc"
        })
@EnableAspectJAutoProxy
@EnableDiscoveryClient
@EnableConfigurationProperties
public class UCWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(UCWebApplication.class, args);
    }

}

