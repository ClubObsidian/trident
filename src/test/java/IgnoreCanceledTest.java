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

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.clubobsidian.trident.EventManager;
import com.clubobsidian.trident.impl.javaassist.JavaAssistEventManager;

public class IgnoreCanceledTest {

	@Test
	public void ignoredCanceled()
	{
		TestListenerIgnore listener = new TestListenerIgnore();
		EventManager manager = new JavaAssistEventManager();
		manager.registerEvents(listener);
		manager.callEvent(new TestCancelableEvent());
		
		assertTrue("Event was not canceled", listener.isCanceled());
		assertTrue("Event was not ignored", listener.getIgnored());
	}

}
