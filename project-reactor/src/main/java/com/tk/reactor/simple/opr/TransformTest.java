package com.tk.reactor.simple.opr;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.util.function.Tuple2;

import java.util.function.Function;

/**
 * @Description:
 * @Date : 2023/04/12 10:51
 * @Auther : tiankun
 */
@Slf4j
public class TransformTest {
    public static void main(String[] args) throws InterruptedException {
        // Function<Flux<String>, Publisher<String>> transformer = stringFlux -> {
        //     stringFlux.index().doOnNext(item -> log.info("{} doOnNext:{}", item.getT1(), item.getT2()));
        //     return stringFlux;
        // };

        Hooks.onOperatorDebug();
        Flux.range(1, 10)
                .map(item -> "item-" + item)
                .concatWith(Flux.error(new RuntimeException("手动异常")))
                .subscribe(System.out::println);




        // Function<Flux<String>, Flux<String>> transformer = stringFlux -> stringFlux.index().doOnNext(item -> log.info("{} doOnNext:{}", item.getT1(), item.getT2())).map(Tuple2::getT2);
        // Flux.range(1000,3)
        //         .map(item -> "user:"+item)
        //         .transform(transformer)
        //         .log()
        //         .subscribe(item -> log.info("item:{}",item));


        Thread.sleep(10000);
    }
}
