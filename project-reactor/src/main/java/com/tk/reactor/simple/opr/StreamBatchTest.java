package com.tk.reactor.simple.opr;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

/**
 * @Description:
 * @Date : 2023/04/11 18:51
 * @Auther : tiankun
 */
@Slf4j
public class StreamBatchTest {
    public static void main(String[] args) throws InterruptedException {
        // Flux.range(1,20)
        //         .buffer(5)
        //         .subscribe(list -> {
        //             log.info("list: {}",list);
        //         });
        //
        //
        // Flux.range(1,30)
        //         .window(5)
        //         .subscribe(windowFlux -> {
        //             windowFlux.collectList().subscribe(list -> {
        //                 log.info("window list: {}",list);
        //             });
        //         });

        Flux.range(1,40)
                .groupBy(item -> item % 2 == 0 ? "偶数": "奇数")
                .subscribe(groupedFlux -> {
                    groupedFlux.scan(new ArrayList<Integer>(),(list,element) -> {
                        // log.info("list: {} , element:{}",list,element);
                        list.add(element);
                        if(list.size() > 2){
                            list.remove(0);
                        }
                        return list;
                    }).subscribe(
                            item -> log.info("key:{}, item:{}",groupedFlux.key() ,item)
                    );
                });

        Thread.sleep(10000);
    }
}
