package com.tk.earlyspringimplement.simple.controller;

import com.tk.earlyspringimplement.simple.entity.Temperature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Description:
 * @Date : 2023/04/05 8:15
 * @Auther : tiankun
 */
@RestController
@CrossOrigin
@Slf4j
public class TemperatureController {

    private final Set<SseEmitter> clients = new CopyOnWriteArraySet<>();

    @GetMapping(value = "/temperature-stream")
    public SseEmitter events(HttpServletRequest request) {
        // ResponseBodyEmitter的子类，用于发送SSE（Server-Send Event）：服务器发送的事件
        // SseEmitter emitter = new SseEmitter();
        // 设置超时时间
        SseEmitter emitter = new SseEmitter(10000L);
        // 将当前发射器放到集合中
        clients.add(emitter);
        // 给当前发射器设置事件处理函数
        /*
            当异步请求超时的时候调用的代码。
            该方法在异步请求超时的时候由容器线程调用。
            */
        emitter.onTimeout(() -> clients.remove(emitter));
        /*
            当异步请求结束的时候调用的代码。
            当超时或网络错误而终止异步请求处理的时候，在容器线程调用该方法。
            该方法一般用于检车一个ResponseBodyEmitter实例已经无用了。
        */
        emitter.onCompletion(() -> clients.remove(emitter));
        return emitter;
    }

    @Async // 异步事件处理
    @EventListener // 事件监听器，该监听器只接收Temperature事件
    public void handleMessage(Temperature temperature) {
        log.info("监听到web的调度事件了 -- " + temperature);
        List<SseEmitter> deadEmitters = new ArrayList<>();
        // 遍历发射器集合
        clients.forEach(emitter -> {
            try {
                // 发射器发送温度对象，json类型
                emitter.send(temperature.getValue());
            } catch (Exception ignore) {
                // 如果抛异常，则将该发射器放到deadEmitters集合中
                deadEmitters.add(emitter);
            }
        });
        // 从clients中移除所有失效的发射器。
        clients.removeAll(deadEmitters);
    }
}
