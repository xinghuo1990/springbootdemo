package com.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.config.JCacheConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Configuration
@EnableCaching
public class RedisConfig {

//    @Value("${spring.redis.cluster.nodes}")
//    private String redisNodes;
    @Value("${spring.redis.max-wait}")
    private Integer maxWait;
    @Value("${spring.redis.jedis.pool.max-idle}")
    private Integer maxIdle;
    @Value("${spring.redis.jedis.pool.min-idle}")
    private Integer minIdle;
    @Value("${spring.redis.timeout}")
    private int timeout;



    /**
    * @describe: 通过配置Spring的CacheManager为redis，即可指定使用redis做缓存
    * @methodName: cacheManager
    * @param: [redisConnectionFactory]
    * @return: org.springframework.cache.CacheManager
    * @date: 2018/11/19
    **/
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory){
//        这样产生的cacheManager只是使用Spring提供的默认配置
        RedisCacheManager cacheManager = RedisCacheManager.create(redisConnectionFactory);
        return cacheManager;
    }

//    /**
//    * @describe: 自定义缓存配置  https://blog.csdn.net/sy793314598/article/details/80719224
//    * @methodName: cacheManager
//    * @param: [redisConnectionFactory]
//    * @return: org.springframework.cache.CacheManager
//    * @date: 2018/11/19
//    **/
//    @Bean
//    public CacheManager selfCacheManager(RedisConnectionFactory redisConnectionFactory){
//        // 生成一个默认配置，通过config对象即可对缓存进行自定义配置
//        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
//        // 设置缓存的默认过期时间，也是使用Duration设置
//        // 不缓存空值
//        config = config.entryTtl(Duration.ofMinutes(1)).disableCachingNullValues();
//        // 设置一个初始化的缓存空间set集合
//        Set<String> cacheNames =  new HashSet<>();
//        cacheNames.add("my-redis-cache1");
//        cacheNames.add("my-redis-cache2");
//        // 对每个缓存空间应用不同的配置
//        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
//        configMap.put("my-redis-cache1", config);
//        configMap.put("my-redis-cache2", config.entryTtl(Duration.ofSeconds(120)));
//        // 使用自定义的缓存配置初始化一个cacheManager
//        RedisCacheManager cacheManager = RedisCacheManager.builder(redisConnectionFactory)
//                // 注意这两句的调用顺序，一定要先调用该方法设置初始化的缓存名，再初始化相关的配置
//                .initialCacheNames(cacheNames)
//                .withInitialCacheConfigurations(configMap)
//                .build();
//        return cacheManager;
//    }


    @Bean
    public RedisTemplate getRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        // 设置序列化
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        // 配置redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        RedisSerializer<?> stringSerializer = new StringRedisSerializer();
        // key序列化
        redisTemplate.setKeySerializer(stringSerializer);
        // value序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        // Hash key序列化
        redisTemplate.setHashKeySerializer(stringSerializer);
        // Hash value序列化
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
