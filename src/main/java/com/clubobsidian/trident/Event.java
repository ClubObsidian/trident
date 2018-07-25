package com.clubobsidian.trident;

/**
 * Superclass of all events. Events should
 * extend this class.
 * 
 * @author virustotalop
 */
public abstract class Event {
	
	/**
	 * Uses reflection getClass().getSimpleName() to return
	 * the name of the event class.
	 * @return name of the event
	 */
	public String getName()
	{
		return this.getClass().getSimpleName();
	}
}