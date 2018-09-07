/*  
   Copyright 2018 Club Obsidian and contributors.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
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
	public void testCanceleable(TestCancelableEvent event)
	{
		event.setCanceled(true);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void testCancelableHighest(TestCancelableEvent event)
	{
		if(event.isCanceled())
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
