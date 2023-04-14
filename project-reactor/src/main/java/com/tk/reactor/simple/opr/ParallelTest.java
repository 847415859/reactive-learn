package com.tk.reactor.simple.opr;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.Random;

/**
 * @Description:
 * @Date : 2023/04/14 7:23
 * @Auther : tiankun
 */
@Slf4j
public class ParallelTest {

    private static Random random = new Random();

    public static void main(String[] args) throws InterruptedException {
        Flux.range(1,1000)
                .parallel()
                .doOnNext(item -> log.info("doOnNext:{}",item))
                .runOn(Schedulers.boundedElastic())
                .doOnNext(item -> log.info("runOn doOnNext:{}",item))
                .map(num -> num + random.nextInt(100))
                .doOnNext(item -> log.info("map doOnNext:{}",item))
                .filter(num -> num % 2 == 0)
                .doOnNext(item -> log.info("filter doOnNext:{}",item))
                .subscribe(
                        item -> log.info("OnNext :{}",item),
                        ex -> log.error("Error :{}",ex),
                        () -> log.info("complete")
                );

        Thread.sleep(1000);

    }
}
