package com.tk.webflux.mapper;

import com.tk.webflux.entity.Order;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @Description:
 * @Date : 2023/04/12 16:07
 * @Auther : tiankun
 */
@Service
public interface OrderMapper {
    Mono<Order> findById(String id);

    Mono<Order> save(Order order);

    Mono<Void> deleteById(String id);
}
