import com.clubobsidian.trident.EventHandler;
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
}