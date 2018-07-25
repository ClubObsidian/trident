package com.clubobsidian.trident.impl.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.clubobsidian.trident.Event;
import com.clubobsidian.trident.Listener;
import com.clubobsidian.trident.MethodExecutor;

/**
 * {@inheritDoc}
 */
public class ReflectionMethodExecutor extends MethodExecutor {

	public ReflectionMethodExecutor(Listener listener, Method method) 
	{
		super(listener, method);
	}

	@Override
	public void execute(Event event) 
	{
		try 
		{
			this.getMethod().invoke(this.getListener(), event);
		} 
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
		{
			e.printStackTrace();
		}
	}
}