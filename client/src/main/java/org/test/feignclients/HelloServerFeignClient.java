package org.test.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.test.common.DemoDto1;
import org.test.configurations.FeignRetryConfig;

@FeignClient(name = "HelloServer", configuration = FeignRetryConfig.class)
public interface HelloServerFeignClient {

    @GetMapping("/")
    @ResponseBody
    DemoDto1 getHello();

    @GetMapping("/{param1}")
    DemoDto1 getHelloWithParam(@PathVariable String param1);

    @PostMapping("/clear-context/{param}")
    void clearContext(@PathVariable String param);

    @PostMapping("/test-post")
    DemoDto1 postHelloWithParam(@RequestBody DemoDto1 param1);
}