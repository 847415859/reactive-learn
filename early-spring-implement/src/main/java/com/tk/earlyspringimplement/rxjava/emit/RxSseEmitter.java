package com.tk.earlyspringimplement.rxjava.emit;

import com.tk.earlyspringimplement.rxjava.entity.Temperature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import rx.Subscriber;

import java.io.IOException;

/**
 * @Description:
 * @Date : 2023/04/05 14:52
 * @Auther : tiankun
 */
@Slf4j
public class RxSseEmitter extends SseEmitter {

    static final long SSE_SESSION_TIMEOUT = 30 * 60 * 1000L;

    private final Subscriber<Temperature> subscriber;

    public RxSseEmitter() {
        super(SSE_SESSION_TIMEOUT);
        this.subscriber = new Subscriber<Temperature>() {
            @Override
            public void onNext(Temperature temperature) {
                try {
                    log.info("onNext:{}",temperature);
                    RxSseEmitter.this.send(temperature);
                } catch (IOException e) {
                    subscriber.unsubscribe();
                }
            }
            @Override
            public void onError(Throwable e) {
                System.err.println(e);
            }
            @Override
            public void onCompleted() {
                System.out.println("job done");
            }
        };
        onCompletion(subscriber::unsubscribe);
        onTimeout(subscriber::unsubscribe);
    }

    public Subscriber<Temperature> getSubscriber() {
        return subscriber;
    }
}
