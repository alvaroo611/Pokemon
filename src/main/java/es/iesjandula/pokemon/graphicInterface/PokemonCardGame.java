package es.iesjandula.pokemon.graphicInterface;

import javax.swing.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import es.iesjandula.pokemon.exceptions.PokemonException;
import es.iesjandula.pokemon.utils.ParserPokemon;
import es.iesjandula.pokemon.utils.Pokemon;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import es.iesjandula.pokemon.utils.PokemonUtils;

/**
 * @author Alejandro Cazalla Perez, Alvaro Marmol Romero
 * 
 * Class in charge doing the main interface
 */
public class PokemonCardGame extends JFrame
{
	/** Player 1 pokemon deck of cards */
	private List<Pokemon> player1Deck;
	/** Player 2 pokemon deck of cards */
	private List<Pokemon> player2Deck;
	/** Player 1 deck index */
	private int player1CardIndex = 0;
	/** Player 2 deck index */
	private int player2CardIndex = 0;
	/** Player 1 panel */
	private JPanel player1Panel;
	/** Player 2 panel */
	private JPanel player2Panel;
	/** Player 1 cardLayout */
	private CardLayout cardLayout;
	/** Player 2 cardLayout */
	private CardLayout cardLayout2;
	/** save game */
	private boolean save = false;

	/**
	 * Method in charge of paint and give funcionality to the graphic interface
	 * @param player1Deck
	 * @param player2Deck
	 * @throws PokemonException
	 */
	public PokemonCardGame(List<Pokemon> player1Deck, List<Pokemon> player2Deck) throws PokemonException
	{
		final Logger logger = LogManager.getLogger();
		this.player1Deck = player1Deck;
		this.player2Deck = player2Deck;

		setTitle("Pokemon Card Game");
		setSize(1000, 600);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		player1Panel = new JPanel();
		player2Panel = new JPanel();
		cardLayout = new CardLayout();
		cardLayout2 = new CardLayout();
		player1Panel.setLayout(cardLayout);
		player2Panel.setLayout(cardLayout2);

		add(player1Panel, BorderLayout.WEST);
		add(player2Panel, BorderLayout.EAST);

		JButton startBattleButton = new JButton("Comenzar Combate");

		JButton player1NextButton = new JButton("Siguiente Jugador 1");
		JButton player1PrevButton = new JButton("Anterior Jugador 1");
		JButton player2NextButton = new JButton("Siguiente Jugador 2");
		JButton player2PrevButton = new JButton("Anterior Jugador 2");
		JButton saveButton = new JButton("Guardar partida");

		player1NextButton.addActionListener(e ->
		{
			player1CardIndex = (player1CardIndex + 1) % player1Deck.size();
			cardLayout.show(player1Panel, Integer.toString(player1CardIndex));
		});

		player1PrevButton.addActionListener(e ->
		{
			player1CardIndex = (player1CardIndex - 1 + player1Deck.size()) % player1Deck.size();
			cardLayout.show(player1Panel, Integer.toString(player1CardIndex));
		});

		player2NextButton.addActionListener(e ->
		{
			player2CardIndex = (player2CardIndex + 1) % player2Deck.size();
			cardLayout2.show(player2Panel, Integer.toString(player2CardIndex));
		});

		player2PrevButton.addActionListener(e ->
		{
			player2CardIndex = (player2CardIndex - 1 + player2Deck.size()) % player2Deck.size();
			cardLayout2.show(player2Panel, Integer.toString(player2CardIndex));
		});

		saveButton.addActionListener(e ->
		{
			save = true;
			try
			{
				ParserPokemon.savePokemon(player1Deck, "jugador1.txt");
				ParserPokemon.savePokemon(player2Deck, "jugador2.txt");
			} catch (PokemonException exception)
			{
				String error = "Excepcion propia";
				logger.error(error, e);
			}
		});

		startBattleButton.addActionListener(e ->
		{
			List<Pokemon> combate = displayInitialCards(player1Panel, player2Panel);
			Pokemon attack = combate.get(0);
			Pokemon defender = combate.get(1);
			if (!player1Deck.isEmpty() && !player2Deck.isEmpty())
			{
				updateHealthBars(attack, defender);
			} else
			{
				dispose();
				System.exit(0);

				System.out.println("El juego ha terminado");
			}
			Pokemon loser = PokemonUtils.determineLoser(attack, defender);
			combate.remove(loser);
		});
		JPanel buttonStart = new JPanel();
		JPanel buttonPanel1 = new JPanel();
		JPanel buttonPanel2 = new JPanel();
		buttonPanel1.add(player1PrevButton);
		buttonPanel1.add(player1NextButton);
		buttonStart.add(startBattleButton);
		buttonStart.add(saveButton);
		buttonPanel2.add(player2PrevButton);
		buttonPanel2.add(player2NextButton);

		JPanel buttonContainer = new JPanel();
		buttonContainer.setLayout(new GridLayout(3, 1));

		JPanel labelsPanel = new JPanel();
		labelsPanel.setLayout(new BoxLayout(labelsPanel, BoxLayout.Y_AXIS));

		buttonContainer.add(buttonPanel1);
		buttonContainer.add(buttonStart);

		add(buttonContainer, BorderLayout.NORTH);
		add(labelsPanel, BorderLayout.CENTER);
		add(buttonPanel2, BorderLayout.SOUTH);

		displayInitialCards(player1Panel, player2Panel);

	}

