package com.clubobsidian.trident;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Interface for event registration.
 * When methods are annotated in
 * listeners events are registered in
 * an {@code EventManager} instance.
 * 
 * @author virustotalop
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface EventHandler {

	EventPriority priority() default EventPriority.NORMAL;
	
}