
package pythontoassembly;

import java.util.ArrayList;

/**
 *
 * @author Jordan Cahill
 * @date 28-Feb-2018
 */
public class Timer {
    
    public Timer(){ // Default constructor for method calls
        
    }

    ArrayList<String> setUpTimerNoReload(String[] TmrVals) {
        
        
        // Add boolean parameter for "if reload or not" 
        
        ArrayList<String> setUpText = new ArrayList<>();      
        String left = TmrVals[0];
        String right = TmrVals[1];
         
        setUpText.add("settingUpTimer:");
        setUpText.add("XOR R1,R1,R1");
         
        int j = left.length()-1; // Index used to set bit
        for(int i = 0; i<left.length(); i++){
            if ((Character.getNumericValue(left.charAt(i))) == 1){
                setUpText.add("SETBR R1, " + j);
            }
            j--;
        }

        setUpText.add("MOVRSFR SFR1, R1");
        setUpText.add("XOR R1,R1,R1");

        j = right.length()-1;
        for(int i = 0; i<right.length(); i++){
            if ((Character.getNumericValue(right.charAt(i))) == 1){
                setUpText.add("SETBR R1, " + j);
            }
            j--;
        }

        // Set up global flags
        setUpText.add("SETBSFR SFR0, 0 ; enable global interrupts");
        setUpText.add("SETBSFR SFR0, 3 ; enable timer interrupt");
        setUpText.add("SETBSFR SFR0, 6 ; down timer");
        setUpText.add("SETBSFR SFR0, 4 ; enable timer. Include as last bit set");
        setUpText.add("RET"); 
        
        return setUpText;
    }

}
