package com.tk.earlyspringimplement.rxjava.controller;

import com.tk.earlyspringimplement.rxjava.source.TemperatureSensor1;
import com.tk.earlyspringimplement.rxjava.emit.RxSseEmitter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Date : 2023/04/05 15:04
 * @Auther : tiankun
 */
@RestController
@CrossOrigin
public class TemperatureController1 {

    private final TemperatureSensor1 temperatureSensor;
    public TemperatureController1(TemperatureSensor1 temperatureSensor) {
        this.temperatureSensor = temperatureSensor;
    }

    @GetMapping(value = "/emit/temperature-stream")
    public SseEmitter events(HttpServletRequest request) {
        RxSseEmitter emitter = new RxSseEmitter();
        temperatureSensor.temperatureObservable().subscribe(emitter.getSubscriber());
        return emitter;
    }

}
