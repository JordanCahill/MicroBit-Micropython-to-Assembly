
package sccassemblygenerator;

import static java.lang.Math.pow;

/**
 * Contains method to support the Pause() JavaScript method and the Sleep() Python method
 * Sets up the Timer ISR too run for a user-defined length of time based on the method parameters
 * outputVals[0] = TMRH, outputVals[1] = TMRL
 * Assumes the SCC uses a clk frequency of 25 MHz
 * 
 * @author Jordan Cahill
 * @date 08-Feb-2018
 */
class Sleep {
    
    private String[] outputVals = new String[2];
    private final String inputLine;
    private final boolean py; // If true, user is parsing Python, else JavaScript

    // Constructor
    Sleep(String line, boolean py) {
        this.inputLine = line;
        this.py = py;
        
        outputVals = sleep(inputLine);
        }

    // Returns the output assembly commands
    String[] getOutputVals() {
        return outputVals;
    }

    /**
     * Creates an array of assembly commands to set up the timer ISR by adding appropriate
     * values to TMRH and TMRL, and enabling the relevant interrupts
     * 
     * @param line The command to parse
     * @return Assembly commands, index 0 = TMRH, index 1 = TMRL
     */
    private String[] sleep(String line) {
   
        // Delimit the input string to its parameters
        Formatter delim = new Formatter(line);
        String[] dlmtd = delim.delimit();
        
        // Java implementation of "10^-9"
        double powerOfMinus9 = pow(10,-9);
        
        String timeStr = dlmtd[0]; // Length of time to sleep for in seconds
        double time = Double.valueOf(timeStr); // Parse as a double

        if(!py){ // JavaScript pause() uses ms as a parameter
            time = time / 1000.0;
        }
        
        // Calculate number of cycles required based on a 25 MHz clk cycle
        int cycles = (int) (time/(80*(powerOfMinus9)));
        
        String command = "; Unknown problem converting sleep() function"; // Default string

        // Convert the number of cycles to a binary number
        String binVal = Integer.toBinaryString(cycles);

        // Original Strings will be the wrong way round so must be flipped later
        String TMRLFlipped = "";
        String TMRHFlipped = "";

        // 16 least significant bits to be stored in TMRL
        // Rest to be stored in TMRH
        for(int i=binVal.length()-1;i>=0;i--){
            if (i>(binVal.length()-17)){
                TMRLFlipped += binVal.charAt(i);
            } else {
                TMRHFlipped += binVal.charAt(i);
            }
        }
        
        // Strings to store values after flipping
        String TMRL = "";
        String TMRH = "";
        
        // Flip the strings to the right way round 
        for(int i=TMRLFlipped.length()-1;i>=0;i--){
            TMRL += TMRLFlipped.charAt(i);
        }
        for(int i=TMRHFlipped.length()-1;i>=0;i--){
            TMRH += TMRHFlipped.charAt(i);
        }
                
        outputVals[0] = TMRH;
        outputVals[1] = TMRL;
        
        return outputVals;
    }
}
