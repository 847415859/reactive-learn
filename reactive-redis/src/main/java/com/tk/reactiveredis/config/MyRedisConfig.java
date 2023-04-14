package com.tk.reactiveredis.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Description:
 * @Date : 2023/04/14 19:27
 * @Auther : tiankun
 */
@Configuration
@EnableCaching
public class MyRedisConfig extends CachingConfigurerSupport {
    private static final StringRedisSerializer STRING_SERIALIZER = new StringRedisSerializer();
    private static final GenericJackson2JsonRedisSerializer JACKSON_SERIALIZER = new GenericJackson2JsonRedisSerializer();
    @Bean
    public RedisTemplate<String, Object>
    redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new
                RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(STRING_SERIALIZER);
        redisTemplate.setValueSerializer(JACKSON_SERIALIZER);
        return redisTemplate;
    }

    @Bean
    public ReactiveRedisTemplate<String, Object> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        RedisSerializer serializer = new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer valueSerializer = new GenericJackson2JsonRedisSerializer();
        RedisSerializationContext<String, Object> context = RedisSerializationContext.newSerializationContext()
                        .key(serializer)
                        .value(valueSerializer)
                        .hashKey(serializer)
                        .hashValue(valueSerializer).build();
        ReactiveRedisTemplate<String, Object> reactiveRedisTemplate = new ReactiveRedisTemplate<String, Object>(factory, context);
        return reactiveRedisTemplate;
    }


}
