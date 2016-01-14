/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casino_dice_decoder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author justintumale
 */
public class CasinoDiceDecoder {
    
    public static StringBuilder stringBuilder1 = new StringBuilder();
    static String finalString = "";
    
    public static double computeProbability( int previousState, int currentState, int roll, double probPrev){
        int beginning = 0;
        int fair = 1;
        int loaded = 2;
        double value;
        
        
        if (previousState == 0){
            if (currentState == 1){
                value =  .8 * 1.0/6.0;
                //value =  .5 * 1.0/6.0;
                return value;
            }
            else if (currentState == 2){
                if (roll == 6){
                    value =  .2 * .5;
                    //value =  .5 * .5;
                    return value;
                }
                else 
                {   value = (.2 * .1);
                    //value = (.5 * .1);
                    return value;
                }
            }
        }
        
        if (previousState == 1){
            
            if (currentState == 1){
                value = .5 * 1.0/6.0 * probPrev;
                //value = .95 * 1.0/6.0 * probPrev;
                return value;
            }
            else if (currentState == 2){
                if (roll == 6){
                value = .5 * .5 * probPrev;
                //value = .05 * .5 * probPrev;
                return value;
                }
                else return .5 * .10 * probPrev;
                //.05 * .10 * probPrev;
            }
        }//previousState == fair
        
        if (previousState == 2){
            if (currentState == 1){
                value = .3 * 1/6 * probPrev;
                //value = .1 * 1/6 * probPrev;
                
                return value;
            }
            else if (currentState == 2){
                if (roll == 6){
                    value =  .7 * .5 * probPrev;
                    //value =  .9 * .5 * probPrev;
                    return value;
                }
                else {
                    value = .7 * .1  * probPrev;
                    //value = .9 * .1  * probPrev;
                    return value;
                }
            }
        }
   
        return 0;
    }
    
    public static String decoder(String s){
        
        //System.out.println("Sequence: " + s + "\n");
      
        
        double[][] table = new double[2][s.length()];
        
        for (int i = 0; i < s.length(); i++){
            
            for (int k = 0; k <2; k++){
            //table[k][i] = String.valueOf(s.charAt(i));
            int charInt = Character.getNumericValue(s.charAt(i));

                //B Stage to X1
                if ( i == 0 && k == 0){
                    table[k][i] = computeProbability(0, 1, charInt, 1);
                 }// if ( i == 0 && k == 0)
                if ( i == 0 && k == 1){
                    table[k][i] = computeProbability(0, 2, charInt, 1);
                }//if ( i == 0 && k == 1)  
                
                if (i != 0 && k == 0){
                    table[k][i] = Math.max( computeProbability(1, 1, charInt, table[k][i-1]), computeProbability(2, 1, charInt, table[k+1][i-1]));
                }
                if (i != 0 && k == 1){
                    table[k][i] = Math.max( computeProbability(1, 2, charInt, table[k-1][i-1]), computeProbability(2, 2, charInt,table[k][i-1]));
                }
    
            }//for (int k = 0; k <2; k++)
        }//for (int i = 0; i < s.length(); i++)
       
        
//        System.out.println("Probability Table: ");
//        for (int i = 0; i < 2; i++){
//            for (int j = 0; j < s.length(); j++){
//                System.out.print(table[i][j] + "  |");
//            }
//            System.out.println();
//        }
        
        
        //System.out.println();
        System.out.println("Predicted sequence of states: ");
        
        for (int i = 0; i < 2; i++){
           for (int j = 0; j < s.length(); j++){
               if (i == 0){
                   if (table[i][j] > table[i+1][j]){
                       System.out.print(s.charAt(j) + " F | ");
                       stringBuilder1.append("F");
                   }
                   else {
                       System.out.print(s.charAt(j) + " L |");
                        stringBuilder1.append("L");
                   }
               }
           }//for (int j = 0; j < s.length(); j++)
           System.out.println();
        }//for (int i = 0; i < 2; i++)
  
        return s;
    }

    public static void useFiles(){
        String s = "";
        try {
            //"/Users/justintumale/Desktop/Java/DiceRoller/NumberSequence.txt"
            s = new Scanner(new File("NumberSequence.txt")).next();
        } catch (FileNotFoundException ex) {
        }
      
        
        decoder(s);
        
        
        //Create text file
        finalString = finalString + stringBuilder1.toString();
        //"/Users/justintumale/Desktop/Java/DiceRoller/SequenceStatesTest.txt"
        File file1 = new File("SequenceStatesTest.txt");
        if (!file1.exists()) // if file doesn't exists, then create it
	{
            try {
                file1.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(CasinoDiceDecoder.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
	FileWriter fw1 = null;
        try {
            fw1 = new FileWriter(file1.getAbsoluteFile());
        } catch (IOException ex) {
            Logger.getLogger(CasinoDiceDecoder.class.getName()).log(Level.SEVERE, null, ex);
        }
	BufferedWriter br1 = new BufferedWriter(fw1);
        try {
            br1.write(finalString);
        } catch (IOException ex) {
            Logger.getLogger(CasinoDiceDecoder.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            br1.close();
        } catch (IOException ex) {
            Logger.getLogger(CasinoDiceDecoder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
