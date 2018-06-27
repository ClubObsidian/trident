import com.clubobsidian.trident.Cancellable;

public class TestEventCancellable extends TestEvent implements Cancellable {

	private boolean cancelled;
	
	@Override
	public boolean isCancelled() 
	{
		return this.cancelled;
	}

	public void setCancelled(boolean cancelled)
	{
		this.cancelled = cancelled;
	}
}