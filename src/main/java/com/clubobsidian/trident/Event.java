package com.clubobsidian.trident;

public abstract class Event {
	
	public String getName()
	{
		return this.getClass().getSimpleName();
	}
}