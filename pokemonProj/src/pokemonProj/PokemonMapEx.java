package pokemonProj;



import java.io.File;

import java.io.FileNotFoundException;

import java.util.HashMap;

import java.util.Scanner;



public class PokemonMapEx {



	public static void main(String[] args) {

		HashMap<String, Type> pokemon = new HashMap<String, Type>();

		

		String directory = "pokemon_data.txt";

//		Scanner scanner = new Scanner(System.in);
//		String x;
//		scanner.nextLine();
		

		Scanner pkmnIn = null;

		try {

			pkmnIn = new Scanner(new File(directory));

		} catch (FileNotFoundException e) {

			System.out.println("Please specify a proper directory");

			e.printStackTrace();

		}
	
		String[] currentPkmn = new String[1];
//		String currentPkmn[] = {"",""};

		while(pkmnIn.hasNextLine())

		{

			currentPkmn = pkmnIn.nextLine().split(",");

			if(currentPkmn[1].contains("/"))

				pokemon.put(currentPkmn[0], new Type(currentPkmn[1].split("/")));

			else 

				pokemon.put(currentPkmn[0], new Type(currentPkmn[1]));

		}

		

		

		System.out.println(pokemon.get("Dragonite").compareTo(pokemon.get("Lapras")));

		System.out.println(pokemon.get("Zubat"));

		System.out.println(pokemon.get("Jigglypuff"));

		System.out.println(pokemon.get("Diglett"));

	}

}