package com.carlinx.shiro.utils.cache;

import cn.hutool.core.collection.CollUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
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


    //*****************************通用*********************************

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
     * 前缀通配key删除
     * @param prefix
     * @return
     */
    public Boolean deletePrefix(String prefix){
        try {
            String key = prefix+"*";
            Set<String> keys = redisTemplate.keys(key);
            if(keys.size() > 0){
                redisTemplate.delete(keys);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }




    //*****************************String类型*********************************

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



    //*****************************Hash类型*********************************


    /**
     * 加hash缓存
     * @param key
     * @param hashKey
     * @param hashValue
     * @return
     */
    public Boolean setHash(String key,String hashKey,String hashValue){
        try {
            redisTemplate.opsForHash().put(key,hashKey,hashValue);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 判断hashKey是否存在
     * @param key
     * @param hashKey
     * @return
     */
    public Boolean hasHashKey(String key,String hashKey){
        try {
            Boolean result = redisTemplate.opsForHash().hasKey(key, hashKey);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 根据key获取所有hashkey   hashvalue
     * @param key
     * @return
     */
    public Map<Object,Object> getAllHashByKey(String key){
        try {
            Map<Object, Object> result = redisTemplate.opsForHash().entries(key);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取hash
     * @param key
     * @param hashKey
     * @return
     */
    public Object getHash(String key,String hashKey){
        try {
            Object result = redisTemplate.opsForHash().get(key, hashKey);
            return result;
        }catch (Exception e){
            return null;
        }
    }











}
