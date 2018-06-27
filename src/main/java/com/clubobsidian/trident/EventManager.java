package com.clubobsidian.trident;

public interface EventManager {

	public void callEvent(Event event);
	public boolean register(Listener listener);
	public boolean unregister(Listener listener);

}