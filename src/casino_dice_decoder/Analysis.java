/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casino_dice_decoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author justintumale
 */
public class Analysis {
 public static void analyze(){
             String ogSequence = "";
        try {
            ogSequence = new Scanner(new File("SequenceStates.txt")).next();
        } catch (FileNotFoundException ex) {
        }
        
        String predictedSequence = "";
        try {
            predictedSequence = new Scanner(new File("SequenceStatesTest.txt")).next();
        } catch (FileNotFoundException ex) {
        }
        
        int [] error = new int [predictedSequence.length()];
        double correct = 0;
        
        for (int i = 0; i < predictedSequence.length(); i++){
            if (predictedSequence.charAt(i) == ogSequence.charAt(i)){
                error[i] = 1;
                correct++;
            }
            else error[i] = 0;
        }
        
        System.out.println("sequence length: " + error.length);
//        for (int i = 0; i < error.length; i++){
//            System.out.print(error[i]);
//        }
        //System.out.println();
        System.out.println("correct: " +  correct / error.length );
        double errorPercentage = 1 - correct / error.length;
        System.out.println("errors: " + errorPercentage );
        
 }   
}
