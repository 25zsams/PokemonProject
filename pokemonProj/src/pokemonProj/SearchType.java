package pokemonProj;

import javax.swing.*;

public class SearchType 
{
	//*****************************main()********************************
	public static void main(String[] args)
	{
	   SwingUtilities.invokeLater(new Runnable()
	   {
		   public void run()
		   {
			   JFrame jf = new JFrame("PokeGoBDB");	 
		       jf.setSize(1000, 750);
		       jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		       jf.setLocationRelativeTo(null);
		       jf.getContentPane().add(new SearchPanel());
		       jf.setVisible(true);
		   }
	   });   
	}
}
