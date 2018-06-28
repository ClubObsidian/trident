package com.clubobsidian.trident;

public interface EventManager {

	public boolean callEvent(Event event);
	public boolean registerEvents(Listener listener);
	public boolean unregisterEvents(Listener listener);

}