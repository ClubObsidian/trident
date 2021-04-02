package com.clubobsidian.trident.eventbus.methodhandle;

import com.clubobsidian.trident.EventBus;
import com.clubobsidian.trident.MethodExecutor;

import java.lang.reflect.Method;

/**
 * {@inheritDoc}
 */
public class MethodHandleEventBus extends EventBus {

    @Override
    protected MethodExecutor createMethodExecutor(Object listener, Method method, boolean ignoreCancelled) {
        try {
            return new MethodHandleExecutor(listener, method, ignoreCancelled);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}