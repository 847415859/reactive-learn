package com.tk.rsocket;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.ByteBufPayload;
import io.rsocket.util.DefaultPayload;

/**
 * @Description:
 * @Date : 2023/04/14 18:28
 * @Auther : tiankun
 */
public class RsocketClient {
    public static void main(String[] args) {
        // RSocket socket = RSocketFactory.connect()
        //         .transport(TcpClientTransport.create("localhost", 7000))
        //         .start()
        //         .block();
        //
        //
        // socket.requestResponse(DefaultPayload.create("World"))
        //         .map(Payload::getDataUtf8)
        //         .doOnNext(System.out::println)
        //         .doFinally(signalType -> socket.dispose())
        //         .then()
        //         .block();
    }
}
