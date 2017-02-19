package resources;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;

import resources.*;
import info.gridworld.actor.*;
import info.gridworld.gui.*;
import info.gridworld.world.*;

public class CardDialog extends JDialog implements ItemListener
{
	private ArrayList<JCheckBox> boxList;
	private ArrayList<Card> cardArray;
	private World<?> riskGame;
	private JButton closeButton, turnInButton;
	private int[] choices;
	
	public CardDialog(World game)
	{
		super(game.getFrame(), "Cards", true);
		
		riskGame = game;
		
		boxList = new ArrayList<JCheckBox>();
		cardArray = riskGame.getPlayer(riskGame.getPlayerNum()).getCards();
		choices = new int[cardArray.size()];
		WorldFrame frame = riskGame.getFrame();
		
		if (cardArray==null || cardArray.size()==0)
		{
			JOptionPane.showMessageDialog(frame,"You have no cards");
			setVisible(false);
		}
		else
		{
			ArrayList<ImageIcon> iconList = new ArrayList<ImageIcon>();
			ArrayList<JLabel> cardLabels = new ArrayList<JLabel>();
			JPanel checkPanel = new JPanel(new GridLayout(0,cardArray.size()));
			JPanel imagePanel = new JPanel(new GridLayout(0,cardArray.size()));
			JPanel cardPanel = new JPanel();
			JPanel buttonPanel = new JPanel();
			JPanel contentPanel = new JPanel();
			closeButton = new JButton("Close");
			turnInButton = new JButton("Turn In - "+String.valueOf(riskGame.getSetValue())+" Armies");
			Dimension spacer = new Dimension(5, closeButton.getPreferredSize().height + 10);
			
			buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
			buttonPanel.add(closeButton);
			buttonPanel.add(Box.createRigidArea(spacer));
			buttonPanel.add(turnInButton);
			
			closeButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					dispose();
				}
			});
			
			turnInButton.setEnabled(false);
			turnInButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					turnIn();
				}
			});
			imagePanel.setBackground(Color.LIGHT_GRAY);
			imagePanel.setOpaque(true);
			
			checkPanel.setBackground(Color.LIGHT_GRAY);
			checkPanel.setOpaque(true);
			
			iconList.add(new ImageIcon("resources/Infantry.png"));
			iconList.add(new ImageIcon("resources/Cavalry.png"));
			iconList.add(new ImageIcon("resources/Artillery.png"));
			iconList.add(new ImageIcon("resources/WildCard.png"));
			
			for (int x=0; x<cardArray.size(); x++)
			{
				boxList.add(new JCheckBox(cardArray.get(x).getName()));
				boxList.get(x).setSelected(false);
				boxList.get(x).addItemListener(this);
				boxList.get(x).setHorizontalAlignment(SwingConstants.CENTER);
				boxList.get(x).setHorizontalTextPosition(SwingConstants.CENTER);
				boxList.get(x).setVerticalTextPosition(SwingConstants.TOP);
				boxList.get(x).setPreferredSize(new Dimension(154, 50));
				boxList.get(x).setBackground(Color.LIGHT_GRAY);
				boxList.get(x).setOpaque(true);
				checkPanel.add(boxList.get(x));
			
				cardLabels.add(new JLabel(iconList.get(cardArray.get(x).getType()), JLabel.CENTER));
				cardLabels.get(x).setVerticalAlignment(SwingConstants.CENTER);
				cardLabels.get(x).setBorder(
					BorderFactory.createCompoundBorder(
						BorderFactory.createMatteBorder(10, 10, 0, 10, imagePanel.getBackground()),
						BorderFactory.createLineBorder(Color.BLACK)));
				cardLabels.get(x).setPreferredSize(new Dimension(154,198));
				cardLabels.get(x).setOpaque(true);
				cardLabels.get(x).setBackground(Color.WHITE);
				imagePanel.add(cardLabels.get(x));
				
				choices[x]=0;
			}
		
			cardPanel.setBorder(BorderFactory.createEtchedBorder());
			cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
			cardPanel.add(imagePanel);
			cardPanel.add(checkPanel);
		
			contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
			contentPanel.add(cardPanel);
			contentPanel.add(buttonPanel);
			contentPanel.setBorder(
				BorderFactory.createCompoundBorder(
					BorderFactory.createEmptyBorder(20, 20, 20, 20),
					BorderFactory.createEtchedBorder()));
			
			setContentPane(contentPanel);
			pack();
			setResizable(false);
			setLocationRelativeTo(frame);
			setVisible(true);
		}
	}
	public CardDialog(World game, Card card)
	{
		super(game.getFrame(), "New Card", true);
		
		riskGame=game;
		WorldFrame frame = riskGame.getFrame();
		ImageIcon icon = new ImageIcon();
		JPanel checkPanel = new JPanel(new GridLayout(0,1));
		JPanel imagePanel = new JPanel(new GridLayout(0,1));
		JPanel cardPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		JPanel contentPanel = new JPanel();
		JButton okButton = new JButton("Ok");
		
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(okButton);
		
		okButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		
		buttonPanel.setPreferredSize(new Dimension(buttonPanel.getPreferredSize().width, okButton.getPreferredSize().height+ 10));
		
		if(card.getType()==Card.INFANTRY)
			icon = new ImageIcon("resources/Infantry.png");
		else if(card.getType()==Card.CAVALRY)
			icon = new ImageIcon("resources/Cavalry.png");
		else if(card.getType()==Card.ARTILLERY)
			icon = new ImageIcon("resources/Artillery.png");
		else if(card.getType()==Card.WILD_CARD)
			icon = new ImageIcon("resources/WildCard.png");
			
		JLabel cardLabel = new JLabel(icon, JLabel.CENTER);

		cardLabel.setVerticalAlignment(SwingConstants.CENTER);
		cardLabel.setBorder(
			BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(10, 10, 0, 10, imagePanel.getBackground()),
				BorderFactory.createLineBorder(Color.BLACK)));
		cardLabel.setPreferredSize(new Dimension(154,198));
		cardLabel.setOpaque(true);
		cardLabel.setBackground(Color.WHITE);
		imagePanel.add(cardLabel);
		
		JLabel textLabel = new JLabel(card.getName());

		textLabel.setHorizontalAlignment(SwingConstants.CENTER);
		textLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		textLabel.setVerticalTextPosition(SwingConstants.TOP);
		textLabel.setPreferredSize(new Dimension(154, 30));
		textLabel.setOpaque(true);
		checkPanel.add(textLabel);
		
		//cardPanel.setBorder(BorderFactory.createEtchedBorder());
		cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
		cardPanel.add(imagePanel);
		cardPanel.add(checkPanel);
			
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.add(cardPanel);
		contentPanel.add(buttonPanel);
		contentPanel.setBorder(
				BorderFactory.createCompoundBorder(
					BorderFactory.createEmptyBorder(20, 20, 20, 20),
					BorderFactory.createEtchedBorder()));
		
		setContentPane(contentPanel);
		pack();
		setResizable(false);
		setLocationRelativeTo(frame);
		setVisible(true);
	}
	public void itemStateChanged(ItemEvent e)
	{
        int index = 0;
		int value = 0;
        Object source = e.getItemSelectable();
		
		for (int x=0; x<cardArray.size(); x++)
		{
			if (source == boxList.get(x))
			{
				index = x;
				value = 1;
			}
		}

        if (e.getStateChange() == ItemEvent.DESELECTED) {
            value = 0;
        }

		choices[index]=value;
		updateStatus();
    }
	public void updateStatus()
	{
		ArrayList<Integer> types = getTypesSelected();
		boolean allSameType = true, allDifferentType = true;
		if (types!=null)
		{
			for (int x=0; x<types.size(); x++)
			{
				if (types.get(x)!=Card.WILD_CARD)
				{
					if (types.get(x)!=types.get(0))
					{
						allSameType=false;
					}
					for (int y=x+1; y<types.size(); y++)
					{
						if (types.get(x)==types.get(y))
						{
							allDifferentType = false;
						}
					}
				}
			}
			if (allDifferentType==true || allSameType==true)
			{
				turnInButton.setEnabled(true);
			}
			else
				turnInButton.setEnabled(false);
		}
		else
		{
			turnInButton.setEnabled(false);
		}
	}
	public void turnIn()
	{
		ArrayList<Card> removedCards = new ArrayList<Card>();
		ArrayList<TerritoryInfo> territoryList = new ArrayList<TerritoryInfo>();
		ArrayList<String> territoriesOwned = new ArrayList<String>();
		Object[] ownedChoicesList;
		Object territorySelected;
		String chosenTerritory;
		Player playerObj = riskGame.getPlayer(riskGame.getPlayerNum());
		for(Actor x:riskGame.getAllActors())
		{
			if(x instanceof TerritoryInfo)
			{
				territoryList.add((TerritoryInfo)x);
			}
		}
		for(TerritoryInfo x:territoryList)
		{
			for (int y=choices.length-1; y>=0; y--)
			{
				if (choices[y]==1)
				{
					if(x.getName().equals(playerObj.getCards().get(y).getName()) && x.getOwner().equals(playerObj.getName()))
					{
						territoriesOwned.add(x.getName());
					}
				}
			}
			
		}
		if(territoriesOwned.size()!=0)
		{
			ownedChoicesList = new Object[territoriesOwned.size()];
			for(int x=0; x<territoriesOwned.size(); x++)
			{
				ownedChoicesList[x]=territoriesOwned.get(x);
			}
			territorySelected = JOptionPane.showInputDialog(
				riskGame.getFrame(),
				"Select territory to place two bonus armies:",
				"Trade In",
				JOptionPane.DEFAULT_OPTION,
				null,
				ownedChoicesList,
				ownedChoicesList[0]);
			if (territorySelected==null)
				chosenTerritory=(String)ownedChoicesList[0];
			else	
				chosenTerritory=(String)territorySelected;
			for(TerritoryInfo x:territoryList)
			{
				if(x.getName().equals(chosenTerritory))
					x.setArmies(x.getArmies()+2);
			}
		}
		
		for (int x=choices.length-1; x>=0; x--)
		{
			if (choices[x]==1)
			{
				removedCards.add(playerObj.getCards().remove(x));
			}
		}
		playerObj.setUnplacedArmies(playerObj.getUnplacedArmies()+riskGame.getSetValue());
		riskGame.setMessage(riskGame.phaseInfo()+"\n"+riskGame.getPlayer(riskGame.getPlayerNum()).getName()+" recieved "+String.valueOf(riskGame.getSetValue())+" armies");
		riskGame.turnInCards(removedCards);
		riskGame.getFrame().getGUIControl().getDoneButton().setEnabled(false);
		riskGame.getFrame().repaint();
		riskGame.addSetValue();
		dispose();
	}
	public int getBoxesSelected()
	{
		int boxesChecked = 0;
		for (int x:choices)
		{
			if (x==1)
				boxesChecked++;
		}
		return boxesChecked;
	}
	public ArrayList<Integer> getTypesSelected()
	{
		ArrayList<Integer> types = new ArrayList<Integer>();
		if (getBoxesSelected()==3)
		{
			for (int x=0; x<choices.length; x++)
			{
				if (choices[x]==1)
					types.add(cardArray.get(x).getType());
			}
			return types;
		}
		return null;
	}
}