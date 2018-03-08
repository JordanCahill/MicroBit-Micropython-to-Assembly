
package sccassemblyparser;

import java.util.ArrayList;

/**
 * Class used to set up the SCC's timer ISR 
 *
 * @author Jordan Cahill
 * @date 28-Feb-2018
 */
public class Timer {
    
    public Timer(){} // Default constructor for method calls    

    /**
     * This method is used to set up the SCC's timer WITHOUT interrupts
     * 
     * @param TmrVals Values to pass to TMRH and TMRL
     * @return ArrayList of assembly commands
     */
    ArrayList<String> setUpTimerNoReload(String[] TmrVals) { 
        
        ArrayList<String> setUpText = new ArrayList<>(); // Store asm commands     
        String left = TmrVals[0];
        String right = TmrVals[1];
         
        setUpText.add("settingUpTimer:");
        setUpText.add("XOR R1,R1,R1 ; Clear R1");
         
        /**** TMRL ****/
        int j = left.length()-1; // Index of the bit in TMRH to be set 
        // This loop searches for each asserted bit in "left" and asserts the respective bit in R1
        for(int i = 0; i<left.length(); i++){
            if ((Character.getNumericValue(left.charAt(i))) == 1){ // If bit is asserted
                setUpText.add("SETBR R1, " + j); // Assert TMRL(j)
            }
            j--;
        } // R1 is then moved to SFR1 (TMRL)
        setUpText.add("MOVRSFR SFR1, R1 ; Passing the value in R1 to TMRL");
        
        setUpText.add("XOR R1,R1,R1 ; Clear R1");
        
        /**** TMRH ****/
        j = right.length()-1; // Index of the bit in TMRH to be set
        // This loop searches for each asserted bit in "right" and asserts the respective bit in R1
        for(int i = 0; i<right.length(); i++){
            if ((Character.getNumericValue(right.charAt(i))) == 1){ // If bit is asserted
                setUpText.add("SETBR R1, " + j); // Assert TMRH(j)
            }
            j--;
        }
        setUpText.add("MOVRSFR SFR2, R1 ; Passing the value in R1 to TMRH");

        // Set up global flags
        setUpText.add("SETBSFR SFR0, 0 ; enable global interrupts");
        setUpText.add("SETBSFR SFR0, 3 ; enable timer interrupt");
        setUpText.add("SETBSFR SFR0, 6 ; down timer");
        setUpText.add("SETBSFR SFR0, 4 ; enable timer. Include as last bit set");
        setUpText.add("RET"); 
        
        return setUpText;
    }
}
