package com.tk.earlyspringimplement.rxjava.source;

import com.tk.earlyspringimplement.rxjava.entity.Temperature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import rx.Observable;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Date : 2023/04/05 13:35
 * @Auther : tiankun
 */
@Component
@Slf4j
public class TemperatureSensor1 {

    private final Random random = new Random();
    private final Observable<Temperature> dataObservable  = Observable.range(0,Integer.MAX_VALUE)
            .concatMap(tick ->
                    Observable.just(tick)
                            .delay(random.nextInt(5000),TimeUnit.MILLISECONDS)
                            .map(tickKey -> this.probe())
                            .publish()
                            .refCount()
            );



    private Temperature probe() {
        return new Temperature(16 + random.nextGaussian() * 10);
    }

    public Observable<Temperature> temperatureObservable(){
        return dataObservable;
    }

    // public static void main(String[] args) {
    //     Observable<Temperature> dataObservable  = Observable.range(0,Integer.MAX_VALUE)
    //             .concatMap(tick -> {
    //                 log.info("执行:{}",tick);
    //                 Thread.sleep(200);
    //                 return Observable.just(new Temperature(1));
    //             }); ;
    //
    //     dataObservable.subscribe(new Consumer<Temperature>() {
    //         @Override
    //         public void accept(Temperature temperature) throws Throwable {
    //             log.info("accept:{}", temperature);
    //         }
    //     });
    //
    // }

}
