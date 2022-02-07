package my.project.security.annotation;

import java.lang.annotation.*;

/**
 * 自定義註解，有該註解的暫存方法會抛出異常
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheException {
}
