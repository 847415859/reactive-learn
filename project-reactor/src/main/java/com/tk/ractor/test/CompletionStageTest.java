package com.tk.ractor.test;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @Description: 针对于 JDK8  CompletionStage 接口的测试
 * @Date : 2023/04/05 18:56
 * @Auther : tiankun
 */
@Slf4j
public class CompletionStageTest {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        testSeq();

    }

    /**
     * 测试
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void testSeq() throws InterruptedException, ExecutionException {
        // 串行关系
        CompletableFuture completableFuture = new CompletableFuture();
        completableFuture.thenApply(item -> {
            log.info("处理了我");
            return item;
        });
        completableFuture.thenAccept(o -> log.info("accept:{}",o));
        completableFuture.thenApplyAsync(item -> {
            log.info("异步处理了我：{}",item);
            int i = 1/0;
            return item + "1";
        });
        completableFuture.thenRun(() -> {log.info("run");});
        completableFuture.thenCompose(item -> {
            log.info("compose:{}",item);
            int i = 1 / 0;
            return item + "2";
        });
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            completableFuture.complete("执行后的结果");
        }).start();

        Object o = completableFuture.get();
        System.out.println(o);
    }
}
