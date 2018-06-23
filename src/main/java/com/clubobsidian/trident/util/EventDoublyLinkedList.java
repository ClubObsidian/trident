package com.clubobsidian.trident.util;

import java.io.Serializable;

import com.clubobsidian.trident.EventPriority;
import com.clubobsidian.trident.MethodExecutor;

public class EventDoublyLinkedList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6359060072540225110L;

	/* Class for implementing a doubly linked list 
	 * that takes event priority into account.
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
			EventNode found = this.findPriorityNode(priorityValue);
			if(found.equals(this.head))
			{
				if(priorityValue < found.getPriority())
				{
					EventNode oldHead = this.head;
					this.head = newNode;
					this.head.setNext(oldHead);
				}
				else
				{
					if(this.head.getNext() == null)
					{
						this.head.setNext(newNode);
						newNode.setPrev(this.head);
						return true;
					}
					else
					{
						newNode.setNext(this.head.getNext());
						this.head.setPrev(newNode);
						this.head.setNext(newNode);
						newNode.setPrev(this.head);
						return true;
					}	
				}
			}
			else if(found.getPriority() > priorityValue)
			{
				found.getPrev().setNext(newNode);
				newNode.setNext(found);
				newNode.setPrev(found);
				found.setPrev(newNode);
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
	 * @return The last node that can be found for the priority value 
	 */
	private EventNode findPriorityNode(int priorityValue)
	{
		EventNode next = this.head;
		while(next != null)
		{
			if(next.getNext() == null) //if tail
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
