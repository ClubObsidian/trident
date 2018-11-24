import com.clubobsidian.trident.EventHandler;
import com.clubobsidian.trident.EventPriority;
import com.clubobsidian.trident.Listener;

public class TestListenerIgnore implements Listener {

	private boolean canceled = false;
	private boolean ignored = true;

	public boolean isCanceled()
	{
		return this.canceled;
	}
	
	public boolean getIgnored()
	{
		return this.ignored;
	}
	
	@EventHandler
	public void setCanceled(TestCancelableEvent e)
	{
		e.setCanceled(true);
		this.canceled = true;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCanceled = true)
	public void test(TestCancelableEvent e)
	{
		this.ignored = false;
	}
}