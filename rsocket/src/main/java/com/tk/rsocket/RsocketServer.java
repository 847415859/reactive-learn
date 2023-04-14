package com.tk.rsocket;

import io.rsocket.Closeable;
import io.rsocket.Payload;
import io.rsocket.core.RSocketServer;
import io.rsocket.transport.ServerTransport;
import io.rsocket.transport.netty.server.TcpServerTransport;
import io.rsocket.util.DefaultPayload;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.Duration;

/**
 * @Description:
 * @Date : 2023/04/14 12:33
 * @Auther : tiankun
 */
public class RsocketServer {
    public static void main(String[] args) throws IOException {
        // RSocketFactory.receive()
        //         .acceptor(((setup, sendingSocket) -> Mono.just(
        //                 new AbstractRSocket() {
        //                     @Override
        //                     public Mono<Payload> requestResponse(Payload payload) {
        //                         return Mono.just(DefaultPayload.create("Hello: " + payload.getDataUtf8()));
        //                     }
        //                 }
        //         )))
        //         .transport(TcpServerTransport.create("localhost", 7000))
        //         .start()
        //         .log()
        //         .subscribe(
        //                 item -> System.out.println("onNext"+ item),
        //                 ex -> System.out.println("出错了"),
        //                 () -> System.out.println("完成了")
        //         );

        System.in.read();
    }
}
