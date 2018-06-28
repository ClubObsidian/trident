package com.clubobsidian.trident.impl.javaassist;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.clubobsidian.trident.Event;
import com.clubobsidian.trident.EventHandler;
import com.clubobsidian.trident.EventManager;
import com.clubobsidian.trident.Listener;
import com.clubobsidian.trident.MethodExecutor;
import com.clubobsidian.trident.util.ClassUtil;
import com.clubobsidian.trident.util.EventDoublyLinkedList;
import com.clubobsidian.trident.util.EventNode;

public class JavaAssistEventManager implements EventManager {

	private Map<Listener, Queue<MethodExecutor>> registeredEventListeners = new ConcurrentHashMap<>();
	private Map<Class<?>, EventDoublyLinkedList> registeredExecutors = new ConcurrentHashMap<>();

	@Override
	public boolean callEvent(Event event) 
	{
		EventDoublyLinkedList executors = this.registeredExecutors.get(event.getClass());

		if(executors == null)
			return false;

		boolean ran = false;
		EventNode node = executors.getHead();
		if(node != null)
		{
			ran = true;
		}
		while(node != null)
		{
			MethodExecutor executor = node.getData();
			executor.execute(event);
			node = node.getNext();
		}
		return ran;
	}

	@Override
	public boolean registerEvents(Listener listener) 
	{
		if(this.registeredEventListeners.keySet().contains(listener))
		{
			return false;
		}

		this.registerEventsFromListener(listener);
		return true;
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
					if(ClassUtil.getSuperClass(eventClass) != null)
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
	public boolean unregisterEvents(Listener listener) 
	{
		Queue<MethodExecutor> executors = this.registeredEventListeners.remove(listener);
		if(executors == null)
			return false;

		this.unregisterEventsFromExecutors(executors);
		return true;
	}

	//Unregistering might be too slow, this should be tested later
	private void unregisterEventsFromExecutors(Queue<MethodExecutor> executors)
	{
		for(EventDoublyLinkedList list : this.registeredExecutors.values())
		{
			Iterator<MethodExecutor> it = executors.iterator();
			while(it.hasNext())
			{
				MethodExecutor executor = it.next();
				list.remove(executor);
			}
		}
	}
}