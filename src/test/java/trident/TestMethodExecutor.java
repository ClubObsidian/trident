package trident;

import java.lang.reflect.Method;

import com.clubobsidian.trident.Event;
import com.clubobsidian.trident.Listener;
import com.clubobsidian.trident.MethodExecutor;

public class TestMethodExecutor extends MethodExecutor {

	public TestMethodExecutor(Listener listener, Method method, boolean ignoreCanceled) 
	{
		super(listener, method, ignoreCanceled);
	}

	@Override
	public void execute(Event event) 
	{
		
	}
}