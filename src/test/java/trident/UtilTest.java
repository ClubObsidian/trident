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
package trident;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.clubobsidian.trident.Event;

import com.clubobsidian.trident.util.ClassUtil;

public class UtilTest {

	@Test
	public void classUtilTest()
	{
		Event event = new TestEvent();
		boolean superClass = ClassUtil.hasEventSuperClass(event.getClass());
		assertTrue("Super class for TestEvent was not event", superClass);
		
		superClass = ClassUtil.hasEventSuperClass(String.class);
		assertFalse("Super class is not null for string class", superClass);
	}
}
