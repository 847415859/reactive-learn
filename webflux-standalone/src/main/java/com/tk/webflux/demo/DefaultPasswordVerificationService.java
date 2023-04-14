package com.tk.webflux.demo;

import com.tk.webflux.dto.PasswordDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.Mono;

import java.util.function.Predicate;

import static org.springframework.http.HttpStatus.EXPECTATION_FAILED;

/**
 * @Description:
 * @Date : 2023/04/12 17:52
 * @Auther : tiankun
 */
public class DefaultPasswordVerificationService implements PasswordVerificationService{

    final WebClient webClient;

    public DefaultPasswordVerificationService(WebClient.Builder
                                                      webClientBuilder) {
        webClient = webClientBuilder
                .baseUrl("http://localhost:8080").build();
    }


        @Override
    public Mono<Void> check(String raw, String encoded) {
            return WebClient.create("http://localhost:8080").post()
                    .uri("/password")
                    .body(BodyInserters.fromPublisher(
                            Mono.just(new PasswordDTO(raw, encoded)),
                            PasswordDTO.class
                    ))
                    .retrieve()
                    .toEntityFlux(ResponseEntity.class)
                    .flatMap(response -> {
                        if (response.getStatusCode().is2xxSuccessful()) {
                            return Mono.empty();
                        } else if (response.getStatusCode() ==
                                EXPECTATION_FAILED) {
                            return Mono.error(new BadCredentialsException("Invalid credentials"));
                        }
                        return Mono.error(new IllegalStateException());
                    });

        }


    public static void main(String[] args) {
        WebClient webClient = WebClient.builder().baseUrl("aa")
                .filter(new ExchangeFilterFunction() {
                    @Override
                    public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
                        return null;
                    }
                }).build();
    }
}
