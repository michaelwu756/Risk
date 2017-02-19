package resources;
public class Card
{
	public static final int INFANTRY = 0;
	public static final int CAVALRY = 1;
	public static final int ARTILLERY = 2;
	public static final int WILD_CARD = 3;
	private String name;
	private int type;
	
	public Card(String name, int type)
	{
		this.name=name;
		this.type=type;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getType()
	{
		return type;
	}
}