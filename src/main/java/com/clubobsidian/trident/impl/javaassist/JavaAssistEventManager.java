/*  
   Copyright 2018 Club Obsidian and contributors.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
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

/**
 * {@inheritDoc}
 */
public class JavaAssistEventManager implements EventManager {

	private Map<Listener, Queue<MethodExecutor>> registeredEventListeners;
	private Map<Class<?>, EventDoublyLinkedList> registeredExecutors;

	public JavaAssistEventManager()
	{
		this.registeredEventListeners = new ConcurrentHashMap<>();
		this.registeredExecutors = new ConcurrentHashMap<>();
	}
	
	@Override
	public boolean callEvent(final Event event) 
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
	public boolean registerEvents(final Listener listener) 
	{
		if(this.registeredEventListeners.keySet().contains(listener))
		{
			return false;
		}

		this.registerEventsFromListener(listener);
		return true;
	}

	private void registerEventsFromListener(final Listener listener)
	{
		Class<?> cl = listener.getClass();
		for(Method method : cl.getDeclaredMethods())
		{
			for(EventHandler handler : method.getAnnotationsByType(EventHandler.class))
			{
				if(method.getParameters().length == 1)
				{
					Class<?> eventClass = method.getParameterTypes()[0];
					if(ClassUtil.hasEventSuperClass(eventClass))
					{
						if(this.registeredExecutors.get(eventClass) == null)
						{
							this.registeredExecutors.put(eventClass, new EventDoublyLinkedList());
						}
						if(this.registeredEventListeners.get(listener) == null)
						{
							this.registeredEventListeners.put(listener, new ConcurrentLinkedQueue<>());
						}

						MethodExecutor executor = JavaAssistUtil.generateMethodExecutor(listener, method);
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
