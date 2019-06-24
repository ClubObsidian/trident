package com.clubobsidian.trident.test.impl;

import java.lang.reflect.Method;

import com.clubobsidian.trident.Event;
import com.clubobsidian.trident.MethodExecutor;

public class TestMethodExecutor extends MethodExecutor {

	public TestMethodExecutor(Object listener, Method method, boolean ignoreCanceled) 
	{
		super(listener, method, ignoreCanceled);
	}

	@Override
	public void execute(Event event) 
	{
		
	}
}