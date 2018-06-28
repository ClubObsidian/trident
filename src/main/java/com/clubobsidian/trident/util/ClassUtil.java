package com.clubobsidian.trident.util;

import com.clubobsidian.trident.Event;

public final class ClassUtil {

	private ClassUtil() {}
	
	public static Class<?> getSuperClass(Class<?> cl)
	{
		while((cl = cl.getSuperclass()) != null)
		{
			if(cl == Event.class)
			{
				return cl;
			}
		}
		return null;
	}
}