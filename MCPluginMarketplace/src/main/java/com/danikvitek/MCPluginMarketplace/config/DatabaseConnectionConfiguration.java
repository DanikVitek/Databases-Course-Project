package com.danikvitek.MCPluginMarketplace.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.annotation.Resource;
import java.util.Objects;

@Configuration
public class DatabaseConnectionConfiguration {
    @Resource
    private Environment environment;

    @Bean
    public StringRedisTemplate redisTemplate() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.afterPropertiesSet();
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(jedisConnectionFactory);
        return stringRedisTemplate;
    }

    @Bean
    public DriverManagerDataSource dataSource() {
        String username = redisTemplate().opsForValue()
                .get(Objects.requireNonNull(environment.getProperty("spring.datasource.username")));
        String password = redisTemplate().opsForValue()
                .get(Objects.requireNonNull(environment.getProperty("spring.datasource.password")));
        String driverClassName = Objects.requireNonNull(environment.getProperty("spring.datasource.driver-class-name"));
        String url = Objects.requireNonNull(environment.getProperty("spring.datasource.url"));

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }
}
