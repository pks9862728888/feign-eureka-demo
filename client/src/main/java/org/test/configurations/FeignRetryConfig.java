package org.test.configurations;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.concurrent.TimeUnit.SECONDS;

@Configuration
public class FeignRetryConfig {

    @Bean
    public Retryer retryer() {
        // period = start time
        // 2nd param = interval between retries
        // 3rd param = max attempts
        return new Retryer.Default(100, SECONDS.toMillis(2), 7);
    }
}
