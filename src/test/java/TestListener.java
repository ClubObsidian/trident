import com.clubobsidian.trident.EventHandler;
import com.clubobsidian.trident.Listener;

public class TestListener implements Listener {

	private String data;
	public TestListener(String data)
	{
		this.data = data;
	}
	
	public String getData()
	{
		return this.data;
	}
	
	@EventHandler
	public void test(TestEvent event)
	{
		
	}
}