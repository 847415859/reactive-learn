package com.tk.reactor.simple;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * @Description:
 * @Date : 2023/04/11 7:24
 * @Auther : tiankun
 */
@Slf4j
public class FluxTest {
    public static void main(String[] args) {
        testMono();
    }

    static boolean isValidSeed(String seed) {
        System.out.println("调用了isValidSeed方法");
        return true;
    }

    static String getData(String seed) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "echo : " + seed;
    }

    static Mono<String> requestData(String seed) {
        return isValidSeed(seed) ? Mono.fromCallable(() -> getData(seed))
                : Mono.error(new RuntimeException("Invalid seed value"));
    }

    static Mono<String> requestDeferData(String seed) {
        return Mono.defer(
                () -> isValidSeed(seed) ?
                        Mono.fromCallable(() -> getData(seed))
                        : Mono.error(new RuntimeException("Invalid seed value"))
        );
    }


    private static void testMonoHttpRequest() {
        // Mono.fromCallable(() -> httpRequest()).subscribe(System.out::println);
        Mono.fromCallable(FluxTest::httpRequest).subscribe(
                System.out::println,
                ex -> System.out.println("异常信息：" + ex),
                () -> System.out.println("执行完成了")
        );
    }


    public static String httpRequest() throws IOException,
            InterruptedException {
        URL url = new URL("https://edu.lagou.com");
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();
        InputStream inputStream = urlConnection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String tmp = null;
        StringBuffer stringBuffer = new StringBuffer();
        while ((tmp = reader.readLine()) != null) {
            stringBuffer.append(tmp).append("\r\n");
        }
        return stringBuffer.toString();
    }


    private static void testMono() {
        Mono<String> mono = Mono.just("hello");
        mono.subscribe(System.out::println);

        Mono<Object> objectMono = Mono.justOrEmpty(null);
        objectMono.subscribe(System.out::println);

        Mono<Object> objectMono1 = Mono.justOrEmpty(Optional.empty());
        objectMono1.subscribe(System.out::println);

        Mono.fromSupplier((Supplier<Object>) () -> getData("aaaa")).subscribe(System.out::println);
    }

    private static void testFlux() {
        Flux<String> flux = Flux.just("hello", "world");
        flux.subscribe(System.out::println);

        Flux.fromArray(new String[]{"hello1", "world1"})
                .subscribe(System.out::println);

        Flux.fromIterable(Arrays.asList("hello2", "world2"))
                .subscribe(System.out::println);

        // 参数1： 起点
        // 参数2： 序列中元素的数量
        Flux.range(100, 5)
                .subscribe(System.out::println);
    }
}
