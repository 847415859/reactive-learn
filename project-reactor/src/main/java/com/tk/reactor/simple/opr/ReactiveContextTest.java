package com.tk.reactor.simple.opr;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.context.Context;
import reactor.util.context.ContextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @Description:
 * @Date : 2023/04/14 7:41
 * @Auther : tiankun
 */
@Slf4j
public class ReactiveContextTest {
    public static void main(String[] args) throws InterruptedException {
        Flux.range(1, 10)
                .flatMap(item -> Mono.subscriberContext()
                        .doOnNext(context -> {
                            double value = new Random(item).nextGaussian();
                            Map<Object, Object> map = context.get("randoms");
                            map.put(item,value);
                            log.info("存放的值为：{}", value);
                        }).thenReturn(item))
                .publishOn(Schedulers.parallel())
                .flatMap(
                        item -> Mono.subscriberContext()
                                .map(context -> {
                                    log.info("context:{}",context);
                                    Map<Object, Object> map = context.get("randoms");
                                    Object arg = map.get(item);
                                    log.info("获取到的数据:{}", arg);
                                    return arg;
                                })
                ).subscriberContext(context -> context.put("randoms", new HashMap<>()))
                        .blockLast();

            Thread.sleep(10000);
    }

    private static void testThreadLocal() {
        ThreadLocal<Map<Object,Object>> threadLocal = new ThreadLocal<>();
        threadLocal.set(new HashMap<>());

        Flux.range(1,10)
                .doOnNext(
                        item -> {
                            double value = new Random(item).nextGaussian();
                            log.info("存放的值为：{}",value);
                            threadLocal.get().put(item, value);
                        }
                )
                .publishOn(Schedulers.parallel())
                .map(item ->  {
                    log.info("current Thread");
                    Object arg = threadLocal.get().get(item);
                    log.info("获取的值：{}", arg);
                    return arg;
                })
                .blockLast();
    }

}
