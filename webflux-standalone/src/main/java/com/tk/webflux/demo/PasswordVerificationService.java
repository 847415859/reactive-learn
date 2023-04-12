package com.tk.webflux.demo;

import reactor.core.publisher.Mono;

/**
 * @Description:
 * @Date : 2023/04/12 17:52
 * @Auther : tiankun
 */
public interface PasswordVerificationService {

    Mono<Void> check(String raw, String encoded);

}
