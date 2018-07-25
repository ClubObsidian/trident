package com.clubobsidian.trident;

/**
 * Interface for implementing EventManager
 * classes. For an example see @see com.clubobsidian.trident.impl.javaassist.JavaAssistEventManager
 * 
 * @author virustotalop
 */
public interface EventManager {

	/**
	 * @param event  Event to call
	 * @return if the event was called
	 */
	public boolean callEvent(Event event);
	
	/**
	 * @param listener listener to be registered
	 * @return if the listener was registered
	 */
	public boolean registerEvents(Listener listener);
	
	/**
	 * 
	 * @param listener listener to be unregistered
	 * @return if the listener was unregistered
	 */
	public boolean unregisterEvents(Listener listener);

}