	/**
	 * Method to show the initial cards
	 * @param player1Panel
	 * @param player2Panel
	 * @return
	 */
	private List<Pokemon> displayInitialCards(JPanel player1Panel, JPanel player2Panel)
	{
		for (int i = 0; i < player1Deck.size(); i++)
		{
			player1Panel.add(new CardPanel(player1Deck.get(i)), Integer.toString(i));

		}
		for (int i = 0; i < player2Deck.size(); i++)
		{
			player2Panel.add(new CardPanel(player2Deck.get(i)), Integer.toString(i));
		}
		cardLayout.show(player1Panel, Integer.toString(player1CardIndex));
		cardLayout2.show(player2Panel, Integer.toString(player2CardIndex));
		List<Pokemon> rivales = new ArrayList<Pokemon>();
		Pokemon rival1 = this.player1Deck.get(player1CardIndex);
		rivales.add(rival1);
		Pokemon rival2 = this.player2Deck.get(player2CardIndex);
		rivales.add(rival2);
		return rivales;
	}

	/**
	 * Method to update the health bars of both pokemon
	 * @param player1
	 * @param player2
	 */
	private void updateHealthBars(Pokemon player1, Pokemon player2)
	{
		if (player1Panel != null && player2Panel != null)
		{
			int player1Speed = player1.getSpAttack();
			int player2Speed = player2.getSpAttack();
			if (player1Speed > player2Speed)
			{
				player1.attack(player2);
				player2.attack(player1);
			} else if (player1Speed < player2Speed)
			{
				player2.attack(player1);
				player1.attack(player2);
			} else
			{
				player1.attack(player2);
				player2.attack(player1);
			}
			int attackerIndex = player1Deck.indexOf(player1);
			int defenderIndex = player2Deck.indexOf(player2);

			if (attackerIndex >= 0 && defenderIndex >= 0)
			{
				((CardPanel) player1Panel.getComponent(attackerIndex)).updateHealthBar(player1.getHp());
				((CardPanel) player2Panel.getComponent(defenderIndex)).updateHealthBar(player2.getHp());
			}

			if (player1.getHp() <= 0)
			{
				player1Deck.remove(player1);
				if (attackerIndex >= 0)
				{
					player1Panel.remove(attackerIndex);
				}
			}
			if (player2.getHp() <= 0)
			{
				player2Deck.remove(player2);
				if (defenderIndex >= 0)
				{
					player2Panel.remove(defenderIndex);
				}
			}

			player1Panel.revalidate();
			player1Panel.repaint();
			player2Panel.revalidate();
			player2Panel.repaint();

			if (player1Deck.isEmpty())
			{
				String message = "Hasta la proxima, el jugador 2 gano";
				displayWinner(message);
				dispose();
				System.exit(0);
			}
			else if(player2Deck.isEmpty())
			{
				String message = "Hasta la proxima, el jugador 1 gano";
				displayWinner(message);
				dispose();
				System.exit(0);
			}
			else if(player1Deck.isEmpty() && player2Deck.isEmpty())
			{
				String message = "Hasta la proxima, esto ha sido un empate";
				displayWinner(message);
				dispose();
				System.exit(0);
			}
		}
	}

	/**
	 * Method to show a message of end of the combat
	 * @param message
	 */
	private void displayWinner(String message)
	{
		JLabel endLabel = new JLabel(message);

		Font font = new Font("Arial", Font.BOLD, 24);
		endLabel.setFont(font);

		JPanel winnerPanel = new JPanel();
		winnerPanel.setLayout(new BoxLayout(winnerPanel, BoxLayout.Y_AXIS));
		winnerPanel.add(endLabel);

		JOptionPane.showMessageDialog(this, winnerPanel, "Fin del Combate", JOptionPane.INFORMATION_MESSAGE);

		File savedGame1 = new File("jugador1.txt");
		File savedGame2 = new File("jugador2.txt");
		if (save == false)
		{
			savedGame1.delete();
			savedGame2.delete();
		}

		dispose();
		System.exit(0);
	}

}
