package ro.fortech.application.bidstore.backend.util;

import javax.interceptor.InterceptorBinding;
import java.lang.annotation.*;

/**
 * Created by robert.ruja on 21-Apr-17.
 */
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface Loggable {
}
