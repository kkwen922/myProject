package my.project.config;

import my.project.common.config.BaseRedisConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * Redis配置類
 */
@EnableCaching
@Configuration
public class RedisConfig extends BaseRedisConfig {

}
