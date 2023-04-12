package com.tk.reactor.simple.opr;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

/**
 * @Description:
 * @Date : 2023/04/12 10:00
 * @Auther : tiankun
 */
@Slf4j
public class DateHandlerTest {
    public static void main(String[] args) throws InterruptedException {
        Flux.range(1,100)
                .delayElements(Duration.ofSeconds(1))
                .subscribe(item -> log.info("item :{}",item));


        Thread.sleep(20000);
    }

    private static void testInterval() throws InterruptedException {
        Flux.interval(Duration.ofMillis(100))
                .subscribe(item -> log.info("item -> {}",item));

        Flux.interval(Duration.ofSeconds(3),Duration.ofMillis(100))
                .subscribe(item -> log.info("item -> {}",item));

        Flux.interval(Duration.ofSeconds(3),Duration.ofMillis(100), Schedulers.parallel())
                .subscribe(item -> log.info("item -> {}",item));

        Flux.interval(Duration.ofSeconds(3),Duration.ofMillis(100), Schedulers.newSingle("tk"))
                .subscribe(item -> log.info("item -> {}",item));
    }
}
