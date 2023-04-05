package com.tk.rajava;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.Time;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Date : 2023/04/05 11:21
 * @Auther : tiankun
 */
public class TestMain1 {
    public static void main(String[] args) throws InterruptedException, IOException {
        Observable.fromCallable(() -> {
            System.out.println("做一些同步阻塞的事儿");
            Thread.sleep(2000);
            return "aa";
        })
        .subscribeOn(Schedulers.newThread())
        .subscribe(s -> System.out.println("获取到的数据为:"+s));

        System.in.read();
    }

    private static void testZip() {
        Observable.zip(
                Observable.just(1,2,3,4,5),
                Observable.just("a","b","c","d","e"),
                (i1,i2) ->  i1 + i2
        ).forEach(System.out::println);
        System.out.println("元素不匹配");
        Observable.zip(
                Observable.just(1,2,3,4,5),
                Observable.just("a","b","c","d"),
                (i1,i2) ->  i1 + i2
        ).forEach(System.out::println);
    }

    private static void testMap() {
        Observable.fromIterable(Arrays.asList(1,2,3,4,5,6))
                .map(item -> {
                    System.out.println("当前处理的数据为："+item);
                    return item + 1;
                })
                .forEach(System.out::println);
    }

    private static void test2() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Disposable subscribe = Observable.interval(100, TimeUnit.MILLISECONDS)
                .subscribe(System.out::println);
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                if(subscribe.isDisposed()) {
                    subscribe.dispose();
                }
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        System.out.println("=============");
        // 主线程等待
        countDownLatch.await();
        System.out.println("-------------");
    }

    private static void test1() throws InterruptedException {
        Observable.interval(100, TimeUnit.MILLISECONDS)
                .subscribe(System.out::println);
        Thread.sleep(5000);
    }
}
