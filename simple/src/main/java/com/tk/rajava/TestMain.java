package com.tk.rajava;


import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Subscriber;

/**
 * @Description:
 * @Date : 2023/04/05 9:42
 */
public class TestMain {
    public static void main(String[] args) {

    }

    /**
     * 通过组合其他 Observable 实例来创建 Observable流
     */
    private static void test4() {
        Observable.concat(
                Observable.just("hello"),
                Observable.from(new String[] {"lagou"}),
                Observable.just("!")
        ).forEach(
                msg -> System.out.println("OnNext接收到的消息："+msg),
                throwable -> System.out.println("OnError处理异常：" + throwable),
                () -> System.out.println("OnCompleted")
        );
    }

    private static void test3() {
        Observable.fromCallable(() -> "hello,lagou")
            .subscribe(
                msg -> System.out.println("OnNext接收到的消息："+msg),
                throwable -> System.out.println("OnError处理异常：" + throwable),
                () -> System.out.println("OnCompleted")
            );
    }

    private static void test2() {
        Observable.just("1","2","3","4","5")
                .subscribe(
                        msg -> System.out.println("OnNext接收到的消息："+msg),
                        throwable -> System.out.println("OnError处理异常：" + throwable),
                        () -> System.out.println("OnCompleted")
                );
    }

    private static void test1() {
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                for (int i = 0; i < 10; i++) {
                    subscriber.onNext("hello,world :" + i);
                }
                subscriber.onCompleted();
            }
        });
        observable.subscribe(
                msg -> System.out.println("OnNext接收到的消息："+msg),
                throwable -> System.out.println("OnError处理异常：" + throwable),
                () -> System.out.println("OnCompleted")
        );


        // 简写 + 链式编程
        Observable.create((subscriber) -> {
            for (int i = 0; i < 10; i++) {
                subscriber.onNext("hello,world2 :" + i);
            }
            subscriber.onCompleted();
        }).subscribe(
                msg -> System.out.println("OnNext接收到的消息："+msg),
                throwable -> System.out.println("OnError处理异常：" + throwable),
                () -> System.out.println("OnCompleted")
        );
    }
}
