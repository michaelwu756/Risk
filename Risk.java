import info.gridworld.actor.*;
import info.gridworld.grid.*;
import info.gridworld.gui.WorldFrame;

import resources.*;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class Risk extends ActorWorld
{
	private static Risk app;
	private TerritoryInfo previousInfo;
	private ArrayList<Player> playerList = new ArrayList<Player>();
	private ArrayList<Card> deck = new ArrayList<Card>(), garbageDeck = new ArrayList<Card>();
	private boolean claimTerritories, reinforceTerritories, territoryTaken, fortified, gameOver;
	private int setValue;
	
	public Risk()
    {
		super(new BoundedGrid<Actor>(32,46));
		int num;
		Object[] possibilities = {3, 4, 5, 6};
		Object numObj = JOptionPane.showInputDialog(null,"Please Select Number Of Players:","Risk",JOptionPane.DEFAULT_OPTION,null,possibilities, possibilities[3]);
		if (numObj!=null)
		{
			num = (int) numObj;
			String name;
			for (int x=1;x<num+1;x++)
			{
				name = JOptionPane.showInputDialog(null,"Enter name for player "+String.valueOf(x)+":", "Risk", JOptionPane.PLAIN_MESSAGE);
				if (name == null || name.equals(""))
					playerList.add(new Player("Player "+String.valueOf(x)));
				else
					playerList.add(new Player(name));
				getPlayer(x-1).setUnplacedArmies(20+5*(6-num));
			}
			createMap();
			fillDeck();
		
			setNumPlayers(num);
			setNumPhase(1);
			claimTerritories = true;
			reinforceTerritories = false;
			territoryTaken = false;
			fortified=false;
			gameOver = false;
			setValue = 4;
			show();
			getFrame().getDisplay().setToolTipsEnabled(false);
			
		}
    }
	public Risk(int num)
    {
		super(new BoundedGrid<Actor>(32,46));
		String name;
		for (int x=1;x<num+1;x++)
		{
			name = JOptionPane.showInputDialog(null,"Enter name for player "+String.valueOf(x)+":", "Risk", JOptionPane.PLAIN_MESSAGE);
			if (name == null || name.equals(""))
				playerList.add(new Player("Player "+String.valueOf(x)));
			else
				playerList.add(new Player(name));
			getPlayer(x-1).setUnplacedArmies(20+5*(6-num));
		}
		createMap();
		fillDeck();
		
		setNumPlayers(num);
		setNumPhase(1);
		claimTerritories = true;
		reinforceTerritories = false;
		territoryTaken = false;
		fortified=false;
		gameOver = false;
		setValue = 4;
		show();
		getFrame().getDisplay().setToolTipsEnabled(false);
		
    }	
	public void show()
    {
        setMessage(phaseInfo());
        super.show();
    }
	public void createMap()
	{
		ArrayList<String> list;
		//North America
		{
			//Alaska
			{
				list = new ArrayList<String>();
				list.add("Northwest Territory");
				list.add("Alberta");
				list.add("Kamchatka");
				for (int x=0;x<3;x++)
					for(int y=0;y<4;y++)
						if (x!=1 || y!=1)
							add(new Location(3+y,2+x), new Territory("Alaska"));
				add(new Location(4,3), new TerritoryInfo("Alaska", list));
				add(new Location(5,1), new Territory("Alaska"));
				add(new Location(6,1), new Territory("Alaska"));
			}
			//Northwest Territory
			{
				list = new ArrayList<String>();
				list.add("Alaska");
				list.add("Alberta");
				list.add("Greenland");
				list.add("Ontario");
				for (int x=0;x<6;x++)
					for(int y=0;y<2;y++)
						if (x!=2 || y!=0)
							add(new Location(4+y,5+x), new Territory("Northwest Territory"));
				add(new Location(4,7), new TerritoryInfo("Northwest Territory", list));
				add(new Location(4,11), new Territory("Northwest Territory"));
				add(new Location(3,10), new Territory("Northwest Territory"));
			}
			//Alberta
			{
				list = new ArrayList<String>();
				list.add("Alaska");
				list.add("Western United States");
				list.add("Northwest Territory");
				list.add("Ontario");
				for (int x=0;x<4;x++)
					for(int y=0;y<3;y++)
						if (x!=2 || y!=1)
							add(new Location(6+y,5+x), new Territory("Alberta"));
				add(new Location(7,7), new TerritoryInfo("Alberta", list));
			}
			//Ontario
			{
				list = new ArrayList<String>();
				list.add("Northwest Territory");
				list.add("Alberta");
				list.add("Western United States");
				list.add("Eastern United States");
				list.add("Greenland");
				list.add("Quebec");
				for (int x=0;x<3;x++)
					for(int y=0;y<2;y++)
						if (x!=1 || y!=1)
							add(new Location(7+y,9+x), new Territory("Ontario"));
				add(new Location(8,10), new TerritoryInfo("Ontario", list));
				add(new Location(6,9), new Territory("Ontario"));
				add(new Location(9,11), new Territory("Ontario"));
			}
			//Quebec
			{
				list = new ArrayList<String>();
				list.add("Eastern United States");
				list.add("Greenland");
				list.add("Ontario");
				for (int x=0;x<2;x++)
					for(int y=0;y<4;y++)
						if (x!=1 || y!=1)
							add(new Location(6+y,12+x), new Territory("Quebec"));
				add(new Location(7,13), new TerritoryInfo("Quebec", list));
				add(new Location(7,14), new Territory("Quebec"));
				add(new Location(8,14), new Territory("Quebec"));
			}
			//Eastern United States
			{
				list = new ArrayList<String>();
				list.add("Western United States");
				list.add("Quebec");
				list.add("Ontario");
				for (int x=0;x<4;x++)
					for(int y=0;y<3;y++)
						if (x!=1 || y!=1)
							add(new Location(10+y,10+x-y), new Territory("Eastern United States"));
				add(new Location(11,10), new TerritoryInfo("Eastern United States", list));
				add(new Location(13,8), new Territory("Eastern United States"));
				add(new Location(13,11), new Territory("Eastern United States"));
				add(new Location(9,10), new Territory("Eastern United States"));
			}
			//Western United States
			{
				list = new ArrayList<String>();
				list.add("Central America");
				list.add("Alberta");
				list.add("Eastern United States");
				list.add("Ontario");
				for (int x=0;x<3;x++)
					for(int y=0;y<3;y++)
						if (x!=1 || y!=1)
							add(new Location(9+y,6+x), new Territory("Western United States"));
				add(new Location(10,7), new TerritoryInfo("Western United States", list));
				add(new Location(10,9), new Territory("Western United States"));
				add(new Location(9,9), new Territory("Western United States"));
				add(new Location(11,5), new Territory("Western United States"));
				add(new Location(10,5), new Territory("Western United States"));
				add(new Location(12,6), new Territory("Western United States"));
				add(new Location(12,7), new Territory("Western United States"));
			}
			//Central America
			{
				list = new ArrayList<String>();
				list.add("Western United States");
				list.add("Eastern United States");
				list.add("Venezuela");
				for (int x=0;x<2;x++)
					for(int y=0;y<3;y++)
						if (x!=1 || y!=1)
							add(new Location(14+y,8+x), new Territory("Central America"));
				add(new Location(14,7), new TerritoryInfo("Central America", list));
				add(new Location(13,7), new Territory("Central America"));
				add(new Location(13,6), new Territory("Central America"));
			}
			//Greenland
			{
				list = new ArrayList<String>();
				list.add("Iceland");
				list.add("Northwest Territory");
				list.add("Quebec");
				list.add("Ontario");
				for (int x=0;x<5;x++)
					for(int y=0;y<3;y++)
						if (x!=2 || y!=1)
							add(new Location(2+y,14+x), new Territory("Greenland"));
				add(new Location(3,16), new TerritoryInfo("Greenland", list));
				add(new Location(2,13), new Territory("Greenland"));
				add(new Location(3,13), new Territory("Greenland"));
				add(new Location(5,14), new Territory("Greenland"));
				add(new Location(5,15), new Territory("Greenland"));
				add(new Location(5,16), new Territory("Greenland"));
				add(new Location(6,15), new Territory("Greenland"));
				add(new Location(1,15), new Territory("Greenland"));
				add(new Location(1,16), new Territory("Greenland"));
				add(new Location(1,17), new Territory("Greenland"));
			}
		}
		//South America
		{
			//Venezuela
			{
				list = new ArrayList<String>();
				list.add("Central America");
				list.add("Brazil");
				list.add("Peru");
				for (int x=0;x<3;x++)
					for(int y=0;y<2;y++)
						if (x!=0 || y!=1)
							add(new Location(16+y,10+x), new Territory("Venezuela"));
				add(new Location(17,10), new TerritoryInfo("Venezuela", list));
				add(new Location(17,9), new Territory("Venezuela"));
				add(new Location(18,9), new Territory("Venezuela"));
				add(new Location(18,10), new Territory("Venezuela"));
				add(new Location(17,13), new Territory("Venezuela"));
			}
			//Brazil
			{
				list = new ArrayList<String>();
				list.add("Venezuela");
				list.add("Peru");
				list.add("Argentina");
				list.add("North Africa");
				for (int x=0;x<4;x++)
					for(int y=0;y<3;y++)
						if (x!=1 || y!=1)
							add(new Location(19+y,13+x), new Territory("Brazil"));
				add(new Location(20,14), new TerritoryInfo("Brazil", list));
				for (int x=0;x<4;x++)
					add(new Location(18,11+x), new Territory("Brazil"));
				for (int x=0;x<3;x++)
					add(new Location(19,10+x), new Territory("Brazil"));
				for (int y=0;y<3;y++)
					add(new Location(22+y,14), new Territory("Brazil"));
				add(new Location(22,15), new Territory("Brazil"));
				add(new Location(20,12), new Territory("Brazil"));
			}
			//Peru
			{
				list = new ArrayList<String>();
				list.add("Brazil");
				list.add("Venezuela");
				list.add("Argentina");
				for (int x=0;x<3;x++)
					for(int y=0;y<3;y++)
						if (x!=1 || y!=1)
							add(new Location(20+y,9+x+y), new Territory("Peru"));
				add(new Location(21,11), new TerritoryInfo("Peru", list));
				add(new Location(19,9), new Territory("Peru"));
			}
			//Argentina
			{
				list = new ArrayList<String>();
				list.add("Brazil");
				list.add("Peru");
				for (int x=0;x<4;x++)
					for(int y=0;y<3;y++)
						if (x!=2 || y!=1)
							add(new Location(23+y,10+x), new Territory("Argentina"));
				add(new Location(24,12), new TerritoryInfo("Argentina", list));
				for (int x=0;x<3;x++)
					for(int y=0;y<2;y++)
						if (x!=2 || y!=1)
							add(new Location(26+y,10+x), new Territory("Argentina"));
				add(new Location(28,11), new Territory("Argentina"));
				add(new Location(29,11), new Territory("Argentina"));
				add(new Location(29,12), new Territory("Argentina"));
			}
		}
		//Europe
		{
			//Iceland
			{
				list = new ArrayList<String>();
				list.add("Greenland");
				list.add("Great Britain");
				list.add("Scandinavia");
				for (int x=0;x<3;x++)
					for(int y=0;y<2;y++)
						if (x!=1 || y!=1)
							add(new Location(5+y,18+x), new Territory("Iceland"));
				add(new Location(6,19), new TerritoryInfo("Iceland", list));
				add(new Location(7,19), new Territory("Iceland"));
			}
			//Great Britain
			{
				list = new ArrayList<String>();
				list.add("Iceland");
				list.add("Scandinavia");
				list.add("Western Europe");
				list.add("Northern Europe");
				for (int x=0;x<3;x++)
					for(int y=0;y<2;y++)
						if (x!=1 || y!=0)
							add(new Location(9+y,17+x), new Territory("Great Britain"));
				add(new Location(9,18), new TerritoryInfo("Great Britain", list));
				add(new Location(8,18), new Territory("Great Britain"));
				add(new Location(8,19), new Territory("Great Britain"));
				add(new Location(10,20), new Territory("Great Britain"));
			}
			//Western Europe
			{
				list = new ArrayList<String>();
				list.add("Great Britain");
				list.add("Northern Europe");
				list.add("Southern Europe");
				list.add("North Africa");
				for (int x=0;x<3;x++)
					for(int y=0;y<2;y++)
						if (x!=1 || y!=1)
							add(new Location(12+y,19+x), new Territory("Western Europe"));
				add(new Location(13,20), new TerritoryInfo("Western Europe", list));
				for (int x=0;x<3;x++)
					for(int y=0;y<2;y++)
						add(new Location(14+y,18+x), new Territory("Western Europe"));
				add(new Location(13,18), new Territory("Western Europe"));
				add(new Location(11,20), new Territory("Western Europe"));
			}
			//Southern Europe
			{
				list = new ArrayList<String>();
				list.add("Western Europe");
				list.add("Northern Europe");
				list.add("Ukraine");
				list.add("North Africa");
				list.add("Middle East");
				list.add("Egypt");
				for (int x=0;x<3;x++)
					for(int y=0;y<3;y++)
						if (x!=1 || y!=1)
							add(new Location(12+y,22+x), new Territory("Southern Europe"));
				add(new Location(13,23), new TerritoryInfo("Southern Europe", list));
				add(new Location(11,24), new Territory("Southern Europe"));
				add(new Location(15,24), new Territory("Southern Europe"));
			}
			//Northern Europe
			{
				list = new ArrayList<String>();
				list.add("Great Britain");
				list.add("Scandinavia");
				list.add("Ukraine");
				list.add("Southern Europe");
				list.add("Western Europe");
				for (int x=0;x<3;x++)
					for(int y=0;y<2;y++)
						if (x!=1 || y!=1)
							add(new Location(9+y,22+x), new Territory("Northern Europe"));
				add(new Location(10,23), new TerritoryInfo("Northern Europe", list));
				for (int x=0;x<3;x++)
					add(new Location(11,21+x), new Territory("Northern Europe"));
				add(new Location(10,21), new Territory("Northern Europe"));
				add(new Location(8,22), new Territory("Northern Europe"));
			}
			//Scandinavia
			{
				list = new ArrayList<String>();
				list.add("Iceland");
				list.add("Great Britain");
				list.add("Ukraine");
				list.add("Northern Europe");
				for (int x=0;x<2;x++)
					for(int y=0;y<3;y++)
						if (x!=0 || y!=1)
							add(new Location(4+y,23+x), new Territory("Scandinavia"));
				add(new Location(5,23), new TerritoryInfo("Scandinavia", list));
				for (int x=0;x<2;x++)
					for(int y=0;y<3;y++)
						if (x!=0 || y!=0)
							add(new Location(5+y,21+x), new Territory("Scandinavia"));
				add(new Location(7,23), new Territory("Scandinavia"));
				add(new Location(8,23), new Territory("Scandinavia"));
			}
			//Ukraine
			{
				list = new ArrayList<String>();
				list.add("Scandinavia");
				list.add("Northern Europe");
				list.add("Southern Europe");
				list.add("Middle East");
				list.add("Afghanistan");
				list.add("Ural");
				for (int x=0;x<3;x++)
					for(int y=0;y<7;y++)
						if (x!=2 || y!=4)
							add(new Location(4+y,25+x), new Territory("Ukraine"));
				add(new Location(8,27), new TerritoryInfo("Ukraine", list));
				for (int x=0;x<4;x++)
					for(int y=0;y<2;y++)
						add(new Location(11+y,25+x), new Territory("Ukraine"));
				for (int x=0;x<2;x++)
					for(int y=0;y<6;y++)
						add(new Location(4+y,28+x), new Territory("Ukraine"));
				add(new Location(7,24), new Territory("Ukraine"));
				add(new Location(8,24), new Territory("Ukraine"));
				add(new Location(13,27), new Territory("Ukraine"));
				add(new Location(13,28), new Territory("Ukraine"));
				add(new Location(8,30), new Territory("Ukraine"));
			}
		}
		//Africa
		{
			//North Africa
			{
				list = new ArrayList<String>();
				list.add("Brazil");
				list.add("Western Europe");
				list.add("Southern Europe");
				list.add("Egypt");
				list.add("Congo");
				list.add("East Africa");
				for (int x=0;x<4;x++)
					for(int y=0;y<6;y++)
						if (x!=2 || y!=3)
							add(new Location(16+y,19+x), new Territory("North Africa"));
				add(new Location(19,21), new TerritoryInfo("North Africa", list));
				for (int x=0;x<2;x++)
					for(int y=0;y<3;y++)
						if (x!=1 || y!=2)
							add(new Location(19+y,23+x), new Territory("North Africa"));
				add(new Location(22,23), new Territory("North Africa"));
				add(new Location(15,22), new Territory("North Africa"));
			}
			//Congo
			{
				list = new ArrayList<String>();
				list.add("North Africa");
				list.add("East Africa");
				list.add("South Africa");
				for (int x=0;x<2;x++)
					for(int y=0;y<3;y++)
						if (x!=0 || y!=1)
							add(new Location(21+y,24+x), new Territory("Congo"));
				add(new Location(22,24), new TerritoryInfo("Congo", list));
				add(new Location(22,26), new Territory("Congo"));
				add(new Location(24,25), new Territory("Congo"));
				add(new Location(22,23), new Territory("Congo"));
				add(new Location(23,23), new Territory("Congo"));
			}
			//South Africa
			{
				list = new ArrayList<String>();
				list.add("Madagascar");
				list.add("Congo");
				list.add("East Africa");
				for (int x=0;x<4;x++)
					for(int y=0;y<3;y++)
						if (x!=2 || y!=1)
							add(new Location(25+y,23+x), new Territory("South Africa"));
				add(new Location(26,25), new TerritoryInfo("South Africa", list));
				for (int x=0;x<3;x++)
					for(int y=0;y<2;y++)
						if (x!=2 || y!=1)
							add(new Location(28+y,24+x), new Territory("South Africa"));
				add(new Location(24,23), new Territory("South Africa"));
				add(new Location(24,24), new Territory("South Africa"));
				add(new Location(25,27), new Territory("South Africa"));
				add(new Location(26,27), new Territory("South Africa"));
			}
			//Madagascar
			{
				list = new ArrayList<String>();
				list.add("East Africa");
				list.add("South Africa");
				for (int x=0;x<2;x++)
					for(int y=0;y<3;y++)
						if (x!=1 || y!=0)
							add(new Location(26+y,28+x), new Territory("Madagascar"));
				add(new Location(26,29), new TerritoryInfo("Madagascar", list));
				add(new Location(25,29), new Territory("Madagascar"));
				add(new Location(25,30), new Territory("Madagascar"));
			}
			//East Africa
			{
				list = new ArrayList<String>();
				list.add("South Africa");
				list.add("Madagascar");
				list.add("Congo");
				list.add("North Africa");
				list.add("Egypt");
				list.add("Middle East");
				for (int x=0;x<3;x++)
					for(int y=0;y<2;y++)
						add(new Location(19+y,25+x), new Territory("East Africa"));
				add(new Location(21,26), new Territory("East Africa"));
				add(new Location(21,27), new TerritoryInfo("East Africa", list));
				for (int x=0;x<2;x++)
					for(int y=0;y<3;y++)
						add(new Location(21+y,28+x-y), new Territory("East Africa"));
				add(new Location(24,26), new Territory("East Africa"));
				add(new Location(24,27), new Territory("East Africa"));
			}
			//Egypt
			{
				list = new ArrayList<String>();
				list.add("North Africa");
				list.add("Southern Europe");
				list.add("Middle East");
				list.add("East Africa");
				for (int x=0;x<4;x++)
					for(int y=0;y<2;y++)
						if (x!=1 || y!=0)
							add(new Location(17+y,23+x), new Territory("Egypt"));
				add(new Location(17,24), new TerritoryInfo("Egypt", list));
				add(new Location(16,23), new Territory("Egypt"));
				add(new Location(16,24), new Territory("Egypt"));
			}
		}
		//Asia
		{
			//Middle East
			{
				list = new ArrayList<String>();
				list.add("Southern Europe");
				list.add("Ukraine");
				list.add("Egypt");
				list.add("East Africa");
				list.add("India");
				list.add("Afghanistan");
				for (int x=0;x<4;x++)
					for(int y=0;y<5;y++)
						if (x!=1 || y!=2)
							add(new Location(14+y,27+x), new Territory("Middle East"));
				add(new Location(16,28), new TerritoryInfo("Middle East", list));
				for (int x=0;x<2;x++)
					for(int y=0;y<2;y++)
						add(new Location(14+y,25+x), new Territory("Middle East"));
				for (int x=0;x<3;x++)
					add(new Location(19,28+x), new Territory("Middle East"));
			}
			//Afghanistan
			{
				list = new ArrayList<String>();
				list.add("Ukraine");
				list.add("Ural");
				list.add("China");
				list.add("India");
				list.add("Middle East");
				for (int x=0;x<4;x++)
					for(int y=0;y<3;y++)
						if (x!=1 || y!=1)
							add(new Location(10+y,29+x), new Territory("Afghanistan"));
				add(new Location(11,30), new TerritoryInfo("Afghanistan", list));
				for (int x=0;x<3;x++)
					add(new Location(13,30+x), new Territory("Afghanistan"));
				add(new Location(9,30), new Territory("Afghanistan"));
				add(new Location(10,28), new Territory("Afghanistan"));
			}
			//Ural
			{
				list = new ArrayList<String>();
				list.add("Ukraine");
				list.add("Afghanistan");
				list.add("China");
				list.add("Siberia");
				for (int x=0;x<3;x++)
					for(int y=0;y<2;y++)
						if (x!=1 || y!=1)
							add(new Location(6+y,30+x), new Territory("Ural"));
				add(new Location(7,31), new TerritoryInfo("Ural", list));
				for (int x=0;x<3;x++)
					for(int y=0;y<2;y++)
						if (x!=2 || y!=0)
							add(new Location(8+y,31+x), new Territory("Ural"));
				for (int y=0;y<3;y++)
					add(new Location(3+y,30), new Territory("Ural"));
				add(new Location(5,31), new Territory("Ural"));
			}
			//Siberia
			{
				list = new ArrayList<String>();
				list.add("Ural");
				list.add("China");
				list.add("Mongolia");
				list.add("Yakutsk");
				list.add("Irkutsk");
				for (int x=0;x<3;x++)
					for(int y=0;y<4;y++)
						if (x!=1 || y!=2)
							add(new Location(2+y,32+x), new Territory("Siberia"));
				add(new Location(4,33), new TerritoryInfo("Siberia", list));
				for (int x=0;x<2;x++)
					for(int y=0;y<4;y++)
						add(new Location(6+y,33+x), new Territory("Siberia"));
				for (int y=0;y<6;y++)
					if (y!=2)
						add(new Location(2+y,35), new Territory("Siberia"));
				add(new Location(3,31), new Territory("Siberia"));
				add(new Location(4,31), new Territory("Siberia"));
			}
			//Yakutsk
			{
				list = new ArrayList<String>();
				list.add("Siberia");
				list.add("Kamchatka");
				list.add("Irkutsk");
				for (int x=0;x<2;x++)
					for(int y=0;y<4;y++)
						if (x!=1 || y!=2)
							add(new Location(2+y,36+x), new Territory("Yakutsk"));
				add(new Location(4,37), new TerritoryInfo("Yakutsk", list));
				for(int y=0;y<3;y++)
					add(new Location(2+y,38), new Territory("Yakutsk"));
				add(new Location(4,35), new Territory("Yakutsk"));
			}
			//Kamchatka
			{
				list = new ArrayList<String>();
				list.add("Alaska");
				list.add("Mongolia");
				list.add("Irkutsk");
				list.add("Yakutsk");
				list.add("Japan");
				for (int x=0;x<4;x++)
					for(int y=0;y<3;y++)
						if (x!=1 || y!=1)
							add(new Location(3+y,39+x), new Territory("Kamchatka"));
				add(new Location(4,40), new TerritoryInfo("Kamchatka", list));
				for(int y=0;y<3;y++)
					add(new Location(5+y,38),new Territory("Kamchatka"));
				for(int y=0;y<4;y++)
					add(new Location(6+y,39), new Territory("Kamchatka"));
				add(new Location(6,41), new Territory("Kamchatka"));
				add(new Location(7,41), new Territory("Kamchatka"));
				add(new Location(4,43), new Territory("Kamchatka"));
			}
			//Irkutsk
			{
				list = new ArrayList<String>();
				list.add("Siberia");
				list.add("Yakutsk");
				list.add("Kamchatka");
				list.add("Mongolia");
				for (int x=0;x<2;x++)
					for(int y=0;y<4;y++)
						if (x!=0 || y!=2)
							add(new Location(6+y,36+x), new Territory("Irkutsk"));
				add(new Location(8,36), new TerritoryInfo("Irkutsk", list));
				add(new Location(8,38), new Territory("Irkutsk"));
				add(new Location(8,35), new Territory("Irkutsk"));
				add(new Location(9,35), new Territory("Irkutsk"));
			}
			//Mongolia
			{
				list = new ArrayList<String>();
				list.add("Japan");
				list.add("China");
				list.add("Kamchatka");
				list.add("Irkutsk");
				list.add("Siberia");
				for (int x=0;x<5;x++)
					for(int y=0;y<2;y++)
						if (x!=2 || y!=0)
							add(new Location(10+y,35+x), new Territory("Mongolia"));
				add(new Location(10,37), new TerritoryInfo("Mongolia", list));
				for (int x=0;x<3;x++)
					add(new Location(12,37+x), new Territory("Mongolia"));
				add(new Location(9,38), new Territory("Mongolia"));
			}
			//Japan
			{
				list = new ArrayList<String>();
				list.add("Kamchatka");
				list.add("Mongolia");
				for (int x=0;x<2;x++)
					for(int y=0;y<5;y++)
						if (x!=0 || y!=3)
							add(new Location(8+y,41+x), new Territory("Japan"));
				add(new Location(11,41), new TerritoryInfo("Japan", list));
				add(new Location(11,40), new Territory("Japan"));
				add(new Location(12,40), new Territory("Japan"));
			}
			//China
			{
				list = new ArrayList<String>();
				list.add("Siam");
				list.add("India");
				list.add("Afghanistan");
				list.add("Ural");
				list.add("Siberia");
				list.add("Mongolia");
				for (int x=0;x<5;x++)
					for(int y=0;y<2;y++)
						if (x!=1 || y!=1)
							add(new Location(13+y,35+x), new Territory("China"));
				add(new Location(14,36), new TerritoryInfo("China", list));
				for (int x=0;x<2;x++)
					for(int y=0;y<5;y++)
						add(new Location(10+y,33+x), new Territory("China"));
				for (int x=0;x<4;x++)
					add(new Location(15,36+x), new Territory("China"));
				add(new Location(16,38), new Territory("China"));
				add(new Location(12,35), new Territory("China"));
				add(new Location(12,36), new Territory("China"));
			}
			//India
			{
				list = new ArrayList<String>();
				list.add("Middle East");
				list.add("Afghanistan");
				list.add("China");
				list.add("Siam");
				for (int x=0;x<3;x++)
					for(int y=0;y<4;y++)
						if (x!=1 || y!=1)
							add(new Location(15+y,32+x), new Territory("India"));
				add(new Location(16,33), new TerritoryInfo("India", list));
				for(int y=0;y<3;y++)	
					add(new Location(14+y,31), new Territory("India"));
				add(new Location(14,32), new Territory("India"));
				add(new Location(15,35), new Territory("India"));
				add(new Location(16,35), new Territory("India"));
				add(new Location(19,33), new Territory("India"));
				add(new Location(20,33), new Territory("India"));
			}
			//Siam
			{
				list = new ArrayList<String>();
				list.add("India");
				list.add("China");
				list.add("Indonesia");
				list.add("New Guinea");
				for (int x=0;x<2;x++)
					for(int y=0;y<3;y++)
						if (x!=1 || y!=1)
							add(new Location(16+y,36+x), new Territory("Siam"));
				add(new Location(17,37), new TerritoryInfo("Siam", list));
				for(int y=0;y<3;y++)
					add(new Location(17+y,38), new Territory("Siam"));
				add(new Location(19,37), new Territory("Siam"));
				add(new Location(17,35), new Territory("Siam"));
			}
		}
		//Australia
		{
			//Indonesia
			{
				list = new ArrayList<String>();
				list.add("Siam");
				list.add("New Guinea");
				list.add("Western Australia");
				for (int x=0;x<4;x++)
					for(int y=0;y<2;y++)
						if (x!=2 || y!=1)
							add(new Location(21+y,35+x), new Territory("Indonesia"));
				add(new Location(22,37), new TerritoryInfo("Indonesia", list));
				for (int x=0;x<3;x++)
					for(int y=0;y<2;y++)
						if (x!=0 || y!=1)
							add(new Location(23+y,36+x), new Territory("Indonesia"));
				add(new Location(22,39), new Territory("Indonesia"));
				add(new Location(23,39), new Territory("Indonesia"));
				add(new Location(20,38), new Territory("Indonesia"));
			}
			//New Guinea
			{
				list = new ArrayList<String>();
				list.add("Eastern Australia");
				list.add("Indonesia");
				for (int x=0;x<2;x++)
					for(int y=0;y<2;y++)
						if (x!=1 || y!=1)
							add(new Location(20+y,40+x), new Territory("New Guinea"));
				add(new Location(21,41), new TerritoryInfo("New Guinea", list));
				for (int x=0;x<2;x++)
					for(int y=0;y<2;y++)
						if (x!=0 || y!=0)
							add(new Location(21+y,41+x), new Territory("New Guinea"));
			}
			//Western Australia
			{
				list = new ArrayList<String>();
				list.add("Eastern Australia");
				list.add("Indonesia");
				for (int x=0;x<3;x++)
					for(int y=0;y<4;y++)
						if (x!=1 || y!=1)
							add(new Location(25+y,38+x), new Territory("Western Australia"));
				add(new Location(26,39), new TerritoryInfo("Western Australia", list));
				for(int y=0;y<3;y++)
					add(new Location(26+y,41), new Territory("Western Australia"));
				add(new Location(24,40), new Territory("Western Australia"));
			}
			//Eastern Australia
			{
				list = new ArrayList<String>();
				list.add("New Guinea");
				list.add("Western Australia");
				for (int x=0;x<3;x++)
					for(int y=0;y<4;y++)
						if (x!=1 || y!=1)
							add(new Location(25+y,42+x), new Territory("Eastern Australia"));
				add(new Location(26,43), new TerritoryInfo("Eastern Australia", list));
				for (int x=0;x<3;x++)
					for(int y=0;y<2;y++)		
						add(new Location(23+y,41+x), new Territory("Eastern Australia"));
				add(new Location(25,41), new Territory("Eastern Australia"));
				add(new Location(29,42), new Territory("Eastern Australia"));
				add(new Location(29,43), new Territory("Eastern Australia"));
			}
		}
	}		
	public void fillDeck()
	{
		deck.add(new Card("Alaska",Card.INFANTRY));
		deck.add(new Card("Alberta",Card.CAVALRY));
		deck.add(new Card("Central America",Card.ARTILLERY));
		deck.add(new Card("Eastern United States",Card.INFANTRY));
		deck.add(new Card("Greenland",Card.CAVALRY));
		deck.add(new Card("Northwest Territory",Card.ARTILLERY));
		deck.add(new Card("Ontario",Card.INFANTRY));
		deck.add(new Card("Quebec",Card.CAVALRY));
		deck.add(new Card("Western United States",Card.ARTILLERY));
		
		deck.add(new Card("Argentina",Card.INFANTRY));
		deck.add(new Card("Brazil",Card.CAVALRY));
		deck.add(new Card("Peru",Card.ARTILLERY));
		deck.add(new Card("Venezuela",Card.INFANTRY));
		
		deck.add(new Card("Great Britain",Card.CAVALRY));
		deck.add(new Card("Iceland",Card.ARTILLERY));
		deck.add(new Card("Northern Europe",Card.INFANTRY));
		deck.add(new Card("Scandinavia",Card.CAVALRY));
		deck.add(new Card("Southern Europe",Card.ARTILLERY));
		deck.add(new Card("Ukraine",Card.INFANTRY));
		deck.add(new Card("Western Europe",Card.CAVALRY));
		
		deck.add(new Card("Congo",Card.ARTILLERY));
		deck.add(new Card("East Africa",Card.INFANTRY));
		deck.add(new Card("Egypt",Card.CAVALRY));
		deck.add(new Card("Madagascar",Card.ARTILLERY));
		deck.add(new Card("North Africa",Card.INFANTRY));
		deck.add(new Card("South Africa",Card.CAVALRY));
		
		deck.add(new Card("Afghanistan",Card.ARTILLERY));
		deck.add(new Card("China",Card.INFANTRY));
		deck.add(new Card("India",Card.CAVALRY));
		deck.add(new Card("Irkutsk",Card.ARTILLERY));
		deck.add(new Card("Japan",Card.INFANTRY));
		deck.add(new Card("Kamchatka",Card.CAVALRY));
		deck.add(new Card("Middle East",Card.ARTILLERY));
		deck.add(new Card("Mongolia",Card.INFANTRY));
		deck.add(new Card("Siam",Card.CAVALRY));
		deck.add(new Card("Siberia",Card.ARTILLERY));
		deck.add(new Card("Ural",Card.INFANTRY));
		deck.add(new Card("Yakutsk",Card.CAVALRY));
		
		deck.add(new Card("Eastern Australia",Card.ARTILLERY));
		deck.add(new Card("Indonesia",Card.INFANTRY));
		deck.add(new Card("New Guinea",Card.CAVALRY));
		deck.add(new Card("Western Australia",Card.ARTILLERY));
		deck.add(new Card("Wild Card",Card.WILD_CARD));
		deck.add(new Card("Wild Card",Card.WILD_CARD));
		Collections.shuffle(deck);
	}
	public void newInstance()
	{
		Object[] possibilities = {3, 4, 5, 6};
		Object num = JOptionPane.showInputDialog(null,"Please Select Number Of Players:","New Game",JOptionPane.DEFAULT_OPTION,null,possibilities, possibilities[3]);
		if (num==null){}
		else
		{
			Risk next=new Risk((int)num);
			getFrame().dispose();
		}
	}
	public void showSelector()
	{
		CardDialog dialog = new CardDialog(this);
	}	
	public void nextPhase()
	{
		if (claimTerritories == true)
		{
			ArrayList<Actor> actors = getAllActors();
			boolean filled = true;
			for (Actor x:actors)
			{
				if (x instanceof TerritoryInfo)
				{
					TerritoryInfo y = (TerritoryInfo)x;
					if (y.getArmies()==0)
					{
						filled = false;
						break;
					}
				}
			}
			if (filled == true)
			{
				claimTerritories = false;
				reinforceTerritories = true;
			}
			super.nextPhase();
		}
		else if (reinforceTerritories == true)
		{
			boolean allArmiesPlaced=true;
			for(Player x:playerList)
			{
				if (x.getUnplacedArmies()!=0)
				{
					allArmiesPlaced=false;
					break;
				}
			}
			if (allArmiesPlaced == true)
			{
				setNumPhase(3);
				setPlayerNum(0);
				reinforceTerritories=false;
				getPlayer(getPlayerNum()).setUnplacedArmies(calculateUnplacedArmies());
				getFrame().getGUIControl().getCardsButton().setEnabled(true);
			}
			else
			{
				super.nextPhase();
			}
		}
		else
		{
			super.nextPhase();
			if(getPhaseNum()==0)
			{
				if(fortified==true)
					fortified=false;
				while(getPlayer(getPlayerNum()).getNumTerritories()==0)
				{
					setPlayerNum(getPlayerNum()+1);
				}
				getPlayer(getPlayerNum()).setUnplacedArmies(calculateUnplacedArmies());
				setMessage(phaseInfo()+"\n"+getPlayer(getPlayerNum()).getName()+" recieved "+String.valueOf(calculateUnplacedArmies())+" armies");
				getFrame().getGUIControl().getDoneButton().setEnabled(false);
				getFrame().getGUIControl().getCardsButton().setEnabled(true);
				getFrame().repaint();
				if (getPlayer(getPlayerNum()).getCards().size()>=5)
				{
					CardDialog dialog = new CardDialog(this);
					while (getPlayer(getPlayerNum()).getCards().size()>=5)
					{
						dialog.setVisible(true);
					}
				}
			}
			else if(getPhaseNum()==1)
			{
				previousInfo=null;
				getFrame().getGUIControl().getCardsButton().setEnabled(false);
				setMessage(phaseInfo());
			}
			else if(getPhaseNum()==2)
			{
				previousInfo=null;
				setMessage(phaseInfo());
				if (territoryTaken==true)
				{
					getPlayer(getPlayerNum()).addCard(deck.remove(0));
					if (deck.size()==0)
					{
						deck = garbageDeck;
						garbageDeck = new ArrayList<Card>();
						Collections.shuffle(deck);
					}
					CardDialog dialog = new CardDialog(this, getPlayer(getPlayerNum()).getCards().get(getPlayer(getPlayerNum()).getCards().size()-1));
					territoryTaken=false;
				}
			}
		}
	}	
	public boolean locationClicked(Location loc)
    {
		TerritoryInfo info = switchTerritory(loc);
		int mouseButton = getFrame().getGUIControl().getWhich();
		Player playerObj = getPlayer(getPlayerNum());
		if (info == null || gameOver==true)
			setMessage(phaseInfo());
		else
		{
			if (mouseButton == InputEvent.BUTTON3_MASK)
				setMessage(phaseInfo()+"\n"+info.returnInfo());
			else if (claimTerritories == true)
			{
				if (info.getOwner()=="")
				{
					info.setOwner(playerObj.getName(), getPlayerNum());
					info.setArmies(1);
					playerObj.setUnplacedArmies(playerObj.getUnplacedArmies()-1);
					playerObj.setNumTerritories(playerObj.getNumTerritories()+1);
					nextPhase();
					setMessage(phaseInfo()+"\n"+info.returnInfo());
				}
				else
				{
					setMessage(phaseInfo()+"\n"+info.returnInfo()+"\nThis territory is already occupied");
				}
			}
			else if (reinforceTerritories == true)
			{
				if (info.getOwner().equals(playerObj.getName()))
				{
					info.setArmies(info.getArmies()+1);
					playerObj.setUnplacedArmies(playerObj.getUnplacedArmies()-1);
					nextPhase();
					if (reinforceTerritories == false)
						setMessage(phaseInfo()+"\n"
							+info.returnInfo()+"\n"
							+getPlayer(getPlayerNum()).getName()+" recieved "+String.valueOf(calculateUnplacedArmies())+" armies");
					else
						setMessage(phaseInfo()+"\n"+info.returnInfo());
				}
				else
					setMessage(phaseInfo()+"\n"+info.returnInfo()+"\nThis territory is not owned by you");
			}
			else
			{
				if (getPhaseNum()==0)
				{
					if (info.getOwner().equals(playerObj.getName()))
					{
						if (playerObj.getUnplacedArmies()>0)
						{
							info.setArmies(info.getArmies()+1);
							playerObj.setUnplacedArmies(playerObj.getUnplacedArmies()-1);
							setMessage(phaseInfo()+"\n"+info.returnInfo());
							if(playerObj.getUnplacedArmies()==0)
								getFrame().getGUIControl().getDoneButton().setEnabled(true);
						}
						else
							setMessage(phaseInfo()+"\n"+info.returnInfo()+"\nYou have no armies to place");
					}
					else
						setMessage(phaseInfo()+"\n"+info.returnInfo()+"\nThis territory is not owned by you");
				}
				else if(getPhaseNum()==1)
				{
					if (previousInfo==null || !previousInfo.getOwner().equals(playerObj.getName()) || info.getOwner().equals(playerObj.getName()))
					{
						previousInfo=info;
						setMessage(phaseInfo()+"\n"+info.returnInfo());
					}
					else
					{
						boolean bordering = false;
						for (String x:previousInfo.getBorderingTerritories())
						{
							if (x.equals(info.getName()))
							{
								bordering=true;
							}
						}
						if (bordering==true)
						{
							if (previousInfo.getArmies()==1)
							{
								previousInfo=info;
								setMessage(phaseInfo()+"\n"+info.returnInfo()+"\nNot enough armies");
							}
							else
							{	
								attack(previousInfo, info);
								previousInfo=null;
								if (gameOver==true)
									setMessage(phaseInfo());
								else
									setMessage(phaseInfo()+"\n"+info.returnInfo());
							}
						}
						else
						{
							previousInfo=info;
							setMessage(phaseInfo()+"\n"+info.returnInfo());
						}
					}
				}
				else if(getPhaseNum()==2)
				{
					if (previousInfo==null || !previousInfo.getOwner().equals(playerObj.getName()) || !info.getOwner().equals(playerObj.getName()))
					{
						previousInfo = info;
						setMessage(phaseInfo()+"\n"+info.returnInfo());
					}
					else
					{
						boolean bordering = false;
						for (String x:previousInfo.getBorderingTerritories())
						{
							if (x.equals(info.getName()))
							{
								bordering=true;
							}
						}
						if(bordering==true)
						{
							if(fortified==true)
							{
								previousInfo=info;
								setMessage(phaseInfo()+"\n"+info.returnInfo()+"\nYou have already fortified your position");
							}
							else if (previousInfo.getArmies()==1)
							{
								previousInfo=info;
								setMessage(phaseInfo()+"\n"+info.returnInfo()+"\nNot enough armies");
							}
							else
							{	
								fortify(previousInfo, info);
								previousInfo=null;
								setMessage(phaseInfo()+"\n"+info.returnInfo());
							}
						}
						else
						{
							previousInfo=info;
							setMessage(phaseInfo()+"\n"+info.returnInfo());
						}
					}
				}
			}
		}
		return true;
	}
	public int calculateUnplacedArmies()
	{
		int armies;
		Player playerObj = getPlayer(getPlayerNum());
		armies = playerObj.getNumTerritories()/3;
		ArrayList<TerritoryInfo> asia = new ArrayList<TerritoryInfo>();
		ArrayList<TerritoryInfo> northAmerica = new ArrayList<TerritoryInfo>();
		ArrayList<TerritoryInfo> southAmerica = new ArrayList<TerritoryInfo>();
		ArrayList<TerritoryInfo> europe = new ArrayList<TerritoryInfo>();
		ArrayList<TerritoryInfo> africa = new ArrayList<TerritoryInfo>();
		ArrayList<TerritoryInfo> australia = new ArrayList<TerritoryInfo>();
		if (armies<3)
			armies = 3;
		for (Actor x:getAllActors())
		{
			if (x instanceof TerritoryInfo)
			{
				TerritoryInfo info = (TerritoryInfo)x;
				if (info.getOwner().equals(playerObj.getName()))
				{
					String territoryName = info.getName();
					if(territoryName.equals("Afghanistan") ||
						territoryName.equals("China") ||
						territoryName.equals("India") ||
						territoryName.equals("Irkutsk") ||
						territoryName.equals("Japan") ||
						territoryName.equals("Kamchatka") ||
						territoryName.equals("Middle East") ||
						territoryName.equals("Mongolia") ||
						territoryName.equals("Siam") ||
						territoryName.equals("Siberia") ||
						territoryName.equals("Ural") ||
						territoryName.equals("Yakutsk"))
						asia.add((TerritoryInfo)x);
					else if(territoryName.equals("Alaska") ||
						territoryName.equals("Alberta") ||
						territoryName.equals("Central America") ||
						territoryName.equals("Eastern United States") ||
						territoryName.equals("Greenland") ||
						territoryName.equals("Northwest Territory") ||
						territoryName.equals("Ontario") ||
						territoryName.equals("Quebec") ||
						territoryName.equals("Western United States"))
						northAmerica.add((TerritoryInfo)x);
					else if(territoryName.equals("Argentina") ||
						territoryName.equals("Brazil") ||
						territoryName.equals("Peru") ||
						territoryName.equals("Venezuela"))
						southAmerica.add((TerritoryInfo)x);
					else if(territoryName.equals("Great Britain") ||
						territoryName.equals("Iceland") ||
						territoryName.equals("Northern Europe") ||
						territoryName.equals("Scandinavia") ||
						territoryName.equals("Southern Europe") ||
						territoryName.equals("Ukraine") ||
						territoryName.equals("Western Europe"))
						europe.add((TerritoryInfo)x);
					else if(territoryName.equals("Congo") ||
						territoryName.equals("East Africa") ||
						territoryName.equals("Egypt") ||
						territoryName.equals("Madagascar") ||
						territoryName.equals("North Africa") ||
						territoryName.equals("South Africa"))
						africa.add((TerritoryInfo)x);
					else if(territoryName.equals("Eastern Australia") ||
						territoryName.equals("Indonesia") ||
						territoryName.equals("New Guinea") ||
						territoryName.equals("Western Australia"))
						australia.add((TerritoryInfo)x);
				}
			}
		}
		if (asia.size()==12)
			armies+=7;
		else if (northAmerica.size()==9)
			armies+=5;
		else if (southAmerica.size()==4)
			armies+=2;
		else if (europe.size()==7)
			armies+=5;
		else if (africa.size()==6)
			armies+=3;
		else if (australia.size()==4)
			armies+=2;
		return armies;
	}
	public void attack(TerritoryInfo attacking, TerritoryInfo defending)
	{
		int finalAttackArmies=0;
		do{
			Object[] attackingNumList, defendingNumList;
			int[] attackingRolls,defendingRolls;
			int attackingNum, defendingNum, attackingLosses=0, defendingLosses=0;
			
			if (attacking.getArmies()>3)
				attackingNumList= new Object[]{1,2,3};
			else if(attacking.getArmies()>2)
				attackingNumList=new Object[]{1,2};
			else
				attackingNumList=new Object[]{1};
			Object attackingNumDialog = JOptionPane.showInputDialog(
				getFrame(),
				String.valueOf(attacking.getArmies())+" armies remaining\n"+
				"Select number of armies to attack with:",
				attacking.getOwner(),
				JOptionPane.DEFAULT_OPTION,
				null,
				attackingNumList,
				attackingNumList[attackingNumList.length-1]);
			if (attackingNumDialog==null)
				break;
			else
				attackingNum=(int)attackingNumDialog;
			
			String plurality;
			if(defending.getArmies()>=2)
			{
				defendingNumList=new Object[]{1,2};
				plurality="armies";
			}
			else
			{
				defendingNumList=new Object[]{1};
				plurality="army";
			}
			Object defendingNumDialog = JOptionPane.showInputDialog(
				getFrame(),
				String.valueOf(defending.getArmies())+" "+plurality+" remaining\n"+
				"Select number of armies to defend with:",
				defending.getOwner(),
				JOptionPane.DEFAULT_OPTION,
				null,
				defendingNumList,
				defendingNumList[defendingNumList.length-1]);
			if (defendingNumDialog==null)
				defendingNum=(int)defendingNumList[defendingNumList.length-1];
			else
				defendingNum=(int)defendingNumDialog;
				
			attackingRolls=new int[attackingNum];
			defendingRolls=new int[defendingNum];
			for(int x=0;x<attackingNum;x++)
				attackingRolls[x]=(int)(Math.random()*6) +1;
			for(int x=0;x<defendingNum;x++)
				defendingRolls[x]=(int)(Math.random()*6) +1;
			Arrays.sort(attackingRolls);
			Arrays.sort(defendingRolls);
			if(attackingNum<defendingNum)
			{
				for(int x=0;x<attackingNum;x++)
					if (attackingRolls[attackingNum-1-x]>defendingRolls[defendingNum-1-x])
						defendingLosses++;
					else
						attackingLosses++;
			}
			else
			{
				for(int x=0;x<defendingNum;x++)
					if (attackingRolls[attackingNum-1-x]>defendingRolls[defendingNum-1-x])
						defendingLosses++;
					else
						attackingLosses++;
			}
			
			JOptionPane.showMessageDialog(
				getFrame(),
				attacking.getOwner()+" rolled: "+Arrays.toString(attackingRolls)+"\n"+
				defending.getOwner()+" rolled: "+Arrays.toString(defendingRolls)+"\n"+
				attacking.getOwner()+" losses: "+String.valueOf(attackingLosses)+"\n"+
				defending.getOwner()+" losses: "+String.valueOf(defendingLosses),
				"Results",
				JOptionPane.DEFAULT_OPTION);
			attacking.setArmies(attacking.getArmies()-attackingLosses);
			defending.setArmies(defending.getArmies()-defendingLosses);
			if (defending.getArmies()==0)
				finalAttackArmies=attackingNum;
			else
				getFrame().repaint();
		}while (attacking.getArmies()>1 && defending.getArmies()>0);
		if (defending.getArmies()==0)
		{
			String defendingPlayer = defending.getOwner();
			territoryTaken=true;
			getPlayer(getPlayerNum()).setNumTerritories(getPlayer(getPlayerNum()).getNumTerritories()+1);
			getPlayer(defendingPlayer).setNumTerritories(getPlayer(defendingPlayer).getNumTerritories()-1);
			Object[] armiesToMoveList=new Object[attacking.getArmies()-finalAttackArmies];
			int armiesToMoveNum=0;
			for(int x=finalAttackArmies; x<attacking.getArmies(); x++)
				armiesToMoveList[x-finalAttackArmies]=x;
			Object armiesToMove = JOptionPane.showInputDialog(
				getFrame(),
				"Select number of armies to move into "+defending.getName()+":",
				attacking.getOwner(),
				JOptionPane.DEFAULT_OPTION,
				null,
				armiesToMoveList,
				armiesToMoveList[0]);
			if (armiesToMove==null)
				armiesToMoveNum=finalAttackArmies;
			else
				armiesToMoveNum=(int)armiesToMove;
			defending.setOwner(attacking.getOwner(), getPlayerNum());
			defending.setArmies(armiesToMoveNum);
			attacking.setArmies(attacking.getArmies()-armiesToMoveNum);
			
			if(getPlayer(defendingPlayer).getNumTerritories()==0)
			{
				JOptionPane.showMessageDialog(
					getFrame(),
					defendingPlayer+" has been defeated\n"+
					attacking.getOwner()+" receives "+getPlayer(defendingPlayer).getCards().size()+" Risk cards",
					"Player Defeated",
					JOptionPane.DEFAULT_OPTION);
				CardDialog displayDialog;
				for (int x=getPlayer(defendingPlayer).getCards().size()-1; x>=0; x--)
				{
					displayDialog = new CardDialog(this,getPlayer(defendingPlayer).getCards().get(x));
					getPlayer(getPlayerNum()).getCards().add(getPlayer(defendingPlayer).getCards().remove(x));
				}
				Boolean onlyPlayerLeft = true;
				for (Player x:playerList)
				{
					if (!x.getName().equals(attacking.getOwner()))
					{
						if(x.getNumTerritories()!=0)
						{
							onlyPlayerLeft=false;
						}
					}
				}
				if (onlyPlayerLeft==true)
				{
					gameOver = true;
					getFrame().getGUIControl().getDoneButton().setEnabled(false);
					getFrame().getGUIControl().getCardsButton().setEnabled(false);
					JOptionPane.showMessageDialog(
						getFrame(),
						attacking.getOwner()+" has won",
						"Game Over",
						JOptionPane.DEFAULT_OPTION);
				}
				else if(getPlayer(getPlayerNum()).getCards().size()>=6)
				{
					setPhaseNum(0);
					setMessage(phaseInfo());
					getFrame().repaint();
					CardDialog turnInDialog = new CardDialog(this);
					while (getPlayer(getPlayerNum()).getCards().size()>=5)
					{
						turnInDialog= new CardDialog(this);
					}
				}
			}
		}
	}
	public void fortify(TerritoryInfo giving, TerritoryInfo receiving)
	{
		int givenArmies=0;
		Object[] givingNumList = new Object[giving.getArmies()-1];
		for(int x=0; x<giving.getArmies()-1; x++)
				givingNumList[x]=x+1;
		Object givingDialog = JOptionPane.showInputDialog(
				getFrame(),
				"Armies to move from "+giving.getName()+" to "+receiving.getName()+":",
				giving.getOwner(),
				JOptionPane.DEFAULT_OPTION,
				null,
				givingNumList,
				givingNumList[0]);
		if (givingDialog!=null)
		{
			givenArmies=(int)givingDialog;
			giving.setArmies(giving.getArmies()-givenArmies);
			receiving.setArmies(receiving.getArmies()+givenArmies);
			fortified=true;
		}
	}
	public String phaseInfo()
	{
		String phaseName="";
		String playerName="";
		Player playerObj = getPlayer(getPlayerNum());
		if (claimTerritories == true)
			phaseName = "Select a territory to place army";
		else if (reinforceTerritories == true)
			phaseName = "Select any owned territory to add an army";
		else
			switch(getPhaseNum()){
				case 0: phaseName = "Place New Armies";
					break;
				case 1: phaseName = "Attack Territories";
					break;
				case 2: phaseName = "Fortify";
					break;
			}
		if (gameOver == true)
			return playerObj.getName()+" has won";
		if (playerObj.getUnplacedArmies()==1)
			return  playerObj.getName()+" - "+String.valueOf(playerObj.getUnplacedArmies())+" unplaced army\n" +phaseName;
		if (playerObj.getUnplacedArmies()==0)
			return  playerObj.getName()+" - No unplaced armies\n" +phaseName;
		return  playerObj.getName()+" - "+String.valueOf(playerObj.getUnplacedArmies())+" unplaced armies\n" +phaseName;
	}
	public TerritoryInfo switchTerritory(Location loc)
	{
		Actor selected = getGrid().get(loc);
		if (selected instanceof Territory)
		{
			Territory selectedTerritory = (Territory)selected;
			ArrayList<Actor> actors = getAllActors();
			for (Actor x: actors)
				if (x instanceof TerritoryInfo)
				{
					TerritoryInfo selectedTerritoryInfo = (TerritoryInfo)x;
					if (selectedTerritoryInfo.getName().equals(selectedTerritory.getName()))
					{
						getFrame().getDisplay().setLocation(x.getLocation());
						setMessage(phaseInfo()+"\n"+selectedTerritoryInfo.returnInfo());
						return selectedTerritoryInfo;
					}
				}
		}
		return null;
	}
	public ArrayList<Actor> getAllActors()
	{
		ArrayList<Actor> actors = new ArrayList<Actor>();
		ArrayList<Location> locs = getGrid().getOccupiedLocations();
		for (Location x: locs)
			actors.add(getGrid().get(x));
		return actors;
	}	
	public Player getPlayer(int x)
	{
		return playerList.get(x);
	}	
	public Player getPlayer(String name)
	{
		for(int x=0; x<getNumPlayers(); x++)
			if (playerList.get(x).getName().equals(name))
				return playerList.get(x);
		return null;
	}
	public void turnInCards(ArrayList<Card> cardList)
	{
		for (Card x:cardList)
		{
			garbageDeck.add(x);
		}
	}
	public void addSetValue()
	{
		if (setValue<12)
			setValue +=2;
		else if (setValue ==12)
			setValue = 15;
		else if (setValue > 12)
			setValue += 5;
	}
	public int getSetValue()
	{
		return setValue;
	}
	/*public void saveGame()
	{
		try
		{
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("lastGame.ser"));
			out.writeObject(this);
			out.close();
			System.out.println("Serialized data is saved in /tmp/employee.ser");
		}
		catch(IOException i)
		{
			i.printStackTrace();
		}
	}
	public void loadGame()
	{
		try
		{
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("lastGame.ser"));
			app = (Risk) in.readObject();
			in.close();
			getFrame().dispose();
		}
		catch(IOException i)
		{
			i.printStackTrace();
			JOptionPane.showMessageDialog(getFrame(), "No save game found.");
		}
		catch(ClassNotFoundException c)
		{
			return;
		}
	}*/
	public static void main(String[] args)
    {
		app = new Risk();
	}
}