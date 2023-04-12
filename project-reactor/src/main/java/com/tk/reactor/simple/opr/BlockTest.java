package com.tk.reactor.simple.opr;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @Description:
 * @Date : 2023/04/11 22:14
 * @Auther : tiankun
 */
public class BlockTest {
    public static void main(String[] args) {
        final Iterable<Integer> integers = Flux.just(1, 2, 3)
                .delayElements(Duration.ofSeconds(1))
                .toIterable();
        System.out.println("==================");
        for (Integer integer : integers) {
            System.out.println(integer);
        }
        System.out.println("==================");
        

//         Stream<Integer> integerStream = Flux.just(1, 2, 3)
//                 .delayElements(Duration.ofSeconds(1))
//                 .toStream();
//         System.out.println("==================");
//         integerStream.forEach(System.out::println);
//         System.out.println("==================");
//         Integer integer = Flux.just(1, 2, 3)
//                 .delayElements(Duration.ofSeconds(1))
//                 .doOnNext(System.out::println)
//                 .blockFirst();
//         System.out.println("==============");
//         System.out.println(integer);
//         System.out.println("==============");
//         Thread.sleep(5000);
// // 该方法不会阻塞主线程
//         Flux.just(1, 2, 3)
//                 .delayElements(Duration.ofSeconds(1))
//                 .doOnEach(System.out::println)
//                 .subscribe();
// // 该方法阻塞，直到流处理到最后一个元素
//         Integer integer = Flux.just(1, 2, 3)
//                 .delayElements(Duration.ofSeconds(1))
//                 .doOnEach(System.out::println)
//                 .blockLast();
//         System.out.println("================");
//         System.out.println(integer);
//         System.out.println("================");
//         Flux<Integer> integerFlux = Flux.just(1, 2,
//                 3).delayElements(Duration.ofSeconds(1));
//         integerFlux.subscribe(item -> System.out.println("第一个订阅：" + item));
//         integerFlux.subscribe(item -> System.out.println("第二个订阅：" + item));
//         integerFlux.subscribe(item -> System.out.println("第三个订阅：" + item));
//         final Integer integer = integerFlux.blockLast();
//         System.out.println("阻塞等待最后一个元素" + integer);
//         System.out.println("-=-=-=-=-=-=-=-=-");
//         Thread.sleep(5000);

    }
}
