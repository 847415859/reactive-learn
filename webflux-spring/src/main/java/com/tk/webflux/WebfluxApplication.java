package com.tk.webflux;

import com.tk.webflux.function.PasswordHandlerFunction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @Description:
 * @Date : 2023/04/12 15:54
 * @Auther : tiankun
 */
@SpringBootApplication
public class WebfluxApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebfluxApplication.class, args);
    }

    @Bean
    public static RouterFunction<ServerResponse>  routes(PasswordHandlerFunction handlerFunction) {
        return route(POST("/password"), handlerFunction);
    }
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {
        return serverHttpSecurity.csrf()
                .disable()
                .build();
    }


}
