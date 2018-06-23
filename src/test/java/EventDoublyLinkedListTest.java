import static org.junit.Assert.assertTrue;

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
			TestListener test = new TestListener();
			
			MethodExecutor executor = new JavaAssistMethodExecutor(test, test.getClass().getDeclaredMethod("test", TestEvent.class));
			boolean high = list.insert(executor, EventPriority.HIGH);
			assertTrue("High could not be inserted at head", high);
			
			assertTrue("High was not head", list.getHead().getData().equals(executor));
			
			TestListener2 test2 = new TestListener2();
			MethodExecutor executor2 = new JavaAssistMethodExecutor(test2, test2.getClass().getDeclaredMethod("test", TestEvent.class));
			boolean lowest = list.insert(executor2, EventPriority.LOWEST);
			assertTrue("Lowest could not be inserted", lowest);
			
			
			assertTrue("Lowest was not head", list.getHead().getData().equals(executor2));
			
			TestListener3 test3 = new TestListener3();
			MethodExecutor executor3 = new JavaAssistMethodExecutor(test3, test3.getClass().getDeclaredMethod("test", TestEvent.class));
			boolean nextLowest = list.insert(executor3, EventPriority.LOWEST);
			assertTrue("NextLowest could not be inserted", nextLowest);
			
			assertTrue("NextLowest is not next to head", list.getHead().getNext().getData().equals(executor3));
			
			TestListener4 test4 = new TestListener4();
			MethodExecutor executor4 = new JavaAssistMethodExecutor(test4, test4.getClass().getDeclaredMethod("test", TestEvent.class));
			boolean highest = list.insert(executor4, EventPriority.HIGHEST);
			assertTrue("Highest could not be inserted", highest);
			
			
			EventNode node = list.getHead();
			while(node != null)
			{
				if(node.getNext() == null)
				{
					break;
				}
				node = node.getNext();
			}
			assertTrue("Highest is not tail", node.getData().equals(executor4));
			
			
		} 
		catch (NoSuchMethodException | SecurityException e) 
		{
			e.printStackTrace();
		}
		
	}
}