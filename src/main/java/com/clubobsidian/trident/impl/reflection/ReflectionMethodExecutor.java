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
package com.clubobsidian.trident.impl.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.clubobsidian.trident.Event;
import com.clubobsidian.trident.Listener;
import com.clubobsidian.trident.MethodExecutor;

/**
 * {@inheritDoc}
 */
public class ReflectionMethodExecutor extends MethodExecutor {

	public ReflectionMethodExecutor(Listener listener, Method method, boolean ignoreCanceled) 
	{
		super(listener, method, ignoreCanceled);
	}

	@Override
	public void execute(final Event event) 
	{
		try 
		{
			this.getMethod().invoke(this.getListener(), event);
		} 
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
		{
			e.printStackTrace();
		}
	}
}