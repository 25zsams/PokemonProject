package pokemonProj;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Pokemon {

	public static void main (String[] args) {
		PokemonPanel.frame = new JFrame("Pokemon Chart");
		PokemonPanel.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		PokemonPanel pokemonPanel = new PokemonPanel();
		JScrollPane scroll = new JScrollPane(pokemonPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		
		pokemonPanel.setLayout(null);
		PokemonPanel.frame.add(scroll);
		PokemonPanel.frame.getContentPane().add(scroll);
		PokemonPanel.frame.pack();
		PokemonPanel.frame.setVisible(true);
		
		
		

	}

}
