package es.iesjandula.pokemon.graphicInterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import es.iesjandula.pokemon.utils.Pokemon;

/**
 * @author Alejandro Cazalla Perez, Alvaro Marmol Romero
 * 
 * This class is in charge of designing the pokemon cards
 */
public class CardPanel extends JPanel
{
	final Logger logger = LogManager.getLogger();
	/** pokemon card */
	private Pokemon card;
	/** Maximum health points */
	private int maxHp;
	/** Actual health points */
	private int actualHp;

	/**
	 * Constructor of CardPanel class
	 * @param card
	 */
	public CardPanel(Pokemon card)
	{
		this.card = card;
		this.maxHp = card.getInitialHealth();
		this.actualHp = card.getHp();
		setPreferredSize(new Dimension(200, 300));
	}

	/**
	 * Method to update the card
	 * @param card
	 */
	public void updateCard(Pokemon card)
	{
		this.card = card;
		this.maxHp = card.getInitialHealth();
		this.actualHp = card.getHp();
		repaint();
	}

	/**
	 * Method to paint the card 
	 */
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		g.drawString("Type 1: " + card.getType1(), 10, 65);
		g.drawString("Type 2: " + card.getType2(), 10, 85);
		g.drawString("Total Points: " + String.valueOf(card.getTotal()), 10, 105);
		g.drawString("HP: " + String.valueOf(card.getHp()), 10, 125);
		g.drawString("Attack: " + String.valueOf(card.getAttack()), 10, 145);
		g.drawString("Defense: " + String.valueOf(card.getDefense()), 10, 165);
		g.drawString("Sp.Atack: " + String.valueOf(card.getSpAttack()), 10, 185);
		g.drawString("Sp.Defense: " + String.valueOf(card.getSpDefense()), 10, 205);
		g.drawString("Generation: " + String.valueOf(card.getGeneration()), 10, 225);
		g.drawString("Legendary: " + String.valueOf(card.isLegendary()), 10, 245);
		try
		{
			BufferedImage image = ImageIO.read(new File(card.getUrl()));

			int newWidth = 100; 
			int newHeight = 100; 

			Image resizedImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

			g.drawImage(resizedImage, 10, 250, this);

		} catch (IOException e)
		{
			String error = "An error ocurred while the image was charging";
			logger.error(error, e);
		}

		g.setColor(Color.RED);
		g.fillRect(10, 30, 180, 20);

		int lifeWidth = (int) (180.0 * actualHp / maxHp);
		g.setColor(Color.GREEN);
		g.fillRect(10, 30, lifeWidth, 20);

		g.setColor(Color.BLACK);
		String lifeText = "Life: " + actualHp + " / " + maxHp;
		FontMetrics fm = g.getFontMetrics();
		int textWidth = fm.stringWidth(lifeText);
		int x = 10 + (180 - textWidth) / 2;
		g.drawString(lifeText, x, 45);

		g.setColor(Color.BLACK);

		Font font = g.getFont().deriveFont(Font.BOLD, 14f);
		g.setFont(font);

		g.drawString(card.getName(), 10, 20);

		updateCard(card);
	}

	/**
	 * Method to update the health bar 
	 * @param newHealth
	 */
	public void updateHealthBar(int newHealth)
	{
		this.actualHp = newHealth;
		repaint();
	}
}
