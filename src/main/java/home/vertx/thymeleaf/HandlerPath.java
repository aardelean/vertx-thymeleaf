package home.vertx.thymeleaf;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.TYPE})
@Service
public @interface HandlerPath {
    String path();
    String template() default "";
}
