package com.tk.reactor.simple.opr;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Random;
import java.util.function.Supplier;

/**
 * @Description:
 * @Date : 2023/04/12 7:57
 * @Auther : tiankun
 */
@Slf4j
public class ErrorProcessTest {

    static Random random = new Random();


    public static void main(String[] args) {

        Flux.defer(new Supplier<Publisher<Integer>>() {
            @Override
            public Publisher<Integer> get() {
                return Flux.just(111);
            }
        }).doOnSubscribe(System.out::println);

        Flux.from(new Publisher<String>() {
            @Override
            public void subscribe(Subscriber<? super String> subscriber) {
                subscriber.onError(new RuntimeException("出错了"));
            }
        }).subscribe(System.out::println,System.err::println);
    }



    static Flux<String> recommendedBooks(String userId) {
        return Flux.defer(() -> {
            if (random.nextInt(10) < 7) {
                return Flux.<String>error(new RuntimeException("Err"))
                        // 整体向后推移指定时间，元素发射频率不变
                        .delaySequence(Duration.ofMillis(100));
            } else {
                return Flux.just("Blue Mars", "The Expanse").delayElements(Duration.ofMillis(50));
            }
        }).doOnSubscribe(
                item -> System.out.println("请求：" + userId)
        );
    }
}

