/*   Copyright 2018 Club Obsidian and contributors.

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
package com.clubobsidian.trident.util;

import com.clubobsidian.trident.Event;

/**
 * Utility class for class based operations.
 * 
 * @author virustotalop
 */
public final class ClassUtil {

	private ClassUtil() {}

	/**
	 * Checks to see if a given class has {@link Event}
	 * as a super class. Uses reflection to recursively
	 * call {@link Class#getSuperclass()} to check
	 * the super class.
	 * 
	 * @param cl class to check for an event super class
	 * @return whether or not the given class has {@link Event}
	 * as a super class.
	 */
	public static boolean hasEventSuperClass(Class<?> cl)
	{
		while((cl = cl.getSuperclass()) != null)
		{
			if(cl == Event.class)
			{
				return true;
			}
		}
		return false;
	}
}
