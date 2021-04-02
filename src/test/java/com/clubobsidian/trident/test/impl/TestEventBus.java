package com.clubobsidian.trident.test.impl;

import com.clubobsidian.trident.EventBus;
import com.clubobsidian.trident.MethodExecutor;

import java.lang.reflect.Method;

public class TestEventBus extends EventBus {

    @Override
    protected MethodExecutor createMethodExecutor(Object listener, Method method, boolean ignoreCancelled) {
        return null;
    }
}