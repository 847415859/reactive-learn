package com.tk.reactor.simple.opr;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.SynchronousSink;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/**
 * @Description: 以编程方式创建流
 * @Date : 2023/04/12 6:38
 * @Auther : tiankun
 */
@Slf4j
public class TestCreateStreamByProgram {
    public static void main(String[] args) throws InterruptedException {



        Thread.sleep(10000);
    }

    private static void testUsing() {
        Flux.using(
                Connection::newConnection,
                connection -> Flux.fromIterable(connection.getData()),
                Connection::close
        ).subscribe(
                data -> log.info("onNext接收到的数据：" + data),
                ex -> log.info("onError接收到的异常信息：" + ex),
                () -> log.info("处理完毕")
        );
    }


    static class Connection implements AutoCloseable {
        private final Random rnd = new Random();
        static Connection newConnection() {
            System.out.println("创建Connection对象");
            return new Connection();
        }
        public Iterable<String> getData() {
            if (rnd.nextInt(10) < 3) {
                throw new RuntimeException("通信异常");
            }
            return Arrays.asList("数据1", "数据2");
        }
        // close方法可以释放内部资源，并且应该始终被调用，即使在getData执行期间发生错误也是如此。
        @Override
        public void close() {
            System.out.println("关闭Connection连接");
        }
    }



    private static void testGenerate() {
        Flux.generate(() -> {
                            ArrayList<Long> longs = new ArrayList<>();
                            longs.add(0L);
                            longs.add(1L);
                            return longs;
                        },
                        // 负责生成斐波拉契数列
                        // 函数的第一个参数类型，函数第二个参数类型，函数计算结果类型
                        (BiFunction<ArrayList<Long>, SynchronousSink<Long>, ArrayList<Long>>) (longs, sink) -> {
                            final Long aLong = longs.get(longs.size() - 1);
                            final Long aLong1 = longs.get(longs.size() - 2);
                            // 向下游发射流元素
                            sink.next(aLong);
                            longs.add(aLong + aLong1);
                            return longs;
                        }
                )
                .take(10)
                .subscribe(item -> log.info("value:{}", item));
    }


    private static void testCreate() {
        MyEventProcessor eventProcessor = new MyEventProcessor();
        Flux.create(fluxSink -> eventProcessor.register(new MyEventListener<String>() {
            @Override
            public void onDataChunk(List<String> chunk) {
                chunk.forEach(fluxSink::next);
            }

            @Override
            public void processComplete() {
                fluxSink.complete();
            }
        })).subscribe(
                item -> log.info("next: {}", item),
                ex -> log.info("error： {}", ex),
                () -> log.info("completed")
        );
        eventProcessor.process();
    }


    static class MyEventProcessor {
        private MyEventListener listener;
        private Random random = new Random();

        void register(MyEventListener listener) {
            this.listener = listener;
        }

        public void process() {
            while (random.nextInt(10) % 3 != 0) {
                List<String> dataChunk = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    dataChunk.add("data-" + i);
                }
                listener.onDataChunk(dataChunk);
            }
            listener.processComplete();
        }
    }

    interface MyEventListener<T> {
        void onDataChunk(List<T> chunk);

        void processComplete();
    }


    /**
     * Push
     *
     * @throws InterruptedException
     */
    private static void testPush() throws InterruptedException {
        Flux.push(fluxSink -> IntStream.range(2000, 5).forEach(fluxSink::next))
                .delayElements(Duration.ofMillis(50))
                .subscribe(event -> log.info("onNext: :{}", event));
    }
}
