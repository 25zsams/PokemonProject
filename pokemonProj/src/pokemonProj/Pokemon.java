package pokemonProj;

import javax.swing.*;
import java.awt.*;

public class Pokemon 
{
	
	final static String EFFECTPANEL = "Effectiveness Chart";
    final static String SEARCHPANEL = "Search By Type";
    final static int extraWindowWidth = 100;

    public void addComponentToPane(Container pane) 
    {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont( new Font( "Dialog", Font.BOLD|Font.ITALIC, 24 ) );

        PokemonPanel pokemonPanel = new PokemonPanel();
        JScrollPane scroll = new JScrollPane(pokemonPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        SearchPanel searchPanel = new SearchPanel();

        tabbedPane.addTab(EFFECTPANEL, scroll);
        tabbedPane.addTab(SEARCHPANEL, searchPanel);

        pane.add(tabbedPane, BorderLayout.CENTER);
    }
    
	public static void main (String[] args) 
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				PokemonPanel.frame = new JFrame("PokeGoBDB");
				PokemonPanel.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				//PokemonPanel pokemonPanel = new PokemonPanel();
				//JScrollPane scroll = new JScrollPane(pokemonPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				
				Pokemon tab = new Pokemon();
				tab.addComponentToPane(PokemonPanel.frame.getContentPane());
		
				//pokemonPanel.setLayout(null);
				//PokemonPanel.frame.add(scroll);
				//PokemonPanel.frame.getContentPane().add(scroll);
				PokemonPanel.frame.setIconImage(new ImageIcon("logo_vertical.png").getImage());
				PokemonPanel.frame.pack();
				PokemonPanel.frame.setVisible(true);
			}
	    });
	}
}
