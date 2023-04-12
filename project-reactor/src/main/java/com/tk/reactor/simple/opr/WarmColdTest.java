package com.tk.reactor.simple.opr;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.UUID;

/**
 * @Description:
 * @Date : 2023/04/12 9:05
 * @Auther : tiankun
 */
@Slf4j
public class WarmColdTest {
    public static void main(String[] args) throws InterruptedException {
        Flux<Integer> source = Flux.range(0, 5)
                .delayElements(Duration.ofMillis(200))
                .doOnSubscribe(
                        s -> System.out.println("冷发布者的新订阅票据")
                );
        Flux<Integer> cachedSource = source.share();
        cachedSource.subscribe(item -> System.out.println("[S 1] onNext: " + item));
        Thread.sleep(400);
        cachedSource.subscribe(item -> System.out.println("[S 2] onNext: " + item));
        Thread.sleep(1000);
        cachedSource.subscribe(item -> System.out.println("[S 3] onNext: " + item));
        Thread.sleep(10000);
    }

    private static void testCache() throws InterruptedException {
        Flux<Integer> source = Flux.range(0, 5)
                .doOnSubscribe(
                        s -> System.out.println("冷发布者的新订阅票据")
                );
        Flux<Integer> cache = source.cache(Duration.ofSeconds(1));
        cache.subscribe(item -> log.info("item1 :{}",item));
        cache.subscribe(item -> log.info("item2 :{}",item));
        Thread.sleep(1200);
        cache.subscribe(item -> log.info("item3 :{}",item));
    }

    private static void testConnectableFlux() {
        Flux<Integer> source = Flux.range(0, 3)
                .doOnSubscribe(
                        s -> System.out.println("对冷发布者的新订阅票据：" + s)
                );
        ConnectableFlux<Integer> connectableFlux = source.publish();
        connectableFlux.subscribe(item -> log.info("item1 :{}",item));
        connectableFlux.subscribe(item -> log.info("item2 :{}",item));
        connectableFlux.connect();
    }

    private static void test1() {
        Flux<String> coldPublisher = Flux.defer(() -> {
            System.out.println("生成新数据");
            return Flux.just(UUID.randomUUID().toString());
        });
        System.out.println("尚未生成新数据");
        coldPublisher.subscribe(e -> System.out.println("onNext: " + e));
        coldPublisher.subscribe(e -> System.out.println("onNext: " + e));
        System.out.println("为两个订阅者生成了两次数据");
    }
}
