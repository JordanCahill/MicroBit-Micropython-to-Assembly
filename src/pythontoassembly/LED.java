
package pythontoassembly;

import java.util.ArrayList;

/**
 * Constructor used to call methods that can convert micropython LED commands for an SCC
 * Assumes the SCC is using R2 as a 4x4 16-bit LED array in the following format:
 * 
 * 15 | 14 | 13 | 12
 * 11 | 10 | 9  | 8 
 * 7  | 6  | 5  | 4
 * 3  | 2  | 1  | 0
 * 
 * where bit 0 corresponds to coordinates (3,0) and bit 12 corresponds to (3,3) etc. 
 * 
 * @author Jordan Cahill
 * @date 07-Feb-2018
 */
public final class LED {
    
    private final String inputLine;
    private String outputLine;
    
    public LED(String in){
        this.inputLine = in;
        
        if (inputLine.contains("set_pixel")){
            outputLine = setPixel(inputLine);
        }else if (inputLine.contains("clear")){
            clearPixels(inputLine);
        }
    }
    
    /**
     * Function used to switch a pixel on or off
     * NOTE: Assumes the LED is being inverted
     * 
     * @param line input line need for coordinates
     * @return output command
     */
    String setPixel(String line) {
  
        Formatter delim = new Formatter(line);
        String[] coordinates = delim.delimit();
        String command = "; Unknown problem converting set_pixel() function"; // Default assignment
        //String[] coordinates = delimit(line); // Calls the delimit function to retrieve the (x,y) LED coordinate
        
       
        String x = coordinates[0];
        String y = coordinates[1];
        String intensity = coordinates[2];
        intensity = intensity.replace(")","");

        
        // Coordinates must be between (0,0) and (3,3)
        if (Integer.parseInt(x)>3 || Integer.parseInt(y)>3){
            command = "; Coordinates of set_pixel() too high";
        }
        
        // If intensity between 0 and 4, turn LED off
        if (((Integer.valueOf(intensity))<5) && ((Integer.valueOf(intensity))>=0)){
            // Must check 4 x-values for each y-value and return an appropriate command
            switch (y){
                case "0":
                    switch (x){
                        case "3":
                            command = "CLRBR R0, 0";
                            break;
                        case "2":
                            command = "CLRBR R0, 1";
                            break;
                        case "1":
                            command = "CLRBR R0, 2";
                            break;
                        case "0":
                            command = "CLRBR R0, 3";
                            break;
                    } break; 
                case "1":
                    switch (x){
                        case "3":
                            command = "CLRBR R0, 4";
                            break;
                        case "2":
                            command = "CLRBR R0, 5";
                            break;
                        case "1":
                            command = "CLRBR R0, 6";
                            break;
                        case "0":
                            command = "CLRBR R0, 7";
                            break;
                    } break;
                case "2":
                    switch (x){
                        case "3":
                            command = "CLRBR R0, 8";
                            break;
                        case "2":
                            command = "CLRBR R0, 9";
                            break;
                        case "1":
                            command = "CLRBR R0, 10";
                            break;
                        case "0":
                            command = "CLRBR R0, 11";
                            break;
                    } break;
                case "3":
                    switch (x){
                        case "3":
                            command = "CLRBR R0, 12";
                            break;
                        case "2":
                            command = "CLRBR R0, 13";
                            break;
                        case "1":
                            command = "CLRBR R0, 14";
                            break;
                        case "0":
                            command = "CLRBR R0, 15";
                            break;
                    } break;
            } // If intensity between 5 and 9, turn LED on
        }else if (((Integer.valueOf(intensity))>=5) && ((Integer.valueOf(intensity))<10)){
            // Must check 4 x-values for each y-value and return an appropriate command
            switch (y){
                case "0":
                    switch (x){
                        case "3":
                            command = "SETBR R0, 0";
                            break;
                        case "2":
                            command = "SETBR R0, 1";
                            break;
                        case "1":
                            command = "SETBR R0, 2";
                            break;
                        case "0":
                            command = "SETBR R0, 3";
                            break;
                    } break; 
                case "1":
                    switch (x){
                        case "3":
                            command = "SETBR R0, 4";
                            break;
                        case "2":
                            command = "SETBR R0, 5";
                            break;
                        case "1":
                            command = "SETBR R0, 6";
                            break;
                        case "0":
                            command = "SETBR R0, 7";
                            break;
                    } break;
                case "2":
                    switch (x){
                        case "3":
                            command = "SETBR R0, 8";
                            break;
                        case "2":
                            command = "SETBR R0, 9";
                            break;
                        case "1":
                            command = "SETBR R0, 10";
                            break;
                        case "0":
                            command = "SETBR R0, 11";
                            break;
                    } break;
                case "3":
                    switch (x){
                        case "3":
                            command = "SETBR R0, 12";
                            break;
                        case "2":
                            command = "SETBR R0, 13";
                            break;
                        case "1":
                            command = "SETBR R0, 14";
                            break;
                        case "0":
                            command = "SETBR R0, 15";
                            break;
                    } break;
            }   
        }else{command = "; Python syntax error at 'setPixel()', intensity must be an integer between 0 and 9";}
        return command;
    }
    
    ArrayList<String> displayImage(){
        Formatter delim = new Formatter(inputLine);
        String[] image = delim.delimit();
        System.out.println(image[0]);
        return null;
    }

    String getOutputLine() { // Passes the assembly command to the main method when called
        return outputLine;
    }

    // Clears the LED display by "XORing" the register with itself
    private void clearPixels(String inputLine) {
        outputLine = "XOR R2, R2, R2 ; Clear R2";
    }

}
