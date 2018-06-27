package com.clubobsidian.trident.util;

import java.io.Serializable;

import com.clubobsidian.trident.EventPriority;
import com.clubobsidian.trident.MethodExecutor;

public class EventDoublyLinkedList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6359060072540225110L;

	/** Class for implementing a doubly linked list 
	 * that orders the list based off of creation time and {@link EventPriority}.
	 */
	private EventNode head;
	public EventDoublyLinkedList()
	{
		this.head = null;
	}
	
	public EventNode getHead()
	{
		return this.head;
	}
	
	public boolean insert(MethodExecutor executor, EventPriority priority)
	{
		int priorityValue = priority.getValue();
		EventNode newNode = new EventNode(executor, priorityValue);
		if(this.head == null)
		{
			this.head = new EventNode(executor, priorityValue);
			return true;
		}
		else
		{
			EventNode found = this.findInsertionNode(priorityValue);
			if(found.equals(this.head) && priorityValue < this.head.getPriority())
			{
				EventNode oldHead = this.head;
				this.head = newNode;
				this.head.setNext(oldHead);
				oldHead.setPrev(this.head);
				return true;
			}
			else if(found.getPriority() > priorityValue)
			{
				newNode.setNext(found);
				newNode.setPrev(found.getPrev());
				newNode.getPrev().setNext(newNode);
				found.setPrev(newNode);
				return true;
			}
			else if(found.getNext() == null)
			{
				found.setNext(newNode);
				newNode.setPrev(found);
				return true;
			}
			else if(found.getPriority() == priorityValue)
			{
				newNode.setNext(found.getNext());
				found.setNext(newNode);
				newNode.getNext().setPrev(newNode);
				return true;
			}
		}
	
		return false;
	}
	
	public boolean remove(MethodExecutor executor)
	{
		EventNode found = this.find(executor);
		if(found == null)
			return false;
		
		if(found.equals(this.head))
		{
			if(this.head.getNext() == null)
			{
				this.head = null;
			}
			else
			{
				this.head = this.head.getNext();
			}
			return true;
		}
		else if(found.getNext() == null)
		{
			found.getPrev().setNext(null);
			return true;
		}
		else
		{
			found.getPrev().setNext(found.getNext());
			return true;
		}
	}
	
	
	public EventNode find(MethodExecutor executor)
	{
		EventNode node = this.head;
		while(node != null)
		{
			if(node.getData().equals(executor))
				return node;
			node = node.getNext();
		}
		return null;
	}
	
	/**
	 * @return The node to be inserted around
	 */
	private EventNode findInsertionNode(int priorityValue)
	{
		EventNode next = this.head;
		while(next != null)
		{
			if(next.getNext() == null) //if tail
			{
				return next;
			}
			else if(next.getPriority() == priorityValue && next.getNext() == null)
			{
				return next;
			}
			else if(next.getPriority() == priorityValue && next.getNext().getPriority() > priorityValue)
			{
				return next;
			}
			else if(next.getPriority() > priorityValue)
			{
				return next;
			}
			next = next.getNext();
		}
		return null;
	}
}