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