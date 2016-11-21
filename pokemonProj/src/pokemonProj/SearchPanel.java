package pokemonProj;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.*;

public class SearchPanel extends JPanel
{
	static JFrame frame;
	final int SIZE = 85;
	final int NUMOFPOKEMON = 151;
	String[] typesArray1 = {"Bug","Dark","Dragon","Electric","Fairy","Fighting","Fire","Flying","Ghost","Grass","Ground","Ice","Normal","Poison","Psychic","Rock","Steel","Water"};
	String[] typesArray2 = {"None","Does Not Matter","Bug","Dark","Dragon","Electric","Fairy","Fighting","Fire","Flying","Ghost","Grass","Ground","Ice","Normal","Poison","Psychic","Rock","Steel","Water"};
	JComboBox<String> type1_dropdown;
	JComboBox<String> type2_dropdown;
	JButton search_button;
	JButton effectiveness_button;
	JButton help_button;
	String search_type1 = "None";
	String search_type2 = "None";
	
	Scanner scan = new Scanner(System.in);
	String filePath = new File("").getAbsolutePath();
	String data_file = filePath.concat("\\pokemon_data.txt");
	String image_dir = filePath.concat("\\go sprites resized_100x100\\");
	String help_file = filePath.concat("\\help_file.txt");
	
	Scanner pkmnIn = null;
	boolean DoesNotMatter = false;
	HashMap<String, ArrayList<String>> typeMap = new HashMap<String, ArrayList<String>>();
	ArrayList<String> BugList = new ArrayList<>();
	ArrayList<String> DarkList = new ArrayList<>();
	ArrayList<String> DragonList = new ArrayList<>();
	ArrayList<String> ElectricList = new ArrayList<>();
	ArrayList<String> FairyList = new ArrayList<>();
	ArrayList<String> FightingList = new ArrayList<>();
	ArrayList<String> FireList = new ArrayList<>();
	ArrayList<String> FlyingList = new ArrayList<>();
	ArrayList<String> GhostList = new ArrayList<>();
	ArrayList<String> GrassList = new ArrayList<>();
	ArrayList<String> GroundList = new ArrayList<>();
	ArrayList<String> IceList = new ArrayList<>();
	ArrayList<String> NormalList = new ArrayList<>();
	ArrayList<String> PoisonList = new ArrayList<>();
	ArrayList<String> PsychicList = new ArrayList<>();
	ArrayList<String> RockList = new ArrayList<>();
	ArrayList<String> SteelList = new ArrayList<>();
	ArrayList<String> WaterList = new ArrayList<>();
	ArrayList<String> NoneList = new ArrayList<>();
	ArrayList<String> result = new ArrayList<>();
	String[] pokeName123 = new String[NUMOFPOKEMON];
	String[] pokeType123 = new String[NUMOFPOKEMON];
	Icon[] picture = new ImageIcon[NUMOFPOKEMON];
	JLabel[] picLabel = new JLabel[NUMOFPOKEMON];//this label holds graphical pictures
	JLabel[] pokeNameLabel = new JLabel[NUMOFPOKEMON];//this label holds string names of pokemon
	JPanel[] pokePanel = new JPanel[NUMOFPOKEMON];//this panel holds pokemon name and picture
	
	JPanel mainPanel;
	JPanel leftPanel;
	JPanel rightPanel;
	JPanel resultPanel;
	JPanel pokemonPanel;
	JLabel labelNMF;
	
	Color left = new Color(222,184,135);// background color for the search form
	Color right = new Color(238,232,170);// background color for the results panel
	
	ArrayList<String> plist1;
	ArrayList<String> plist2;
	
