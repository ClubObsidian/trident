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
package trident;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import com.clubobsidian.trident.EventPriority;
import com.clubobsidian.trident.MethodExecutor;
import com.clubobsidian.trident.impl.javaassist.JavaAssistUtil;
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
						MethodExecutor testExecutor = JavaAssistUtil.generateMethodExecutor(testListener, testListener.getClass().getDeclaredMethod("test", TestEvent.class), false);
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
