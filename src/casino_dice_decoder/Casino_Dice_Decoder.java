/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casino_dice_decoder;


/**
 *
 * @author justintumale
 */
public class Casino_Dice_Decoder {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("------------DICE ROLLER------------");
        diceRoller a = new diceRoller();
        a.initializeStates();    
        System.out.println();
        System.out.println();
        System.out.println();
        
        
        System.out.println("------------DICE DECODER------------");
        CasinoDiceDecoder b = new CasinoDiceDecoder();
        b.useFiles();
        System.out.println();
        
        System.out.println("--------------ANALYSIS--------------");
        Analysis c = new Analysis();
        c.analyze();
        System.out.println();
     
    }
    

    
}
