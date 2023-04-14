package com.tk.reactiveredis.controller;

import com.tk.reactiveredis.entity.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * @Description:
 * @Date : 2023/04/14 19:29
 * @Auther : tiankun
 */
@RestController
@RequestMapping("redis")
public class RediscReactiveController {

    @Autowired
    private ReactiveRedisTemplate reactiveRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping(value = "/{id}")
    public Mono<City> findCityById(@PathVariable("id") Long id) {
        String key = "city_" + id;
        ReactiveValueOperations<String, City> operations =
                reactiveRedisTemplate.opsForValue();
        Mono<City> city = operations.get(key);
        return city;
    }

    @PostMapping
    public Mono<City> saveCity(@RequestBody City city) {
        return reactiveRedisTemplate.opsForValue()
                .getAndSet("city_" + city.getId(), city);
    }

    @DeleteMapping(value = "/{id}")
    public Mono<Long> deleteCity(@PathVariable("id") Long id) {
        String key = "city_" + id;
        return reactiveRedisTemplate.delete(key);
    }

}
