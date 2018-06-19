package com.clubobsidian.trident.impl.javaassist;

import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.clubobsidian.trident.Event;
import com.clubobsidian.trident.EventManager;
import com.clubobsidian.trident.Listener;

public class JavaAssistEventManager implements EventManager {

	private Queue<Listener> registeredListeners = new ConcurrentLinkedQueue<>();
	private Map<Listener, Queue<MethodExecutor>> listenerRegisteredExecutors = new ConcurrentHashMap<>();
	private Map<Class<?>, Queue<MethodExecutor>> classRegisteredEvents = new ConcurrentHashMap<>();
	
	@Override
	public boolean dispatchEvent(Event event) 
	{
		Queue<MethodExecutor> events = this.classRegisteredEvents.get(event.getClass());
		if(events == null)
			return false;
		Iterator<MethodExecutor> it = events.iterator();
		
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