	public SearchPanel()
	{
		setBackground(Color.white);
		Border lineBorder = BorderFactory.createLineBorder(Color.black,4);
		setLayout(new BorderLayout());
		mainPanel = new JPanel();
		GridLayout gridMain = new GridLayout(1, 2, 10, 10);
		mainPanel.setLayout(gridMain);
		
		// setting up the title 'Search By Type'
		JLabel labelSBT = new JLabel("Search By Type");
		labelSBT.setToolTipText("Search By Type");
		labelSBT.setFont(new Font("SansSerif", Font.BOLD + Font.ITALIC, 35));
		labelSBT.setHorizontalAlignment(JLabel.CENTER);
	    labelSBT.setVerticalAlignment(JLabel.CENTER);
	    
	    // setting up the help button
	    help_button = new JButton("Help");
		help_button.setFont(new Font("SansSerif", Font.BOLD, 20));
		help_button.setToolTipText("POkeGoBDB Help");
		help_button.addActionListener(new java.awt.event.ActionListener() 
		{
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) 
            {
            	try 
            	{
					String help_text = readHelp(help_file);
					JOptionPane.showMessageDialog(frame, help_text,"PokeGoBDB Help", JOptionPane.INFORMATION_MESSAGE);
				} 
            	catch (IOException e) 
            	{
					// TODO Auto-generated catch block
					e.printStackTrace();
					JOptionPane.showMessageDialog(frame, "About PokeGoDBD","PokeGoBDB Help", JOptionPane.INFORMATION_MESSAGE);
				}
            	repaint();
            }
        });
		
		JPanel titlePanel = new JPanel();
		GridLayout title_grid = new GridLayout(1,5);
		titlePanel.setLayout(title_grid);
		titlePanel.add(new JPanel());
		titlePanel.add(new JPanel());
		titlePanel.add(labelSBT);
		titlePanel.add(new JPanel());
		titlePanel.add(help_button);
		titlePanel.setBorder(new EmptyBorder(20, 75, 20, 75));
		
		add(titlePanel, BorderLayout.NORTH);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(new JPanel(), BorderLayout.NORTH);
		
		JPanel bottomInPanel = new JPanel();
		bottomInPanel.setLayout(new GridLayout(1, 2, 10, 10));
		
		leftPanel = new JPanel();
		BorderLayout leftLayout = new BorderLayout();
		leftPanel.setLayout(leftLayout);
		
		JPanel topleftPanel = new JPanel();
		GridLayout topleftGrid = new GridLayout(1, 2, 5, 5);
		topleftPanel.setLayout(topleftGrid);
		
		JPanel midleftPanel = new JPanel();
		GridLayout midleftGrid = new GridLayout(1, 2, 5, 5);
		midleftPanel.setLayout(midleftGrid);
		
		JPanel bottomleftPanel = new JPanel();
		GridLayout bottomleftGrid = new GridLayout(1, 1, 5, 5);
		bottomleftPanel.setLayout(bottomleftGrid);
		
		// setting up the 'Type 1' drop-down
		JLabel lType1 = new JLabel("TYPE 1:");
		lType1.setFont(new Font("SansSerif", Font.BOLD, 25));
		lType1.setHorizontalAlignment(JLabel.CENTER);
	    lType1.setVerticalAlignment(JLabel.CENTER);
		lType1.setToolTipText("Type 1");
		topleftPanel.add(lType1);
		type1_dropdown = new JComboBox(typesArray1);
		topleftPanel.add(type1_dropdown);
		topleftPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
		
		// setting up the 'Type 2' drop-down
		JLabel lType2 = new JLabel("TYPE 2:");
		lType2.setFont(new Font("SansSerif", Font.BOLD, 25));
		lType2.setHorizontalAlignment(JLabel.CENTER);
		lType2.setVerticalAlignment(JLabel.CENTER);
		lType2.setToolTipText("Type 2");
		midleftPanel.add(lType2);
		type2_dropdown = new JComboBox<String>(typesArray2);
		midleftPanel.add(type2_dropdown);
		midleftPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
		
		// setting up the 'Search' button
		search_button = new JButton("Search");
		search_button.setFont(new Font("SansSerif", Font.BOLD, 20));
		search_button.setToolTipText("Click Button to Search");
		search_button.addActionListener(new ButtonListener());
		bottomleftPanel.add(search_button);
		bottomleftPanel.setBorder(new EmptyBorder(20, 250, 20, 180));
		
