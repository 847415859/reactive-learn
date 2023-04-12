package com.tk.webflux.handler;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

/**
 * @Description:
 * @Date : 2023/04/12 15:58
 * @Auther : tiankun
 */
public class ServerRedirectHandler implements HandlerFunction<ServerResponse> {

    private WebClient webClient = WebClient.create();

    @Override
    public Mono<ServerResponse> handle(ServerRequest request) {
        return webClient
                .method(request.method())
                .uri(request.headers()
                        .header("Redirect-Traffic")
                        .get(0))
                .headers((h) -> h.addAll(request.headers().asHttpHeaders()))
                .body(BodyInserters.fromDataBuffers(
                        request.bodyToFlux(DataBuffer.class)
                ))
                .cookies(c -> request
                        .cookies()
                        .forEach((key, list) -> list.forEach(cookie -> c.add(key,
                                cookie.getValue())))
                )
                .exchange()
                .flatMap(cr -> ServerResponse
                        .status(cr.statusCode())
                        .cookies(c -> c.addAll(cr.cookies()))
                        .headers(hh -> hh.addAll(cr.headers().asHttpHeaders()))
                        .body(BodyInserters.fromDataBuffers(
                                cr.bodyToFlux(DataBuffer.class)
                        ))
                );

    }
}