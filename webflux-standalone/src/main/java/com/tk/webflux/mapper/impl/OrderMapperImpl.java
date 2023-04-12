package com.tk.webflux.mapper.impl;

import com.tk.webflux.entity.Order;
import com.tk.webflux.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.Mapping;
import reactor.core.publisher.Mono;

/**
 * @Description:
 * @Date : 2023/04/12 16:08
 * @Auther : tiankun
 */
@Service
@Slf4j
public class OrderMapperImpl  {


    public static Mono<Order> findById(String id) {
        log.info("findById");
        return Mono.just(new Order("1","田坤"));
    }

    public static Mono<Order> save(Order order) {
        return Mono.just(order);
    }

    public Mono<Void> deleteById(String id) {

        return Mono.empty();
    }
}
