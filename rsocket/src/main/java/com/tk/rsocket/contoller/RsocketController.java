package com.tk.rsocket.contoller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

/**
 * @Description:
 * @Date : 2023/04/14 18:38
 * @Auther : tiankun
 */
@Controller
public class RsocketController {

    @MessageMapping("hello")
    public Mono<String> hello(String input) {
        return Mono.just("Hello: " + input);
    }
}
