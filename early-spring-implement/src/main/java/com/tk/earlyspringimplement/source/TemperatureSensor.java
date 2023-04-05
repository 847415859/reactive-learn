package com.tk.earlyspringimplement.source;

import com.tk.earlyspringimplement.entity.Temperature;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Description:  事件触发源
 * @Date : 2023/04/05 8:04
 * @Auther : tiankun
 */
@Component
public class TemperatureSensor implements ApplicationContextAware,Runnable {

    private final int thread = 5;

    private ApplicationContext applicationContext;

    private final ExecutorService executorService = Executors.newFixedThreadPool(thread);

    private final Random random = new Random();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        for (int i = 0; i < thread; i++) {
            executorService.execute(this);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(random.nextInt(1000));
                applicationContext.publishEvent(new Temperature(random.nextDouble()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
