package com.clubobsidian.trident.util;

import com.clubobsidian.trident.MethodExecutor;

/**
 * Node class for events. Used
 * in {@link com.clubobsidian.trident.util.EventDoublyLinkedList}
 * 
 * @author virustotalop
 */
public class EventNode 
{
	private MethodExecutor data;
	private int priority;
	private EventNode next;
	private EventNode prev;
	public EventNode(MethodExecutor data, int priority)
	{
		this.data = data;
		this.priority = priority;
	}

	/**
	 * @return MethodExecutor for the node
	 */
	public MethodExecutor getData()
	{
		return this.data;
	}

	/**
	 * @return integer representation of the {@link com.clubobsidian.trident.Event}'s priority
	 */
	public int getPriority()
	{
		return this.priority;
	}

	/**
	 * @return node next to this node
	 */
	public synchronized EventNode getNext()
	{
		return this.next;
	}

	/**
	 * @param next node to be set as the next node
	 */
	public synchronized void setNext(EventNode next)
	{
		this.next = next;
	}

	/**
	 * @return node behind this node
	 */
	public synchronized EventNode getPrev()
	{
		return this.prev;
	}

	/**
	 * @param prev node to be set as the previous node
	 */
	public synchronized void setPrev(EventNode prev)
	{
		this.prev = prev;
	}
}