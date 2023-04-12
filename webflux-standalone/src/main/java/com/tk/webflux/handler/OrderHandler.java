package com.tk.webflux.handler;

import com.tk.webflux.entity.Order;
import com.tk.webflux.mapper.OrderMapper;
import com.tk.webflux.mapper.impl.OrderMapperImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * @Description:
 * @Date : 2023/04/12 16:05
 * @Auther : tiankun
 */
@Service
public class OrderHandler {


    public Mono<ServerResponse> create(ServerRequest request) {
        return request
                .bodyToMono(Order.class)
                .flatMap(OrderMapperImpl::save)
                .flatMap(o ->
                        ServerResponse.created(URI.create("/orders/" + o))
                                .build()
                );
    }
    public Mono<ServerResponse> get(ServerRequest request) {
        return OrderMapperImpl
                .findById(request.pathVariable("id"))
                .flatMap(order ->
                        ServerResponse
                                .ok()
                                .syncBody(order)
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }
    public Mono<ServerResponse> list(ServerRequest request) {
        return null;
    }

}
