import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;

import org.junit.Test;

import com.clubobsidian.trident.Listener;
import com.clubobsidian.trident.MethodExecutor;
import com.clubobsidian.trident.impl.reflection.ReflectionMethodExecutor;

public class ReflectionMiscEventTest {

	@Test
	public void methodExecutorTest()
	{
		try 
		{
			Listener listener = new TestListener("test");
			Method testEventMethod = listener.getClass().getDeclaredMethod("test", TestEvent.class);
			MethodExecutor executor = new ReflectionMethodExecutor(listener, testEventMethod);
			
			assertTrue("Listeners are not equal for method executor", listener.equals(executor.getListener()));
			assertTrue("Methods are not equal for method executor", testEventMethod.equals(executor.getMethod()));
		} 
		catch (NoSuchMethodException | SecurityException e) 
		{
			e.printStackTrace();
		}		
	}
}