package resources;

import java.util.ArrayList;

public class Player 
{
	private int unplacedArmies;
	private int numTerritories;
	private String name;
	private ArrayList<Card> cardArray;
	
	public Player(String name)
	{
		unplacedArmies = 0;
		numTerritories = 0;
		cardArray = new ArrayList<Card>();
		this.name = name;
	}
	
	public int getUnplacedArmies()
	{
		return unplacedArmies;
	}
	public int getNumTerritories()
	{
		return numTerritories;
	}
	public void setUnplacedArmies(int num)
	{
		unplacedArmies = num;
	}
	public void setNumTerritories(int num)
	{
		numTerritories = num;
	}
	public String getName()
	{
		return name;
	}
	public ArrayList<Card> getCards()
	{
		return cardArray;
	}
	public void addCard(Card card)
	{
		cardArray.add(card);
	}
}