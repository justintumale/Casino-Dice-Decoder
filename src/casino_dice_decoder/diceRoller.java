/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casino_dice_decoder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author justintumale
 */
public class diceRoller {
    
        public int faceLoaded;              // holds the face of fair dice
	public  int faceFair;                  // holds the face of loaded dice
	public int n;                      // number of states to be generated
	public int numberSequence[];              // stores the sequence of numbers generated
        
        
                public void initializeStates(){
            	diceRoller d = new diceRoller();
                
		int k = 0;
		final Float B[] = new Float[9];       // B[] holds the data of text file
		String finalString = "";
		String finalString2 = "";
                
                //Assign the probabilities of moving from one state to another
		try (BufferedReader br = new BufferedReader(new FileReader("States.txt"))) 
		{
			String line;
			while ((line = br.readLine()) != null) 
			{
				String[] values = line.split("=");
				for (int j = 1; j < values.length; j++) 
				{
					B[k++] = new Float(values[j]);
				}
			}
			Scanner in = new Scanner(System.in);
			System.out.print("Enter the number of dice rolls: ");
			n = in.nextInt();
			//System.out.println("You entered: " + n);   // number of states to be produced by the model
			String[] States = new String[n];    // States contains the sequence of states generated randomly
			Random generator = new Random();
			numberSequence = new int[n];
			int value = generator.nextInt(100);
			if (value < (B[0] * 100)) 
			{
				States[0] = "F";
				numberSequence[0] = roll("F", B);
				//System.out.println("Throw : " + 1 + " is on fair dice");
			} 
			else
			{
				States[0] = "L";
				numberSequence[0] = roll("L", B);
				//System.out.println("Throw : " + 1 + " is on Loaded dice");
			}
			for (int i = 1; i < n; i++) 
			{
				Random generator2 = new Random();
				if (States[i - 1] == "F") 
				{
					int x = generator2.nextInt(100);
					if (x < B[2] * 100) 
					{
						States[i] = "F";
						//System.out.println("Throw : " + (i + 1)+ " is on fair dice");
						numberSequence[i] = roll("F", B);
					} 
					else 
					{
						States[i] = "L";
						//System.out.println("Throw : " + (i + 1)+ " is on loaded dice");
						numberSequence[i] = roll("L", B);
					}
				} 
				else 
				{
					int y = generator2.nextInt(100);
					if (y < B[4] * 100)
					{
						States[i] = "L";
						//System.out.println("Throw : " + (i + 1)+ " is on Loaded dice");
						numberSequence[i] = roll("L", B);
					} 
					else
					{
						States[i] = "F";
						//System.out.println("Throw : " + (i + 1)+ " is on fair dice");
						numberSequence[i] = roll("F", B);
					}
				}//else
			}//for
                        System.out.println("Sequence of states: ");
			for (int i = 0; i < n; i++) 
			{
				//System.out.print(States[i]);
				System.out.print(numberSequence[i] + " "+ States[i] + " | ");
				StringBuilder stringBuilder1 = new StringBuilder();
				StringBuilder stringBuilder2 = new StringBuilder();
				String loopDelim = "";
				stringBuilder1.append(numberSequence[i]);
				stringBuilder2.append(States[i]);
				stringBuilder2.append(loopDelim);				
				finalString = finalString + stringBuilder1.toString();
				finalString2 = finalString2 + stringBuilder2.toString();
			}
			
			File file1 = new File("NumberSequence.txt");
			File file2 = new File("SequenceStates.txt");
			
			if (!file1.exists()) // if file doesn't exists, then create it
			{
				file1.createNewFile();
			}
			FileWriter fw1 = new FileWriter(file1.getAbsoluteFile());
			BufferedWriter br1 = new BufferedWriter(fw1);
			br1.write(finalString);
			br1.close();
			
			if (!file2.exists()) 
			{
				file2.createNewFile();
			}
			FileWriter fw2 = new FileWriter(file2.getAbsoluteFile());
			BufferedWriter br2 = new BufferedWriter(fw2);
			br2.write(finalString2);
			br2.close();
		}//try
		catch (Exception e) 
		{
			e.printStackTrace();
		}
        }//initializeStates
        
        	public  int roll(String dice, Float B[]) // roll method is for generating the number on the selected dice
													 
	{
		Random generator1 = new Random();
		if (dice == "F") 
		{
			faceFair = generator1.nextInt(6) + 1;
			return faceFair;
		} 
		else 
		{
			int y = generator1.nextInt(100);
			if (y < B[8] * 100) 
			{
				faceLoaded = 6;
			} 
			else 
			{
				faceLoaded = generator1.nextInt(5) + 1;
			}
			return faceLoaded;
		}//else         
	} // roll        
        
    
}
