package com.clubobsidian.trident.test.impl;

import java.lang.reflect.Method;

import com.clubobsidian.trident.EventBus;
import com.clubobsidian.trident.Listener;
import com.clubobsidian.trident.MethodExecutor;

public class TestEventBus extends EventBus {

	@Override
	protected MethodExecutor createMethodExecutor(Listener listener, Method method, boolean ignoreCanceled) 
	{
		return null;
	}
}