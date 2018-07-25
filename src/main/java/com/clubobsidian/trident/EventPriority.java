package com.clubobsidian.trident;

/**
 * Enum representation of priority for registered events.
 * Default priority is {@link EventPriority#NORMAL}
 * 
 * @author virustotalop
 */
public enum EventPriority {

	LOWEST(0),
	LOW(1),
	NORMAL(2),
	HIGH(3),
	HIGHEST(4),
	MONITOR(5);

	private int value;
	private EventPriority(int value)
	{
		this.value = value;
	}
	
	/**
	 * @return the integer representation of the underlying enum
	 */
	public int getValue()
	{
		return this.value;
	}
	
	/**
	 * @param value the integer value of an eventpriority
	 * @return The EventPriority found by the given value
	 */
	public static EventPriority getByValue(int value)
	{
		if(value < 0 || value >= EventPriority.values().length)
		{
			return null;
		}
		return EventPriority.values()[value];
	}
}