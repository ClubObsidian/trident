package com.clubobsidian.trident.util;

import com.clubobsidian.trident.MethodExecutor;

public class EventNode 
{

	private MethodExecutor data;
	private int priority;
	private EventNode next;
	private EventNode prev;
	public EventNode(MethodExecutor data, int priority)
	{
		this.data = data;
	}
	
	public MethodExecutor getData()
	{
		return this.data;
	}
	
	public int getPriority()
	{
		return this.priority;
	}
	
	public EventNode getNext()
	{
		return this.next;
	}
	
	public void setNext(EventNode next)
	{
		this.next = next;
	}
	
	public EventNode getPrev()
	{
		return this.prev;
	}
	
	public void setPrev(EventNode prev)
	{
		this.prev = prev;
	}
}