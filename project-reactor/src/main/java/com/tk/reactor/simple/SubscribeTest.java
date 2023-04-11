package com.tk.reactor.simple;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.Disposable;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.util.context.Context;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @Description:
 * @Date : 2023/04/11 8:10
 * @Auther : tiankun
 */
@Slf4j
public class SubscribeTest {
    public static void main(String[] args) throws InterruptedException {
        Flux.just("hello","world")
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        log.info("订阅成功，开始请求第一个元素");
                        request(1);
                    }

                    @Override
                    protected void hookOnNext(String value) {
                        log.info("onNext: {}", value);
                        log.info("再次请求一个元素");
                        request(1);
                    }
                });
    }

    private static void testCustomSubscriber() {
        Flux.just("hello","world")
                .subscribe(new Subscriber<String>() {

                    private Subscription subscription;

                    @Override
                    public void onSubscribe(Subscription subscription) {
                        log.info("initial request for 1 element");
                        this.subscription = subscription;
                        subscription.request(1);
                    }

                    @Override
                    public void onNext(String s) {
                        log.info("onNext: {}",s);
                        log.info("requesting 1 more element");
                        subscription.request(1);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        log.error("error: {}",throwable);
                    }

                    @Override
                    public void onComplete() {
                        log.info("complete");
                    }
                });
    }

    private static void dispose() throws InterruptedException {
        Disposable disposable = Flux.interval(Duration.ofMillis(100))
                .subscribe(data -> System.out.println("OnNext: " + data));

        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            disposable.dispose();
        }).start();

        Thread.sleep(2000);
    }

    private static void testManualSubscribeHandle() {
        Flux.range(1,100)
                .subscribe(
                        item -> System.out.println("OnNext: " + item),
                        ex -> System.out.println("异常情况：" + ex),
                        () -> System.out.println("处理结束"),
                        subscription -> {
                            // 订阅响应式流5个元素
                            subscription.request(5);
                            // 取消订阅
                            subscription.cancel();
                        }
                );
    }

    private static void testSubscribeSuccessHandle() {
        Flux.range(1,100)
                .subscribe(
                        item -> System.out.println("OnNext: "+item),
                        ex -> System.out.println("异常情况：" + ex),
                        () -> System.out.println("处理结束"),
                        subscription -> subscription.cancel()   // 订阅成功就取消订阅
                );
    }

    private static void testErrorHandle() {
        Flux.from(subscriber -> {
            for (int i = 0; i < 5; i++) {
                subscriber.onNext(i);
            }
            subscriber.onError(new Exception("异常测试"));
        }).subscribe(
                item -> System.out.println("OnNext: "+item),
                ex -> System.out.println("异常情况：" + ex)
        );
    }

    private static void testAddSideEffect() {
        Flux.range(1,100)
                .filter(item -> item % 7 == 0)
                .map(item -> "hello -"+item)
                .doOnNext(System.out::println)  // 添加副作用
                .subscribe();
    }
}
