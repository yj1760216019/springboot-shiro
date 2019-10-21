package com.carlinx.shiro.utils.cache;

import cn.hutool.core.collection.CollUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisOperator {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }


    /**
     * 指定缓存失效时间
     * @param key
     * @param time
     * @return
     */
    public boolean expire(String key,Long time){
        try {
            if(time >0 ){
                redisTemplate.expire(key,time, TimeUnit.SECONDS);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 根据key获取过期时间  0代表永久有效
     * @param key
     * @return
     */
    public Long getExpire(String key){
        return redisTemplate.getExpire(key,TimeUnit.SECONDS);
    }


    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    public Boolean hasKey(String key){
        try {
            return redisTemplate.hasKey(key);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 删除缓存
     * @param keys
     */
    @SuppressWarnings("unchecked")
    public void delete(String ... keys){
        if(keys != null && keys.length >0){
            if(keys.length == 1){
                redisTemplate.delete(keys[0]);
            }else{
                redisTemplate.delete(CollUtil.toList(keys));
            }
        }
    }


    /**
     * 普通缓存获取
     * @param key
     * @return
     */
    public Object get(String key){
        return key == null ? null:redisTemplate.opsForValue().get(key);
    }


    /**
     * 加普通缓存
     * @param key
     * @param value
     * @return
     */
    public Boolean set(String key,Object value){
        try {
            redisTemplate.opsForValue().set(key,value);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 加普通缓存并设置缓存时间
     * @param key
     * @param value
     * @param expireTime
     * @return
     */
    public  Boolean set(String key,String value,Long expireTime){
        try {
            redisTemplate.opsForValue().set(key,value,expireTime,TimeUnit.SECONDS);
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
