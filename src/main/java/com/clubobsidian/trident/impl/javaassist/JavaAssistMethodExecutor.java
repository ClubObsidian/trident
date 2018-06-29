package com.clubobsidian.trident.impl.javaassist;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.clubobsidian.trident.Event;
import com.clubobsidian.trident.Listener;
import com.clubobsidian.trident.MethodExecutor;
import com.clubobsidian.trident.util.JavaAssistUtil;

import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.LoaderClassPath;
import javassist.NotFoundException;

public class JavaAssistMethodExecutor extends MethodExecutor {

	private MethodCallback callBack;
	public JavaAssistMethodExecutor(Listener listener, Method method)
	{
		super(listener, method);
		this.callBack = JavaAssistUtil.generateCallBack(listener, method, JavaAssistEventManager.class.getClassLoader());
	}

	public MethodCallback getCallBack()
	{
		return this.callBack;
	}

	@Override
	public void execute(Event event) 
	{
		this.callBack.call(event);
	}
}