		// setting the background color of the search form
		topleftPanel.setBackground(left);
		midleftPanel.setBackground(left);
		bottomleftPanel.setBackground(left);
		
		// adding the elements of the form to a larger panel
		leftPanel.add(topleftPanel, BorderLayout.NORTH);
		leftPanel.add(midleftPanel, BorderLayout.CENTER);
		leftPanel.add(bottomleftPanel, BorderLayout.SOUTH);
		leftPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Color.BLACK));
		
		JPanel lPanel = new JPanel();
		BorderLayout lGrid = new BorderLayout();
		lPanel.setLayout(lGrid);
		lPanel.add(leftPanel, BorderLayout.NORTH);
		lPanel.setBorder(lineBorder);
		
		bottomInPanel.add(lPanel);

		rightPanel = new JPanel();
		BorderLayout rightLayout = new BorderLayout();
		rightPanel.setLayout(rightLayout);
		rightPanel.setBackground(right);
		rightPanel.setBorder(lineBorder);
		
		// setting up the label 'Results'
		JLabel labelResults = new JLabel("Results");
		labelResults.setToolTipText("Results");
		labelResults.setFont(new Font("SansSerif", Font.BOLD, 35));
		labelResults.setHorizontalAlignment(JLabel.CENTER);
	    labelResults.setVerticalAlignment(JLabel.TOP);
	    labelResults.setBorder(new EmptyBorder(10, 0, 10, 0));
		rightPanel.add(labelResults, BorderLayout.NORTH);
		
		resultPanel = new JPanel();
		resultPanel.setBackground(right);
		FlowLayout flowResult = new FlowLayout();
		resultPanel.setLayout(flowResult);
		
		// setting up the label for 'No Matches Found!"
		labelNMF = new JLabel("");
		labelNMF.setToolTipText("");
		labelNMF.setFont(new Font("SansSerif", Font.BOLD, 25));
		labelNMF.setHorizontalAlignment(JLabel.LEADING);
	    labelNMF.setVerticalAlignment(JLabel.CENTER);
		resultPanel.add(labelNMF);
		
		rightPanel.add(resultPanel, BorderLayout.CENTER);
		
		bottomInPanel.add(rightPanel);
		
		bottomPanel.add(bottomInPanel, BorderLayout.CENTER);
		
		mainPanel.add(bottomPanel);
		
		add(mainPanel, BorderLayout.CENTER);
		
		setup();
	}
	
	private void setup()
	{
		String type1 = "None";
		String type2 = "None";
		try 
		{
			pkmnIn = new Scanner(new File(data_file));
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("Please specify a proper data_file");
			e.printStackTrace();
		}
		
		// reading in the data from the pokemon_data.txt file
		String[] currentPkmn;
		String PokemonName;
		int counter = 0;
		while(pkmnIn.hasNextLine())
		{
			currentPkmn = pkmnIn.nextLine().split(",");
			PokemonName = currentPkmn[0];
			pokeType123[counter] = currentPkmn[1];//stores the pokemon type(s)
			pokeName123[counter] = currentPkmn[0];//stores the pokemon name in # order
			counter++;
			
			if(currentPkmn[1].contains("/"))
			{
				type1 = currentPkmn[1].split("/")[0];
				switch(type1)
				{
					case "Bug":
						BugList.add(PokemonName);
						break;
					case "Dark":
						DarkList.add(PokemonName);
						break;
					case "Dragon":
						DragonList.add(PokemonName);
						break;
					case "Electric":
						ElectricList.add(PokemonName);
						break;
					case "Fairy":
						FairyList.add(PokemonName);
						break;
					case "Fighting":
						FightingList.add(PokemonName);
						break;
					case "Fire":
						FireList.add(PokemonName);
						break;
					case "Flying":
						FlyingList.add(PokemonName);
						break;
					case "Ghost":
						GhostList.add(PokemonName);
						break;
					case "Grass":
						GrassList.add(PokemonName);
						break;
					case "Ground":
						GroundList.add(PokemonName);
						break;
					case "Ice":
						IceList.add(PokemonName);
						break;
					case "Normal":
						NormalList.add(PokemonName);
						break;
					case "Poison":
						PoisonList.add(PokemonName);
						break;
					case "Psychic":
						PsychicList.add(PokemonName);
						break;
					case "Rock":
						RockList.add(PokemonName);
						break;
					case "Steel":
						SteelList.add(PokemonName);
						break;
					case "Water":
						WaterList.add(PokemonName);
						break;
				}
				type2 = currentPkmn[1].split("/")[1];
				switch(type2)
				{
					case "Bug":
						BugList.add(PokemonName);
						break;
					case "Dark":
						DarkList.add(PokemonName);
						break;
					case "Dragon":
						DragonList.add(PokemonName);
						break;
					case "Electric":
						ElectricList.add(PokemonName);
						break;
					case "Fairy":
						FairyList.add(PokemonName);
						break;
					case "Fighting":
						FightingList.add(PokemonName);
						break;
					case "Fire":
						FireList.add(PokemonName);
						break;
					case "Flying":
						FlyingList.add(PokemonName);
						break;
					case "Ghost":
						GhostList.add(PokemonName);
						break;
					case "Grass":
						GrassList.add(PokemonName);
						break;
					case "Ground":
						GroundList.add(PokemonName);
						break;
					case "Ice":
						IceList.add(PokemonName);
						break;
					case "Normal":
						NormalList.add(PokemonName);
						break;
					case "Poison":
						PoisonList.add(PokemonName);
						break;
					case "Psychic":
						PsychicList.add(PokemonName);
						break;
					case "Rock":
						RockList.add(PokemonName);
						break;
					case "Steel":
						SteelList.add(PokemonName);
						break;
					case "Water":
						WaterList.add(PokemonName);
						break;
				}		
			}
			else
			{
				type1 = currentPkmn[1];
				switch(type1)
				{
					case "Bug":
						BugList.add(PokemonName);
						break;
					case "Dark":
						DarkList.add(PokemonName);
						break;
					case "Dragon":
						DragonList.add(PokemonName);
						break;
					case "Electric":
						ElectricList.add(PokemonName);
						break;
					case "Fairy":
						FairyList.add(PokemonName);
						break;
					case "Fighting":
						FightingList.add(PokemonName);
						break;
					case "Fire":
						FireList.add(PokemonName);
						break;
					case "Flying":
						FlyingList.add(PokemonName);
						break;
					case "Ghost":
						GhostList.add(PokemonName);
						break;
					case "Grass":
						GrassList.add(PokemonName);
						break;
					case "Ground":
						GroundList.add(PokemonName);
						break;
					case "Ice":
						IceList.add(PokemonName);
						break;
					case "Normal":
						NormalList.add(PokemonName);
						break;
					case "Poison":
						PoisonList.add(PokemonName);
						break;
					case "Psychic":
						PsychicList.add(PokemonName);
						break;
					case "Rock":
						RockList.add(PokemonName);
						break;
					case "Steel":
						SteelList.add(PokemonName);
						break;
					case "Water":
						WaterList.add(PokemonName);
						break;
				}
				type2 = "None";
				NoneList.add(PokemonName);
			}
		}
		typeMap.put("Bug",BugList);
		typeMap.put("Dark",DarkList);
		typeMap.put("Dragon",DragonList);
		typeMap.put("Electric",ElectricList);
		typeMap.put("Fairy",FairyList);
		typeMap.put("Fighting",FightingList);
		typeMap.put("Fire",FireList);
		typeMap.put("Flying",FlyingList);
		typeMap.put("Ghost",GhostList);
		typeMap.put("Grass",GrassList);
		typeMap.put("Ground",GroundList);
		typeMap.put("Ice",IceList);
		typeMap.put("Normal",NormalList);
		typeMap.put("Poison",PoisonList);
		typeMap.put("Psychic",PsychicList);
		typeMap.put("Rock",RockList);
		typeMap.put("Steel",SteelList);
		typeMap.put("Water",WaterList);
		typeMap.put("None",NoneList);
		
		for(int i = 0; i < NUMOFPOKEMON; i++)
		{
			// assign each label a Pokemon name in 123 order.
			pokeNameLabel[i] = new JLabel(pokeName123[i]);
			pokeNameLabel[i].setFont(new Font("SansSerif", Font.BOLD, 16));
			pokeNameLabel[i].setBorder(new EmptyBorder(0, 25, 0, 0));
			
			//NEED PICTURE FILES
			//attach jpg or png file on to Icon picture, then place all the pictures on to a JLabel
			picLabel[i] = new JLabel(new ImageIcon(image_dir + pokeName123[i] + ".png"));
			picLabel[i].setToolTipText("Type:"+pokeType123[i]);
			
			// creating the individual Pokemon panels containing their image and name
			pokePanel[i] = new JPanel();
			pokePanel[i].setBackground(right);
			pokePanel[i].setLayout(new BorderLayout());
			pokePanel[i].add(pokeNameLabel[i],BorderLayout.SOUTH);
			pokePanel[i].add(picLabel[i],BorderLayout.CENTER);
		}
		
	}
	
	// function to open and read the text from the help file
	private String readHelp(String fileName) throws IOException 
	{
	    BufferedReader br = new BufferedReader(new FileReader(fileName));
	    try 
	    {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) 
	        {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        return sb.toString();
	    } 
	    finally 
	    {
	        br.close();
	    }
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
	
	private void remove_pokemon()
	{
		for(int p = 0; p < NUMOFPOKEMON; p++)
		{
			resultPanel.remove(pokePanel[p]);//always reset and remove label when starting new action.
			repaint();
		}
		
	}
	
	class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			result.clear();
			search_type1 = (String)type1_dropdown.getSelectedItem();
			search_type2 = (String)type2_dropdown.getSelectedItem();
			
			remove_pokemon();
			
			// if 'Does Not Matter' is selected for Type 2
			if (search_type2.equals("Does Not Matter"))
			{
				DoesNotMatter = true;
			}
			plist1 = typeMap.get(search_type1);
			if (DoesNotMatter == false)
			{
				plist2 = typeMap.get(search_type2);
				for (String p_name : plist1) 
				{
					if (plist2.contains(p_name))
					{
						result.add(p_name);
					}
				}
			}
			else
			{
				result = new ArrayList<String>(plist1);
				DoesNotMatter = false;
			}
			// if no matches are found
			if (result.isEmpty())
			{
				// display 'No Matches Found!' message
				resultPanel.add(labelNMF);
				labelNMF.setText("<html><font color='red'>No Matches Found!</font></html>");
				labelNMF.setToolTipText("No Matches Found!");
				labelNMF.setBorder(new EmptyBorder(0, 20, 0, 0));
				//System.out.println("No Matches Found!");
				//System.out.println();
				repaint();
			}
			// if matches are found
			else
			{
				resultPanel.remove(labelNMF);
				int j = 0;
				int k = 0;
				for (String p_name : result) 
				{
					//System.out.println(p_name);
					if(j % 5 == 0 && j !=0)
					{
						j = 0;
						k++;
					}
				    for(int i = 0; i < NUMOFPOKEMON; i++)
					{
						if (p_name.equals(pokeName123[i]))
						{
							resultPanel.add(pokePanel[i]);
					        //resultPanel.setBorder(BorderFactory.create(Color.black));
					        //pokeNameLabel[i].setBounds(950 + 100 * j, 200 + 130 * k, SIZE, 20);
					        //picLabel[i].setBounds(950 + 100 * j, 120 + 130 * k, SIZE+10, SIZE);
					        //pokeNameLabel[i].setHorizontalAlignment(SwingConstants.CENTER);
					        j++;
						}
					}
				}
				//System.out.println();
				resultPanel.revalidate();
				repaint();
			}
			
		}
		
    }
}
