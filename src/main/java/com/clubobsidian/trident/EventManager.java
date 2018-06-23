package com.clubobsidian.trident;

public interface EventManager {

	public void dispatchEvent(Event event);
	public boolean register(Listener listener);
	public boolean unregister(Listener listener);

}
