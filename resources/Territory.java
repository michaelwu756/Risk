package resources;

import info.gridworld.actor.Actor;

public class Territory extends Actor
{
	private String name;
	
	public Territory ()
	{
		name = "Territory";
	}
	
	public Territory(String input)
	{
		name = input;
	}
	public String getName()
	{
		return name;
	}
	public String toString()
	{
		return name;
	}
}