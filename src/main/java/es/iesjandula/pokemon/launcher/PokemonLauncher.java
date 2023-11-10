package es.iesjandula.pokemon.launcher;

import java.io.File;
import java.util.List;

import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import es.iesjandula.pokemon.utils.Pokemon;
import es.iesjandula.pokemon.exceptions.PokemonException;
import es.iesjandula.pokemon.graphicInterface.PokemonCardGame;
import es.iesjandula.pokemon.utils.ParserPokemon;

/**
 * @author Alejandro Cazalla Perez, Alvaro Marmol Romero
 * 
 * This is the main class of the program
 */
public class PokemonLauncher
{
	public static void main(String[] args) throws PokemonException
	{
		final Logger logger = LogManager.getLogger();
		/**Pokemon list with all the pokemon*/
		List<Pokemon> pokemon = ParserPokemon.parseCsv();

		System.out.println("YOUR CARDS: ");
		List<Pokemon> pokemonListUser = Pokemon.randomCards(pokemon);
		for (Pokemon card : pokemonListUser)
		{
			System.out.println(card);
		}

		System.out.println("OPPONENT CARDS: ");
		List<Pokemon> pokemonListOpponent = Pokemon.randomCards(pokemon);
		for (Pokemon card : pokemonListOpponent)
		{
			System.out.println(card);
		}

		/**file with the pokemon of player 1 when the game was saved*/
		File savedGame1 = new File("jugador1.txt");
		/**file with the pokemon of player 2 when the game was saved*/
		File savedGame2 = new File("jugador2.txt");

		if (savedGame1.exists() && savedGame2.exists())
		{
			List<Pokemon> player1List = ParserPokemon.readSelectedPokemonFromFile("jugador1.txt");
			List<Pokemon> player2List = ParserPokemon.readSelectedPokemonFromFile("jugador2.txt");
			SwingUtilities.invokeLater(() ->
			{
				PokemonCardGame game = null;
				try
				{
					game = new PokemonCardGame(player1List, player2List);
				} catch (PokemonException e)
				{
					String error = "Excepcion propia";
					logger.error(error, e);
				}
				game.setVisible(true);
			});
		} else
		{
			SwingUtilities.invokeLater(() ->
			{
				PokemonCardGame game = null;
				try
				{
					game = new PokemonCardGame(pokemonListUser, pokemonListOpponent);
				} catch (PokemonException e)
				{
					String error = "self exception";
					logger.error(error, e);
				}
				game.setVisible(true);
			});
		}
	}
}
