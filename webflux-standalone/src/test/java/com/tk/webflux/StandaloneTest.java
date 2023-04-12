package com.tk.webflux;

import com.tk.webflux.demo.DefaultPasswordVerificationService;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.time.Duration;

/**
 * @Description:
 * @Date : 2023/04/12 17:51
 * @Auther : tiankun
 */
public class StandaloneTest {

    @Test
    public void checkApplicationRunning() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(18);
        DefaultPasswordVerificationService service =
                new DefaultPasswordVerificationService(WebClient.builder());
        StepVerifier.create(service.check("test1", encoder.encode("test")))
                .expectSubscription()
                .expectComplete()
                .verify(Duration.ofSeconds(30));
    }

}
