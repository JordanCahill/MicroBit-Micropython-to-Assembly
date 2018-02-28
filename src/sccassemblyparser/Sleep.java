
package sccassemblyparser;

import static java.lang.Math.pow;

/**
 * Assumes clk frequency of 25 MHz
 * 
 * @author Jordan Cahill
 * @date 08-Feb-2018
 */
class Sleep {
    
    private String[] outputVals = new String[2];
    private String inputLine;

    Sleep(String line) {
        this.inputLine = line;
        
        outputVals = sleep(inputLine);
        }

    String[] getOutputVals() {
        return outputVals;
    }

    private String[] sleep(String line) {
        
        // Delimit the input string
        Formatter delim = new Formatter(line);
        String[] dlmtd = delim.delimit();
        
        // No "^" operator in Java
        double powerOfMinus9 = pow(10,-9);
        
        String timeStr = dlmtd[0]; // Sleep value in seconds
        
        // Calculate number of cycles required based on a 25 MHz clk cycle
        int cycles = (int) ((Double.valueOf(timeStr))/(80*(powerOfMinus9)));
        
        // Default string
        String command = "; Unknown problem converting sleep() function";

        // Convert the number of cycles to a binary number
        String binVal = Integer.toBinaryString(Integer.valueOf(cycles));


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
