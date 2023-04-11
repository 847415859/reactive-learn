package com.tk.reactor.simple.opr;

import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @Description:
 * @Date : 2023/04/11 13:11
 * @Auther : tiankun
 */
public class CollectionTest {
    public static void main(String[] args) {
        // Flux.just(1, 6, 2, 8, 3, 1, 5, 1)
        //         .collectMap(
        //                 item -> "key:"+item
        //         ).subscribe(System.out::println);

        // Flux.just(1, 2, 3)
        //         .repeat(3)
        //         .distinctUntilChanged()
        //         .subscribe(System.out::println);
        // System.out.println("==========");
        // Flux.just(1, 1, 2, 2, 3, 3)
        //         .distinctUntilChanged()
        //         .subscribe(System.out::println);

        int arrlength = 5;
        Flux.range(1,100)
                .index()
                .scan(new int[arrlength], (arr,entry) -> {
                    System.out.println("T1:"+entry.getT1());;
                    System.out.println("T2:"+entry.getT2());;
                    arr[(int) (entry.getT1() % arrlength)] = entry.getT2();
                    return arr;
                })
                .skip(arrlength)
                .subscribe(arr -> System.out.println(Arrays.toString(arr)));
    }

    private static void testSample() {
        Flux.just(1, 6, 2, 8, 3, 1, 5, 1)
                .collectSortedList(Comparator.reverseOrder())
                .subscribe(System.out::println);
    }
}
