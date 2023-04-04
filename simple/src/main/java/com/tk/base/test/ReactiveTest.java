package com.tk.base.test;

import com.tk.base.publisher.AsyncIterablePublisher;
import com.tk.base.subsriber.AsyncSubscriber;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description:
 * @Date : 2023/04/04 19:50
 * @Auther : tiankun
 */
public class ReactiveTest  {
    public static void main(String[] args) {
        Set<Integer> elements = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            elements.add(i);
        }
        final ExecutorService executorService = Executors.newFixedThreadPool(5);
        AsyncIterablePublisher<Integer> publisher = new AsyncIterablePublisher<>(elements, executorService);
        final AsyncSubscriber<Integer> subscriber = new AsyncSubscriber<>
                (Executors.newFixedThreadPool(2)) {
            @Override
            protected boolean whenNext(Integer element) {
                System.out.println(Thread.currentThread().getId() + "接收到的流元素：" + element);
                return true;
            }
        };
        publisher.subscribe(subscriber);
    }
}
