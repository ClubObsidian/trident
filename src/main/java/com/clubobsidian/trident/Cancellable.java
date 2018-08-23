package com.clubobsidian.trident;

public interface Cancellable {

	public boolean isCancelled();
	public void setCancelled(boolean cancel);
}