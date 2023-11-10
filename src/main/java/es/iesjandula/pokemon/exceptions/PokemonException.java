package es.iesjandula.pokemon.exceptions;

/**
 * @author Alejandro Cazalla Perez, Alvaro Marmol Romero
 * 
 * This is the class with the self exception
 */
public class PokemonException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PokemonException()
	{
		super();
	}

	public PokemonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PokemonException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public PokemonException(String message)
	{
		super(message);
	}

	public PokemonException(Throwable cause)
	{
		super(cause);
	}
	

}
