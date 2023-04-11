package com.tk.reactor.simple.opr;

import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * @Description:
 * @Date : 2023/04/11 9:01
 * @Auther : tiankun
 */
public class OprStreamTest {
    public static void main(String[] args) throws InterruptedException {
        Flux.interval(Duration.ofMillis(500))
                .map(item -> "fluxelement" + item)
                .skipUntilOther(Flux.just("start").delayElements(Duration.ofSeconds(3)))
                .takeUntilOther(Flux.just("stop").delayElements(Duration.ofSeconds(6)))
                .subscribe(System.out::println);
        Thread.sleep(10000);
    }

    private static void testTimeStamp() {
        Flux.range(1,10)
                .map(item -> "hello - "+ item)
                .timestamp()
                .subscribe(System.out::println);

        Flux.range(1,10)
                .map(item -> "hello - "+ item)
                .timestamp()
                .subscribe(item -> System.out.println(item.getT1() + "<--->"+ item.getT2() ));
    }

    private static void testIndex() {
        Flux.range(1,10)
                .map(item -> "hello - "+ item)
                .index()
                .subscribe(System.out::println);
    }

    private static void testMapCast() {
        Flux<String> flux = Flux.range(1, 10)
                .map(item -> item + "1")
                .cast(String.class);
        flux.subscribe(System.out::println);
    }
}
