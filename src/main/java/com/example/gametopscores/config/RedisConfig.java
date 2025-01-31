package com.example.gametopscores.config;

import com.example.gametopscores.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, User> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, User> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // Set key serializer
        template.setKeySerializer(new StringRedisSerializer());

        // Set value serializer
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(User.class));

        return template;
    }
}
