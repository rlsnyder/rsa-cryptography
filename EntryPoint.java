/**
 * @author R. Logan Snyder
 * <p> EntryPoint
 * <p> User inputs strings of text, they get encrypted or decrypted, depending on what the user wants.
 */

import java.util.*;

public class EntryPoint
{

	public static void main(String[] args)
	{
		// Initializing objects: Scanner and Crypto classes.
		Scanner in = new Scanner(System.in);
		Crypto crypter = new Crypto(); // Can't think of better name. Encrypts and decrypts, so I'm just calling it crypter.
		
		String choice; // Used to store response to encrypt / decrypt prompt.
		String request; // used to store encryption request
		boolean done = false; //Used to determine if user is done with requests.
		
		while (!done)
		{
			System.out.print("Encrypt / Decrypt: ");
			choice = in.nextLine();
			
			if (choice.equalsIgnoreCase("Encrypt"))
			{
				System.out.print("Encryption request: ");
				request = in.nextLine();
				
				// The following three lines store the encryption request in an array list of long values, tell the user the encrypted
				// request is below, and print it out
				String encrypted = crypter.encrypt(request);
				
				System.out.println(); // Just adds some spacing
				System.out.println("Your request is in encrypted form below.");
				System.out.println(encrypted);
				
				boolean valid = false; // Used to determine if response to more requests is valid
				
				while (!valid)
				{
					System.out.println(); // Just adds some spacing
					System.out.print("Have more to encrypt / decrypt? (Y / N): ");
					
          				// Scanner has no nextChar() method, so I just use nextLine() method then store the first char of the nextLine.
					char moreResponse = in.nextLine().charAt(0);
					moreResponse = Character.toUpperCase(moreResponse); // Makes response uppercase so I don't have to worry about case.
					
					if (moreResponse == 'Y')
					{
						// By setting 'valid' to true, this while loop is exited, but since 'done' is still false, it starts at the top of the
						// while loop this one is nested in, with the first thing happening being a prompt for if the user wants to
						// encrypt / decrypt
						valid = true;
					}
					else if (moreResponse == 'N')
					{
						// sets both while loop control variables to true, exiting both loops and terminating the program.
						valid = true;
						done = true;
					}
					else
					{
						System.out.println("Invalid response");
					}
				}
				
			}
			else if (choice.equalsIgnoreCase("Decrypt"))
			{
				// Decryption input has to have a special scanner, as a comma needs to be a delimiter in decrypting, but not encrypting.
				Scanner decryptInput = new Scanner(System.in);
				decryptInput.useDelimiter(",");
				
				// This runs if a private key has not been set yet
				if (!crypter.hasSetKey())
				{	
					System.out.print("Private Key: ");
					crypter.setPrivateKey(in.nextInt()); // Calls mutator method in Crypto object, setting the private key. Only one works of course.
					
					System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"); // Gets private key off of screen
				}
				
				// Stores all the numbers to be decrypted as letters.
				ArrayList<Long> decryptVals = new ArrayList<Long>();
				
				// Just adds spacing. If the key hasn't been set, it will add YET ANOTHER new line (in addition to the ones that get the key off screen, line 83)
				// If it has not been set, however, this makes the output a little more readable.
				System.out.println();
				
				// Use of the delimter in Scanner requires these odd input format requirements.
        			System.out.println("Below, there can be no spaces, and two commas must be added to end of input.");
				System.out.print("Decryption request: ");
				
				// There's probably a spiffier way to do this, but I simply store the first value, then use a loop to store all the rest in
				// decryptVals, which is an array list initialized above.
				long firstVal = decryptInput.nextLong();
				decryptVals.add(firstVal);
				
				while (decryptInput.hasNextLong())
				{
					decryptVals.add(decryptInput.nextLong());
				}
				
				// Creates an array list of characters to store the decrypted output, which is created by calling the decrypt() method here
				// and passing it the decryptVals, which has been populated above
				String decrypted = crypter.decrypt(decryptVals);
				
				System.out.println(); // Just adds some spacing
				System.out.println("Your request is in decrypted form below.");
				System.out.println(decrypted);
				
				boolean valid = false; // Used to determine if response to more requests is valid
				
				while (!valid)
				{
					System.out.println(); // Just adds some spacing
					System.out.print("Have more to encrypt / decrypt? (Y / N): ");
					char moreResponse = in.next().charAt(0);
					moreResponse = Character.toUpperCase(moreResponse); // Makes response uppercase so I don't have to worry about case.
					
					if (moreResponse == 'Y')
					{
						// By setting 'valid' to true, this while loop is exited, but since 'done' is still false, it starts at the top of the
						// while loop this one is nested in, with the first thing happening being a prompt for if the user wants to
						// encrypt / decrypt
						valid = true;
					}
					else if (moreResponse == 'N')
					{
						// Close both scanners to avoid a resource leak.
						in.close();
						decryptInput.close();
						
						// sets both while loop control variables to true, exiting both loops and terminating the program.
						valid = true;
						done = true;
					}
					else
					{
						System.out.println("Invalid response");
					}
				}
			}
			else
			{
				System.out.println("Invalid request");
			}
		}
		
		// Bids the user adieu.
		System.out.println(); // Just adds spacing
		System.out.println("Goodbye.");
	}

}
