package com.mjc.school.repository.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Determines behaviour on author deletion.
 * Possible values:
 * <b>set null</b> - set authorId field for corresponding news to null,
 * <b>remove</b> - remove corresponding news
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnDelete {
	String value() default "";
}