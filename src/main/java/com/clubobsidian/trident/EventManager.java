package com.clubobsidian.trident;

public interface EventManager {

	public void callEvent(Event event);
	public boolean registerEvents(Listener listener);
	public boolean unregisterEvents(Listener listener);

}