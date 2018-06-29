import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;

import org.junit.Test;

import com.clubobsidian.trident.Event;
import com.clubobsidian.trident.EventPriority;
import com.clubobsidian.trident.Listener;
import com.clubobsidian.trident.MethodExecutor;
import com.clubobsidian.trident.impl.javaassist.JavaAssistMethodExecutor;

public class JavaAssistMiscEventTest {

	@Test
	public void eventNameTest()
	{
		Event event = new TestEvent();
		assertTrue(event.getName().equals("TestEvent"));
	}
	
	@Test
	public void methodExecutorTest()
	{
		try 
		{
			Listener listener = new TestListener("test");
			Method testEventMethod = listener.getClass().getDeclaredMethod("test", TestEvent.class);
			MethodExecutor executor = new JavaAssistMethodExecutor(listener, testEventMethod);
			
			assertTrue("Listeners are not equal for method executor", listener.equals(executor.getListener()));
			assertTrue("Methods are not equal for method executor", testEventMethod.equals(executor.getMethod()));
		} 
		catch (NoSuchMethodException | SecurityException e) 
		{
			e.printStackTrace();
		}		
	}
	
	@Test
	public void eventPriorityTest()
	{
		assertTrue("-1 event priority is not null", EventPriority.getByValue(-1) == null);
		assertTrue("Length event priority is not null", EventPriority.getByValue(EventPriority.values().length) == null);
	}
	
	@Test
	public void classPoolTest()
	{
		assertTrue("Class pool is null", JavaAssistMethodExecutor.getClassPool() != null);
	}
	
	@Test
	public void generateCallbackNullTest()
	{
		JavaAssistMethodExecutor executor = new JavaAssistMethodExecutor(null, null);
		assertTrue("Executor is not null", executor.getCallBack() == null);
	}
}
