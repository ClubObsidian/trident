package com.clubobsidian.trident.eventbus.methodhandle;

import com.clubobsidian.trident.Event;
import com.clubobsidian.trident.MethodExecutor;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

/**
 * {@inheritDoc}
 */
public class MethodHandleExecutor extends MethodExecutor {

    private final MethodHandle handle;

    public MethodHandleExecutor(Object listener, Method method, boolean ignoreCancelled) throws IllegalAccessException {
        super(listener, method, ignoreCancelled);
        this.handle = MethodHandles.lookup().unreflect(method);
    }

    @Override
    public void execute(final Event event) {
        try {
            this.handle.invoke(this.getListener(), event);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }
}