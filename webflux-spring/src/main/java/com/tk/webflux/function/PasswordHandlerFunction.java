package com.tk.webflux.function;

import com.tk.webflux.dto.PasswordDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @Description:
 * @Date : 2023/04/12 16:55
 * @Auther : tiankun
 */
@Component
@Slf4j
public class PasswordHandlerFunction implements HandlerFunction {

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(18);

    @Override
    public Mono<Void> handle(ServerRequest request) {
        return request.bodyToMono(PasswordDTO.class)
                .doOnSubscribe(item -> log.info("onSubscribe:{}",item))
                .map(p -> passwordEncoder.matches(p.getRaw(), p.getSecured()))
                .doOnNext(item -> log.info("onNext:{}",item))
                .flatMap(isMatched -> isMatched
                        ? ServerResponse.ok().build()
                        : ServerResponse.status(HttpStatus.EXPECTATION_FAILED)
                                .build()).then(Mono.empty());

    }
}
