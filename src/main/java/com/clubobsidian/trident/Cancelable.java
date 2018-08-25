package com.clubobsidian.trident;

/**
 * Interface to implement Cancelable. 
 * Events should implement this interface
 * if the event should be able to be cancelled.
 * 
 * @author virustotalop
 */
public interface Cancelable {

	/**
	 * Returns if the event is cancelled.
	 * @return if the event is cancelled
	 */
	public boolean isCanceled();
	
	/**
	 * Set whether or not for an event to be cancelled.
	 */
	public void setCanceled(boolean cancel);
}