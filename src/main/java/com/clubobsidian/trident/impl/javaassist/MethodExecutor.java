package com.clubobsidian.trident.impl.javaassist;

import java.lang.reflect.Method;

public class MethodExecutor {

	private Runnable runnable;
	public MethodExecutor(Method method)
	{
		
	}
	
	public void run()
	{
		this.runnable.run();
	}
}