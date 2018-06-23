package com.clubobsidian.trident.impl.javaassist;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.clubobsidian.trident.Event;
import com.clubobsidian.trident.EventHandler;
import com.clubobsidian.trident.EventManager;
import com.clubobsidian.trident.Listener;
import com.clubobsidian.trident.MethodExecutor;
import com.clubobsidian.trident.util.EventDoublyLinkedList;
import com.clubobsidian.trident.util.EventNode;

public class JavaAssistEventManager implements EventManager {

	private Queue<Listener> registeredListeners = new ConcurrentLinkedQueue<>();
	private Map<Listener, Queue<MethodExecutor>> registeredEventListeners = new ConcurrentHashMap<>();
	private Map<Class<?>, EventDoublyLinkedList> registeredExecutors = new ConcurrentHashMap<>();
	
	@Override
	public void dispatchEvent(Event event) 
	{
		EventDoublyLinkedList executors = this.registeredExecutors.get(event.getClass());
		
		if(executors == null)
			return;
		
		EventNode node = executors.getHead();
		while(node != null)
		{
			MethodExecutor executor = node.getData();
			executor.execute(event);
			node = node.getNext();
		}
	}
	
	@Override
	public boolean register(Listener listener) 
	{
		if(this.registeredListeners.contains(listener))
		{
			return false;
		}
		this.registerEventsFromListener(listener);
		return this.registeredListeners.add(listener);
	}
	
	private void registerEventsFromListener(Listener listener)
	{
		Class<?> cl = listener.getClass();
		for(Method method : cl.getDeclaredMethods())
		{
			for(EventHandler handler : method.getAnnotationsByType(EventHandler.class))
			{
				if(method.getParameters().length == 1)
				{
					Class<?> eventClass = method.getParameterTypes()[0];
					if(eventClass.isAssignableFrom(Event.class))
					{
						if(this.registeredExecutors.get(eventClass) == null)
						{
							this.registeredExecutors.put(eventClass, new EventDoublyLinkedList());
						}
						if(this.registeredEventListeners.get(listener) == null)
						{
							this.registeredEventListeners.put(listener, new ConcurrentLinkedQueue<>());
						}
						MethodExecutor executor = new JavaAssistMethodExecutor(listener, method);
						this.registeredExecutors.get(eventClass).insert(executor, handler.priority());
						this.registeredEventListeners.get(listener).add(executor);
					}
				}
			}
		}
	}

	@Override
	public boolean unregister(Listener listener) 
	{
		boolean result = this.registeredListeners.remove(listener);
		this.unregisterEventsFromListener(listener);
		return result;
	}
	
	private void unregisterEventsFromListener(Listener listener)
	{
		
	}
}