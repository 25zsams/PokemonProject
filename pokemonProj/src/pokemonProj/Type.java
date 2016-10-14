package pokemonProj;
import javax.swing.JPanel;

public class Type extends JPanel implements Comparable<Type>{

	private int type1, type2;

	public static final int NORMAL = 0, FIGHTING = 1, FLYING = 2, POISON = 3, GROUND = 4, ROCK = 5, BUG = 6, GHOST = 7,
			STEEL = 8, FIRE = 9, WATER = 10, GRASS = 11, ELECTRIC = 12, PSYCHIC = 13, ICE = 14, DRAGON = 15, DARK = 16,
			FAIRY = 17, DNE = 18;

	public static final String[] TYPES = { "Normal", "Fighting", "Flying", "Poison", "Ground", "Rock", "Bug", "Ghost",
			"Steel", "Fire", "Water", "Grass", "Electric", "Psychic", "Ice", "Dragon", "Dark", "Fairy" };

	// 2 = effective, 1 = neutral, 0 = ineffective
	private static final int[][] chart = { 
			{ 1, 1, 1, 1, 1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, // normal
			{ 2, 1, 0, 0, 1, 2, 0, 0, 2, 1, 1, 1, 1, 0, 2, 1, 2, 0 }, // fighting
			{ 1, 2, 1, 1, 1, 0, 2, 1, 0, 1, 1, 2, 0, 1, 1, 1, 1, 1 }, // flying
			{ 1, 1, 1, 0, 0, 0, 1, 0, 0, 1, 1, 2, 1, 1, 1, 1, 1, 2 }, // poison
			{ 1, 1, 0, 2, 1, 2, 0, 1, 2, 2, 1, 0, 2, 1, 1, 1, 1, 1 }, // ground
			{ 1, 0, 2, 1, 0, 1, 2, 1, 0, 2, 1, 1, 1, 1, 2, 1, 1, 1 }, // rock
			{ 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 2, 1, 2, 1, 1, 2, 0 }, // bug
			{ 0, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1 }, // ghost
			{ 1, 1, 1, 1, 1, 2, 1, 1, 0, 0, 0, 1, 0, 1, 2, 1, 1, 2 }, // steel
			{ 1, 1, 1, 1, 1, 0, 2, 1, 2, 0, 0, 2, 1, 1, 2, 0, 1, 1 }, // fire
			{ 1, 1, 1, 1, 2, 2, 1, 1, 1, 2, 0, 0, 1, 1, 1, 0, 1, 1 }, // water
			{ 1, 1, 0, 0, 2, 2, 0, 1, 0, 0, 2, 0, 1, 1, 1, 0, 1, 1 }, // grass
			{ 1, 1, 2, 1, 0, 1, 1, 1, 1, 1, 2, 0, 0, 1, 1, 0, 1, 1 }, // electric
			{ 1, 2, 1, 2, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1 }, // psychic
			{ 1, 1, 2, 1, 2, 1, 1, 1, 0, 0, 0, 2, 1, 1, 0, 2, 1, 1 }, // ice
			{ 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 2, 1, 0 }, // dragon
			{ 1, 0, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 0, 0 }, // dark
			{ 1, 2, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 2, 2, 1 } // fairy
	};

	public String[] getTYPES(){
		return TYPES;
	}
	
	public Type(int a) {
		this(a, DNE);
	}

	public Type(int a, int b) {
		type1 = a;
		type2 = b;
	}
	
	public Type(String a)
	{
		this(toInt(a), DNE);
	}
	
	public Type(String[] a)
	{
		this(toInt(a[0]),toInt(a[1]));
	}

	private static int toInt(String a) {
		switch (a) {
		case "Normal":
			return 0;
		case "Fighting":
			return 1;
		case "Flying":
			return 2;
		case "Poison":
			return 3;
		case "Ground":
			return 4;
		case "Rock":
			return 5;
		case "Bug":
			return 6;
		case "Ghost":
			return 7;
		case "Steel":
			return 8;
		case "Fire":
			return 9;
		case "Water":
			return 10;
		case "Grass":
			return 11;
		case "Electric":
			return 12;
		case "Psychic":
			return 13;
		case "Ice":
			return 14;
		case "Dragon":
			return 15;
		case "Dark":
			return 16;
		case "Fairy":
			return 17;
		default:
			return DNE;

		}
	}

	public String toString() {
		if (type2 == DNE)
			return TYPES[type1];
		return TYPES[type1] + ", " + TYPES[type2];
	}

	/**
	 * @param other
	 * @return >0 if this is effective against other, 0 if neutral, <0 if this
	 *         is ineffective against other.
	 */
public int compareTo(Type other) {
		int re = 0;
		boolean thisTypeTwo = this.type2 != DNE ? true : false;
		boolean otherTypeTwo = other.type2 != DNE ? true : false;
		
		
		re += chart[this.type1][other.type1] - 1;

		if (thisTypeTwo)// if this has a 2nd type
			re += chart[this.type2][other.type1] - 1;

		if (otherTypeTwo)// if other has a 2nd type
		{
			re += chart[this.type1][other.type2] - 1;
			if (thisTypeTwo)// if this has a 2nd type and other has a 2nd
									// type
				re += chart[this.type2][other.type2] - 1;
		}
		
		//comparing other against this
		
		re -= chart[other.type1][this.type1] + 1;

		if (otherTypeTwo)// if this other a 2nd type
			re -= chart[other.type2][this.type1] + 1;

		if (thisTypeTwo)// if this has a 2nd type
		{
			re -= chart[other.type1][this.type2] + 1;
			if (otherTypeTwo)// if other has a 2nd type and this has a 2nd
									// type
				re -= chart[other.type2][this.type2] + 1;
		}

		return re;
	}


}
