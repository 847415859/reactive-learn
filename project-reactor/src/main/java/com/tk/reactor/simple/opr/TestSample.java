package com.tk.reactor.simple.opr;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.Random;

/**
 * @Description:
 * @Date : 2023/04/11 19:57
 * @Auther : tiankun
 */
@Slf4j
public class TestSample {

    private static final Random random = new Random();

    public static void main(String[] args) throws InterruptedException {
        // Flux.range(1,100)
        //         .delayElements(Duration.ofMillis(20))
        //         .sample(Duration.ofMillis(500))
        //         .subscribe(item -> log.info("item: {}",item));
        //
        // Flux.range(1,1000)
        //         .delayElements(Duration.ofMillis(20))
        //         .sampleTimeout(item -> Mono.delay(Duration.ofMillis(random.nextInt(100) + 20)),20)
        //         .subscribe(item -> log.info("item: {}",item));
        // Thread.sleep(10000);


        Random random = new Random();
        Flux.just(Arrays.asList(1, 2, 3), Arrays.asList("a", "b", "c", "d"),
                        Arrays.asList(7, 8, 9))
                .doOnNext(System.out::println)
                .concatMap(item -> Flux.fromIterable(item)
                        .delayElements(Duration.ofMillis(random.nextInt(100) + 100))
                        .doOnSubscribe(subscription -> {
                            System.out.println("已订阅");
                        }))
                .subscribe(System.out::println);
        Thread.sleep(3000);

    }
}
