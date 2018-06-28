import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

import org.junit.Test;

import com.clubobsidian.trident.EventPriority;
import com.clubobsidian.trident.MethodExecutor;
import com.clubobsidian.trident.impl.javaassist.JavaAssistMethodExecutor;
import com.clubobsidian.trident.util.EventDoublyLinkedList;
import com.clubobsidian.trident.util.EventNode;

public class EventDoublyLinkedListTest {

	@Test
	public void testOrder()
	{	
		try 
		{
			EventDoublyLinkedList list = new EventDoublyLinkedList();
			
			TestListener test = new TestListener("test");
			MethodExecutor executor = new JavaAssistMethodExecutor(test, test.getClass().getDeclaredMethod("test", TestEvent.class));
			EventNode low = list.insert(executor, EventPriority.LOW);
			assertTrue("Low could not be inserted at head", low != null);
			
			assertTrue("Low is not head", list.getHead().getData().equals(executor));
			
			
			TestListener test2 = new TestListener("test2");
			MethodExecutor executor2 = new JavaAssistMethodExecutor(test2, test2.getClass().getDeclaredMethod("test", TestEvent.class));
			EventNode lowest = list.insert(executor2, EventPriority.LOWEST);
			assertTrue("Lowest could not be inserted", lowest != null);
			
			assertTrue("Lowest is not head", list.getHead().getData().equals(executor2));
			
			
			TestListener test3 = new TestListener("test3");
			MethodExecutor executor3 = new JavaAssistMethodExecutor(test3, test3.getClass().getDeclaredMethod("test", TestEvent.class));
			EventNode nextLowest = list.insert(executor3, EventPriority.LOWEST);
			assertTrue("NextLowest could not be inserted", nextLowest != null);
			
			assertTrue("NextLowest is not next to head", list.getHead().getNext().getData().equals(executor3));
			
			
			TestListener test4 = new TestListener("test4");
			MethodExecutor executor4 = new JavaAssistMethodExecutor(test4, test4.getClass().getDeclaredMethod("test", TestEvent.class));
			EventNode monitor = list.insert(executor4, EventPriority.MONITOR);
			assertTrue("Monitor could not be inserted", monitor != null);
			
			assertTrue("Monitor is not tail", list.find(executor4).getData().equals(executor4));
			
			
			TestListener test5 = new TestListener("test5");
			MethodExecutor executor5 = new JavaAssistMethodExecutor(test5, test5.getClass().getDeclaredMethod("test", TestEvent.class));
			EventNode high = list.insert(executor5, EventPriority.HIGH);
			assertTrue("High could not be inserted", high != null);
			
			assertTrue("High next node is not priority monitor", list.find(executor5).getNext().getData().equals(executor4));
			
			TestListener test6 = new TestListener("test6");
			MethodExecutor executor6 = new JavaAssistMethodExecutor(test6, test6.getClass().getDeclaredMethod("test", TestEvent.class));
			EventNode normal = list.insert(executor6, EventPriority.NORMAL);
			assertTrue("Normal could not be inserted", normal != null);
			
			assertTrue("Normal next node is not priority high", list.find(executor6).getNext().getData().equals(executor5));
			
			
			TestListener test7 = new TestListener("test7");
			MethodExecutor executor7 = new JavaAssistMethodExecutor(test7, test7.getClass().getDeclaredMethod("test", TestEvent.class));
			EventNode highest = list.insert(executor7, EventPriority.HIGHEST);
			assertTrue("Highest could not be inserted", highest != null);
			
			assertTrue("Highest next node is not priority monitor", list.find(executor7).getNext().getData().equals(executor4));
		} 
		catch (NoSuchMethodException | SecurityException e) 
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRemoval()
	{	
		try 
		{
			EventDoublyLinkedList list = new EventDoublyLinkedList();
			TestListener test = new TestListener("test1");
			MethodExecutor executor = new JavaAssistMethodExecutor(test, test.getClass().getDeclaredMethod("test", TestEvent.class));
			list.insert(executor, EventPriority.MONITOR);
			
			assertTrue("Monitor is not head", list.getHead().getData().equals(executor));
			list.remove(executor);
			assertTrue("Head is not null after monitor removal", list.getHead() == null);
			
			for(int i = EventPriority.values()[0].getValue(); i < (EventPriority.values()[EventPriority.values().length - 1]).getValue() + 1; i++)
			{
				list.insert(new JavaAssistMethodExecutor(new TestListener("test" + i), test.getClass().getDeclaredMethod("test", TestEvent.class)), EventPriority.getByValue(i));
			}
			
			EventNode node = list.getHead();
			int removalCount = 0;
			while(node != null)
			{
				list.remove(node.getData());
				removalCount += 1;
				node = node.getNext();
			}
			
			assertTrue("Head is not null after traversing after removing all node", list.getHead() == null);
			assertTrue("Removed nodes are not equal to EventPriority length", EventPriority.values().length == removalCount);
			
			for(int i = (EventPriority.values()[EventPriority.values().length - 1]).getValue(); i > -1 ; i--)
			{
				list.insert(new JavaAssistMethodExecutor(new TestListener("test" + i), test.getClass().getDeclaredMethod("test", TestEvent.class)), EventPriority.getByValue(i));
			}
			
			node = list.getHead();
			removalCount = 0;
			while(node != null)
			{
				list.remove(node.getData());
				removalCount += 1;
				node = node.getNext();
			}
			
			assertTrue("Head is not null after traversing after removing all nodes reverse", list.getHead() == null);
			assertTrue("Removed nodes are not equal to EventPriority length", EventPriority.values().length == removalCount);
			
			Random rand = new Random();
			
			int iterate = 10000;
			for(int i = 0; i < iterate; i++)
			{
				
				rand.setSeed(System.nanoTime());
				int next = rand.nextInt(6);
				EventNode inserted = list.insert(new JavaAssistMethodExecutor(new TestListener("test" + i), test.getClass().getDeclaredMethod("test", TestEvent.class)), EventPriority.getByValue(next));
			
				assertTrue("Insert failed for random insert for removal", inserted != null);
			}
			
			node = list.getHead();
			removalCount = 0;
			while(node != null)
			{
				list.remove(node.getData());
				removalCount += 1;
				node = node.getNext();
			}
			
			assertTrue("Head is not null after traversing after removing all nodes after random insert", list.getHead() == null);
			assertTrue("Removed nodes are not equal to iterating length after random insert", iterate == removalCount);
		}
		catch (NoSuchMethodException | SecurityException e) 
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void emptyLinkedListTest()
	{
		EventDoublyLinkedList list = new EventDoublyLinkedList();
		TestListener test = new TestListener("test1");
		try 
		{
			MethodExecutor executor = new JavaAssistMethodExecutor(test, test.getClass().getDeclaredMethod("test", TestEvent.class));
			assertTrue("Executor was removed", list.remove(executor) == null);
		} 
		catch (NoSuchMethodException | SecurityException e) 
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void nextIsNullAndNotHeadRemove()
	{
		try 
		{
			EventDoublyLinkedList list = new EventDoublyLinkedList();
			TestListener test1 = new TestListener("test1");
			MethodExecutor executor1 = new JavaAssistMethodExecutor(test1, test1.getClass().getDeclaredMethod("test", TestEvent.class));
			
			list.insert(executor1, EventPriority.LOWEST);
			
			TestListener test2 = new TestListener("test2");
			MethodExecutor executor2 = new JavaAssistMethodExecutor(test2, test2.getClass().getDeclaredMethod("test", TestEvent.class));
			
			list.insert(executor2, EventPriority.LOW);
			
			EventNode removed = list.remove(executor2);
			
			assertTrue("EventNode data was not equal", removed.getData().equals(executor2));
			
			assertTrue("EventNode previous node's next node was not null", removed.getPrev().getNext() == null);
		} 
		catch (NoSuchMethodException | SecurityException e) 
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void foundIsMiddleNodeRemoveTest()
	{
		try 
		{
			EventDoublyLinkedList list = new EventDoublyLinkedList();
			TestListener test1 = new TestListener("test1");
			MethodExecutor executor1 = new JavaAssistMethodExecutor(test1, test1.getClass().getDeclaredMethod("test", TestEvent.class));
			
			list.insert(executor1, EventPriority.LOWEST);
			
			TestListener test2 = new TestListener("test2");
			MethodExecutor executor2 = new JavaAssistMethodExecutor(test2, test2.getClass().getDeclaredMethod("test", TestEvent.class));
			
			list.insert(executor2, EventPriority.LOW);
			
			TestListener test3 = new TestListener("test3");
			MethodExecutor executor3 = new JavaAssistMethodExecutor(test3, test3.getClass().getDeclaredMethod("test", TestEvent.class));
			
			list.insert(executor3, EventPriority.NORMAL);
			
			EventNode removed = list.remove(executor2);
			
			assertTrue("Previous node's next node was not set to removed next node", removed.getPrev().getNext().equals(removed.getNext()));
		
		} 
		catch (NoSuchMethodException | SecurityException e) 
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void findMethodExecutorThatDoesNotExistTest()
	{
		try 
		{
			EventDoublyLinkedList list = new EventDoublyLinkedList();
			TestListener test1 = new TestListener("test1");
			MethodExecutor executor1 = new JavaAssistMethodExecutor(test1, test1.getClass().getDeclaredMethod("test", TestEvent.class));
			assertTrue("Executor was found while not inserted", list.find(executor1) == null);
		} 
		catch (NoSuchMethodException | SecurityException e) 
		{
			e.printStackTrace();
		}
	}
	
	@Test //This should never happen under normal circumstances but is being used for code coverage
	public void findInsertNull()
	{
		try 
		{
			EventDoublyLinkedList list = new EventDoublyLinkedList();
			
			Method findInsertion = list.getClass().getDeclaredMethod("findInsertionNode", int.class);
			findInsertion.setAccessible(true);
			assertTrue("Null node was not found", findInsertion.invoke(list, 6) == null);
		} 
		catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void findIfFoundNodePriorityIsTheSameTest()
	{
		try 
		{
			EventDoublyLinkedList list = new EventDoublyLinkedList();
			TestListener test1 = new TestListener("test1");
			MethodExecutor executor1 = new JavaAssistMethodExecutor(test1, test1.getClass().getDeclaredMethod("test", TestEvent.class));
			
			list.insert(executor1, EventPriority.LOWEST);
			
			TestListener test2 = new TestListener("test2");
			MethodExecutor executor2 = new JavaAssistMethodExecutor(test2, test2.getClass().getDeclaredMethod("test", TestEvent.class));
			
			
			EventPriority priority = EventPriority.LOWEST;
			EventNode inserted = list.insert(executor2, priority);
			
			assertTrue("Previous node's priority is not the same", inserted.getPriority() == priority.getValue());
		} 
		catch (NoSuchMethodException | SecurityException e) 
		{
			e.printStackTrace();
		}
	}
}