package com.clubobsidian.trident;

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
	
	public int getValue()
	{
		return this.value;
	}
	
	public static EventPriority getByValue(int value)
	{
		if(value < 0 || value >= EventPriority.values().length)
		{
			return null;
		}
		return EventPriority.values()[value];
	}
}