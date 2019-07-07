package com.clubobsidian.trident.test;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import com.clubobsidian.trident.EventPriority;
import com.clubobsidian.trident.MethodExecutor;
import com.clubobsidian.trident.test.impl.TestEvent;
import com.clubobsidian.trident.test.impl.TestListener;
import com.clubobsidian.trident.test.impl.TestMethodExecutor;
import com.clubobsidian.trident.util.EventDoublyLinkedList;
import com.clubobsidian.trident.util.EventNode;

public class MultiThreadingTest {
	
	@Test
	public void multiThreadingTestEventDoublyLinkedList()
	{
		AtomicInteger count = new AtomicInteger(0);
		final EventDoublyLinkedList list = new EventDoublyLinkedList();
		Runnable testRunnable = new Runnable()
		{
			@Override
			public void run()
			{
				for(int i = 0; i < 1000; i++)
				{
					try 
					{
						int inc = count.incrementAndGet();
						TestListener testListener = new TestListener("test" + inc);
						MethodExecutor testExecutor = new TestMethodExecutor(testListener, testListener.getClass().getDeclaredMethod("test", TestEvent.class), false);
						EventNode node = list.insert(testExecutor, EventPriority.LOWEST);
						assertTrue("Node was null when inserted", node != null);
					} 
					catch (NoSuchMethodException | SecurityException e) 
					{
						e.printStackTrace();
					}
				}
				Thread.currentThread().interrupt();
			}
		};
		Thread th1 = new Thread(testRunnable);
		Thread th2 = new Thread(testRunnable);
		
		th1.start();
		th2.start();
		
		while(th1.isAlive() || th2.isAlive());
		
		int nodeCount = 0;
		EventNode node = list.getHead();
		while(node != null)
		{
			nodeCount+= 1;
			node = node.getNext();
		}
		assertTrue("Node count not equal when running from multiple thread", nodeCount == count.get());
	}
}