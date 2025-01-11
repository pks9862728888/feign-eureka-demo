package org.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RestController;
import org.test.common.DemoDto1;
import org.test.feignclients.HelloServerFeignClient;

/**
 * @author Spencer Gibb
 */
@SpringBootApplication
@EnableDiscoveryClient
@RestController
@EnableFeignClients(basePackageClasses = {HelloServerFeignClient.class})
@RequiredArgsConstructor
@Slf4j
public class HelloClientApplication implements CommandLineRunner {
    private final HelloServerFeignClient helloServerFeignClient;

    public static void main(String[] args) {
        SpringApplication.run(HelloClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            helloServerFeignClient.clearContext("dummyparam2");
            log.info("/ GET: {}", helloServerFeignClient.getHello());
            log.info("/ GET with param1: dummyparam1: {}", helloServerFeignClient.getHelloWithParam("dummyparam1"));
            log.info("/ POST with DTO: {}", helloServerFeignClient.postHelloWithParam(DemoDto1.builder()
                    .name("postData")
                    .param1("dummyparam2")
                    .build()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
