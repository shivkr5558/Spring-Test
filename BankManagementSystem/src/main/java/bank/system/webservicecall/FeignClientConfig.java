package bank.system.webservicecall;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Retryer;

@Configuration
public class FeignClientConfig {

    @Bean
    public Retryer retryer() {
        return new FeignClientRetry();
    }

}
