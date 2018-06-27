import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.clubobsidian.trident.EventManager;
import com.clubobsidian.trident.impl.javaassist.JavaAssistEventManager;

public class JavaAssistEventManagerTest {

	@Test
	public void testEventFiring()
	{
		TestListener test = new TestListener("test");
		EventManager manager = new JavaAssistEventManager();
		boolean registered = manager.register(test);
		
		assertTrue("Event is not registered", registered);
		assertTrue("Test is not false", !test.getTest());
		
		manager.dispatchEvent(new TestEvent());
		
		assertTrue("Test is not true", test.getTest());
		
		manager.dispatchEvent(new TestEvent());
		
		assertTrue("Test is not false", !test.getTest());
	}
	
	@Test
	public void testEventCancellable()
	{
		TestListener test = new TestListener("test");
		EventManager manager = new JavaAssistEventManager();
		manager.register(test);
		
		manager.dispatchEvent(new TestCancellableEvent());
		
		assertTrue("Test is not true, event was not cancelled", test.getTest());
	}
	
	@Test
	public void testOrder()
	{
		TestListener test = new TestListener("");
		EventManager manager = new JavaAssistEventManager();
		manager.register(test);
		
		manager.dispatchEvent(new TestOrderEvent());
		
		assertTrue("Events were not listened in the correct order", test.getData().equals("012345"));
	}
}