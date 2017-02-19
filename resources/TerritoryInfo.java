package resources;

import java.util.ArrayList;
import java.awt.Color;

public class TerritoryInfo extends Territory
{
	public static final Color PLAYER_COLOR_0 = Color.GRAY;
	public static final Color PLAYER_COLOR_1 = Color.CYAN;
	public static final Color PLAYER_COLOR_2 = Color.BLUE;
	public static final Color PLAYER_COLOR_3 = Color.ORANGE;
	public static final Color PLAYER_COLOR_4 = Color.GREEN;
	public static final Color PLAYER_COLOR_5 = Color.MAGENTA;
	public static final Color COMPUTER_COLOR = Color.DARK_GRAY;
	private int armies;
	private String owner;
	private ArrayList<String> borderingTerritories;
	
	public TerritoryInfo (String input, ArrayList<String> list)
	{
		super(input);
		setColor(null);
		armies = 0;
		owner = "";
		borderingTerritories = list;
	}
	
	public String getOwner()
	{
		return owner;
	}
	
	public void setOwner(String input, int playerNum)
	{
		owner = input;
		if (playerNum==0)
			setColor(PLAYER_COLOR_0);
		else if (playerNum==1)
			setColor(PLAYER_COLOR_1);
		else if (playerNum==2)
			setColor(PLAYER_COLOR_2);
		else if (playerNum==3)
			setColor(PLAYER_COLOR_3);
		else if (playerNum==4)
			setColor(PLAYER_COLOR_4);
		else if (playerNum==5)
			setColor(PLAYER_COLOR_5);
	}
	
	public int getArmies()
	{
		return armies;
	}
	
	public String returnInfo()
	{
		if (armies==0)
			return super.toString()+" - Empty";
		if (armies==1)
			return super.toString()+" - "+owner+" - "+armies+" Army";
		return super.toString()+" - "+owner+" - "+armies+" Armies";
	}
	
	public void setArmies(int input)
	{
		armies = input;
	}
	public ArrayList<String> getBorderingTerritories()
	{
		return borderingTerritories;
	}
	public String toString()
	{
		if (armies == 0)
			return "";
		return String.valueOf(armies);
	}
}