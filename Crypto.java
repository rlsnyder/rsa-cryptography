/**
 * @author R. Logan Snyder
 * <p> Crypto
 * <p> Encodes, encrypts, and decrypts messages, using RSA.
 */

import java.util.*;

public class Crypto
{	
	// _E and _n comprise the public key, and are therefore okay to store in plaintext.
  private static final int _E = 9500003;
	private static final int _n = 13267421;
	private int _privateKey;
	
	/**
	 * No default values need to be set, so this is simply an empty constructor.
	 */
	public Crypto ()
	{
	}
	
	/**
	 * Sets the private key. Only one actually works, of course.
	 * @param key holds the private key value
	 */
	public void setPrivateKey(int key)
	{
		_privateKey = key;
	}
	
	/**
	 * Determines if the user has set the private key yet because it is tedious to keep resetting the same private key. If the value is not 0, it has been set,
	 * that's how the logic of this works.
	 * @return true if they key has been set (does not equal 0), false otherwise
	 */
	public boolean hasSetKey()
	{
		return _privateKey != 0;
	}
	
	/**
	 * Converts a string to an array of its ASCII values.
	 * @param toEncode string to convert
	 * @return decimal ASCII value array list of each character of the given string
	 */
	private ArrayList<Integer> encode(String toEncode)
	{	
		// Array list to store the decimal ASCII values
		ArrayList<Integer> encoded = new ArrayList<Integer>();
		
		// Loops through given string and sets each character to it's ASCII equivalent, which is easily done in java, simply
		// cast a char to an int.
		for (int i = 0; i < toEncode.length(); i++)
		{
			char character = toEncode.charAt(i);
			int ascii = (int) character;
			
			// adds ASCII values to 'encoded' array list
			encoded.add(ascii);
		}
		
		return encoded;
	}

	/**
	 * Fast modular exponentiation algorithm
	 * @param value value to be exponentiated
	 * @param power power to raise base to
	 * @return results of value ^ power (mod _n)
	 */
	private long modExpoAlgo(long value, long power)
	{
		long a = 1;
		long f = value;

		while (power > 0)
		{
			if (power % 2 == 1)
			{
				a = (a * f) % _n;
			}
			
			f = (f * f) % _n;
			power /= 2;
		}
		
		return a % _n;
	}
	
	/**
	 * Encodes and encrypts what the user requests
	 * @param request encryption request
	 * @return encrypted form of request
	 */
	public String encrypt(String request)
	{
		// Encodes request. Turns string to sequence of ASCII integers.
		ArrayList<Integer> encoded = encode(request);
		
		ArrayList<Long> encryptedString = new ArrayList<Long>();
		
		// Encrypts each ASCII character equivalent by raising it to _E, the encrypting power.
		for (int i = 0; i < encoded.size(); i++)
		{
			long encryptChar = modExpoAlgo(encoded.get(i), _E);
			encryptedString.add(encryptChar);	
		}
		
		String output = encryptedToString(encryptedString); // Makes output cleaner / more functional
		
		return output;
	}
	
	/**
	 * Decrypts what the user requests
	 * @param request decryption request
	 * @return decrypted form of request
	 */
	public String decrypt(ArrayList<Long> request)
	{		
		ArrayList<Long> decryptedASCIIString = new ArrayList<Long>(); // Stores ASCII equivalent of encrypted values
		ArrayList<Character> decryptedString = new ArrayList<Character>(); // Stores character equivalent of ASCII values
		
		// Gets back the ascii value for each encrypted value
		for (int i = 0; i < request.size(); i++)
		{
			long decryptChar = modExpoAlgo(request.get(i), _privateKey);
			decryptedASCIIString.add(decryptChar);
		}
		
		// Turns each long value in decryptedASCIIString into an int, then gets the ASCII char equivalent
		// and finally adds that to the ArrayList of decryptedString
		for (int i = 0; i < decryptedASCIIString.size(); i++)
		{
			int ASCII = decryptedASCIIString.get(i).intValue();
			char theChar = (char)ASCII;
			decryptedString.add(theChar);
		}
		
		String output = decryptedToString(decryptedString); // Makes output cleaner / more functional.
		
		return output;
	}
	
	/**
	 * Formats encrypted output as desired by me
	 * @param encrypted unformatted array to format
	 * @return formatted encryption
	 */
	private String encryptedToString(ArrayList<Long> encrypted)
	{
		String output = ""; // Used to store the cleaner / more functional output
		
		// Formats output as x,x,x,
		for (int i = 0; i < encrypted.size(); i++)
		{
			output += encrypted.get(i);
			output += ",";
		}
		
		output += ","; // adds extra comma to satisfy decryption input requirements
		return output;
	}
	
	/**
	 * Formats decrypted output as desired by me
	 * @param decrypted unformatted array to format
	 * @return formatted decryption
	 */
	private String decryptedToString(ArrayList<Character> decrypted)
	{
		String output = ""; // Used to store the cleaner / more functional output
		
		for (int i = 0; i < decrypted.size(); i++)
		{
			output += decrypted.get(i);
		}
		
		return output;
	}

}
