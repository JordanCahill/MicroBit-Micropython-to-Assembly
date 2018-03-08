
package sccassemblyparser;

import java.util.ArrayList;

/**
 * Constructor used to call methods that can convert LED commands for the Micro:bit to suitable SCC commads
 * Assumes the SCC is using R0 as a 4x4 16-bit LED array in the following layout:
 * 
 * 15 | 14 | 13 | 12
 * 11 | 10 | 9  | 8 
 * 7  | 6  | 5  | 4
 * 3  | 2  | 1  | 0
 * 
 * where bit 15 corresponds to coordinates (0,0) and bit 12 corresponds to (3,0) etc. 
 * 
 * For Python case, an intensity of 0-4 turns the LED off, 5-9 turns the LED on
 * 
 * @author Jordan Cahill
 * @date 07-Feb-2018
 */
public final class LED {
    
    private final String inputLine;
    private String outputLine;
    
    /**
     * LED object constructor
     * 
     * @param in Line to be parsed
     */
    public LED(String in){
        this.inputLine = in;
        
        if (inputLine.contains("set_pixel")||inputLine.contains("plot")){
            outputLine = toggleLED(inputLine); // Toggle LED for JavaScript or Python commands
        }else if (inputLine.contains("clear")){
            clearPixels(inputLine); // Clear LEDs
        }
    }
    
    /**
     * Function used to switch a pixel on or off
     *  
     * @param line Python or JavaScript command
     * @return output Assembly command
     */
    String toggleLED(String line) {
  
        Formatter delim = new Formatter(line);
        String[] coordinates = delim.delimit(); // Delimit the command into the LED coordinate to toggle
        String command = "; Unknown problem converting set_pixel() function"; // Default assignment
               
        String x = coordinates[0];
        String y = coordinates[1];
        int intensity = 100; // Set high to account for JavaScript case with no intensity
        
        if(line.contains("display")){ // Python case - get intensity (between 0-9)
            intensity = Integer.valueOf(coordinates[2]);
        }
        
        // Coordinates must be between (0,0) and (3,3)
        if (Integer.parseInt(x)>3 || Integer.parseInt(y)>3){
            command = "; Coordinates of set_pixel() too high";
        }
        
        // If intensity between 0 and 4, or JavaScript unplot() command, turn LED off
        if ((line.contains("unplot")||(intensity<5) && (intensity>=0))){
            switch (y){ // Switch block with case for each coordinate
                case "0":
                    switch (x){
                        case "3":
                            command = "CLRBR R0, 12 ; Turn off LED (3,0)";
                            break;
                        case "2":
                            command = "CLRBR R0, 13 ; Turn off LED (2,0)";
                            break;
                        case "1":
                            command = "CLRBR R0, 14 ; Turn off LED (1,0)";
                            break;
                        case "0":
                            command = "CLRBR R0, 15 ; Turn off LED (0,0)";
                            break;
                    } break; 
                case "1":
                    switch (x){
                        case "3":
                            command = "CLRBR R0, 8 ; Turn off LED (3,1)";
                            break;
                        case "2":
                            command = "CLRBR R0, 9 ; Turn off LED (2,1)";
                            break;
                        case "1":
                            command = "CLRBR R0, 10 ; Turn off LED (1,1)";
                            break;
                        case "0":
                            command = "CLRBR R0, 11 ; Turn off LED (0,1)";
                            break;
                    } break;
                case "2":
                    switch (x){
                        case "3":
                            command = "CLRBR R0, 4 ; Turn off LED (3,2)";
                            break;
                        case "2":
                            command = "CLRBR R0, 5 ; Turn off LED (2,2)";
                            break;
                        case "1":
                            command = "CLRBR R0, 6 ; Turn off LED (1,2)";
                            break;
                        case "0":
                            command = "CLRBR R0, 7 ; Turn off LED (0,2)";
                            break;
                    } break;
                case "3":
                    switch (x){
                        case "3":
                            command = "CLRBR R0, 0 ; Turn off LED (3,3)";
                            break;
                        case "2":
                            command = "CLRBR R0, 1 ; Turn off LED (2,3)";
                            break;
                        case "1":
                            command = "CLRBR R0, 2 ; Turn off LED (1,3)";
                            break;
                        case "0":
                            command = "CLRBR R0, 3 ; Turn off LED (0,3)";
                            break;
                    } break;
            } 
        // If intensity between 5 and 9, or JavaScript command plot(), turn LED on    
        }else if ((line.contains("plot")||((intensity>=5) && (intensity<10)))){
            switch (y){ // Switch block with case for each coordinate
                case "0":
                    switch (x){
                        case "3":
                            command = "SETBR R0, 12 ; Turn on LED (3,0)";
                            break;
                        case "2":
                            command = "SETBR R0, 13 ; Turn on LED (2,0)";
                            break;
                        case "1":
                            command = "SETBR R0, 14 ; Turn on LED (1,0)";
                            break;
                        case "0":
                            command = "SETBR R0, 15 ; Turn on LED (0,0)";
                            break;
                    } break; 
                case "1":
                    switch (x){
                        case "3":
                            command = "SETBR R0, 8 ; Turn on LED (3,1)";
                            break;
                        case "2":
                            command = "SETBR R0, 9 ; Turn on LED (2,1)";
                            break;
                        case "1":
                            command = "SETBR R0, 10 ; Turn on LED (1,1)";
                            break;
                        case "0":
                            command = "SETBR R0, 11 ; Turn on LED (0,1)";
                            break;
                    } break;
                case "2":
                    switch (x){
                        case "3":
                            command = "SETBR R0, 4 ; Turn on LED (3,2)";
                            break;
                        case "2":
                            command = "SETBR R0, 5 ; Turn on LED (2,2)";
                            break;
                        case "1":
                            command = "SETBR R0, 6 ; Turn on LED (1,2)";
                            break;
                        case "0":
                            command = "SETBR R0, 7 ; Turn on LED (0,2)";
                            break;
                    } break;
                case "3":
                    switch (x){
                        case "3":
                            command = "SETBR R0, 0 ; Turn on LED (3,3)";
                            break;
                        case "2":
                            command = "SETBR R0, 1 ; Turn on LED (2,3)";
                            break;
                        case "1":
                            command = "SETBR R0, 2 ; Turn on LED (1,3)";
                            break;
                        case "0":
                            command = "SETBR R0, 3 ; Turn on LED (0,3)";
                            break;
                    } break;
            }   
        }else{command = "; Error parsing command: " + line;}
        return command;
    }
    
