package me.blackout.assist.eventBus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)    // Runs on instance
@Target(ElementType.METHOD)            // Target's Method
public @interface EventHandler {       // Called from @EventHandler
    int priority() default 0;          // Sorted via priority
}