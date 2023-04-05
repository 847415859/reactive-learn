package com.tk.earlyspringimplement.simple.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;

import java.lang.reflect.Method;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Date : 2023/04/05 8:11
 * @Auther : tiankun
 */
@Configuration
@Slf4j
public class AsyncConfg  implements AsyncConfigurer {
    // 异步调用的线程池
    @Override
    public Executor getAsyncExecutor() {
        return new ThreadPoolExecutor(5,10,1, TimeUnit.MINUTES,new ArrayBlockingQueue<>(100));
    }

    // 事务处理事件
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> log.info("异步任务处理失败了...Exception:{} ,method :{}, params：{}",ex,method,params);
    }
}
