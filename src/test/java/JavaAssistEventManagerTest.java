import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.clubobsidian.trident.Event;
import com.clubobsidian.trident.EventManager;
import com.clubobsidian.trident.impl.javaassist.JavaAssistEventManager;

public class JavaAssistEventManagerTest {

	@Test
	public void testEventFiring()
	{
		TestListener test = new TestListener("test");
		EventManager manager = new JavaAssistEventManager();
		boolean registered = manager.registerEvents(test);
		
		assertTrue("Event is not registered", registered);
		assertTrue("Test is not false", !test.getTest());
		
		manager.callEvent(new TestEvent());
		
		assertTrue("Test is not true", test.getTest());
		
		manager.callEvent(new TestEvent());
		
		assertTrue("Test is not false", !test.getTest());
	}
	
	@Test
	public void testEventCancellable()
	{
		TestListener test = new TestListener("test");
		EventManager manager = new JavaAssistEventManager();
		manager.registerEvents(test);
		
		manager.callEvent(new TestCancellableEvent());
		
		assertTrue("Test is not true, event was not cancelled", test.getTest());
	}
	
	@Test
	public void testOrder()
	{
		TestListener test = new TestListener("");
		EventManager manager = new JavaAssistEventManager();
		manager.registerEvents(test);
		
		manager.callEvent(new TestOrderEvent());
		
		assertTrue("Events were not listened in the correct order", test.getData().equals("012345"));
	}
	
	@Test
	public void testDoubleRegister()
	{
		TestListener test = new TestListener("test");
		EventManager manager = new JavaAssistEventManager();
		manager.registerEvents(test);
		
		assertTrue("Event was double registered", !manager.registerEvents(test));
	}
	
	@Test
	public void testCalledEvent()
	{
		Event event = new TestEvent();
		EventManager manager = new JavaAssistEventManager();
		
		assertTrue("Event call ran when event did not exist", !manager.callEvent(event));
	}
	
	@Test
	public void testUnregister()
	{
		TestListener test = new TestListener("test");
		EventManager manager = new JavaAssistEventManager();
		manager.registerEvents(test);
		
		assertTrue("Listener was not registered", manager.unregisterEvents(test));
		assertTrue("Listener was still registered", !manager.unregisterEvents(test));
	}
	
	@Test
	public void benchmark()
	{
		TestListener test = new TestListener("test");
		EventManager manager = new JavaAssistEventManager();
		manager.registerEvents(test);
		
		for(int i = 0; i < 1000000; i++)
		{
			manager.callEvent(new TestEvent());
		}
	}
}