package com.clubobsidian.trident;

/**
 * Interface to implement Cancellable. 
 * Events should implement this interface
 * if the event should be able to be cancelled.
 * 
 * @author virustotalop
 */
public interface Cancellable {

	/**
	 * Returns if the event is cancelled.
	 * @return if the event is cancelled
	 */
	public boolean isCancelled();
	
	/**
	 * Set whether or not for an event to be cancelled.
	 */
	public void setCancelled(boolean cancel);
}