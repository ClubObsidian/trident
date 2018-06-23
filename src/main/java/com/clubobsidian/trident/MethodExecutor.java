package com.clubobsidian.trident;

import java.lang.reflect.Method;

public abstract class MethodExecutor {

	private Listener listener;
	private Method method;
	public MethodExecutor(Listener listener, Method method)
	{
		this.listener = listener;
		this.method = method;
	}

	public Listener getListener()
	{
		return this.listener;
	}
	
	public Method getMethod()
	{
		return this.method;
	}
	
	public abstract void execute(Event event);
}