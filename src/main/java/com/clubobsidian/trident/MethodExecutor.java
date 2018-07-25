package com.clubobsidian.trident;

import java.lang.reflect.Method;

/**
 * Abstract class for MethodExecutor implementations
 * to extend. {@link MethodExecutor#execute(Event)}
 * to be overridden in implementation classes.
 * 
 * @author virustotalop
 */
public abstract class MethodExecutor {

	private Listener listener;
	private Method method;
	public MethodExecutor(Listener listener, Method method)
	{
		this.listener = listener;
		this.method = method;
	}

	/** 
	 * @return listener to execute events on
	 */
	public Listener getListener()
	{
		return this.listener;
	}
	
	/** 
	 * @return method to execute
	 */
	public Method getMethod()
	{
		return this.method;
	}
	
	/**
	 * @param event that is executed on the class listener
	 */
	public abstract void execute(Event event);
}