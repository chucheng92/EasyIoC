package com.tinymood.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 哓哓
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Autowired {
	/**
	 * 
	 * @return 要注入的类类型
	 */
	Class<?> value() default Class.class;
	
	/**
	 * bean的名称
	 * @return
	 */
	String name() default "";
}
