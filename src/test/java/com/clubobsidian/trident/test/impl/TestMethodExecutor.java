package com.clubobsidian.trident.test.impl;

import com.clubobsidian.trident.Event;
import com.clubobsidian.trident.MethodExecutor;

import java.lang.reflect.Method;

public class TestMethodExecutor extends MethodExecutor {

    public TestMethodExecutor(Object listener, Method method, boolean ignoreCanceled) {
        super(listener, method, ignoreCanceled);
    }

    @Override
    public void execute(Event event) {

    }
}