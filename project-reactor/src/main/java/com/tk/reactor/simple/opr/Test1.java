package com.tk.reactor.simple.opr;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

/**
 * @Description:
 * @Date : 2023/04/13 18:42
 * @Auther : tiankun
 */
@Slf4j
public class Test1 {
    public static void main(String[] args) {
        Flux.just(1,2,3,4,5,6)
                .log()
                .flatMap(new Function<Integer, Publisher<?>>() {
                    @Override
                    public Publisher<?> apply(Integer integer) {
                        return Mono.just(integer);
                    }
                })
                .map(new Function<Object, Object>() {
                    @Override
                    public Object apply(Object o) {
                        log.info("apply {}, class:{}",o,o.getClass());
                        return o;
                    }
                })
                .subscribe(
                        item -> log.info("OnNect:{}",item),
                        ex -> log.error("Error:{}", ex),
                        () -> log.info("complete....")
                );
    }
}
