package com.tk.reactor.simple;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * @Description:
 * @Date : 2023/04/05 18:27
 * @Auther : tiankun
 */
public class Test {
    public static void main(String[] args) {
        // CompletableFuture<String> completableFuture = new CompletableFuture<>();
        // completableFuture.
        testMono();
    }

    private static void testMono() {
        Mono<List<Integer>> mono = Flux.range(1, 100)
                .repeat(5)
                .collectList();
        mono.doOnNext(new Consumer<List<Integer>>() {
            @Override
            public void accept(List<Integer> integers) {
                System.out.println("接受到的数据：" + integers.toString());
            }
        }).subscribe(new Consumer<List<Integer>>() {
            @Override
            public void accept(List<Integer> integers) {
                System.out.println("接受到的数据2：" + integers.toString());
            }
        }).dispose();
    }

    private static void testFlux() {
        // range操作符创建从[1到100]的整数序列
        Flux.range(1, 100)
            // repeat操作符在源流完成之后一次又一次地订阅源响应式流。
            // repeat操作符订阅流操作符的结果、接收从1到100的元素以及onComplete信号，
            // 然后再次订阅、接收，不断重复该过程
            .repeat()
            // 使用collectList操作符尝试将所有生成的元素收集到一个集合中。
            // 由于是无限流，最终它会消耗所有内存，导致OOM。
            .collectList()
            // block操作符触发实际订阅并阻塞正在运行的线程，直到最终结果到达
            // 当前场景不会发生，因为响应流是无限的。;
            .block();
    }
}
