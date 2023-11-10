package es.iesjandula.pokemon.utils;

/**
 * @author Alejandro Cazalla Perez, Alvaro Marmol Romero
 * 
 * Class with two methods, the first one to establish the losing pokemon and the second one to check if it is super effective
 */
public class PokemonUtils
{
	/**
	 * Method to establish the losing pokemon
	 * @param pokemon1
	 * @param pokemon2
	 * @return
	 */
	public static Pokemon determineLoser(Pokemon pokemon1, Pokemon pokemon2)
	{
		// Compare the types
		if (isSuperEffectiveAgainst(pokemon1, pokemon2))
		{
			return pokemon1;
		} else if (isSuperEffectiveAgainst(pokemon2, pokemon1))
		{
			return pokemon2;
		} else
		{
			// If the types have no advantages or disadvantages, compare hp
			if (pokemon1.getHp() < pokemon2.getHp())
			{
				return pokemon1;
			} else if (pokemon1.getHp() > pokemon2.getHp())
			{
				return pokemon2;
			} else
			{
				return null;
			}
		}

	}

	/**
	 * Method that checks if one type is super effective against another
	 * @param attacker
	 * @param defender
	 * @return
	 */
	private static boolean isSuperEffectiveAgainst(Pokemon attacker, Pokemon defender)
	{
		String type1 = attacker.getType1();
		String type2 = attacker.getType2();

		if (type1.equals("Grass") || type2.equals("Grass")
				&& (defender.getType1().equals("Water") || defender.getType2().equals("Water")))
		{
			return true;
		} else if (type1.equals("Fire")
				|| type2.equals("Fire") && (defender.getType1().equals("Grass") || defender.getType2().equals("Grass")))
		{
			return true;
		} else if (type1.equals("Water")
				|| type2.equals("Water") && (defender.getType1().equals("Fire") || defender.getType2().equals("Fire")))
		{
			return true;
		} else if (type1.equals("Electric") || type2.equals("Electric")
				&& (defender.getType1().equals("Water") || defender.getType2().equals("Water")))
		{
			return true;
		} else if (type1.equals("Grass") || type2.equals("Grass")
				&& (defender.getType1().equals("Electric") || defender.getType2().equals("Electric")))
		{
			return true;
		} else if (type1.equals("Water") || type2.equals("Water")
				&& (defender.getType1().equals("Electric") || defender.getType2().equals("Electric")))
		{
			return true;
		} else if (type1.equals("Electric") || type2.equals("Electric")
				&& (defender.getType1().equals("Fire") || defender.getType2().equals("Fire")))
		{
			return true;
		}

		return false;

	}

}
