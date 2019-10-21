package com.carlinx.shiro.config.redis;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.util.Arrays;

@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {


    public static final int NO_PARAM_KEY = 0;
    public static final int NULL_PARAM_KEY = 53;


    /**
     * 缓存秘钥生成器
     * @return
     */
    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder key = new StringBuilder();
                Cacheable cacheable = method.getAnnotation(Cacheable.class);
                if (null!=cacheable && StrUtil.isNotBlank(cacheable.value().toString())){
                    key.append(cacheable.value());
                }
                key.append(cacheable.value()[0] + "::");
                if (params.length == 0) {
                    key.append(NO_PARAM_KEY);
                } else {
                    if (params.length == 1) {
                        Object param = params[0];
                        if (param == null) {
                            key.append(NULL_PARAM_KEY);
                        } else if (!param.getClass().isArray()) {
                            key.append(param);
                        }
                    } else {
                        key.append(Arrays.deepHashCode(params));
                    }
                }
                return key.toString();
            }
        };
    }


    @Bean
    @SuppressWarnings("all")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //jackson2JsonRedisSerializer 替换默认序列化方式
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        //设置redis  key value的序列化方式
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        //key的序列化方式未String
        redisTemplate.setKeySerializer(stringRedisSerializer);
        //设置hash的key序列化方式为String
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        //设置value的序列化方式为jackson2JsonRedisSerializer
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }







}
