package org.test.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.test.common.DemoDto1;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class TestController {

    private Map<String, Integer> testRetryMap = new HashMap<>();

    @GetMapping("/")
    public ResponseEntity<DemoDto1> hello() {
        return ResponseEntity.ok(DemoDto1.builder()
                .name("hello")
                .build());
    }

    @GetMapping("/{param1}")
    public ResponseEntity<DemoDto1> hello(@PathVariable String param1) {
        return ResponseEntity.ok(DemoDto1.builder()
                .name("hello with param1")
                .param1(param1).build());
    }

    @PostMapping("/clear-context/{param}")
    public ResponseEntity<Void> clearContext(@PathVariable String param) {
        testRetryMap.put(param, 0);
        log.info("Clear context: {}", param);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/test-post")
    public ResponseEntity<DemoDto1> testPost(@RequestBody DemoDto1 demoDto1) {
        testRetryMap.put(demoDto1.getParam1(), testRetryMap.getOrDefault(demoDto1.getParam1(), 0) + 1);
        log.info("Retry count: {} for param: {}", testRetryMap.get(demoDto1.getParam1()), demoDto1.getParam1());
        if (testRetryMap.get(demoDto1.getParam1()) > 6) {
            return ResponseEntity.ok(demoDto1);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Retry-After", "30");
        return new ResponseEntity<>(null, headers, HttpStatus.SERVICE_UNAVAILABLE);
    }
}
