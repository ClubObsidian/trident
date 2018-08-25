import com.clubobsidian.trident.Cancelable;

public class TestCancellableEvent extends TestEvent implements Cancelable {

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