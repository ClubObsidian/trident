import com.clubobsidian.trident.EventHandler;
import com.clubobsidian.trident.EventPriority;
import com.clubobsidian.trident.Listener;

public class TestListener implements Listener {
	
	private String data;
	private boolean test;
	public TestListener(String data)
	{
		this.data = data;
		this.test = false;
	}
	
	public String getData()
	{
		return this.data;
	}
	
	public boolean getTest()
	{
		return this.test;
	}
	
	@EventHandler
	public void test(TestEvent event)
	{
		this.test = !test;
	}
	
	@EventHandler
	public void testCancelleable(TestCancellableEvent event)
	{
		event.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void testCancellableHighest(TestCancellableEvent event)
	{
		if(event.isCancelled())
		{
			this.test = true;
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void testOrderLowest(TestOrderEvent event)
	{
		this.data += "0";
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void testOrderLow(TestOrderEvent event)
	{
		this.data += "1";
	}
	
	@EventHandler
	public void testOrderNormal(TestOrderEvent event)
	{
		this.data += 2;
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void testOrderHigh(TestOrderEvent event)
	{
		this.data += "3";
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void testOrderHighest(TestOrderEvent event)
	{
		this.data += "4";
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void testOrderMonitor(TestOrderEvent event)
	{
		this.data += "5";
	}
}