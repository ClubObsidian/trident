import com.clubobsidian.trident.Cancelable;

public class TestCancelableEvent extends TestEvent implements Cancelable {

	private boolean canceled;
	
	@Override
	public boolean isCanceled()
	{
		return this.canceled;
	}
	
	public void setCanceled(boolean cancelled)
	{
		this.canceled = cancelled;
	}
}