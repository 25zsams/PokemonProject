package pokemonProj;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class PokemonPanel extends JPanel
{
	
	static JFrame frame;
	
	final int SIZE = 85;
	final int NUMOFPOKEMON = 151;
	private int SCROLL;
	int up = 0, down = 0;
	
	JComboBox list, surf;
	JButton help_button;
	
	HashMap<String, Type> pokemon = new HashMap<String, Type>();
	String[] pokeName123 = new String[NUMOFPOKEMON];
	String[] pokeNameABC = new String[NUMOFPOKEMON];
	String[] pokeType123 = new String[NUMOFPOKEMON];
	
	Icon[] picture = new ImageIcon[NUMOFPOKEMON];
	JLabel[] picLabel = new JLabel[NUMOFPOKEMON];//this label holds graphical pictures
	JLabel[] pokeNameLabel = new JLabel[NUMOFPOKEMON];//this label holds string names of pokemon
	JPanel[] pokePanel = new JPanel[NUMOFPOKEMON];//this panel holds pokemon name and picture
	JLabel selectedPokemon = new JLabel();//will hold the selected pokemon name
	JLabel selectedType = new JLabel();//will hold the type of the selected pokemon
	JLabel selectedPicture = new JLabel();
	JLabel info = new JLabel();
	JLabel title = new JLabel("Effectiveness Chart");
	int[] stats = new int[NUMOFPOKEMON];
	Font fontSize = new Font("Courier New", 1, 26);
	
	String filePath = new File("").getAbsolutePath();
	String directory = filePath.concat("\\pokemon_data.txt");
	String image_dir = filePath.concat("\\go sprites resized_100x100\\");
	String help_file = filePath.concat("\\help_file.txt");
	
	Border lineBorder = BorderFactory.createLineBorder(Color.black,3);
	Border lineBorder_thick = BorderFactory.createLineBorder(Color.black,6);
	JPanel selectPanel;
	JPanel pokeInPanel;
	JPanel pokeOutPanel;
	JPanel left_PokePanel;
	JPanel right_PokePanel;
	JPanel titlePanel;
	
	Color light_green = new Color(144,238,144);// background color for the "Good Against" chart
	Color light_red = new Color(255,99,71);// background color for the "Bad Against" chart
	Color light_blue = new Color(176,196,222);// // background color for the Pokemon info box
	
	public PokemonPanel()
	{
		// calling the private setup function
		setup();
		
		MissingNoListener missingNo = new MissingNoListener();
		String M[] = {"Surf Up", "Surf Down"};
		surf = new JComboBox(M);
		surf.addActionListener(missingNo);
		surf.setPreferredSize(new Dimension(5, 1));
		
		// setting up the title 'Effectiveness'
		setLayout(new BorderLayout());
		title.setFont(new Font("SansSerif", Font.BOLD + Font.ITALIC, 31));
		title.setToolTipText("Effectiveness Chart");
		title.setHorizontalAlignment(JLabel.CENTER);
	    title.setVerticalAlignment(JLabel.CENTER);
	    
	    // setting up the help button
	    help_button = new JButton("Help");
		help_button.setFont(new Font("SansSerif", Font.BOLD, 20));
		help_button.setToolTipText("POkeGoBDB Help");
		help_button.addActionListener(new java.awt.event.ActionListener() 
		{
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) 
            {
            	if (Desktop.isDesktopSupported()) 
            	{
        		    try 
        		    {
        		    	// open the documentation pdf
        		        File myFile = new File("CS177-proj1documentation.pdf");
        		        Desktop.getDesktop().open(myFile);
        		    } 
        		    catch (Exception ex) 
        		    {
        		    	// no application registered for PDFs
        		    	ImageIcon logo = new ImageIcon("logo_vertical.png");
    					Image image = logo.getImage(); 
    					Image newimg = image.getScaledInstance(131, 126,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
    					logo = new ImageIcon(newimg);
        		    	try 
                    	{
                    		// read the text from the help file and display it in the pop-up dialog window
        					String help_text = readHelp(help_file);
        					JOptionPane.showMessageDialog(frame, help_text,"PokeGoBDB Help", JOptionPane.INFORMATION_MESSAGE, logo);
                    		
        				} 
                    	catch (IOException ioex) 
                    	{
        					// TODO Auto-generated catch block
        					ioex.printStackTrace();
        					// if the help file is missing or unreadable show a generic message in dialog window
        					JOptionPane.showMessageDialog(frame, "Read the PokeGoBDB documentation pdf", "PokeGoBDB Help", JOptionPane.INFORMATION_MESSAGE, logo);
        				}
        		        
        		    }
            	}
            	else
            	{
            		ImageIcon logo = new ImageIcon("logo_vertical.png");
					Image image = logo.getImage(); 
					Image newimg = image.getScaledInstance(131, 126,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
					logo = new ImageIcon(newimg);
    		    	try 
                	{
                		// read the text from the help file and display it in the pop-up dialog window
    					String help_text = readHelp(help_file);
    					JOptionPane.showMessageDialog(frame, help_text, "PokeGoBDB Help", JOptionPane.INFORMATION_MESSAGE, logo);
                		
    				} 
                	catch (IOException ioex) 
                	{
    					// TODO Auto-generated catch block
    					ioex.printStackTrace();
    					// if the help file is missing or unreadable show a generic message in dialog window
    					JOptionPane.showMessageDialog(frame, "Read the PokeGoBDB documentation pdf", "PokeGoBDB Help", JOptionPane.INFORMATION_MESSAGE, logo);
    				}
        		}
            	repaint();
            }
        });
	    
	    titlePanel = new JPanel();
	    GridLayout title_grid = new GridLayout(1,5);
	    titlePanel.setLayout(title_grid);
		/*ImageIcon logo = new ImageIcon("logo_horizontal.png");
		Image image = logo.getImage(); 
		Image newimg = image.getScaledInstance(207, 50,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		logo = new ImageIcon(newimg);
		JLabel picLabel = new JLabel(logo);
		titlePanel.add(picLabel);*/
	    titlePanel.add(new JPanel());
	    titlePanel.add(new JPanel());
	    titlePanel.add(title);
	    titlePanel.add(new JPanel());
	    titlePanel.add(help_button);
	    titlePanel.setLayout(title_grid);
	    titlePanel.setBorder(new EmptyBorder(20, 75, 20, 75));
		add(titlePanel, BorderLayout.NORTH);
		
		Arrays.sort(pokeNameABC);
		list = new JComboBox(pokeNameABC);//stores Pokemon names in the drop box.
		list.setToolTipText("Select a Pokémon");
		Plistener listener = new Plistener();
		list.addActionListener(listener);
		list.setPreferredSize(new Dimension(150, 30));
		//list.setBounds(650, 30, 125, 20);

		//selectedPokemon.setBounds(650, 210, 150, 20);//holds the name of the selected pokemon
		//selectedType.setBounds(650, 230, 150, 20);//holds the type of the selected pokemon type
		//selectedPicture.setBounds(650, 100, 110, 110);
		//info.setBounds(640, 0, 200, 20);
		info.setFont(new Font("SansSerif", Font.BOLD, 24)); 
		info.setText("Select a Pokemon:");
		info.setBorder(new EmptyBorder(20, 20, 20, 20));
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		selectPanel = new JPanel();
		selectPanel.setLayout(new FlowLayout());
		selectPanel.add(info);//add the 'Select a Pokemon' label
		selectPanel.add(surf);
		selectPanel.add(list);//add the JComboBox
		mainPanel.add(selectPanel, BorderLayout.NORTH);
		
		JPanel subPanel = new JPanel();
		subPanel.setLayout(new BorderLayout());
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1,5));
		
		pokeOutPanel = new JPanel();
		pokeOutPanel.setLayout(new FlowLayout());
		//pokeOutPanel.setBorder(lineBorder_thick);
		
		// setting up the GUI for the Pokemon info box
		pokeInPanel = new JPanel();
		pokeInPanel.setLayout(new BorderLayout());
		pokeInPanel.add(selectedPicture, BorderLayout.CENTER);
		selectedPokemon.setFont(new Font("SansSerif", Font.BOLD, 18));
		selectedPokemon.setBorder(new EmptyBorder(0, 15, 0, 0));
		pokeInPanel.add(selectedPokemon, BorderLayout.SOUTH);
		pokeOutPanel.add(pokeInPanel);
		selectedType.setFont(new Font("SansSerif", Font.BOLD, 15));
		selectedType.setBorder(new EmptyBorder(0, 25, 0, 0));
		pokeOutPanel.add(selectedType);
		topPanel.add(new JPanel());
		topPanel.add(new JPanel());
		topPanel.add(pokeOutPanel);
		topPanel.add(new JPanel());
		topPanel.add(new JPanel());
		subPanel.add(topPanel, BorderLayout.NORTH);
		
		JPanel effPanel = new JPanel();
		effPanel.setLayout(new GridLayout(1,2));
		
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		
		// setting up the "Good Against" label
		JLabel eff_leftLabel = new JLabel("Good Against");
		eff_leftLabel.setToolTipText("Good Against");
		eff_leftLabel.setFont(new Font("SansSerif", Font.BOLD, 35));
		eff_leftLabel.setHorizontalAlignment(JLabel.CENTER);
		eff_leftLabel.setVerticalAlignment(JLabel.TOP);
		eff_leftLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
		leftPanel.add(eff_leftLabel, BorderLayout.NORTH);
		
		// setting up the "Bad Against" label
		JLabel eff_rightLabel = new JLabel("Bad Against");
		eff_rightLabel.setToolTipText("Bad Against");
		eff_rightLabel.setFont(new Font("SansSerif", Font.BOLD, 35));
		eff_rightLabel.setHorizontalAlignment(JLabel.CENTER);
		eff_rightLabel.setVerticalAlignment(JLabel.TOP);
		eff_rightLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
		rightPanel.add(eff_rightLabel, BorderLayout.NORTH);
		
		// setting up the left panel where the "Good Against" chart will be displayed
		left_PokePanel = new JPanel();
		left_PokePanel.setLayout(new FlowLayout());
		left_PokePanel.setBackground(light_green);
		leftPanel.setBackground(light_green);
		leftPanel.setBorder(lineBorder);
		leftPanel.add(left_PokePanel, BorderLayout.CENTER);
		
		// setting up the right panel where the "Bad Against" chart will be displayed
		right_PokePanel = new JPanel();
		right_PokePanel.setLayout(new FlowLayout());
		right_PokePanel.setBackground(light_red);
		rightPanel.setBackground(light_red);
		rightPanel.setBorder(lineBorder);
		rightPanel.add(right_PokePanel, BorderLayout.CENTER);
		
		// adding the "Good Against" and "Bad Against" charts to a larger panel
		effPanel.add(leftPanel);
		effPanel.add(rightPanel);
		effPanel.setBorder(lineBorder);
		
		subPanel.add(effPanel, BorderLayout.CENTER);
		
		mainPanel.add(subPanel, BorderLayout.CENTER);
		
		add(mainPanel,BorderLayout.CENTER);
		
	    setPreferredSize(new Dimension(1600,800));
	    setBackground(Color.white);
	    //add(surf);
	        
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
	
	private void setup()
	{	
		Scanner pkmnIn = null;
		
		try 
		{
			pkmnIn = new Scanner(new File(directory));
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("Please specify a proper directory");
			e.printStackTrace();
		}
		
		// reading in the data from the pokemon_data.txt file
		String[] currentPkmn;
		int counter = 0;
		while(pkmnIn.hasNextLine())
		{
			currentPkmn = pkmnIn.nextLine().split(",");
			pokeType123[counter] = currentPkmn[1];//stores the pokemon type(s)
			pokeName123[counter] = currentPkmn[0];//stores the pokemon name in # order
			pokeNameABC[counter] = currentPkmn[0];//stores the pokemon name in alphabetical order			
			
			
			if(currentPkmn[1].contains("/"))
			{
				pokemon.put(currentPkmn[0], new Type(currentPkmn[1].split("/")));
			}
			else
			{
				pokemon.put(currentPkmn[0], new Type(currentPkmn[1]));
			}
			counter++;
		}
		
		for(int i = 0; i < NUMOFPOKEMON; i++)
		{
			// assign each label a Pokemon name in 123 order.
			pokeNameLabel[i] = new JLabel(pokeName123[i]);
			pokeNameLabel[i].setBorder(new EmptyBorder(0, 15, 0, 0));
			pokeNameLabel[i].setFont(new Font("SansSerif", Font.BOLD, 16));
		
			//NEED PICTURE FILES
			//attach jpg or png file on to Icon picture, then place all the pictures on to a JLabel
			picLabel[i] = new JLabel(new ImageIcon(image_dir + pokeName123[i] + ".png"));
			picLabel[i].setToolTipText("Type:"+pokemon.get(pokeName123[i]));
			
			// creating the individual Pokemon panels containing their image and name
			pokePanel[i] = new JPanel();
			pokePanel[i].setLayout(new BorderLayout());
			pokePanel[i].add(pokeNameLabel[i],BorderLayout.SOUTH);
			pokePanel[i].add(picLabel[i],BorderLayout.CENTER);
		}
	}
	
	public void paintComponent(Graphics background)
	{
		super.paintComponent(background);	
		//background.setFont(new Font("SansSerif", Font.BOLD, 30)); 
		//background.drawString("Effective Against", 130, 50);
		//background.drawString("Ineffective Against", 1040, 50);
	}

	private void setSCROLL(int _x)
	{
		SCROLL = _x;
	}//seems like direct changes made to SCROLL in the ActionListener doesn't permeate to the paintComponent.
	
    // function to remove all the previous pokemon from the charts when a new action is taken in the drop-down
	private void remove_pokemon()
	{
		for(int p = 0; p < NUMOFPOKEMON; p++)
		{
			left_PokePanel.remove(pokePanel[p]);//always reset and remove label when starting new action.
			right_PokePanel.remove(pokePanel[p]);//always reset and remove label when starting new action.
			repaint();
		}
		
	}
	public class Plistener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String s = list.getSelectedItem().toString();//takes the string entry of the JComboBox
			
			remove_pokemon();

			for(int i = 0; i < NUMOFPOKEMON; i++)
			{
				stats[i] = pokemon.get(s).compareTo(pokemon.get(pokeName123[i]));
			}//assigned every compared pokemon a number -3<0<3
			
			
			//pokeOutPanel.setBorder(lineBorder_thick);
			pokeOutPanel.setBorder(BorderFactory.createMatteBorder(6, 6, 0, 6, Color.BLACK));
			pokeOutPanel.setBackground(light_blue);
			pokeInPanel.setBackground(light_blue);
			String x = pokemon.get(s).toString();//output the attributes of the selected pokemon
			selectedPokemon.setText(s);
			selectedType.setText("TYPE: 	" + x);
			selectedPicture.setIcon(new ImageIcon(image_dir + s + ".png"));
			selectedPicture.setToolTipText(selectedPokemon.getText());
	
			int j = 0;
			int k = 0;
			for(int i = 0; i < NUMOFPOKEMON; i++)
			{
				if(j % 5 == 0 && j !=0)
				{
					j = 0;
					k++;
				}//go to the next row and start from the most left column
				if(stats[i] > 0)
				{
					pokePanel[i].setBackground(light_green);
					left_PokePanel.add(pokePanel[i]);
					pokeNameLabel[i].setBounds(20 + 100 * j, 200 + 130 * k, SIZE, 20);
					picLabel[i].setBounds(20 + 100 * j, 120 + 130 * k, SIZE+10, SIZE);
					pokeNameLabel[i].setHorizontalAlignment(SwingConstants.CENTER);
					j++;
				}
			}//display pokemon on the left side
			
			int j2 = 0;
			int k2 = 0;
			for(int i = 0; i < NUMOFPOKEMON; i++)
			{
				if(j2 % 5 == 0 && j2 !=0)
				{
					j2 = 0;
					k2++;
				}//go to the next row and start from the most left column
				if(stats[i] < 0)
				{
					pokePanel[i].setBackground(light_red);
					right_PokePanel.add(pokePanel[i]);
					pokeNameLabel[i].setBounds(950 + 100 * j2, 200 + 130 * k2, SIZE, 20);
					picLabel[i].setBounds(950 + 100 * j2, 120 + 130 * k2, SIZE+10, SIZE);
					pokeNameLabel[i].setHorizontalAlignment(SwingConstants.CENTER);
					j2++;
				}
			}//display pokemon on the right side.
			
			
			int SCROLL;//sets up the magnitude of the scroll bar
			if(k > k2)
			{
				SCROLL = k;
			}
			else
				SCROLL = k2;
			setPreferredSize(new Dimension(1500 + 1,500 + SCROLL * 100));
			frame.setSize(frame.getSize().width, frame.getSize().height+ SCROLL * 100);
			setSCROLL(SCROLL);//dynamic background depends on the # of pokemon displayed
			repaint();
		}
	}
	

	public class MissingNoListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String s1 = surf.getSelectedItem().toString();
			if(s1 == "Surf Up")up++;
			if(s1 == "Surf Down")down++;
			if(up > 10 && down  > 10)
			{
				selectPanel.remove(surf);
				selectPanel.remove(list);
				titlePanel.remove(help_button);
				pokeOutPanel.setBorder(BorderFactory.createMatteBorder(6, 6, 0, 6, Color.BLACK));
				pokeOutPanel.setBackground(light_blue);
				pokeInPanel.setBackground(light_blue);
				selectedPokemon.setText("MissingNo: ???");
				selectedPokemon.setBorder(new EmptyBorder(0, 5, 0, 0));
				selectedType.setText("Type: Normal,Bird");
				selectedType.setBorder(new EmptyBorder(0, 20, 0, 0));
				ImageIcon missing = new ImageIcon(image_dir+"MissingNo.png");
				Image m_image = missing.getImage(); 
				Image newimg = m_image.getScaledInstance(133, 149,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
				missing = new ImageIcon(newimg);
				selectedPicture.setIcon(missing);
				selectedPicture.setToolTipText("MissingNo");
				for(int p = 0; p < NUMOFPOKEMON; p++)
				{
					left_PokePanel.remove(pokePanel[p]);
					right_PokePanel.remove(pokePanel[p]);
				}
			}
			repaint();
		}
	}
	
}
