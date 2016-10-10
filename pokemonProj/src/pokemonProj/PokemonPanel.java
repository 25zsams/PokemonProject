package pokemonProj;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PokemonPanel extends JPanel{
	
	static JFrame frame;
	
	final int SIZE = 85;
	final int NUMOFPOKEMON = 151;
	private int SCROLL;
	int up = 0, down = 0;
	
	JComboBox list, surf;
	HashMap<String, Type> pokemon = new HashMap<String, Type>();
	String[] pokeName123 = new String[NUMOFPOKEMON];
	String[] pokeNameABC = new String[NUMOFPOKEMON];
	
	Icon[] picture = new ImageIcon[NUMOFPOKEMON];
	JLabel[] picLabel = new JLabel[NUMOFPOKEMON];//this label holds graphical pictures
	JLabel[] pokeNameLabel = new JLabel[NUMOFPOKEMON];//this label holds string names of pokemon
	JLabel selectedPokemon = new JLabel();//will hold the selected pokemon name
	JLabel selectedType = new JLabel();//will hold the type of the selected pokemon
	JLabel selectedPicture = new JLabel();
	int[] stats = new int[NUMOFPOKEMON];
	Font fontSize = new Font("Courier New", 1, 26);
	
	
	public PokemonPanel(){
		
		
		
		String directory = "C:\\Users\\25zsa_000\\Desktop\\Eclipse packages\\pokemonProj\\pokemon_data.txt";//pokemon_data2 works best
		Scanner pkmnIn = null;
		
		try {
			pkmnIn = new Scanner(new File(directory));
		} catch (FileNotFoundException e) {
			System.out.println("Please specify a proper directory");
			e.printStackTrace();
		}
		
		String[] currentPkmn;
		int counter = 0;
		while(pkmnIn.hasNextLine()){
			currentPkmn = pkmnIn.nextLine().split(",");
			
			if(currentPkmn[1].contains("/")){
				pokemon.put(currentPkmn[0], new Type(currentPkmn[1].split("/")));
			}
			else{
				pokemon.put(currentPkmn[0], new Type(currentPkmn[1]));
			}
			
			pokeName123[counter] = currentPkmn[0];
			pokeNameABC[counter] = currentPkmn[0];
			counter++;
		}
		
		
		Arrays.sort(pokeNameABC);
		list = new JComboBox(pokeNameABC);//stores Pokemons names and stats them in the drop box.
		
		
		for(int i = 0; i < NUMOFPOKEMON; i++){
			pokeNameLabel[i] = new JLabel(pokeName123[i]);
		}// assign each label a Pokemon name in 123 order.
		
		
		//NEED PICTURE FILES
		for(int x = 0; x < NUMOFPOKEMON; x++){
			picLabel[x] = new JLabel(new ImageIcon("C:\\Users\\25zsa_000\\Desktop\\Eclipse packages\\pokemonProj\\go sprites resized_100x100\\" + pokeName123[x] + ".png"));
		}//attach jpg or png file on to Icon picture, then place all the pictures on to a JLabel
		

		selectedPokemon.setBounds(650, 310, 150, 20);//holds the name of the selected pokemon
		selectedType.setBounds(650, 330, 150, 20);//holds the name of the seelected pokemon type
		selectedPicture.setBounds(650, 200, 110, 110);
		add(selectedPicture);
		add(selectedType);
		add(selectedPokemon);

		MissingNoListener missingNo = new MissingNoListener();
		String M[] = {"Surf Up", "Surf Down"};
		surf = new JComboBox(M);
		Plistener listener = new Plistener();
		list.addActionListener(listener);
		list.setBounds(650, 30, 125, 20);
		surf.addActionListener(missingNo);
		surf.setBounds(700, 100, 100, 1);
	    setPreferredSize(new Dimension(1500,700));
	    setBackground(Color.gray);
	    add(list);//add the JComboBox
	    add(surf);
	        
	}
	
	public void paintComponent(Graphics background){
		super.paintComponent(background);	
		background.setColor(Color.blue);
		background.drawLine(50, 50, 1000, 700);
		background.drawOval(500, 500, 100, 100);
		background.fillOval(300, 300, 200, 200);
		background.fillRect(100, 100, 2001, 200);
		
		
		
		//do not insert background after THIS line.
		background.setColor(Color.green);
		background.setFont(fontSize);
		background.drawString("Effective Against", 130, 50);
		background.fillRect(15, 80, 500, 130 + SCROLL * 140);	
		background.setColor(Color.red);
		background.drawString("Ineffective Against", 1040, 50);
		background.fillRect(946, 80, 500, 130 + SCROLL * 140);
//		repaint();
	}

	private void setSCROLL(int _x){
		SCROLL = _x;
	}//seems like direct changes made to SCROLL in the ActionListener doesn't permeate to the paintComponent.
	

	public class Plistener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			String s = list.getSelectedItem().toString();//takes the string entry of the JComboBox
			
			for(int p = 0; p < NUMOFPOKEMON; p++){
				remove(pokeNameLabel[p]);//always reset and remove label when starting new action.
				remove(picLabel[p]);//always reset and remove the picture when starting a new action.
			}
			

			for(int i = 0; i < NUMOFPOKEMON; i++){
				stats[i] = pokemon.get(s).compareTo(pokemon.get(pokeName123[i]));
			}//assigned every compared pokemon a number -3<0<3
			
			
			String x = pokemon.get(s).toString();//out puts the attributes of the selected pokemon
			selectedPokemon.setText("Pokemon:	 " + s);
			selectedType.setText("Type: 	" + x);
			selectedPicture.setIcon(new ImageIcon("C:\\Users\\25zsa_000\\Desktop\\Eclipse packages\\pokemonProj\\go sprites resized_100x100\\" + s + ".png"));

	
			int j = 0;
			int k = 0;
			for(int i = 0; i < NUMOFPOKEMON; i++){
				if(j % 5 == 0 && j !=0){
					j = 0;
					k++;
				}//go to the next row and start from the most left column
				if(stats[i] > 0){
					add(pokeNameLabel[i]);
					add(picLabel[i]);
					pokeNameLabel[i].setBounds(20 + 100 * j, 200 + 130 * k, SIZE, 20);
					picLabel[i].setBounds(20 + 100 * j, 120 + 130 * k, SIZE+10, SIZE);
					pokeNameLabel[i].setHorizontalAlignment(SwingConstants.CENTER);
					j++;
				}
			}//display pokemon on the left side
			
			int j2 = 0;
			int k2 = 0;
			for(int i = 0; i < NUMOFPOKEMON; i++){
				if(j2 % 5 == 0 && j2 !=0){
					j2 = 0;
					k2++;
				}//go to the next row and start from the most left column
				if(stats[i] < 0){
					add(pokeNameLabel[i]);
					add(picLabel[i]);
					pokeNameLabel[i].setBounds(950 + 100 * j2, 200 + 130 * k2, SIZE, 20);
					picLabel[i].setBounds(950 + 100 * j2, 120 + 130 * k2, SIZE+10, SIZE);
					pokeNameLabel[i].setHorizontalAlignment(SwingConstants.CENTER);
					j2++;
				}
			}//display pokemon on the right side.
			
			
			int SCROLL;//sets up the magnitude of the scroll bar
			if(k > k2){
				SCROLL = k;
			}
			else
				SCROLL = k2;
			setPreferredSize(new Dimension(1500 + 1,700 + SCROLL * 100));
			frame.setSize(frame.getSize().width, frame.getSize().height+ SCROLL * 100);
			setSCROLL(SCROLL);//dynamic background depends on the # of pokemons displayed
			repaint();
		}
	}
	

	public class MissingNoListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			String s1 = surf.getSelectedItem().toString();
			if(s1 == "Surf Up")up++;
			if(s1 == "Surf Down")down++;
			if(up > 7 && down  > 7){
				remove(list);
				remove(surf);
				selectedPokemon.setText("MissingNo: ???");
				selectedType.setText("Type: Normal,Bird");
				selectedPicture.setIcon(new ImageIcon("C:\\Users\\25zsa_000\\Desktop\\Eclipse packages\\pokemonProj\\go sprites resized_100x100\\MissingNo.png"));
				for(int p = 0; p < NUMOFPOKEMON; p++){
					remove(pokeNameLabel[p]);
					remove(picLabel[p]);
				}
			}
			repaint();
		}
	}



	
	
	
}
