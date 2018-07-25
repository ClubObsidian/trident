import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.clubobsidian.trident.Event;

import com.clubobsidian.trident.util.ClassUtil;

public class UtilTest {

	@Test
	public void classUtilTest()
	{
		Event event = new TestEvent();
		boolean superClass = ClassUtil.hasEventSuperClass(event.getClass());
		assertTrue("Super class for TestEvent was not event", superClass);
		
		superClass = ClassUtil.hasEventSuperClass(String.class);
		assertTrue("Super class is not null for string class", !superClass);
	}
}