    /**
     * Parses a Python show() command into a number of assembly SETBR commands to
     * display a hard-coded image on the LED display
     * 
     * @return ArrayList of SETBR assembly commands
     */
    ArrayList<String> displayImage(){
        Formatter delim = new Formatter(inputLine); // delimit the input command to its parameter
        String[] args = delim.delimit(); String image = args[0]; // Obtain the name of the image to be displayed
        ArrayList<String> regSet = new ArrayList<>(); // Store the SETBR commands in an ArrayList
        
        switch (image){ // Contains a case for each available image
            case "Image.ARROW_NE":
                regSet.add("XOR R0,R0,R0 ; Displaying image: " + image);
                regSet.add("SETBR R0, 3"); regSet.add("SETBR R0, 4"); regSet.add("SETBR R0, 6");
                regSet.add("SETBR R0, 8"); regSet.add("SETBR R0, 9"); regSet.add("SETBR R0, 12");
                regSet.add("SETBR R0, 13"); regSet.add("SETBR R0, 14 ; End of image: "+ image);
                break;
            case "Image.ARROW_NW":
                regSet.add("XOR R0,R0,R0 ; Displaying image: " + image);
                regSet.add("SETBR R0, 0"); regSet.add("SETBR R0, 5"); regSet.add("SETBR R0, 7");
                regSet.add("SETBR R0, 10"); regSet.add("SETBR R0, 11"); regSet.add("SETBR R0, 13");
                regSet.add("SETBR R0, 14"); regSet.add("SETBR R0, 15 ; End of image: "+ image);
                break;
            default: // Default case for unknown parameter
                regSet.add("XOR R0,R0,R0 ; Unknown image");
                regSet.add("INV R0, R0 ; Displaying default image");
                break;
        }
        return regSet;
    }

    // Returns the outputLine stored in the current instantiation
    String getOutputLine() {
        return outputLine;
    }

    // Clears the LED display by "XORing" the register with itself
    private void clearPixels(String inputLine) {
        outputLine = "XOR R0, R0, R0 ; Clear LED display";
    }

}
