package carrot.demo.sso.jedis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
public class JedisClient {

    @Value("${redis.server.host}")
    private String host;

    @Value("${redis.server.port}")
    private int port;

    @Bean
    public JedisPoolConfig getRedisConfig() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        return poolConfig;
    }

    @Bean
    public JedisPool getJedisPool() {
        JedisPoolConfig config = getRedisConfig();
        JedisPool pool = new JedisPool(config,host,port,2000);
        return pool;
    }
}
